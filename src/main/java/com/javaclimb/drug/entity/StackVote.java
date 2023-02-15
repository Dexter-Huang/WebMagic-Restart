package com.javaclimb.drug.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class StackVote implements Serializable {
    @TableId(value = "question_id")
    private Long questionId;
    @TableField(value = "answerer")
    private String answerer;
    @TableField(value = "answer_score")
    private Integer answerScore;
    @TableField(value = "questioner")
    private String questioner;
    @TableField(value = "question_score")
    private Integer questionScore;
    @TableField(value = "answer_cnt")
    private Integer answerCnt;
    @TableField(value = "view_cnt")
    private Integer viewCnt;
    @TableField(value = "link")
    private String link;
    @TableField(value = "title")
    private String title;
    @TableField(value = "favorite")
    private Integer favorite;

    public StackVote(Long questionId, String answerer, Integer answerScore, String questioner, Integer questionScore, Integer answerCnt, Integer viewCnt, String link, String title,Integer favorite) {
        this.questionId = questionId;
        this.answerer = answerer;
        this.answerScore = answerScore;
        this.questioner = questioner;
        this.questionScore = questionScore;
        this.answerCnt = answerCnt;
        this.viewCnt = viewCnt;
        this.link = link;
        this.title = title;
        this.favorite=favorite;
    }
}
