package com.diboot.framework.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 集合批次迭代
 * @author Mazc@dibo.ltd
 * @version 2017/5/17
 * Copyright @ www.dibo.ltd
 */
public class BatchIterator<E> implements Iterator<List<E>> {
    /**
     * 每批次集合数量
     */
    private int batchSize;
    /***
     * 原始list
     */
    private List<E> originalList;
    private int index = 0;
    private List<E> result;
    private int total = 0;

    public BatchIterator(List<E> originalList, int batchSize) {
        if (batchSize <= 0) {
            return;
        }
        this.batchSize = batchSize;
        this.originalList = originalList;
        this.total = V.notEmpty(originalList)? originalList.size() : 0;
        result = new ArrayList<E>(batchSize);
    }

    @Override
    public boolean hasNext() {
        return index < total;
    }

    @Override
    public List<E> next() {
        result.clear();
        for (int i = 0; i < batchSize && index < total; i++) {
            result.add(originalList.get(index++));
        }
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
