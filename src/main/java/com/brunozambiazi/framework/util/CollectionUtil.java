
package com.brunozambiazi.framework.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public final class CollectionUtil {
	
	private CollectionUtil() {
	}
	
	
	/**
	 * 
	 * @param collection
	 * @param argumentKey
	 * @return
	 */
	public static <T, K> Map<K, Collection<T>> map(Collection<T> collection, ArgumentKey<T, K> argumentKey) {
		Map<K, Collection<T>> result = new HashMap<>();
		
		for (T element : collection) {
			K key = argumentKey.getFrom(element);
			
			if (! result.containsKey(key)) {
				result.put(key, new HashSet<T>());
			}
			
			result.get(key).add(element);
		}
		
		return result;
	}
	
	
}
