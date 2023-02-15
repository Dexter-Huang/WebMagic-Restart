package com.javaclimb.drug.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaclimb.drug.entity.Githubrepo;
import com.javaclimb.drug.entity.StackVote;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;
import net.minidev.json.JSONObject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-05-11
 */
public interface StackVoteService extends JoinIService<StackVote> {
    public List<JSONObject> goodquestion();
    public StackVote query(String title);
    public List<JSONObject> queryFavourite();
    public List<JSONObject> queryLike(String title);
    public int questionFavouriteDel(String title);
    public int questionFavouriteAdd(String title);
}
