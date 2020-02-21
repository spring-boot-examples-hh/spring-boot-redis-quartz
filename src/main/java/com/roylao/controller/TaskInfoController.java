package com.roylao.controller;

import com.roylao.common.config.Result;
import com.roylao.entity.QuartzEntity;
import com.roylao.service.TaskInfoService;
import io.swagger.annotations.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(value = "任务", description = "任务操作API", position = 100, protocols = "http")
@RestController
@RequestMapping(value = "job")
public class TaskInfoController {

    @Autowired
    private TaskInfoService taskInfoService;

    @ApiOperation("所有任务列表")
    @ApiResponses({
            @ApiResponse(code = -1, message = "查询失败"),
            @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 0, message = "查询成功") })
   /* @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "信息id", required = true)
    })*/
    @GetMapping(value = "list")
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
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result edit(QuartzEntity info) {
        return taskInfoService.edit(info);
    }

    /**
     * 删除定时任务
     *
     * @param jobName com.roylao.common.job.ScheduledJobOne
     * @param jobGroup 组名称
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
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
    @RequestMapping(value = "pause",method = RequestMethod.POST)
    public Result pause(String jobName, String jobGroup) {
        return taskInfoService.pause(jobName,jobGroup);
    }

    /**
     * 重新开始任务
     *
     * @param jobName
     */
    @RequestMapping(value = "resume",method = RequestMethod.POST)
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
    @RequestMapping(value = "checkExists",method = RequestMethod.POST)
    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        return taskInfoService.checkExists(jobName,jobGroup);
    }
}
