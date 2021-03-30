package com.rd.services.comparator;

import java.util.Comparator;

import com.rd.services.entity.MyProcess;

/**
 * 
 * @author rache
 *
 */
public class PriorityComparator implements Comparator<MyProcess> {

	@Override
	public int compare(MyProcess o1, MyProcess o2) {
		if (o1 != null && o2 != null) {
			if (o1.getPriority() > o2.getPriority()) {
				return 1;
			} else if (o1.getPriority() < o2.getPriority()) {
				return -1;
			}
		}
		return 0;
	}
}
