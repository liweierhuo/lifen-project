package com.lifen.dto;

import com.lifen.dataobject.TcTask;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by liwei on 2018/3/21.
 */
public class ProjectBo {
    private String projectCode;
    private String projectName;
    private String projectType;
    private String projectStatus;
    private Long userId;
    private String userAccount;
    private String projectContent;
    private Date createTime;
    private Date updateTime;
    private String isDeleted;
    private Page<TcTask> taskPage;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
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

    public Page<TcTask> getTaskPage() {
        return taskPage;
    }

    public void setTaskPage(Page<TcTask> taskPage) {
        this.taskPage = taskPage;
    }
}
