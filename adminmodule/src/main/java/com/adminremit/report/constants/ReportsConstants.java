package com.adminremit.report.constants;

import java.util.HashMap;
import java.util.Map;

public class ReportsConstants {
	
	public static Map<Integer, String> relationShipMap;
	
	public static Map<Integer, String> terraPayRelationshipMap;
	static {
		relationShipMap = new HashMap<>();
		relationShipMap.put(0, "Brother-in-law");
		relationShipMap.put(1, "Child");
		relationShipMap.put(2, "Daughter-in-law");
		relationShipMap.put(3, "Father-in-law");
		relationShipMap.put(4, "Fiance");
		relationShipMap.put(5, "Fiancee");
		relationShipMap.put(6, "Grandchild");
		relationShipMap.put(7, "Grandparent");
		relationShipMap.put(8, "Mother-in-law");
		relationShipMap.put(9, "Parent");
		relationShipMap.put(10, "Sibling");
		relationShipMap.put(11, "Sister-in-law");
		relationShipMap.put(12, "Son-in-law");
		relationShipMap.put(13, "Spouse");
		
		
		terraPayRelationshipMap = new HashMap<>();
		terraPayRelationshipMap.put(0, "Self");
		terraPayRelationshipMap.put(1, "Father");
		terraPayRelationshipMap.put(2, "Mother");
		terraPayRelationshipMap.put(3, "Father-in-law");
		terraPayRelationshipMap.put(4, "Spouse");
		terraPayRelationshipMap.put(5, "Son");
		terraPayRelationshipMap.put(6, "Daughter");
		terraPayRelationshipMap.put(7, "Brother");
		terraPayRelationshipMap.put(8, "Sister");
		terraPayRelationshipMap.put(9, "Friend");
    }

}
