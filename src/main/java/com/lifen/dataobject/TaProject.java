package com.lifen.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
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
public class TaProject {
    @Id
    @GeneratedValue
    private Long projectId;
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

    public TaProject() {
    }


}
