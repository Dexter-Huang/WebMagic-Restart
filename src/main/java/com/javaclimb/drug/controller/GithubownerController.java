package com.javaclimb.drug.controller;


import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.service.GithubownerService;
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
 * @author Dexter-Huang
 * @since 2022-05-09
 */
@Controller
@RequestMapping("/githubowner")
public class GithubownerController {
    @Autowired
    private GithubownerService githubownerService;
    @RequestMapping
    public String githubowner(){return "/githubowner";}

    @RequestMapping("/star")
    @ResponseBody
    public Object star(){
        List<JSONObject> list=githubownerService.star();
        return ResultMapUtil.getHashMapList(list);
    }
}
