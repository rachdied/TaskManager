package com.rd.services.comparator;

import java.util.Comparator;

import com.rd.services.entity.MyProcess;

/**
 * 
 * @author rache
 *
 */
public class PIDComparator implements Comparator<MyProcess> {

	@Override
	public int compare(MyProcess o1, MyProcess o2) {
		if (o1 != null && o2 != null && o1.getPID() != null && o2.getPID() != null) {
			return o1.getPID().compareTo(o2.getPID());
		}
		return 0;
	}
}
