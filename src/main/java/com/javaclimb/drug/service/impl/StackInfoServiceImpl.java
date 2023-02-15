package com.javaclimb.drug.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaclimb.drug.entity.Githubowner;
import com.javaclimb.drug.entity.StackInfo;
import com.javaclimb.drug.mapper.StackInfoMapper;
import com.javaclimb.drug.service.StackInfoService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StackInfoServiceImpl extends JoinServiceImpl<StackInfoMapper, StackInfo> implements StackInfoService {
    @Autowired
    private StackInfoMapper stackInfoMapper;

    @Override
    public List<JSONObject> listQuestioner() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("type","questioner");
        wrapper.orderByDesc("score");

        List<StackInfo> stackInfoList=stackInfoMapper.selectList(wrapper);
        for(StackInfo g:stackInfoList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.getName());
            jsonObject.put("score",g.getScore());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public List<JSONObject> listAnswerer() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("type","answerer");
        wrapper.orderByDesc("score");

        List<StackInfo> stackInfoList=stackInfoMapper.selectList(wrapper);
        for(StackInfo g:stackInfoList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.getName());
            jsonObject.put("score",g.getScore());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public List<JSONObject> listAnswerCnt() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("type","answercnt");
        wrapper.orderByDesc("score");

        List<StackInfo> stackInfoList=stackInfoMapper.selectList(wrapper);
        for(StackInfo g:stackInfoList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.getName());
            jsonObject.put("score",g.getScore());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public List<JSONObject> listViewCnt() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("type","viewcnt");
        wrapper.orderByDesc("score");

        List<StackInfo> stackInfoList=stackInfoMapper.selectList(wrapper);
        for(StackInfo g:stackInfoList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.getName());
            jsonObject.put("score",g.getScore());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
