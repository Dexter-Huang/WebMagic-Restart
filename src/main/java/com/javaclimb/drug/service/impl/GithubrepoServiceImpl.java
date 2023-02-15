package com.javaclimb.drug.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaclimb.drug.entity.Githubrepo;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.entity.StackVote;
import com.javaclimb.drug.mapper.GithubrepoMapper;
import com.javaclimb.drug.service.GithubrepoService;
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
 * @author Dexter-Huang
 * @since 2022-05-09
 */
@Service
public class GithubrepoServiceImpl extends JoinServiceImpl<GithubrepoMapper, Githubrepo> implements GithubrepoService {
    @Autowired
    private GithubrepoMapper githubrepoMapper;

    @Override
    public List<JSONObject> goodrepo() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<Githubrepo> wrapper=new QueryWrapper<>();
        wrapper.select("name,owner, star,forks");
        wrapper.orderByDesc("star","forks");

        List<Map<String,Object>> goodrepoList=githubrepoMapper.selectMaps(wrapper);
        for(Map<String,Object> s:goodrepoList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",s.get("name"));
            jsonObject.put("owner",s.get("owner"));
            jsonObject.put("star",s.get("star"));
            jsonObject.put("forks",s.get("forks"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public List<JSONObject> queryLike(String repoName) {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<Githubrepo> wrapper=new QueryWrapper<>();
        wrapper.select("name, owner, star,forks");
        wrapper.like("name",repoName);
        wrapper.orderByDesc("star");

        List<Map<String,Object>> goodquestionList=githubrepoMapper.selectMaps(wrapper);
        for(Map<String,Object> s:goodquestionList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",s.get("name"));
            jsonObject.put("owner",s.get("owner"));
            jsonObject.put("star",s.get("star"));
            jsonObject.put("forks",s.get("forks"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public int repoFavouriteDel(String repoName) {
        UpdateWrapper<Githubrepo> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("name",repoName).set("favorite",0);
        return githubrepoMapper.update(null,updateWrapper);
    }

    @Override
    public int repoFavouriteAdd(String repoName) {
        UpdateWrapper<Githubrepo> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("name",repoName).set("favorite",1);
        return githubrepoMapper.update(null,updateWrapper);
    }

    @Override
    public List<JSONObject> queryFavourite() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<Githubrepo> wrapper=new QueryWrapper<>();
        wrapper.select("name, owner,star,forks");
        wrapper.eq("favorite",1);
        wrapper.orderByDesc("star");

        List<Map<String,Object>> goodrepoList=githubrepoMapper.selectMaps(wrapper);
        for(Map<String,Object> s:goodrepoList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",s.get("name"));
            jsonObject.put("owner",s.get("owner"));
            jsonObject.put("star",s.get("star"));
            jsonObject.put("forks",s.get("forks"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public IPage<Githubrepo> selectPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Githubrepo> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(param)){
            queryWrapper.like("name",param);
        }
        Page<Githubrepo> page=new Page<>(pageNum,pageSize);
        return githubrepoMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int add(Githubrepo githubrepo) {
        return githubrepoMapper.insert(githubrepo);
    }

    @Override
    public int edit(Githubrepo githubrepo) {
        QueryWrapper<Githubrepo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",githubrepo.getId());
        return githubrepoMapper.update(githubrepo,queryWrapper);
    }

    @Override
    public Githubrepo query(String name) {
        QueryWrapper<Githubrepo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return githubrepoMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Githubrepo> queryAll() {
        return githubrepoMapper.selectList(null);
    }

    @Override
    public int del(Long id) {
        QueryWrapper<Githubrepo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return githubrepoMapper.delete(queryWrapper);
    }
    @Override
    public List<JSONObject> wordcloud(){
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<Githubrepo> wrapper=new QueryWrapper<>();

        wrapper.select("topic,count(*) as cnt");
        wrapper.groupBy("topic");
        wrapper.orderByDesc("cnt");

        List<Map<String, Object>> githubtopicList=githubrepoMapper.selectMaps(wrapper);
        for(Map<String,Object> g:githubtopicList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.get("topic"));
            jsonObject.put("value",Integer.parseInt(String.valueOf(g.get("cnt"))));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
