package com.javaclimb.drug.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.entity.StackTags;
import com.javaclimb.drug.mapper.StackTagsMapper;
import com.javaclimb.drug.service.StackTagsService;
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
public class StackTagsServiceImpl extends JoinServiceImpl<StackTagsMapper, StackTags> implements StackTagsService {
    @Autowired
    private StackTagsMapper stackTagsMapper;
    @Override
    public List<JSONObject> wordcloud(){
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<StackTags> wrapper=new QueryWrapper<>();

        wrapper.select("tags,count(*) as cnt");
        wrapper.groupBy("tags");
        wrapper.orderByDesc("cnt");

        List<Map<String, Object>> stackTagsList=stackTagsMapper.selectMaps(wrapper);
        for(Map<String,Object> g:stackTagsList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",g.get("tags"));
            jsonObject.put("value",Integer.parseInt(String.valueOf(g.get("cnt"))));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
