package com.javaclimb.drug.controller;


import org.springframework.ui.Model;
import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.entity.StackVote;
import com.javaclimb.drug.service.StackVoteService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/stackvote")
public class StackVoteController {
    @Autowired
    private StackVoteService stackVoteService;
    @RequestMapping
    public String stackvote(){
        return "/stackvote";
    }

    @RequestMapping("/goodquestion")
    public String goodquestion(){
        return "/goodquestion";
    }

    @RequestMapping("/goodquestion/list")
    @ResponseBody
    public Object list(){
        List<JSONObject> list=stackVoteService.goodquestion();
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/questionSearch")
    @ResponseBody
    public Object querylist(@RequestParam(name="title",required = true)String title){
        List<JSONObject> list=stackVoteService.queryLike(title);
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/questionQuery")
    public String questionQuery(@RequestParam(name="title",required = true)String title, Model model){
        StackVote stackVote=stackVoteService.query(title);
        model.addAttribute("obj",stackVote);
        return "/questionPage";
    }

    @RequestMapping("/questionfavourite")
    public String questionfavourite(){
        return "/questionfavourite";
    }

    @RequestMapping("/questionfavourite/list")
    @ResponseBody
    public Object questionfavouritelist(){
        List<JSONObject> list=stackVoteService.queryFavourite();
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/questionfavourite/del")
    @ResponseBody
    public Object questionfavouriteDel(String title){
        try{
            int i=stackVoteService.questionFavouriteDel(title);
            return ResultMapUtil.getHashMapDel(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }
    @RequestMapping("/questionfavourite/add")
    @ResponseBody
    public Object questionfavouriteAdd(String title){
        try{
            int i=stackVoteService.questionFavouriteAdd(title);
            return ResultMapUtil.getHashMapDel(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }
}
