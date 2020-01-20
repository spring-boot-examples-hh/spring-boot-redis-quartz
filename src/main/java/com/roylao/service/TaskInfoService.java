package com.roylao.service;

import com.roylao.entity.TaskInfo;
import org.quartz.SchedulerException;

public interface TaskInfoService {

    public String list();

    public String addJob(TaskInfo info);

    public String edit(TaskInfo info);

    public String delete(String jobName, String jobGroup);

    public String pause(String jobName, String jobGroup);

    public String resume(String jobName, String jobGroup);

    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException;
}
