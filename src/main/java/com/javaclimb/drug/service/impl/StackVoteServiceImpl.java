package com.javaclimb.drug.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaclimb.drug.entity.Githubrepo;
import com.javaclimb.drug.entity.StackVote;
import com.javaclimb.drug.mapper.StackVoteMapper;
import com.javaclimb.drug.service.StackVoteService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-05-11
 */
@Service
public class StackVoteServiceImpl extends JoinServiceImpl<StackVoteMapper, StackVote> implements StackVoteService {
    @Autowired
    private StackVoteMapper stackVoteMapper;
    @Override
    public List<JSONObject> goodquestion() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackVote> wrapper=new QueryWrapper<>();
        wrapper.select("title, link, question_score");
        wrapper.orderByDesc("question_score");

        List<Map<String,Object>> goodquestionList=stackVoteMapper.selectMaps(wrapper);
        for(Map<String,Object> s:goodquestionList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("title",s.get("title"));
            jsonObject.put("link",s.get("link"));
            jsonObject.put("question_score",s.get("question_score"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public StackVote query(String title) {
        QueryWrapper<StackVote> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("title",title);
        return stackVoteMapper.selectOne(queryWrapper);
    }

    @Override
    public List<JSONObject> queryFavourite() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackVote> wrapper=new QueryWrapper<>();
        wrapper.select("title, link, question_score");
        wrapper.eq("favorite",1);
        wrapper.orderByDesc("question_score");

        List<Map<String,Object>> goodquestionList=stackVoteMapper.selectMaps(wrapper);
        for(Map<String,Object> s:goodquestionList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("title",s.get("title"));
            jsonObject.put("link",s.get("link"));
            jsonObject.put("question_score",s.get("question_score"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public List<JSONObject> queryLike(String title) {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackVote> wrapper=new QueryWrapper<>();
        wrapper.select("title, link, question_score");
        wrapper.like("title",title);
        wrapper.orderByDesc("question_score");

        List<Map<String,Object>> goodquestionList=stackVoteMapper.selectMaps(wrapper);
        for(Map<String,Object> s:goodquestionList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("title",s.get("title"));
            jsonObject.put("link",s.get("link"));
            jsonObject.put("question_score",s.get("question_score"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public int questionFavouriteDel(String title) {
        UpdateWrapper<StackVote> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("title",title).set("favorite",0);
        return stackVoteMapper.update(null,updateWrapper);
    }

    @Override
    public int questionFavouriteAdd(String title) {
        UpdateWrapper<StackVote> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("title",title).set("favorite",1);
        return stackVoteMapper.update(null,updateWrapper);
    }
}
