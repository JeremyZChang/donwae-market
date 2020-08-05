package com.donwae.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * 数据读取器
 * @author Jeremy Zhang
 * 2020/4/26 下午4:58
 */
public class MyReader implements ItemReader<String> {

    private Iterator<String> iterator;

    public MyReader(List<String> list) {
        this.iterator = list.iterator();
    }

    @Override
    public String read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        if(iterator.hasNext()){
            return this.iterator.next();
        } else
            return null;
    }
}
