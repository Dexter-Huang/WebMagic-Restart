package com.javaclimb.drug.controller;


import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.service.StackTagsService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-05-11
 */
@Controller
@RequestMapping("/stacktags")
public class StackTagsController {
    @Autowired
    private StackTagsService stackTagsService;
    @RequestMapping
    public String stacktags(){return "/stacktags";}

    @RequestMapping("/wordcloud")
    @ResponseBody
    public Object wordcloud(){
        List<JSONObject> list=stackTagsService.wordcloud();
        return ResultMapUtil.getHashMapList(list);
    }
}
