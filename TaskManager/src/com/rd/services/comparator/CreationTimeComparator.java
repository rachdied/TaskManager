package com.rd.services.comparator;

import java.util.Comparator;

import com.rd.services.entity.MyProcess;

/**
 * 
 * @author rache
 *
 */
public class CreationTimeComparator implements Comparator<MyProcess> {

	@Override
	public int compare(MyProcess o1, MyProcess o2) {
		if (o1 != null && o2 != null && o1.getTimestamp() != null && o2.getTimestamp() != null) {
			return o1.getTimestamp().compareTo(o2.getTimestamp());
		}
		return 0;
	}
}
