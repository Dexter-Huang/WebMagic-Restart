package com.javaclimb.drug.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName(value = "githubrepo")
public class Githubrepo implements Serializable {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "owner")
    private String owner;
    @TableField(value = "owner_type")
    private String ownerType;
    @TableField(value = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @TableField(value = "star")
    private Integer star;
    @TableField(value = "forks")
    private Integer forks;
    @TableField(value = "description")
    private String description;
    @TableField(value = "html_url")
    private String htmlUrl;
    @TableField(value = "favorite")
    private Integer favorite;

    public Githubrepo(Long id, String name, String owner, String ownerType, Date createdAt, Integer star,
                      Integer forks, String description, String htmlUrl, Integer favorite) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.ownerType = ownerType;
        this.createdAt = createdAt;
        this.star = star;
        this.forks = forks;
        this.description = description;
        this.htmlUrl = htmlUrl;
        this.favorite = favorite;
    }
}
