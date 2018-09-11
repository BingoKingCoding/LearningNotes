package com.great.adou.app.utils.select;

import java.util.Iterator;

/**
 * <遍历回调>
 */

public interface IterateCallback<T>
{
    /**
     * 返回true，结束遍历
     *
     * @param i
     * @param item
     * @param it
     * @return
     */
    boolean next(int i, T item, Iterator<T> it);
}
