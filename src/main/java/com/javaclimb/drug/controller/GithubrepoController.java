package com.javaclimb.drug.controller;


import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.config.Druid;
import com.javaclimb.drug.entity.Githubrepo;
import com.javaclimb.drug.entity.StackVote;
import com.javaclimb.drug.service.GithubrepoService;
import jdk.nashorn.internal.ir.ReturnNode;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;
import java.util.ArrayList;
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
@RequestMapping("/githubrepo")
public class GithubrepoController {
    @Autowired
    private GithubrepoService githubrepoService;
    @RequestMapping
    public String githubrepo(){return "/githubrepo";}

    @RequestMapping("/goodrepo")
    public String goodrepo(){
        return "/goodrepo";
    }

    @RequestMapping("/goodrepo/list")
    @ResponseBody
    public Object list(){
        List<JSONObject> list=githubrepoService.goodrepo();
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/goodrepo/list2")
    @ResponseBody
    public Object list2(@RequestParam(name="name",required = true)String name) throws SQLException {
//        System.out.println("[[]]]"+name);
        List<JSONArray> list=new ArrayList<>();

        JSONArray hh=new JSONArray();
        hh.appendElement("Income").appendElement("Country").appendElement("Year");
        list.add(hh);

        Connection con= Druid.getConnection();
        PreparedStatement stmt=con.prepareStatement("select starnum,repo,startime2 from repostarhistory where repo =? order by startime2;");
        stmt.setString(1,name);
        ResultSet resultSet=stmt.executeQuery();
        while (resultSet.next()){
            JSONArray array=new JSONArray();
            array.appendElement(resultSet.getString("starnum"))
                    .appendElement(resultSet.getString("repo"))
                    .appendElement(resultSet.getString("startime2"));
            list.add(array);
        }

        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/goodrepo/list3")
    @ResponseBody
    public Object list3(@RequestParam(name="name",required = true)String name) throws SQLException {
//        System.out.println("[[]]]"+name);
        List<JSONArray> list=new ArrayList<>();

        JSONArray hh=new JSONArray();
        hh.appendElement("Income").appendElement("Country").appendElement("Year");
        list.add(hh);

        Connection con= Druid.getConnection();
        PreparedStatement stmt=con.prepareStatement("select startime2,repo,sum(starnum) over (partition by repo order by startime2) as starsum from repostarhistory\n" +
                "where repo=? order by startime2;");
        stmt.setString(1,name);
        ResultSet resultSet=stmt.executeQuery();
        while (resultSet.next()){
            JSONArray array=new JSONArray();
            array.appendElement(resultSet.getString("starsum"))
                    .appendElement(resultSet.getString("repo"))
                    .appendElement(resultSet.getString("startime2"));
            list.add(array);
        }

        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/repoSearch")
    @ResponseBody
    public Object querylist(@RequestParam(name="name",required = true)String name){
        List<JSONObject> list=githubrepoService.queryLike(name);
        return ResultMapUtil.getHashMapList(list);
    }
    @RequestMapping("/repoQuery")
    public String repoQuery(@RequestParam(name="name",required = true)String name, Model model){
        Githubrepo githubrepo =githubrepoService.query(name);
        model.addAttribute("obj",githubrepo);
        return "/repoPage";
    }

    @RequestMapping("/repofavourite")
    public String repofavourite(){
        return "/repofavourite";
    }

    @RequestMapping("/repofavourite/list")
    @ResponseBody
    public Object repofavouritelist(){
        List<JSONObject> list=githubrepoService.queryFavourite();
        return ResultMapUtil.getHashMapList(list);
    }

    @RequestMapping("/repofavourite/del")
    @ResponseBody
    public Object repofavouriteDel(String name){
        try{
            int i=githubrepoService.repoFavouriteDel(name);
            return ResultMapUtil.getHashMapDel(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }
    @RequestMapping("/repofavourite/add")
    @ResponseBody
    public Object repofavouriteAdd(String name){
        try{
            int i=githubrepoService.repoFavouriteAdd(name);
            return ResultMapUtil.getHashMapDel(i);
        }catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    @RequestMapping("/repohotpage")
    public Object repohot(@RequestParam(name="name",required = true)String name, Model model){

        model.addAttribute("name",name);
//        System.out.println("晃晃"+name);
        return "/repohotpage";
    }

    @RequestMapping("/repohistorypage")
    public Object repohistory(@RequestParam(name="name",required = true)String name, Model model){

        model.addAttribute("name",name);
//        System.out.println("晃晃"+name);
        return "/repohistorypage";
    }

}
