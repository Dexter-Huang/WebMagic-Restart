package com.javaclimb.drug.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaclimb.drug.entity.Githubrepo;
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
public interface GithubrepoService extends JoinIService<Githubrepo> {
    public IPage<Githubrepo> selectPage(int pageNum, int pageSize, String param);
    public int add (Githubrepo githubrepo);
    public int edit (Githubrepo githubrepo);
    public Githubrepo query(String name);
    public List<Githubrepo> queryAll();
    public int del(Long id);
    public List<JSONObject> wordcloud();
    public List<JSONObject> goodrepo();
    public List<JSONObject> queryLike(String title);
    public int repoFavouriteDel(String repoName);
    public int repoFavouriteAdd(String repoName);
    public List<JSONObject> queryFavourite();

}
