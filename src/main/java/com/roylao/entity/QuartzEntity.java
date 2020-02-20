package com.roylao.entity;

import java.io.Serializable;

public class QuartzEntity implements Serializable {

    private int id = 0;

    /**任务名称*/
    private String jobName;

    /**任务分组*/
    private String jobGroup;

    /**任务类路径**/
    private String jobClassName;

    /**任务描述*/
    private String jobDescription;

    /**任务状态*/
    private String jobStatus;

    /**任务状态*/
    private String jobStatusName;

    /**任务表达式*/
    private String cronExpression;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
        switch (this.jobStatus){
            case "BLOCKED":
                this.jobStatusName = ScheduleStatusEnum.BLOCKED.getJobStatusName();
                break;
            case "COMPLETE":
                this.jobStatusName = ScheduleStatusEnum.COMPLETE.getJobStatusName();
                break;
            case "NONE":
                this.jobStatusName = ScheduleStatusEnum.NONE.getJobStatusName();
                break;
            case "NORMAL":
                this.jobStatusName = ScheduleStatusEnum.NORMAL.getJobStatusName();
                break;
            case "PAUSED":
                this.jobStatusName = ScheduleStatusEnum.PAUSED.getJobStatusName();
                break;
            default:
                this.jobStatusName = ScheduleStatusEnum.ERROR.getJobStatusName();
                break;
        }
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobStatusName() {
        return jobStatusName;
    }

   /* public void setJobStatusName(String jobStatusName) {
        this.jobStatusName = jobStatusName;
    }*/
    /*@Override
    public String toString() {
        return "TaskInfo{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }*/
}