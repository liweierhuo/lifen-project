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
public class TcScore {
    @Id
    @GeneratedValue
    private Long scoreId;
    private String scoreTitle;
    private String scoreContent;
    private Integer score;
    private Long taskId;
    private Long userId;
    private String userAccount;
    private Date createTime;
    private Date updateTime;
    private String isDeleted;

    public TcScore() {
    }
}
