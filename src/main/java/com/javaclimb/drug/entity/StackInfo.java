package com.javaclimb.drug.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "stack_info")
public class StackInfo {
    private String name;
    private String type;
    private int score;

    public StackInfo(String name, String type, int score) {
        this.name = name;
        this.type = type;
        this.score = score;
    }
}
