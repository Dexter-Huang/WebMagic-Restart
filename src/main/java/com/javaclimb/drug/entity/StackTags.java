package com.javaclimb.drug.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StackTags implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long questionId;

    private String tags;

    public StackTags(Long questionId, String tags) {
        this.questionId = questionId;
        this.tags = tags;
    }
}
