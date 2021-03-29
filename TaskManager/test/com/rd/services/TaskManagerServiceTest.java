package com.rd.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.rd.services.entity.MyProcess;
import com.rd.services.enums.Priority;
import com.rd.services.enums.SortOrder;

@RunWith(JUnitPlatform.class)
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
		assertTrue(service.addProcess(pid1, Priority.HIGH));
		assertTrue(service.addProcess(pid2, Priority.HIGH));
		assertTrue(service.addProcess(pid3, Priority.HIGH));
		assertTrue(service.addProcess(pid4, Priority.HIGH));
		assertTrue(service.addProcess(pid5, Priority.HIGH));
		assertTrue(service.addProcess(pid6, Priority.HIGH));
		assertTrue(service.addProcess(pid7, Priority.HIGH));
		assertTrue(service.addProcess(pid8, Priority.HIGH));
		assertTrue(service.addProcess(pid9, Priority.HIGH));
		assertTrue(service.addProcess(pid10, Priority.HIGH));
		assertFalse(service.addProcess(pid11, Priority.HIGH));
	}

	@Test
	public void testQueueFIFO() {
		TaskManagerService service = new TaskManagerService(10);
		assertTrue(service.addProcessFIFO(pid1, Priority.LOW));
		assertTrue(service.addProcessFIFO(pid2, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid3, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid4, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid5, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid6, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid7, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid8, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid9, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid10, Priority.HIGH));
		assertTrue(service.addProcessFIFO(pid11, Priority.HIGH));
	}

	@Test
	public void testQueuePriorityBased() {
		TaskManagerService service = new TaskManagerService(10);
		assertTrue(service.addProcessPriorityBased(pid1, Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(pid2, Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(pid3, Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.LOW));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.HIGH));
		assertTrue(service.addProcessPriorityBased(UUID.randomUUID(), Priority.HIGH));
	}

	@Test
	public void testQueueListByTimestamp() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
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
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
		Stream<MyProcess> stream = service.list(SortOrder.PRIORITY);
		Iterator<MyProcess> iter = stream.iterator();
		assertEquals(iter.next().getPriority(), Priority.HIGH);
		assertEquals(iter.next().getPriority(), Priority.MEDIUM);
		assertEquals(iter.next().getPriority(), Priority.LOW);
	}

	@Test
	public void testQueueListByPID() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
		Stream<MyProcess> stream = service.list(SortOrder.PID);
		Iterator<MyProcess> iter = stream.iterator();
		assertEquals(iter.next().getPID(), pid1);
		assertEquals(iter.next().getPID(), pid2);
		assertEquals(iter.next().getPID(), pid3);
	}

	@Test
	public void testKillAll() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
		Stream<MyProcess> stream = service.killAll();
		assertEquals(stream.count(), 0);
	}

	@Test
	public void testKillAllByPriorityHigh() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
		service.addProcess(pid4, Priority.MEDIUM);
		service.addProcess(pid5, Priority.HIGH);
		service.addProcess(pid6, Priority.LOW);
		Stream<MyProcess> stream = service.killAll(Priority.HIGH);
		assertEquals(stream.count(), 4);
	}

	@Test
	public void testKillAllByPriorityMedium() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
		service.addProcess(pid4, Priority.MEDIUM);
		service.addProcess(pid5, Priority.HIGH);
		service.addProcess(pid6, Priority.LOW);
		Stream<MyProcess> stream = service.killAll(Priority.HIGH);
		assertEquals(stream.count(), 4);
	}

	@Test
	public void testKillAllByPriorityLow() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
		service.addProcess(pid4, Priority.MEDIUM);
		service.addProcess(pid5, Priority.HIGH);
		service.addProcess(pid6, Priority.LOW);
		Stream<MyProcess> stream = service.killAll(Priority.HIGH);
		assertEquals(stream.count(), 4);
	}

	@Test
	public void testKillProcess() {
		TaskManagerService service = new TaskManagerService(10);
		service.addProcess(pid1, Priority.MEDIUM);
		service.addProcess(pid2, Priority.HIGH);
		service.addProcess(pid3, Priority.LOW);
		service.addProcess(pid4, Priority.MEDIUM);
		service.addProcess(pid5, Priority.HIGH);
		service.addProcess(pid6, Priority.LOW);
		Stream<MyProcess> stream = service.killProcess(pid3);
		assertEquals(stream.count(), 5);
	}
}
