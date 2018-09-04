package com.great.adou.app.utils;

import java.util.Collection;
import java.util.List;

/**
 * <集合工具类>
 * Created by WangWB on 2018/09/04  17:02.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> list) {
        return !(list != null && !list.isEmpty());
    }

    public static <T> boolean isIndexLegal(List<T> list, int index) {
        return !isEmpty(list) && index >= 0 && index < list.size();
    }
}
