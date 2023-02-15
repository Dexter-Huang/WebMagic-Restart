package com.javaclimb.drug.controller;

import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.service.RepoCntPerMonthService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/piecnt")
public class RepoCntPerMonthController {
    @Autowired
    private RepoCntPerMonthService repoCntPerMonthService;
    @RequestMapping
    public String hh(){
        return "/piecnt";
    }

    @RequestMapping("/pielist")
    @ResponseBody
    public Object data(){
        List<JSONObject> list =repoCntPerMonthService.pielist();
        return ResultMapUtil.getHashMapList(list);
    }

}
