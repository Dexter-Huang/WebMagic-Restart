package com.javaclimb.drug.task.github;

import com.javaclimb.drug.common.DateUtil;
import com.javaclimb.drug.entity.Githubrepo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GithubRepoProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        String str=page.getHtml().regex("\\{.*\\}").toString();
        List<String> id=new JsonPathSelector("$.items[*].id").selectList(str);
        List<String> name=new JsonPathSelector("$.items[*].name").selectList(str);
        List<String> owner=new JsonPathSelector("$.items[*].owner.login").selectList(str);
        List<String> ownerType=new JsonPathSelector("$.items[*].owner.type").selectList(str);
        List<String> createdAt=new JsonPathSelector("$.items[*].created_at").selectList(str);
        List<String> stars=new JsonPathSelector("$.items[*].stargazers_count").selectList(str);
        List<String> forks=new JsonPathSelector("$.items[*].forks").selectList(str);
        List<String> description=new JsonPathSelector("$.items[*].description").selectList(str);
        List<String> htmlUrl=new JsonPathSelector("$.items[*].html_url").selectList(str);


        List<Githubrepo> list=new ArrayList<>();
        for(int i=0;i<name.size();i++){
            Githubrepo githubrepo= null;
            try {
                githubrepo = new Githubrepo(Long.parseLong(id.get(i)),name.get(i),owner.get(i),ownerType.get(i), DateUtil.StrConvertDate(createdAt.get(i)),
                                            Integer.parseInt(stars.get(i)),Integer.parseInt(forks.get(i)),description.get(i),htmlUrl.get(i),0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(githubrepo);
        }
        page.putField("githubrepo",list);
    }

    private Site site=Site.me()
            .setCharset("UTF-8")
            .setTimeOut(100000)
            .setRetrySleepTime(1000)
            .setRetryTimes(3);
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoProcessor())
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=1&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=2&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=3&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=4&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=5&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=6&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=7&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=8&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=9&per_page=100")
                .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=10&per_page=100")
                .addPipeline(new GithubRepoPipeline())
                .addPipeline(new ConsolePipeline())
                .thread(10)
                .run();
    }
}
