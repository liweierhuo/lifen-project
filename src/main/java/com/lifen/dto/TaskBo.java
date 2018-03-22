package com.lifen.dto;

import com.lifen.dataobject.TcScore;
import com.lifen.dataobject.TcTask;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * Created by liwei on 2018/3/21.
 */
public class TaskBo {
    private Long taskId;
    private long projectId;
    private String projectPublisher;
    private long userId;
    private String userAccount;
    private String taskType;
    private String taskTitle;
    private String taskContent;
    private Date createTime;
    private Date updateTime;
    private String isDeleted;
    private TcScore tcScore;


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getProjectPublisher() {
        return projectPublisher;
    }

    public void setProjectPublisher(String projectPublisher) {
        this.projectPublisher = projectPublisher;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public TcScore getTcScore() {
        return tcScore;
    }

    public void setTcScore(TcScore tcScore) {
        this.tcScore = tcScore;
    }
}
