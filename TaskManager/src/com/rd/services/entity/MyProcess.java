package com.rd.services.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyProcess extends Thread {

	private final UUID PID;
	private final Date timestamp;
	private AtomicBoolean isAlive = new AtomicBoolean();

	public MyProcess(UUID PID, int priority) {
		this.PID = PID;
		this.timestamp = new Date(new Timestamp(System.currentTimeMillis()).getTime());
		super.setPriority(priority);
		isAlive.set(true);
	}

	public UUID getPID() {
		return PID;
	}

	public Date getTimestamp() {
		return (Date) timestamp.clone();
	}

	public void kill() {
		isAlive.set(false);
	}
}
