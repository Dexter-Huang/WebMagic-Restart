package com.javaclimb.drug.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Dexter-Huang
 * @since 2022-05-09
 */
@Data
@TableName(value = "githubowner")
public class Githubowner implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "name")
    private String name;
    @TableField(value = "type")
    private String type;
    @TableField(value = "star_num")
    private Integer starNum;
    @TableField(value = "repo_num")
    private Integer repoNum;
    @TableField(value = "fork_num")
    private Integer forkNum;


}
