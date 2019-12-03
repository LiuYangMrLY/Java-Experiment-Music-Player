package db;

import model.Music;
import model.MusicSheet;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import util.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataBase {
    private static Connection connection = null;
    private static Statement statement = null;

    static {
        connectDataBase();
        makePreparations();
    }

    /**
     * 获取所有的 歌单sheet
     * @return [{"id":      "sheet_id",
     *           "date":    "1970-01-01",
     *           "name":    "sheet_name",
     *           "owner":   "owner"
     *           "picture": "sheet_picture_path"},
     *          ...
     *         ]
     */
    public static ArrayList<HashMap<String, String>> getAllSheets() {
        ArrayList<HashMap<String, String>> array = new ArrayList<>();

        String ALL_SHEETS_SQL = "SELECT * FROM sheet;";
        try {
            ResultSet result = statement.executeQuery(ALL_SHEETS_SQL);
            while (result.next()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", result.getString("id"));
                map.put("date", result.getString("date"));
                map.put("name", result.getString("name"));
                map.put("owner", result.getString("owner"));
                map.put("picture", result.getString("picture"));

                array.add(map);
            }

            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return array;
    }

//    /**
//     * 获取指定歌单中的所有音乐
//     * @param id 歌单sheet 的 id
//     * @return 歌单不存在 null
//     *         歌单存在  [Song, ...] or [] (no song in the sheet)
//     */
//    public static ArrayList<Song> getSongsOfTheSheet(String id) {
//        String JUDGE_SHEET_EXIST_SQL = "SELECT * FROM sheet where id=(id) VALUES (?);";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(JUDGE_SHEET_EXIST_SQL);
//            preparedStatement.setString(1, id);
//
//            if (!preparedStatement.executeQuery().next()) {
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        ArrayList<Song> array = new ArrayList<>();
//
//        String SEARCH_SONGS_OF_THE_SHEET_SQL = "SELECT * FROM music where id=(id) VALUES (?);";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_SONGS_OF_THE_SHEET_SQL);
//            preparedStatement.setString(1, id);
//
//            ResultSet result = preparedStatement.executeQuery();
//            while (result.next()) {
//                array.add(new Song(result.getString("path")));
//            }
//        } catch (SQLException | IOException | CannotReadException | ReadOnlyFileException | TagException | InvalidAudioFrameException e) {
//            e.printStackTrace();
//        }
//
//        return array;
//    }

    /**
     * 获取指定歌单中的所有音乐
     * @param id 歌单sheet 的 id
     * @return 歌单不存在 null
     *         歌单存在  [Music, ...] or [] (no Music in the sheet)
     */
    public static ArrayList<Music> getMusicOfTheSheet(String id) {
        String JUDGE_SHEET_EXIST_SQL = "SELECT * FROM sheet where id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(JUDGE_SHEET_EXIST_SQL);
            preparedStatement.setString(1, id);

            if (!preparedStatement.executeQuery().next()) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Music> array = new ArrayList<>();
        String SEARCH_SONGS_OF_THE_SHEET_SQL = "SELECT * FROM music where sheet=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_SONGS_OF_THE_SHEET_SQL);
            preparedStatement.setString(1, id);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                array.add(new Music(id, result.getString("path")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return array;
    }

    /**
     * 获取指定 sheet id 的 歌单名
     * @param id sheetID
     * @return 歌单名
     */
    public static String getSheetName(String id) {
        String name = null;

        String SEARCH_SHEET_NAME_SQL = "SELECT * FROM sheet where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_SHEET_NAME_SQL);
            preparedStatement.setString(1, id);

            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                name = result.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * | id | name | date | owner | picture |
     * @param sheet
     */
    public static void createMusicSheet(MusicSheet sheet) {
        String INSERT_SHEET_SQL = "INSERT INTO sheet VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SHEET_SQL);
            preparedStatement.setString(1, Integer.toString(sheet.getId()));
            preparedStatement.setString(2, sheet.getName());
            preparedStatement.setString(3, sheet.getDateCreated());
            preparedStatement.setString(4, sheet.getCreator());
            preparedStatement.setString(5, sheet.getPicture());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加歌曲
     * @param sheetID 所属歌单的id
     * @param file 歌曲文件
     */
    public static void addMusic(String sheetID, File file) {
        String ADD_MUSIC_SQL = "INSERT INTO music VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_MUSIC_SQL);
            preparedStatement.setString(1, file.getName());
            preparedStatement.setString(2, sheetID);
            preparedStatement.setString(3, MD5Utils.MD5Encode(file));
            preparedStatement.setString(4, file.getAbsolutePath());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMusic(String sheetID, Music music) {

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
     * Table sheet
     * --------------------------------------
     * | id | name | date | owner | picture |
     *
     *
     * Table music
     * -----------------------------
     * | title | sheet | md5 | path |
     */
    private static void makePreparations() {
        try {
            String SHEET_TABLE_SQL = "CREATE TABLE IF NOT EXISTS sheet(" +
                    "id CHAR(255)," +
                    "name CHAR(255)," +
                    "date TIMESTAMP," +
                    "owner CHAR(255)," +
                    "picture CHAR(255))";
            statement.executeUpdate(SHEET_TABLE_SQL);

            String MUSIC_TABLE_SQL = "CREATE TABLE IF NOT EXISTS music(" +
                    "title CHAR(255)," +
                    "sheet CHAR(255)," +
                    "md5 CHAR(255)," +
                    "path CHAR(255))";
            statement.executeUpdate(MUSIC_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
//        System.out.println(Arrays.toString(DataBase.getAllSheets().toArray()));
//        File file = new File("test.txt");
//        DataBase.addMusic("1", file);
        //String name, String creator, String creatorId, String picture
            createMusicSheet(new MusicSheet("1", "1", "1", "1"));

    }
}
