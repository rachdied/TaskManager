package com.rd.services.comparator;

import java.util.Comparator;

import com.rd.services.entity.MyProcess;

/**
 * 
 * @author rache
 *
 */
public class DefaultComparator implements Comparator<MyProcess> {

	@Override
	public int compare(MyProcess o1, MyProcess o2) {
		if (o1 != null && o2 != null) {
			if (o1.getPriority().compareTo(o2.getPriority()) < 0) {
				return 1;
			} else if (o1.getPriority().compareTo(o2.getPriority()) > 0) {
				return -1;
			} else {
				return o1.getTimestamp().compareTo(o2.getTimestamp());
			}
		}
		return 0;
	}
}
