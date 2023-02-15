package com.javaclimb.drug.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.entity.RepoCntPerMonth;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;
import net.minidev.json.JSONObject;

import java.util.List;

public interface RepoCntPerMonthService extends JoinIService<RepoCntPerMonth> {
    public List<JSONObject> pielist();
}
