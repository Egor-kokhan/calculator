package sample.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends Configs {
    private Connection dbConnection;

    /**
     * @return устанавливает соединение с базой данных используя настройки класса Configs
     */
    private Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:postgresql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);
        return dbConnection;
    }

    /**
     * Добавляет сохраненные записи вычислений в базу данных
     * (немного "грязный" вариант, нужно будет оптимизировать)
     * @param line Строка из списка строк
     */
    public void addLine (String line){
        String insert = "INSERT INTO calk.log (line) VALUES (?)";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)){
            prSt.setString(1,line);
            prSt.executeUpdate();
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выводит 10 последних записей
     */
    public List<String> getTenLines(){
        List<String> tenLines = new ArrayList<>();
        String insert = "SELECT * FROM calk.log ORDER BY id DESC LIMIT 10";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)){
            ResultSet result = prSt.executeQuery();
            while (result.next()){
                tenLines.add(result.getString("line"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tenLines;
    }
}
