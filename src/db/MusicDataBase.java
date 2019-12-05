package db;

import model.Music;
import model.MusicSheet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MusicDataBase {
    /**
     * 获取歌单中的所有歌曲
     * @param sheet 歌单
     * @return 歌曲列表 array of music
     */
    public static ArrayList<Music> getMusicsFromMusicSheet(MusicSheet sheet) {
        ArrayList<Music> musics = new ArrayList<>();

        String SELECT_MUSIC_FROM_MUSIC_SHEET_SQL = "SELECT FROM music WHERE sheetId=?";

        try {
            PreparedStatement preparedStatement = DataBase.connection.prepareStatement(SELECT_MUSIC_FROM_MUSIC_SHEET_SQL);
            preparedStatement.setInt(1, sheet.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                musics.add(new Music(
                        resultSet.getString("name"),
                        resultSet.getInt("sheetId"),
                        resultSet.getString("uuid"),
                        resultSet.getString("path")
                ));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musics;
    }

    /**
     * 从歌单中删除歌曲 delete music from MusicSheet
     * @param music 歌曲
     * @return true  成功
     *         false 失败
     */
    public static boolean deleteMusicFromMusicSheet(Music music) {
        boolean flag = false;

        String DELETE_MUSIC_FROM_MUSIC_SHEET_SQL = "DELETE FROM music WHERE name=? AND sheetId=? AND uuid=? AND path=?";

        try {
            PreparedStatement preparedStatement = DataBase.connection.prepareStatement(DELETE_MUSIC_FROM_MUSIC_SHEET_SQL);
            preparedStatement.setString(1, music.getName());
            preparedStatement.setInt(2, music.getSheetId());
            preparedStatement.setString(3, music.getUuid());
            preparedStatement.setString(4, music.getPath());

            flag = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 向歌单中添加歌曲 insert music into MusicSheet
     * @param music 歌曲
     * @param sheet 歌单
     * @return true   成功
     *         false  失败
     */
    public static boolean insertMusicIntoMusicSheet(Music music, MusicSheet sheet) {
        boolean flag = false;

        String INSERT_MUSIC_INTO_MUSIC_SHEET_SQL = "INSERT INTO music VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = DataBase.connection.prepareStatement(INSERT_MUSIC_INTO_MUSIC_SHEET_SQL);
            preparedStatement.setString(1, music.getName());
            preparedStatement.setInt(2, sheet.getId());
            preparedStatement.setString(3, music.getUuid());
            preparedStatement.setString(4, music.getPath());

            flag = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * Table music
     * ------------------------------------------------
     * |   name   |   sheetId   |   uuid   |   path   |
     * | char(255)|     int     | char(255)| char(255)|
     * |  歌曲名   |  所属歌单 ID  | File MD5 |  文件路径 |
     */
    public static void musicTablePreparation() {
        String CREATE_MUSIC_TABLE_SQL = "CREATE TABLE IF NOT EXISTS music(" +
                "name CHAR(255)," +
                "sheetId INT," +
                "uuid CHAR(255)," +
                "path CHAR(255))";

        try {
            DataBase.statement.executeUpdate(CREATE_MUSIC_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}