module taskmanager {
	requires java.sql;
	requires org.junit.jupiter.api;
	requires junit;
	requires org.junit.platform.runner;

	exports com.rd.services;
	exports com.rd.services.enums;
	exports com.rd.services.entity;
}