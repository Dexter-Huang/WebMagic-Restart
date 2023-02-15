package com.javaclimb.drug.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaclimb.drug.entity.StackTags;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;
import net.minidev.json.JSONObject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-05-11
 */
public interface StackTagsService extends JoinIService<StackTags> {
    public List<JSONObject> wordcloud();
}
