package com.javaclimb.drug.task.github;

import com.javaclimb.drug.config.Druid;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.entity.RepoCntPerMonth;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RepoCntPerMonthPipeline implements Pipeline {
    public static Connection con = null;
    public static PreparedStatement repoCntPerMonth_stmt = null;
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
        String url = "jdbc:mysql://127.0.0.1/"+dbname+"?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
        try {
            con = Druid.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        try {
            repoCntPerMonth_stmt = con.prepareStatement("insert ignore into repo_cnt_per_month values(?,?,?)");
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
                if (repoCntPerMonth_stmt != null) {
                    repoCntPerMonth_stmt.close();
                }
                Druid.closeAll(con);
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    public static void load_RepoCntPerMonth(RepoCntPerMonth tmp){

        try {
            if(con!=null){
                repoCntPerMonth_stmt.setInt(1,tmp.getYear());
                repoCntPerMonth_stmt.setInt(2,tmp.getMonth());
                repoCntPerMonth_stmt.setInt(3,tmp.getRepoCnt());
                repoCntPerMonth_stmt.addBatch();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        balanceChangeLock.lock();
        try{
//            openDB("webmagic");
            RepoCntPerMonth rr =resultItems.get("repoCnt");
            load_RepoCntPerMonth(rr);
            try {
                repoCntPerMonth_stmt.executeBatch();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            commit();
//            closeDB();
        }finally {
            balanceChangeLock.unlock();
        }
    }


}
