package com.rd.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.rd.services.entity.MyProcess;
import com.rd.services.enums.SortOrder;

public class TaskManagerServiceTest {

	private static UUID pid1 = UUID.randomUUID();
	private static UUID pid2 = UUID.randomUUID();
	private static UUID pid3 = UUID.randomUUID();
	private static UUID pid4 = UUID.randomUUID();
	private static UUID pid5 = UUID.randomUUID();
	private static UUID pid6 = UUID.randomUUID();
	private static UUID pid7 = UUID.randomUUID();
	private static UUID pid8 = UUID.randomUUID();
	private static UUID pid9 = UUID.randomUUID();
	private static UUID pid10 = UUID.randomUUID();
	private static UUID pid11 = UUID.randomUUID();

	@Test
	public void testQueueMaxSize() {
		TaskManagerService service = new TaskManagerService(10);
		assertTrue(service.addProcess(pid1, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid2, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid3, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid4, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid5, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid6, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid7, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid8, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid9, Thread.MAX_PRIORITY));
		assertTrue(service.addProcess(pid10, Thread.MAX_PRIORITY));
		assertFalse(service.addProcess(pid11, Thread.MAX_PRIORITY));
	}

	@Test
	public void testQueueFIFO() {
		TaskManagerService service = new TaskManagerService(10);
		assertTrue(service.addProcessFIFO(pid1, Thread.MIN_PRIORITY));
		assertTrue(service.addProcessFIFO(pid2, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid3, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid4, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid5, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid6, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid7, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid8, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid9, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid10, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessFIFO(pid11, Thread.MAX_PRIORITY));
	}

	@Test
	public void testQueuePriorityBased() {
		TaskManagerService service = new TaskManagerService(10);
		assertTrue(service.addProcessPriorityBased(pid1, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(pid2, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(pid3, Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MIN_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MAX_PRIORITY));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Thread.MAX_PRIORITY));
	}

	@Test
	public void testQueueListByTimestamp() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.list(SortOrder.TIMESTAMP);
		Iterator<MyProcess> iter = stream.iterator();
		Date timeStamp1 = iter.next().getTimestamp();
		Date timeStamp2 = iter.next().getTimestamp();
		Date timeStamp3 = iter.next().getTimestamp();
		assertTrue(timeStamp1.compareTo(timeStamp2) >= 0);
		assertTrue(timeStamp1.compareTo(timeStamp3) >= 0);
		assertTrue(timeStamp2.compareTo(timeStamp3) >= 0);
	}

	@Test
	public void testQueueListByPriority() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.list(SortOrder.PRIORITY);
		Iterator<MyProcess> iter = stream.iterator();
		assertEquals(Thread.MAX_PRIORITY, iter.next().getPriority());
		assertEquals(Thread.NORM_PRIORITY, iter.next().getPriority());
		assertEquals(Thread.MIN_PRIORITY, iter.next().getPriority());
	}

	@Test
	public void testQueueListByPID() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.list(SortOrder.PID);
		Iterator<MyProcess> iter = stream.iterator();
		assertEquals(iter.next().getPID(), pid1);
		assertEquals(iter.next().getPID(), pid2);
		assertEquals(iter.next().getPID(), pid3);
	}

	@Test
	public void testKillAll() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.killAll();
		assertEquals(stream.count(), 0);
	}

	@Test
	public void testKillAllByPriorityHigh() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		service.addProcess(pid4, Thread.NORM_PRIORITY);
		service.addProcess(pid5, Thread.MAX_PRIORITY);
		service.addProcess(pid6, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.killAll(Thread.MAX_PRIORITY);
		assertEquals(stream.count(), 4);
	}

	@Test
	public void testKillAllByPriorityMedium() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		service.addProcess(pid4, Thread.NORM_PRIORITY);
		service.addProcess(pid5, Thread.MAX_PRIORITY);
		service.addProcess(pid6, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.killAll(Thread.MAX_PRIORITY);
		assertEquals(stream.count(), 4);
	}

	@Test
	public void testKillAllByPriorityLow() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		service.addProcess(pid4, Thread.NORM_PRIORITY);
		service.addProcess(pid5, Thread.MAX_PRIORITY);
		service.addProcess(pid6, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.killAll(Thread.MAX_PRIORITY);
		assertEquals(stream.count(), 4);
	}

	@Test
	public void testKillProcess() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Thread.NORM_PRIORITY);
		service.addProcess(pid2, Thread.MAX_PRIORITY);
		service.addProcess(pid3, Thread.MIN_PRIORITY);
		service.addProcess(pid4, Thread.NORM_PRIORITY);
		service.addProcess(pid5, Thread.MAX_PRIORITY);
		service.addProcess(pid6, Thread.MIN_PRIORITY);
		Stream<MyProcess> stream = service.killProcess(pid3);
		assertEquals(stream.count(), 5);
	}
}
