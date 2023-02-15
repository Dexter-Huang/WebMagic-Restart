package com.javaclimb.drug.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaclimb.drug.entity.Githubowner;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;
import net.minidev.json.JSONObject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Dexter-Huang
 * @since 2022-05-09
 */
public interface GithubownerService extends JoinIService<Githubowner> {
    public IPage<Githubowner> selectPage(int pageNum, int pageSize, String param);
    public int add (Githubowner githubowner);
    public int edit (Githubowner githubowner);
    public Githubowner query(String name);
    public List<Githubowner> queryAll();
    public int del(String name);
    public List<JSONObject> star();
}
