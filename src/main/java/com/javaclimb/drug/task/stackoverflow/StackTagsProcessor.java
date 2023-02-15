package com.javaclimb.drug.task.stackoverflow;

import com.javaclimb.drug.entity.StackTags;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.List;

public class StackTagsProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        String str=page.getRawText();
//        System.out.println(str);
        List<String> questionId=new JsonPathSelector("$.items[*].question_id").selectList(str);
        List<String> tags= (new JsonPathSelector("$.items[*].tags").selectList(str));
        List<StackTags> list=new ArrayList<>();
        for(int i=0;i<questionId.size();i++){
            String[] tagslist=tags.get(i).substring(1,tags.get(i).length()-1).split(",");
            for(int j=0;j<tagslist.length;j++){
                StackTags stackTags=new StackTags(Long.parseLong(questionId.get(i)),tagslist[j].substring(1,tagslist[j].length()-1));
                list.add(stackTags);
            }
        }
        page.putField("stacktags",list);
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
        Spider.create(new StackTagsProcessor())
                .addUrl("https://api.stackexchange.com/2.3/search?page=1&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=2&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=3&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=4&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=5&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=6&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=7&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=8&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=9&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addUrl("https://api.stackexchange.com/2.3/search?page=10&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow")
                .addPipeline(new StackTagsPipeline())
                .addPipeline(new ConsolePipeline())
                .thread(10)
                .run();
    }
}
