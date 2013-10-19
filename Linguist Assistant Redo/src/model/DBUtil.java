package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBUtil {
    private static String dbUrl;
    private static String dbName;
    private static String dbDriver;
    private static String username;
    private static String password;
    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    
    static {
        //setup();
    }
    
    public static void main(String[] args) throws ClassNotFoundException {
        Connection connection = newConnection();
        try {
          // create a database connection
          connection = DriverManager.getConnection("jdbc:sqlite:LAR.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          ResultSet rs = statement.executeQuery("select * from Feature");
          while(rs.next()) {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
          }
        }
        catch(SQLException e) {
          System.err.println(e.getMessage());
        }
        finally {
            close(connection);
        }
    }
    
    /*    
    private static void setup() {
        ResourceBundle rb = ResourceBundle.getBundle("model.db.db");
        dbUrl = rb.getString("dbUrl");
        dbName = rb.getString("dbName");
        dbDriver = rb.getString("dbDriver");
        username = rb.getString("username");
        password = rb.getString("password");
    }
    
    public static Connection newConnection() {            
        try {
            Class.forName(dbDriver).newInstance();
            conn = DriverManager.getConnection(dbUrl + dbName, username, password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    */
    
    public static Connection newConnection() {            
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:LAR.db");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
    public static void setAutoCommit(boolean status) {            
        try {
            if (conn != null) {
                conn.setAutoCommit(status);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement st) {
        try {
            if (st != null)
                st.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement st) {
        try {
            if (st != null)
                st.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection connection) {
        try {
            if (connection != null) 
                connection.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void rollback(Connection connection) {
        try {
            if (connection != null)   
                connection.rollback();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void executeUpdate(String update) {
        Connection conn = newConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(update); 
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DBUtil.close(ps);
            DBUtil.close(conn);
        }
    }
    
    public static ResultSet executeQuery(String query) {
        conn = DBUtil.newConnection();
        ps = null;
        rs = null;
        try {
            ps = conn.prepareStatement(query); 
            rs = ps.executeQuery();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return rs;
    }
    
    public static void finishQuery() {
        close(conn);
        close(ps);
        close(rs);
    }

}
