package com.roylao.service;

import com.roylao.common.config.Result;
import com.roylao.entity.QuartzEntity;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component(value = "taskInfoService")
public class TaskInfoServiceImpl implements TaskInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private Scheduler scheduler;

    /**
     * 所有任务列表
     * @return
     */
    @Override
    public List<QuartzEntity> list() {
        List<QuartzEntity> list = new ArrayList<>();
        try {
            for (String groupJob : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(groupJob))) {
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggers) {
                        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                        String cronExpression = "", createTime = "";

                        if (trigger instanceof CronTrigger) {
                            CronTrigger cronTrigger = (CronTrigger) trigger;
                            cronExpression = cronTrigger.getCronExpression();
                            createTime = cronTrigger.getDescription();
                        }
                        QuartzEntity info = new QuartzEntity();
                        info.setJobName(jobKey.getName());
                        info.setJobGroup(jobKey.getGroup());
                        info.setJobClassName(jobDetail.getJobClass().getName());
                        info.setJobDescription(jobDetail.getDescription());
                        info.setJobStatus(triggerState.name());
                        info.setCronExpression(cronExpression);
                        info.setCreateTime(createTime);
                        list.add(info);
                        logger.info("find job, this job , jobGroup:{}, jobName:{}", jobKey.getGroup(), jobKey.getName());
                    }
                }
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 添加定时任务
     * @param info
     * @return
     */
    @Override
    public Result addJob(QuartzEntity info) {
        String jobName = info.getJobName(),
                jobClassName = info.getJobClassName(),
                jobGroup = info.getJobGroup(),
                cronExpression = info.getCronExpression(),
                jobDescription = info.getJobDescription(),
                createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            if (checkExists(jobName, jobGroup)) {
                logger.info("add job fail, job already exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
                return Result.error(-1,"job already exist");
            }

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(schedBuilder).build();

            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobClassName);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("类名不存在或执行表达式错误,exception:{}", e.getMessage());
            return Result.error(-1,"类名不存在或执行表达式错误");
        }
        return Result.ok("success");
    }

    /**
     * 修改定时任务
     * @param info
     * @return
     */
    @Override
    public Result edit(QuartzEntity info) {
        String jobName = info.getJobName(),
                jobGroup = info.getJobGroup(),
                cronExpression = info.getCronExpression(),
                jobDescription = info.getJobDescription(),
                createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            if (!checkExists(jobName, jobGroup)) {
                logger.info("edit job fail, job is not exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
                return Result.error(-1,"job is not exist");
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(cronScheduleBuilder).build();

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            HashSet<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        } catch (SchedulerException e) {
            logger.error("类名不存在或执行表达式错误,exception:{}", e.getMessage());
            return Result.error(-1,"类名不存在或执行表达式错误");
        }
        return Result.ok("success");
    }

    /**
     * 删除定时任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Override
    public Result delete(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (!checkExists(jobName, jobGroup)) {
                logger.info("delete job fail, job is not exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
                return Result.error(-1,"job is not exist");
            }
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            logger.info("delete job, triggerKey:{},jobGroup:{}, jobName:{}", triggerKey, jobGroup, jobName);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            return Result.error(-1,e.getMessage());
        }
        return Result.ok("ok");
    }

    /**
     * 暂停定时任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Override
    public Result pause(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (!checkExists(jobName, jobGroup)) {
                logger.info("pause job fail, job is not exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
                return Result.error(-1,"job is not exist");
            }
            scheduler.pauseTrigger(triggerKey);
            logger.info("pause job success, triggerKey:{},jobGroup:{}, jobName:{}", triggerKey, jobGroup, jobName);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            return Result.error(-1,e.getMessage());
        }
        return Result.ok("success");
    }

    /**
     * 重新开始任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Override
    public Result resume(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

        try {
            if (!checkExists(jobName, jobGroup)) {
                logger.info("resume job fail, job is not exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
                return Result.error(-1,"job is not exist");
            }
            scheduler.resumeTrigger(triggerKey);
            logger.info("resume job success,triggerKey:{},jobGroup:{}, jobName:{}", triggerKey, jobGroup, jobName);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            return Result.error(-1,e.getMessage());
        }
        return Result.ok("success");
    }

    /**
     * 验证是否存在
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    @Override
    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }
}
