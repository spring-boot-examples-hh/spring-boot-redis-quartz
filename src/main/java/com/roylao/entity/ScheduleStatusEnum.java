package com.roylao.entity;

/**
 * @author lhh
 * @date 2020-2-20 14:04:45
 * @desc 任务状态枚举
 */
public enum ScheduleStatusEnum {

    BLOCKED("BLOCKED","阻塞"),
    COMPLETE("COMPLETE","完成"),
    ERROR("ERROR","错误"),
    NONE("NONE","不存在"),
    NORMAL("NORMAL","正常"),
    PAUSED("PAUSED","暂停");

    ScheduleStatusEnum(String jobStatus,String jobStatusName){
        this.jobStatus = jobStatus;
        this.jobStatusName = jobStatusName;
    }

    private String jobStatus;
    private String jobStatusName;

    public String getJobStatusName() {
        return jobStatusName;
    }

    public void setJobStatusName(String jobStatusName) {
        this.jobStatusName = jobStatusName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
}
