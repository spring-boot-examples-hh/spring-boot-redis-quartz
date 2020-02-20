package com.roylao.service;

import com.roylao.common.config.Result;
import com.roylao.entity.QuartzEntity;
import org.quartz.SchedulerException;

import java.util.List;

public interface TaskInfoService {

    public List<QuartzEntity> list();

    public Result addJob(QuartzEntity info);

    public Result edit(QuartzEntity info);

    public Result delete(String jobName, String jobGroup);

    public Result pause(String jobName, String jobGroup);

    public Result resume(String jobName, String jobGroup);

    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException;
}
