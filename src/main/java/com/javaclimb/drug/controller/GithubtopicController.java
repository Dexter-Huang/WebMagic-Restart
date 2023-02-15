package com.javaclimb.drug.controller;


import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.service.GithubtopicService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Dexter-Huang
 * @since 2022-05-09
 */
@Controller
@RequestMapping("/githubtopic")
public class GithubtopicController {
    @Autowired
    private GithubtopicService githubtopicService;
    @RequestMapping
    public String githubtopic(){return "/githubtopic";}

    @RequestMapping("/wordcloud")
    @ResponseBody
    public Object wordcloud(){
        List<JSONObject> list=githubtopicService.wordcloud();
        return ResultMapUtil.getHashMapList(list);
    }
}
