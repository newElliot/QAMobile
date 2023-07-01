package com.yourcompany.utilities;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListUtils {
private static Logger logger = LogManager.getLogger();
	
	public <T extends Comparable<T>> boolean compareLists(List<T> list, List<T> list2){
		boolean flag = true;
		System.out.println("list.size " + list.size() +"--> list " + list);
		System.out.println("list2.size " + list2.size() +"--> list2" + list2);
		if(list.size() != list2.size()){
			for(int i=0;i<100;i++){
				if(i<list.size()){
					System.out.print(list.get(i)+"<-->");

				}
				if(i<list2.size()){
					System.out.println(list2.get(i));

				}
			}
		}
		if(list.size() == list2.size()){

			Collections.sort(list);
			Collections.sort(list2);
			
			
			for(int i=0;i<list.size();i++){
				if(!list.get(i).toString().toUpperCase().trim().equalsIgnoreCase(list2.get(i).toString().toUpperCase().trim())){
					logger.error("No Match for Element: List1 " + list.get(i));
					flag = false;
					break;
				} 
			}

			logger.info("Matched All Elements");
		} else {
			flag = false;
		}
		return flag;
	}
	
	public <T extends Comparable<T>> boolean compareLists(List<T> list, List<T> list2, T oldVal, T newVal){
		boolean flag = true;
		if(list.size() == list2.size()){

			replaceListVals(list, oldVal, newVal);
			replaceListVals(list2, oldVal, newVal);
			
			Collections.sort(list);
			Collections.sort(list2);
			
			for(int i=0;i<list.size();i++){
				//if(!list.get(i).equals(list2.get(i))){
				if(!list.get(i).toString().toUpperCase().trim().equalsIgnoreCase(list2.get(i).toString().toUpperCase().trim())){
					logger.error("No Match for Element: List1 " + list.get(i));
					flag = false;
					break;
				} 
			}
			System.out.println("list " + list);
			System.out.println("list2" + list2);
			logger.info("Matched All Elements");
		} else {
			flag = false;
		}
		return flag;
	}
	
	public <T extends Comparable<T>> void replaceListVals(List<T> list, T oldVal, T newVal){
		Collections.replaceAll(list, oldVal, newVal);
	}
	
	/**
	 * Sort list object with using its property
	 * @param list
	 * @param fieldName
	 */
	public static void sortListObjects(List<?> list, String fieldName) {
		if (list != null && !list.isEmpty() ) {
			Collections.sort(list, new Comparator<Object>() {
	            @Override
	            public int compare(Object o1, Object o2) {
					try {
						Field f1 = o1.getClass().getDeclaredField(fieldName);
						f1.setAccessible(true);
						Field f2 = o2.getClass().getDeclaredField(fieldName);
						f2.setAccessible(true);
						return f1.get(o1).toString().compareTo(f2.get(o2).toString());
					} catch (Exception e) {
						return 0;
					}
	            }
	        });
		}
	}
}
