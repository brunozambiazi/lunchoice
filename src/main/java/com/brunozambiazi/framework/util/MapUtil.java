
package com.brunozambiazi.framework.util;

import java.util.Collection;
import java.util.Map;


public final class MapUtil {

	private MapUtil() {
	}
	
	
	public static <T, K> K getMostRelevantKey(Map<K, Collection<T>> map) {
		K mostRelevantKey = null;
		
		for (K key : map.keySet()) {
			if (mostRelevantKey == null) {
				mostRelevantKey = key;
				continue;
			}
			
			if (map.get(key).size() > map.get(mostRelevantKey).size()) {
				mostRelevantKey = key;
			}
		}
		
		return mostRelevantKey;
	}
	
}
