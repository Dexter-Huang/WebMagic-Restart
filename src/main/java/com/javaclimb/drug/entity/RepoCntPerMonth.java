package com.javaclimb.drug.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repo_cnt_per_month")
public class RepoCntPerMonth {
    @TableField(value = "year")
    private int year;
    @TableField(value = "month")
    private int month;
    @TableField(value = "repoCnt")
    private int repoCnt;
    @TableField(value = "rate")
    private double rate;

    public RepoCntPerMonth(int year, int month, int repoCnt) {
        this.year = year;
        this.month = month;
        this.repoCnt = repoCnt;
    }
}
