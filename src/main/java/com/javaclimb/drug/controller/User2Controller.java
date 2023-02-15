package com.javaclimb.drug.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javaclimb.drug.common.ResultMapUtil;
import com.javaclimb.drug.entity.User;
import com.javaclimb.drug.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class User2Controller {
@Autowired
    private IUserService iUserService;

    @RequestMapping
    public String user2(){
        return "/user";
    }
    @RequestMapping(value = "/userQueryPage")
    @ResponseBody
    public Object supplierQueryPage(String param, @RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "1000")int pageSize){
        try{
            IPage<User> iPage = iUserService.selectUserPage(pageNum,pageSize,param);
            return ResultMapUtil.getHashMapMysqlPage(iPage);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }


    @RequestMapping(value = "/addUserPage")
    public String userPage(){
        return "/userPage";
    }

    @RequestMapping(value = "/userAdd")
    @ResponseBody
    public Object userAdd(User user){
        try{
            int i = iUserService.addUser(user);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }


    @RequestMapping(value = "/userQueryById")
    public String userQueryById(@RequestParam(name = "id",required = true)Integer id, Model model){
        User user = iUserService.queryUserById(id);
        model.addAttribute("obj",user);
        return "/userPage";
    }


    @RequestMapping(value = "/userEdit")
    @ResponseBody
    public Object userEdit(User user){
        try{
            int i = iUserService.editUser(user);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }


    @RequestMapping(value = "/userDelById")
    @ResponseBody
    public Object userDelById(Integer id){
        try{
            int i = iUserService.delUserById(id);
            return ResultMapUtil.getHashMapDel(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 获取所有出版社
     */
    @RequestMapping(value = "/userList")
    @ResponseBody
    public Object userList(){
        List<User> userList = iUserService.queryUserList();
        return ResultMapUtil.getHashMapList(userList);
    }

}
