package com.javaclimb.drug.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.google.gson.JsonArray;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.mapper.GithubtopicMapper;
import com.javaclimb.drug.service.GithubtopicService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Dexter-Huang
 * @since 2022-05-09
 */
@Service
public class GithubtopicServiceImpl extends JoinServiceImpl<GithubtopicMapper, Githubtopic> implements GithubtopicService {
    @Autowired
    private GithubtopicMapper githubtopicMapper;
    @Override
    public IPage<Githubtopic> selectPage(int pageNum, int pageSize, String param) {


        QueryWrapper<Githubtopic> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(param)){
            queryWrapper.like("topic",param);
        }
        Page<Githubtopic> page=new Page<>(pageNum,pageSize);
        return githubtopicMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int add(Githubtopic githubtopic) {
        return githubtopicMapper.insert(githubtopic);
    }

    @Override
    public int edit(Githubtopic githubtopic) {
        QueryWrapper<Githubtopic> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",githubtopic.getId()).eq("topic",githubtopic.getTopic());
        return githubtopicMapper.update(githubtopic,queryWrapper);
    }

    @Override
    public Githubtopic query(Long id,String topic) {
        QueryWrapper<Githubtopic> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id).eq("topic",topic);
        return githubtopicMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Githubtopic> queryAll() {
        return githubtopicMapper.selectList(null);
    }

    @Override
    public int del(Long id,String topic) {
        QueryWrapper<Githubtopic> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id).eq("topic",topic);
        return githubtopicMapper.delete(queryWrapper);
    }

    public List<JSONObject> wordcloud(){
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<Githubtopic> wrapper=new QueryWrapper<>();

        wrapper.select("topic,count(*) as cnt");
        wrapper.groupBy("topic");
        wrapper.orderByDesc("cnt");

        List<Map<String, Object>> githubtopicList=githubtopicMapper.selectMaps(wrapper);
        for(Map<String,Object> g:githubtopicList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.get("topic"));
            jsonObject.put("value",Integer.parseInt(String.valueOf(g.get("cnt"))));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
