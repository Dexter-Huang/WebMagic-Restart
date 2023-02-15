package com.javaclimb.drug.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaclimb.drug.entity.Githubtopic;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Dexter-Huang
 * @since 2022-05-09
 */
public interface GithubtopicService extends JoinIService<Githubtopic> {
    public IPage<Githubtopic> selectPage(int pageNum, int pageSize, String param);
    public int add (Githubtopic githubtopic);
    public int edit (Githubtopic githubtopic);
    public Githubtopic query(Long id,String topic);
    public List<Githubtopic> queryAll();
    public int del(Long id,String topic);
    public List<JSONObject> wordcloud();

}
