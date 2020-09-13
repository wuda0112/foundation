package com.wuda.foundation.lang.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link IdPidEntry}类的工具类.
 *
 * @author wuda
 */
public class IdPidEntryUtils {

    /**
     * 根据ID分组,由于ID是唯一的,因此一个ID只对应一个节点.
     *
     * @param entryList list of entry
     * @return key - id , value - entry
     */

    public static <T extends Comparable<T>, E extends IdPidEntry<T>> Map<T, E> groupById(List<E> entryList) {
        int size = entryList.size();
        Map<T, E> map = new HashMap<>(size);
        for (E entry : entryList) {
            map.put(entry.getId(), entry);
        }
        return map;
    }
}
