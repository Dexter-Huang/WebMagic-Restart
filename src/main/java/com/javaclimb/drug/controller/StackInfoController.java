package com.javaclimb.drug.controller;

import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.service.StackInfoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/stackinfo")
public class StackInfoController {
    @Autowired
    private StackInfoService stackInfoService;
    @RequestMapping
    public String stackinfo(){
        return "/stackinfo";
    }

    @RequestMapping("/questioner")
    @ResponseBody
    public Object listQusetioner(){
        List<JSONObject> list=stackInfoService.listQuestioner();
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/answerer")
    @ResponseBody
    public Object listAnswerer(){
        List<JSONObject> list=stackInfoService.listAnswerer();
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/answercnt")
    @ResponseBody
    public Object listAnswerCnt(){
        List<JSONObject> list=stackInfoService.listAnswerCnt();
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/viewcnt")
    @ResponseBody
    public Object list(){
        List<JSONObject> list=stackInfoService.listViewCnt();
        return ResultMapUtil.getHashMapList(list);
    }


}
