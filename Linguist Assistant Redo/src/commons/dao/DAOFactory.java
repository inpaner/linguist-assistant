package commons.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class represents a DAO factory for a SQL database. You can use {@link #getInstance(String)}
 * to obtain a new instance for the given database name. The specific instance returned depends on
 * the properties file configuration. You can obtain DAO's for the DAO factory instance using the 
 * DAO getters.
 * 
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public class DAOFactory {

    // Constants ----------------------------------------------------------------------------------

    private final String fUrl = "jdbc:sqlite:LAR.db";

    // Actions ------------------------------------------------------------------------------------

    public static DAOFactory getInstance() {
            try {
                Class.forName("org.sqlite.JDBC");
            }
            catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            DAOFactory instance = new DAOFactory();
            return instance;
        }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(fUrl);
    }
}

