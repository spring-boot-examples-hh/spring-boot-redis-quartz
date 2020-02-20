package com.roylao.common.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @DisallowConcurrentExecution: 禁止并发执行多个相同定义的JobDetail
 * Quartz定时任务默认都是并发执行的，不会等待上一次任务执行完毕，只要间隔时间到就会执行, 如果定时任执行太长，会长时间占用资源，导致其它任务堵塞,
 */
@DisallowConcurrentExecution
public class ScheduledJobOne implements Job , Serializable {


    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("ScheduledJobOne-----,quartz ScheduledJobOne Tasks: " + dateFormat().format(new Date()));
        try {
            System.out.println("ScheduledJobDemoOne-----,quartz wait.....");
//            Thread.sleep(3*60*1000);
            System.out.println("ScheduledJobDemoOne-----,quartz end....." + dateFormat().format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}