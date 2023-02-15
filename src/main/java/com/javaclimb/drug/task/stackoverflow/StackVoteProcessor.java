package com.javaclimb.drug.task.stackoverflow;

import com.javaclimb.drug.entity.StackVote;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackVoteProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        String str=page.getRawText();
//        System.out.println(str);
        List<String> questionId=new JsonPathSelector("$.items[*].question_id").selectList(str);
        List<String> answerId=new JsonPathSelector("$.items[*].accepted_answer_id").selectList(str);
        List<String> answerCnt=new JsonPathSelector("$.items[*].answer_count").selectList(str);
        List<String> viewCnt=new JsonPathSelector("$.items[*].view_count").selectList(str);
        List<String> link=new JsonPathSelector("$.items[*].link").selectList(str);
        List<String> title=new JsonPathSelector("$.items[*].title").selectList(str);
        List<String> questionScore=new JsonPathSelector("$.items[*].score").selectList(str);
        List<String> questioner=new JsonPathSelector("$.items[*].owner.display_name").selectList(str);
        List<StackVote> stackVotes=new ArrayList<>();
        StringBuilder sb=new StringBuilder("");
        answerId.stream().forEach(o->sb.append(";").append(o));
        String ans_ids=sb.substring(1);
        String url="https://api.stackexchange.com/2.3/answers/"+ans_ids+"?site=stackoverflow";
        //建立一个新的请求客户端
        CloseableHttpClient httpClient=HttpClients.createDefault();

        //使用HttpGet的方式请求网址
        HttpGet httpGet = new HttpGet(url);
        //获取网址的返回结果
        CloseableHttpResponse response=null;
        try {
            response=httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取返回结果中的实体
        HttpEntity entity = response.getEntity();
        String str1="";
        try {
            str1=EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        List<String> qid=new JsonPathSelector("$.items[*].question_id").selectList(str1);
        List<String> ans_score=new JsonPathSelector("$.items[*].score").selectList(str1);
        List<String> ans_name=new JsonPathSelector("$.items[*].owner.display_name").selectList(str1);

        for(int i=0;i<questionId.size();i++){
            int target=-1;
            for(int j=0;j<qid.size();j++){
                if(qid.get(j).equals(questionId.get(i))){
                    target=j;
                    break;
                }
            }
            if(target!=-1){
                stackVotes.add(new StackVote(Long.parseLong(questionId.get(i)),ans_name.get(target),Integer.parseInt(ans_score.get(target)),
                                            questioner.get(i),Integer.parseInt(questionScore.get(i)),Integer.parseInt(answerCnt.get(i)),
                                            Integer.parseInt(viewCnt.get(i)),link.get(i),title.get(i),0));
            }
            else {
                stackVotes.add(new StackVote(Long.parseLong(questionId.get(i)),"null",0,
                        questioner.get(i),Integer.parseInt(questionScore.get(i)),Integer.parseInt(answerCnt.get(i)),
                        Integer.parseInt(viewCnt.get(i)),link.get(i),title.get(i),0));
            }
        }

        page.putField("stackvote",stackVotes);

    }
    private Site site=Site.me()
            .setCharset("UTF-8")
            .setTimeOut(100000)
            .setRetrySleepTime(2500)
            .setRetryTimes(3);
    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider.create(new StackVoteProcessor())
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
                .addPipeline(new StackVotePipeline())
                .addPipeline(new ConsolePipeline())
                .thread(10)
                .run();
    }
}
