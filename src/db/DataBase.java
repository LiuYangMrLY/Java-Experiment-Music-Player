package db;

import java.sql.*;

public class DataBase {
    static Connection connection = null;
    static Statement statement = null;

    static {
        connectDataBase();
        makePreparations();
    }

    /**
     * 结束程序时调用
     */
    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接 SQLite 数据库
     */
    private static void connectDataBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Music.db");
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 创建 sheet 和 music 表
     */
    private static void makePreparations() {
        MusicSheetDataBase.musicSheetTablePreparation();
        MusicDataBase.musicTablePreparation();
    }

    public static void main(String[] args) {

    }
}