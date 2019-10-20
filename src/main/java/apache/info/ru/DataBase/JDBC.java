package apache.info.ru.DataBase;

import java.sql.*;

public class JDBC {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/archive_message?serverTimezone=EST5EDT";
    private static final String user = "root";
    private static final String password = "root";
    private static final String driver = "com.mysql.jdbc.Driver";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    private void createConnection(String query){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String text = resultSet.getString(2);
                Date date = resultSet.getDate(3);
                System.out.println("id " + id + " text: " + text + " date: " + date);
            }
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }finally {
            try { connection.close(); } catch (SQLException se) {/*can't do anything */ }
            try { statement.close(); } catch(SQLException se) { /*can't do anything */ }
            try { resultSet.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    public void insert (String query) {
        query = "INSERT INTO archive (text)" + "VALUES (?)";
        createConnection(query);
    }

    public void select(String query) {
        query = "SELECT * FROM archive";
        createConnection(query);
    }

    public void delete(String query){
        query = "DELETE FROM archive WHERE id=?";
        createConnection(query);
    }
}
