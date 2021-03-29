package com.rd.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

import com.rd.services.comparator.CreationTimeComparator;
import com.rd.services.comparator.DefaultComparator;
import com.rd.services.comparator.PriorityComparator;
import com.rd.services.entity.MyProcess;
import com.rd.services.enums.Priority;
import com.rd.services.enums.SortOrder;

public class TaskManagerService {

	private static BlockingQueue<MyProcess> blockingQueue;

	public TaskManagerService(Integer maxCapacity) {
		if (maxCapacity != null) {
			blockingQueue = new LinkedBlockingDeque<>(maxCapacity);
		} else {
			blockingQueue = new LinkedBlockingDeque<>();
		}
	}

	/**
	 * Add a new process to the Task Manager
	 * 
	 * @param pid      An unique ID
	 * @param priority The priority (low, medium, high of the process)
	 * @return True if the new process could be added, otherwise false
	 */
	public boolean addProcess(UUID pid, Priority priority) {
		MyProcess process = new MyProcess(pid, priority);
		return blockingQueue.offer(process);
	}

	/**
	 * Add a new process to the Task Manager. If the Task Manager is full remove the
	 * oldest process.
	 * 
	 * @param pid      An unique ID
	 * @param priority The priority (low, medium, high of the process)
	 * @return True if the new process could be added, otherwise false
	 */
	public boolean addProcessFIFO(UUID pid, Priority priority) {
		MyProcess process = new MyProcess(pid, priority);
		boolean success = blockingQueue.offer(process);
		if (!success) {
			blockingQueue.poll();
			success = blockingQueue.offer(process);
		}
		return success;
	}

	/**
	 * Add a new process to the Task Manager. If the Task Manager is full remove the
	 * oldest lowest priority process.
	 * 
	 * @param pid      An unique ID
	 * @param priority The priority (low, medium, high of the process)
	 * @return True if the new process could be added, otherwise false
	 */
	public boolean addProcessPriorityBased(UUID pid, Priority priority) {
		MyProcess process = new MyProcess(pid, priority);
		boolean success = blockingQueue.offer(process);
		if (!success) {
			Comparator<MyProcess> reverseComparator = Collections.reverseOrder(new DefaultComparator());
			Queue<MyProcess> reverseQueue = new PriorityQueue<MyProcess>(10, reverseComparator);
			reverseQueue.addAll(blockingQueue);
			MyProcess oldestProcess = reverseQueue.peek();
			if (oldestProcess.getPriority().getValue() < priority.getValue()) {
				blockingQueue.remove(reverseQueue.poll());
				success = blockingQueue.offer(process);
			}
		}
		return success;
	}

	/**
	 * 
	 * @param sortOrder The order in which to prepare the list output
	 */
	public Stream<MyProcess> list(SortOrder sortOrder) {
		Queue<MyProcess> orderedQueue = new PriorityQueue<MyProcess>(10, getComparator(sortOrder));
		orderedQueue.addAll(blockingQueue);
		return orderedQueue.stream();
	}

	/**
	 * 
	 * @param sortOrder The order in which to sort the list
	 * @return The comparator to sort the list
	 */
	private Comparator<MyProcess> getComparator(SortOrder sortOrder) {
		switch (sortOrder) {
		case TIMESTAMP:
			return Collections.reverseOrder(new CreationTimeComparator());
		case PRIORITY:
			return Collections.reverseOrder(new PriorityComparator());
		case PID:
			return Collections.reverseOrder(new CreationTimeComparator());
		default:
			return Collections.reverseOrder(new DefaultComparator());
		}
	}

	public Stream<MyProcess> killProcess(UUID pid) {
		Iterator<MyProcess> iter = blockingQueue.iterator();
		while (iter.hasNext()) {
			MyProcess process = iter.next();
			if (process.getPID().equals(pid)) {
				process.kill();
				blockingQueue.remove(process);
			}
		}
		return blockingQueue.stream();
	}

	public Stream<MyProcess> killAll(Priority priority) {
		Iterator<MyProcess> iter = blockingQueue.iterator();
		while (iter.hasNext()) {
			MyProcess process = iter.next();
			if (process.getPriority() == priority) {
				process.kill();
				blockingQueue.remove(process);
			}
		}
		return blockingQueue.stream();
	}

	public Stream<MyProcess> killAll() {
		Iterator<MyProcess> iterator = blockingQueue.iterator();
		iterator.forEachRemaining((process) -> process.kill());
		blockingQueue.clear();
		return blockingQueue.stream();
	}
}
