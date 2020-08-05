package com.donwae.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * 使用监听器
 * @author Jeremy Zhang
 * 2020/4/25 下午5:48
 */
public class MyChuckListener {
    @BeforeChunk
    public void beforeChuck(ChunkContext context) {
        System.out.println(context.getStepContext().getStepName() + " before");
    }

    @AfterChunk
    public void afterChuck(ChunkContext context) {
        System.out.println(context.getStepContext().getStepName() + " after");
    }
}
