package com.roylao.controller;

import com.roylao.common.config.Result;
import com.roylao.entity.QuartzEntity;
import com.roylao.service.TaskInfoService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "job")
public class TaskInfoController {

    @Autowired
    private TaskInfoService taskInfoService;

    /**
     * 所有任务列表
     */
    @RequestMapping(value = "list")
    public Result list() {
        return Result.ok(taskInfoService.list());
    }

    /**
     * 添加定时任务
     *
     * @param info
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public Result addJob(QuartzEntity info) {
        return taskInfoService.addJob(info);
    }

    /**
     * 修改定时任务
     *
     * @param info
     */
    @RequestMapping(value = "update")
    public Result edit(QuartzEntity info) {
        return taskInfoService.edit(info);
    }

    /**
     * 删除定时任务
     *
     * @param jobName com.roylao.common.job.ScheduledJobOne
     * @param jobGroup 组名称
     */
    @RequestMapping(value = "delete")
    public Result delete(String jobName, String jobGroup) {
        return taskInfoService.delete(jobName,jobGroup);
    }

    @PostMapping("/trigger")
    public Result trigger(String jobName, String jobGroup) {
        return taskInfoService.resume(jobName,jobGroup);
    }

    /**
     * 暂停定时任务
     *
     * @param jobName
     */
    @RequestMapping(value = "pause")
    public Result pause(String jobName, String jobGroup) {
        return taskInfoService.pause(jobName,jobGroup);
    }

    /**
     * 重新开始任务
     *
     * @param jobName
     */
    @RequestMapping(value = "resume")
    public Result resume(String jobName, String jobGroup) {
        return taskInfoService.resume(jobName,jobGroup);
    }

    /**
     * 验证是否存在
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    @RequestMapping(value = "checkExists")
    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        return taskInfoService.checkExists(jobName,jobGroup);
    }
}
