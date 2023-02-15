package com.javaclimb.drug.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaclimb.drug.entity.StackInfo;
import com.javaclimb.drug.entity.StackTags;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;
import net.minidev.json.JSONObject;

import java.util.List;

public interface StackInfoService extends JoinIService<StackInfo> {
    public List<JSONObject> listQuestioner();
    public List<JSONObject> listAnswerer();
    public List<JSONObject> listAnswerCnt();
    public List<JSONObject> listViewCnt();

}
