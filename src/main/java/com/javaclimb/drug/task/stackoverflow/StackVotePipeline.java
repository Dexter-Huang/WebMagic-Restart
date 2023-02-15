package com.javaclimb.drug.task.stackoverflow;

import com.javaclimb.drug.config.Druid;
import com.javaclimb.drug.entity.Githubtopic;
import com.javaclimb.drug.entity.StackVote;
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

public class StackVotePipeline  implements Pipeline {
    public static Connection con = null;
    public static PreparedStatement stackVote_stmt = null;
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
            stackVote_stmt = con.prepareStatement("insert ignore into stack_vote values(?,?,?,?,?,?,?,?,?,?)");
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
                if (stackVote_stmt != null) {
                    stackVote_stmt.close();
                }
                Druid.closeAll(con);
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    public static void load_Stack_Vote(StackVote tmp){

        try {
            if(con!=null){
                stackVote_stmt.setLong(1,tmp.getQuestionId());
                stackVote_stmt.setString(2,tmp.getAnswerer());
                stackVote_stmt.setInt(3,tmp.getAnswerScore());
                stackVote_stmt.setString(4,tmp.getQuestioner());
                stackVote_stmt.setInt(5,tmp.getQuestionScore());
                stackVote_stmt.setInt(6,tmp.getAnswerCnt());
                stackVote_stmt.setInt(7,tmp.getViewCnt());
                stackVote_stmt.setString(8,tmp.getLink());
                stackVote_stmt.setString(9,tmp.getTitle());
                stackVote_stmt.setInt(10,0);
                stackVote_stmt.addBatch();
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
            List<StackVote> stackVotes =resultItems.get("stackvote");
            stackVotes.stream().forEach(o->load_Stack_Vote(o));
            try {
                stackVote_stmt.executeBatch();
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
