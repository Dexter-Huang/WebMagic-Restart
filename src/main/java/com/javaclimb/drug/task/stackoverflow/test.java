package com.javaclimb.drug.task.stackoverflow;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;
import java.util.List;

public class test {
    //声明需要爬取的网址
    static String URL="https://oauth2:access_token=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@api.github.com/repos/unclebob/fitnesse/stargazers?q=created:2021-01..2022-01&page=1&per_page=100";

    //主函数入口
    public static void main(String args[]){
        //建立一个新的请求客户端
        CloseableHttpClient httpClient=HttpClients.createDefault();

        //使用HttpGet的方式请求网址
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Accept","application/vnd.github.v3.star+json");

        //获取网址的返回结果
        CloseableHttpResponse response=null;
        try {
            response=httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取返回结果中的实体
        HttpEntity entity = response.getEntity();

        //将返回的实体输出
        try {
            String str=EntityUtils.toString(entity);
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getAnswer(List<String> answer_id) throws IOException {
        StringBuilder sb=new StringBuilder("");
        answer_id.stream().forEach(o->sb.append(o).append(";"));
        String url="https://api.stackexchange.com/2.3/answers/"+sb.toString()+"?site=stackoverflow";
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
        return EntityUtils.toString(entity);
    }
}