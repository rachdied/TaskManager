package com.rd.services.enums;

public enum Priority {

	LOW(1), MEDIUM(2), HIGH(3);

	Integer value;

	Priority(int value) {
		this.value = value;
	};

	public Integer getValue() {
		return value;
	}

	public int compare(Priority priority) {
		return priority.getValue().compareTo(this.getValue());
	}
}
