package com.javaclimb.drug.task.meituan;

import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.*;
import java.util.List;

public class MeituanProcessor {
    public static void main(String[] args) throws IOException {
        File file = new File("1.txt");



        BufferedReader br = new BufferedReader(new FileReader(file));



        String st,str="";

        while ((st = br.readLine()) != null)
            str+=st;

        List<String> array=new JsonPathSelector("$.data.searchresult").selectList(str);


    }

}
