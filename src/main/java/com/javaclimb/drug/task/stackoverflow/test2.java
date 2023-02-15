package com.javaclimb.drug.task.stackoverflow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class test2 {
    public static void main(String[] args) throws SQLException, IOException {
        Connection con = null;
        String url = "jdbc:mysql://" + "localhost" + "/" + "webmagic";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
        try {
            con = DriverManager.getConnection(url, props);
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        PreparedStatement s=con.prepareStatement("select tmp.kk, tmp.star from (select concat(owner,'/',name) as kk,star from githubrepo order by star desc limit 10) as tmp order by star asc");
        ResultSet resultSet=s.executeQuery();
        BufferedWriter bw=new BufferedWriter(new FileWriter("1.csv"));
        while (resultSet.next()){
            int tmp=Integer.parseInt(resultSet.getString("star"));
            int t=(tmp+100-1)/100;
            bw.write(resultSet.getString("kk")+","+t+"\n");
        }
        bw.flush();
        bw.close();

    }
}
