package com.lifen.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by liwei on 2018/3/7.
 */
@Entity
@DynamicUpdate
@Data
public class TcTask {
    @Id
    @GeneratedValue
    private Long taskId;
    private long ProjectId;
    private long userId;
    private String userAccount;
    private String taskType;
    private String taskTitle;
    private String taskContent;
    private Date createTime;
    private Date updateTime;
    private String isDeleted;

    public TcTask() {
    }
}
