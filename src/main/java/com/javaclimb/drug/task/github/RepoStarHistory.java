package com.javaclimb.drug.task.github;

import com.javaclimb.drug.config.Druid;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class RepoStarHistory {
    public static void main(String[] args) throws IOException, InterruptedException {


        BufferedReader br=new BufferedReader(new FileReader("1.csv"));
        String repo,target,arr[],url;
        int t;
        while ((repo=br.readLine())!=null){
            arr=repo.split(",");
            repo=arr[0];
            t=Integer.parseInt(arr[1]);
            HashMap<String,Integer> hashmap=new HashMap<>();
//            System.out.println("~~~~~~~t~~~~~~~"+t);
            for(int i=1;i<=t;i++){
                url="https://api.github.com/repos/"+repo+"/stargazers?page="+i+"&per_page=100";
                CloseableHttpClient httpClient= HttpClients.createDefault();
                //使用HttpGet的方式请求网址
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Accept","application/vnd.github.v3.star+json");
                httpGet.setHeader("Authorization","token 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");
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
                    String json= EntityUtils.toString(entity);
//                    System.out.println(json);
                    List<String> star_at=new JsonPathSelector("$[*].starred_at").selectList(json);
//                    Thread.sleep(800);
                    for(String tmp:star_at){
                        String target2=repo+"/"+tmp.substring(0,7);
                        if(hashmap.containsKey(target2)){
                            Integer num=hashmap.get(target2);
                            num++;
                            hashmap.put(target2,num);
                        }else{
                            hashmap.put(target2,1);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            //结束了一个项目
            try {
                Connection con = Druid.getConnection();
                con.setAutoCommit(false);
                PreparedStatement stmt=con.prepareStatement("insert ignore into repostarhistory values (?,?,?,?,?);");
                for(String key:hashmap.keySet()){
//                        System.out.println(key);
                    String[] strArray=key.split("/");
                    stmt.setString(1,strArray[0]);
                    stmt.setString(2,strArray[1]);
                    stmt.setString(3,strArray[2]);
                    stmt.setInt(4,Integer.parseInt(strArray[2].replace("-","")));
                    stmt.setInt(5,hashmap.get(key));
                    stmt.addBatch();
                }
                stmt.executeBatch();
                con.commit();
                stmt.clearBatch();
                Druid.closeAll(con);
            } catch (SQLException e) {
                System.err.println("Database connection failed");
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

    }
}
