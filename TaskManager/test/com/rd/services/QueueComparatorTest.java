package com.rd.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rd.services.comparator.DefaultComparator;
import com.rd.services.entity.MyProcess;

/**
 * @author rache
 *
 */
public class QueueComparatorTest {

	static Queue<MyProcess> queue = new PriorityQueue<MyProcess>(10, new DefaultComparator());
	static MyProcess process1 = new MyProcess(UUID.randomUUID(), Thread.MAX_PRIORITY);
	static MyProcess process2 = new MyProcess(UUID.randomUUID(), Thread.MIN_PRIORITY);
	static MyProcess process3 = new MyProcess(UUID.randomUUID(), Thread.NORM_PRIORITY);
	static MyProcess process4 = new MyProcess(UUID.randomUUID(), Thread.NORM_PRIORITY);
	static MyProcess process5 = new MyProcess(UUID.randomUUID(), Thread.MIN_PRIORITY);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void init() throws Exception {
		queue.offer(process1);
		queue.offer(process2);
		queue.offer(process3);
		queue.offer(process4);
		queue.offer(process5);
	}

	@AfterEach
	public void teardown() {
		queue.clear();
	}

	@Test
	public void testQueueSort() {
		// create an immutable list
		Collection<Queue<MyProcess>> list = Collections.nCopies(1, queue);
		Queue<MyProcess> queue = new PriorityQueue<MyProcess>(10, new DefaultComparator());
		Iterator<Queue<MyProcess>> iter = list.iterator();
		while (iter.hasNext()) {
			queue.addAll(iter.next());
		}
		assertNotNull(queue);
		assert (queue.size() == 5);
		int priority = Thread.MAX_PRIORITY;
		while (!queue.isEmpty()) {
			MyProcess process = queue.poll();
			assert (process.getPriority() <= priority);
		}
	}

	@Test
	public void removeLowestAndOldest() {
		Comparator<MyProcess> reverse = Collections.reverseOrder(new DefaultComparator());
		Queue<MyProcess> queue1 = new PriorityQueue<MyProcess>(10, reverse);
		queue1.addAll(queue);
		queue1.poll();
		assert (queue1.size() == 4);
		while (!queue1.isEmpty()) {
			MyProcess process = queue1.poll();
			assertNotNull(process);
		}
	}
}
