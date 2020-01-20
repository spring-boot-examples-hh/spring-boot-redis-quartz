package com.roylao.common.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@DisallowConcurrentExecution
public class ScheduledJobOne implements Job , Serializable {


    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("ScheduledJobDemoOne-----,quartz ScheduledJobOne Tasks: " + dateFormat().format(new Date()));
        try {
            System.out.println("ScheduledJobDemoOne-----,quartz wait.....");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}