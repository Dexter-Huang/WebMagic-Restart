package com.javaclimb.drug.task.github;

import com.javaclimb.drug.common.DateUtil;
import com.javaclimb.drug.entity.Githubtopic;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GithubTopicProcessor implements PageProcessor
{
    @Override
    public void process(Page page) {
        String str=page.getHtml().regex("\\{.*\\}").toString();
        List<String> id=new JsonPathSelector("$.items[*].id").selectList(str);
        List<String> topic= (new JsonPathSelector("$.items[*].topics").selectList(str));
        List<String> name=new JsonPathSelector("$.items[*].name").selectList(str);
        List<String> owner=new JsonPathSelector("$.items[*].owner.login").selectList(str);
        List<String> ownerType=new JsonPathSelector("$.items[*].owner.type").selectList(str);
        List<String> createdAt=new JsonPathSelector("$.items[*].created_at").selectList(str);
        List<Githubtopic> list=new ArrayList<>();
        for(int i=0;i<id.size();i++){
            try {
                if(topic.get(i).length()>2) {
                    String[] topiclist=topic.get(i).substring(1,topic.get(i).length()-1).split(",");
                    for(int j=0;j<topiclist.length;j++){
                        String hh=topiclist[j];
                        Githubtopic githubtopic=new Githubtopic(Long.parseLong(id.get(i)), topiclist[j].substring(1,topiclist[j].length()-1),
                                name.get(i), owner.get(i),ownerType.get(i), DateUtil.StrConvertDate(createdAt.get(i)));
                        list.add(githubtopic);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        page.putField("githubtopic",list);
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
            Spider.create(new GithubTopicProcessor())
                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page=1&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+2+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+3+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+4+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+5+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+6+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+7+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+8+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+9+"&per_page=100")
//                    .addUrl("https://api.github.com/search/repositories?q=language:java&sort=stars&page="+10+"&per_page=100")
                    .addPipeline(new GithubTopicPipeline())
                    .addPipeline(new ConsolePipeline())
                    .thread(3)
                    .run();
    }
}
