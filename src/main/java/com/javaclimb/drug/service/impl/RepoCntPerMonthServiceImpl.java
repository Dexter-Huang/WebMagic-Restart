package com.javaclimb.drug.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.entity.RepoCntPerMonth;
import com.javaclimb.drug.mapper.GithubtopicMapper;
import com.javaclimb.drug.mapper.RepoCntPerMonthMapper;
import com.javaclimb.drug.service.GithubtopicService;
import com.javaclimb.drug.service.RepoCntPerMonthService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RepoCntPerMonthServiceImpl extends JoinServiceImpl<RepoCntPerMonthMapper, RepoCntPerMonth> implements RepoCntPerMonthService {
    @Autowired RepoCntPerMonthMapper repoCntPerMonthMapper;
    @Override
    public List<JSONObject> pielist() {
        List<JSONObject> jsonArray=new ArrayList<>();
        QueryWrapper<RepoCntPerMonth> wrapper=new QueryWrapper<>();
        wrapper.select("repoCnt,rate");
        wrapper.orderByAsc("year","month");

        List<Map<String, Object>> repocntList=repoCntPerMonthMapper.selectMaps(wrapper);
        for(Map<String,Object> g:repocntList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("rate",g.get("rate"));
            jsonObject.put("repoCnt",g.get("repoCnt"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
