package com.javaclimb.drug.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaclimb.drug.entity.Githubowner;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.mapper.GithubownerMapper;
import com.javaclimb.drug.service.GithubownerService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import net.minidev.json.JSONObject;
import org.apache.ibatis.mapping.ResultMap;
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
public class GithubownerServiceImpl extends JoinServiceImpl<GithubownerMapper, Githubowner> implements GithubownerService {
    @Autowired
    private GithubownerMapper githubownerMapper;
    @Override
    public IPage<Githubowner> selectPage(int pageNum, int pageSize, String param) {
        QueryWrapper<Githubowner> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(param)){
            queryWrapper.like("name",param);
        }
        Page<Githubowner> page=new Page<>(pageNum,pageSize);
        return githubownerMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int add(Githubowner githubowner) {
        return githubownerMapper.insert(githubowner);
    }

    @Override
    public int edit(Githubowner githubowner) {
        QueryWrapper<Githubowner> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",githubowner.getName());

        return githubownerMapper.update(githubowner,queryWrapper);
    }

    @Override
    public Githubowner query(String name) {
        QueryWrapper<Githubowner> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return githubownerMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Githubowner> queryAll() {
        return githubownerMapper.selectList(null);
    }

    @Override
    public int del(String name) {
        QueryWrapper<Githubowner> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return githubownerMapper.delete(queryWrapper);
    }

    @Override
    public List<JSONObject> star() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<Githubowner> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("star_num");

        List<Githubowner> githubownerList=githubownerMapper.selectList(wrapper);
        for(Githubowner g:githubownerList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.getName());
            jsonObject.put("type",g.getType());
            jsonObject.put("star_num",g.getStarNum());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
