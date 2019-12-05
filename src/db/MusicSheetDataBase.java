package db;

import model.MusicSheet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MusicSheetDataBase {
    /**
     * 获取歌单 MusicSheet
     * @param id 歌单 Id
     * @return 歌单 MusicSheet
     */
    public static MusicSheet getMusicSheet(int id) {
        MusicSheet sheet = null;

        String SELECT_MUSIC_SHEET_OF_ID_SQL = "SELECT * FROM sheet WHERE id=?";

        try {
            PreparedStatement preparedStatement = DataBase.connection.prepareStatement(SELECT_MUSIC_SHEET_OF_ID_SQL);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sheet = new MusicSheet(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("dateCreated"),
                        resultSet.getString("creator"),
                        resultSet.getString("creatorId"),
                        resultSet.getString("picture"),
                        resultSet.getString("uuid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sheet;
    }

    /**
     * 获取所有歌单 get all of MusicSheet
     * @return 歌单列表 array of MusicSheet
     */
    public static ArrayList<MusicSheet> getAllMusicSheets() {
        ArrayList<MusicSheet> sheets = new ArrayList<>();

        String SELECT_ALL_MUSIC_SHEET_SQL = "SELECT * FROM sheet";

        try {
            ResultSet resultSet = DataBase.statement.executeQuery(SELECT_ALL_MUSIC_SHEET_SQL);

            while (resultSet.next()) {
                sheets.add(new MusicSheet(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("dateCreated"),
                        resultSet.getString("creator"),
                        resultSet.getString("creatorId"),
                        resultSet.getString("picture"),
                        resultSet.getString("uuid"))
                );
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sheets;
    }

    /**
     * 删除歌单 delete MusicSheet
     * @param sheet 歌单
     * @return true  成功
     *         false 失败
     */
    public static boolean deleteMusicSheet(MusicSheet sheet) {
        boolean flag = false;

        String DELETE_MUSIC_SHEET_SQL = "DELETE FROM sheet WHERE id=?";

        try {
            PreparedStatement preparedStatement = DataBase.connection.prepareStatement(DELETE_MUSIC_SHEET_SQL);
            preparedStatement.setInt(1, sheet.getId());

            flag = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 创建歌单 create MusicSheet
     * @param sheet 歌单
     * @return true   成功
     *         false  失败
     */
    public static boolean insertMusicSheet(MusicSheet sheet) {
        boolean flag = false;

        String INSERT_MUSIC_SHEET_SQL = "INSERT INTO sheet (name, dateCreated, creator, creatorId, picture, uuid) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = DataBase.connection.prepareStatement(INSERT_MUSIC_SHEET_SQL);
            preparedStatement.setString(1, sheet.getName());
            preparedStatement.setString(2, sheet.getDateCreated());
            preparedStatement.setString(3, sheet.getCreator());
            preparedStatement.setString(4, sheet.getCreatorId());
            preparedStatement.setString(5, sheet.getPicture());
            preparedStatement.setString(6, sheet.getUuid());

            flag = preparedStatement.execute();

            if (flag) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    sheet.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * Table sheet
     * ----------------------------------------------------------------------------------------------
     * |   id   |   name   |   dateCreated   |   creator   |   creatorId   |   picture   |   uuid   |
     * | key int| char(255)|    timestamp    |  char(255)  |   char(255)   |   char(255) | char(255)|
     * |   ID   |   歌单名  |      创建时间    |    创建者    |    创建者 ID   | 歌单封面图片路径| name MD5 |
     */
    public static void musicSheetTablePreparation() {
        String CREATE_MUSIC_SHEET_TABLE_SQL =
                "CREATE TABLE IF NOT EXISTS sheet(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name CHAR(255)," +
                        "dateCreated TIMESTAMP," +
                        "creator CHAR(255)," +
                        "creatorId CHAR(255)," +
                        "picture CHAR(255)," +
                        "uuid CHAR(255))";

        try {
            DataBase.statement.executeUpdate(CREATE_MUSIC_SHEET_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}