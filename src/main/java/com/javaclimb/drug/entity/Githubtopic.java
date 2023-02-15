package com.javaclimb.drug.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Dexter-Huang
 * @since 2022-05-09
 */
@Data
@TableName(value = "githubtopic")
public class Githubtopic implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    private Long id;

    private String topic;
    @TableField(value = "name")
    private String name;
    @TableField(value = "owner")
    private String owner;
    @TableField(value = "owner_type")
    private String ownerType;
    @TableField(value = "created_at")
    private Date createdAt;

    public Githubtopic(Long id, String topic, String name, String owner, String ownerType, Date createdAt) {
        this.id = id;
        this.topic = topic;
        this.name = name;
        this.owner = owner;
        this.ownerType = ownerType;
        this.createdAt = createdAt;
    }
}
