package searchrobot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.Date;

public final class GatewayDB {
    private static volatile GatewayDB instance = null;

    private final String DB_DRIVER = "org.postgresql.Driver";
    private final String DB_USER = "user";
    private final String DB_PASS = "pass";
    private final String DB_CONNECTION = "jdbc:postgresql://hostname:port/database";
	private final String DB_TABLE = "table";
    private Connection dbConnection;

    private GatewayDB() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASS);
    }

    public static GatewayDB getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            synchronized (GatewayDB.class) {
                if (instance == null) {
                    instance = new GatewayDB();
                }
            }
        }
        return instance;
    }

    public void closeConnection() throws SQLException {
        dbConnection.close();
    }

    public ResultSet selectByDate(String reqDate) throws SQLException {
        java.sql.Date sqlDate = new java.sql.Date(0000-00-00);

        String selectTableSQL = "SELECT id, url FROM ? WHERE date < ? OR date is NULL";
        PreparedStatement preStatement = dbConnection.prepareStatement(selectTableSQL);
        preStatement.setString(1, DB_TABLE);
		preStatement.setDate(2, sqlDate.valueOf(reqDate));

        ResultSet rs = preStatement.executeQuery();

        return rs;
    }

    public void updateStatus(int id, int status) throws SQLException {
        Date currDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = dateFormat.format(currDate);
        java.sql.Date sqlDate = new java.sql.Date(0000-00-00);

        String updateTableSQL = "UPDATE ? SET status = ?, date = ? WHERE id = ?";
        PreparedStatement preStatement = dbConnection.prepareStatement(updateTableSQL);
        preStatement.setString(1, DB_TABLE);
		preStatement.setInt(2, status);
        preStatement.setDate(3, sqlDate.valueOf(formatedDate));
        preStatement.setInt(4, id);

        preStatement.execute();
    }

}
