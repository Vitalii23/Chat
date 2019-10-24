package apache.info.ru.DataBase;

import java.sql.*;

public class JDBC {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/archive_message?serverTimezone=EST5EDT";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    // insert
    public void insert(String line) {
        String sql = "INSERT INTO archive (text)" + "VALUES (?)";
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, line);
            ps.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { connection.close(); } catch (SQLException se) {/*can't do anything */ }
            try { statement.close(); } catch (SQLException se) { /*can't do anything */ }
        }
    }

    public void select(){
        String sql = "SELECT text FROM archive";
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String text = resultSet.getString(1);
                System.out.println(text + "\n");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { connection.close(); } catch (SQLException se) {/*can't do anything */ }
            try { statement.close(); } catch (SQLException se) { /*can't do anything */ }
            try { resultSet.close(); } catch (SQLException se) { /*can't do anything */ }
        }
    }
}