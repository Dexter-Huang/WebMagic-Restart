package com.javaclimb.drug.task.github;

import com.javaclimb.drug.config.Druid;
import com.javaclimb.drug.entity.Githubrepo;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.service.GithubrepoService;
import com.javaclimb.drug.service.impl.GithubrepoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GithubRepoPipeline implements Pipeline {
    public static Connection con = null;
    public static PreparedStatement githubRepo_stmt = null;
    public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    public Lock balanceChangeLock=new ReentrantLock();

    public static void openDB(String dbname) {
        try {
            //
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
            System.err.println("Cannot find the Mysql driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            con = Druid.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }


        try {
            githubRepo_stmt = con.prepareStatement("insert ignore into githubrepo values(?,?,?,?,?,?,?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }
    public static void commit(){
        try {
            con.commit();
        } catch (SQLException throwables) {
            System.err.println("SQL error: " + throwables.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        }
    }

    public static void closeDB() {
        if (con != null) {
            try {
                if (githubRepo_stmt != null) {
                    githubRepo_stmt.close();
                }
                Druid.closeAll(con);
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    public static void load_Githubrepo(Githubrepo tmp){
        try {
            if(con!=null){
                githubRepo_stmt.setString(1,tmp.getName());
                githubRepo_stmt.setString(2,tmp.getOwner());
                githubRepo_stmt.setString(3,tmp.getOwnerType());
                githubRepo_stmt.setDate(4, new java.sql.Date (tmp.getCreatedAt().getTime()));
                githubRepo_stmt.setInt(5,tmp.getStar());
                githubRepo_stmt.setInt(6,tmp.getForks());
                githubRepo_stmt.setLong(7,tmp.getId());
                githubRepo_stmt.setString(8,tmp.getDescription());
                githubRepo_stmt.setString(9,tmp.getHtmlUrl());
                githubRepo_stmt.setInt(10,tmp.getFavorite());
                githubRepo_stmt.addBatch();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        balanceChangeLock.lock();
        try{
            openDB("webmagic");
            List<Githubrepo> githubrepo =resultItems.get("githubrepo");
            githubrepo.stream().forEach(o->load_Githubrepo(o));
            try {
                githubRepo_stmt.executeBatch();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            commit();
            closeDB();
        }finally {
            balanceChangeLock.unlock();
        }

        }

}
