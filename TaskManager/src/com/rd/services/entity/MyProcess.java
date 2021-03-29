package com.rd.services.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.rd.services.enums.Priority;

public class MyProcess {

	private final UUID PID;
	private final Priority priority;
	private final Date timestamp;

	public MyProcess(UUID PID, Priority priority) {
		this.PID = PID;
		this.priority = priority;
		this.timestamp = new Date(new Timestamp(System.currentTimeMillis()).getTime());
	}

	public UUID getPID() {
		return PID;
	}

	public Priority getPriority() {
		return priority;
	}

	public Date getTimestamp() {
		return (Date) timestamp.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PID == null) ? 0 : PID.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyProcess other = (MyProcess) obj;
		if (PID == null) {
			if (other.PID != null)
				return false;
		} else if (!PID.equals(other.PID))
			return false;
		if (priority != other.priority)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	public void kill() {
		// TODO
	}
}
