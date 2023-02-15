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

public class GithubTopicPipeline implements Pipeline {
    public static Connection con = null;
    public static PreparedStatement githubTopic_stmt = null;
    public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    public Lock balanceChangeLock =new ReentrantLock();

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
            githubTopic_stmt = con.prepareStatement("insert ignore into githubtopic values(?,?,?,?,?,?)");
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
                if (githubTopic_stmt != null) {
                    githubTopic_stmt.close();
                }
                Druid.closeAll(con);
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    public static void load_Githubtopic(Githubtopic tmp){

        try {
            if(con!=null){
                githubTopic_stmt.setLong(1,tmp.getId());
                githubTopic_stmt.setString(2,tmp.getTopic());
                githubTopic_stmt.setString(3,tmp.getName());
                githubTopic_stmt.setString(4,tmp.getOwner());
                githubTopic_stmt.setString(5,tmp.getOwnerType());
                githubTopic_stmt.setDate(6, new java.sql.Date (tmp.getCreatedAt().getTime()));
                githubTopic_stmt.addBatch();
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
            List<Githubtopic> githubtopic =resultItems.get("githubtopic");
            githubtopic.stream().forEach(o->load_Githubtopic(o));
            try {
                githubTopic_stmt.executeBatch();
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
