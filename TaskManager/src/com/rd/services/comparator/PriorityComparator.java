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
		if (o1 != null && o2 != null && o1.getPriority() != null && o2.getPriority() != null) {
			return o1.getPriority().getValue().compareTo(o2.getPriority().getValue());
		}
		return 0;
	}
}
