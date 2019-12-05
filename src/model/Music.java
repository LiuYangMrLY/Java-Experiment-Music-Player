package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

public class Music {
    // 主属性 main attributes
    private String name;
    private int sheetId;
    private String uuid;
    private String path;

    // 其他属性  other attributes
    private MusicSheet sheet = null;
    private File file = null;

    // 检测属性
    private boolean isLoading = true;
    private boolean canPlay = false;

    // 前端显示属性                name 歌名
    private String singer;         // 歌手名
    private String album;          // 专辑名
    private double duration = 0.0; // 时长

    @Deprecated
    public Music() {}

    /**
     * 数据库 new Music
     * @param name     歌曲名
     * @param sheetIdm 所属歌单 ID
     * @param uuid     File MD5
     * @param path     文件路径
     */
    public Music(String name, int sheetIdm, String uuid, String path, MusicSheet sheet) {
        this.name = name;
        this.sheetId = sheetIdm;
        this.uuid = uuid;
        this.path = path;

        this.sheet = sheet;

        this.loadMusic();
    }

    /**
     * 新建歌曲时使用
     * @param file 歌曲文件
     */
    private Music(File file) {
        this.path = file.getAbsolutePath();

        this.loadMusic();
        this.uuid = this.generateMD5();
    }

    /**
     * 仅用于显示
     * @param name 歌曲名
     * @param uuid MD5
     */
    public Music(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * 新建歌曲
     * @param files 歌曲文件
     * @return 可以加载的歌曲列表
     */
    public static ArrayList<Music> newMusics(File[] files) {
        ArrayList<Music> musics = new ArrayList<>();

        if (files == null) {
            return musics;
        }

        for (File file: files) {
            Date startDate = new Date();
            Music music = new Music(file);

            Date endDate = new Date();
            while (music.isLoading() || (endDate.getTime() - startDate.getTime()) < 1000) {
                System.out.println("0000");
                endDate = new Date();
            }

            if (music.isCanPlay()) {
                musics.add(music);
            }
        }

        return musics;
    }

    /**
     * 加载音乐
     */
    public void loadMusic() {
        if (this.path == null) {
            return;
        }

        if (this.file == null) {
            this.file = new File(this.path);
        }

        try {
            Media media = new Media(this.file.toURI().toURL().toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    name = (String) media.getMetadata().get("title");
                    singer = (String) media.getMetadata().get("artist");
                    album = (String) media.getMetadata().get("album");
                    duration = media.getDuration().toSeconds();

                    isLoading = false;

                    if (name != null) {
                        canPlay = true;
                    }
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成歌曲的 uuid
     * @return File md5 uuid
     */
    private String generateMD5() {
        String str = null;
        try {
            str = DigestUtils.md5Hex(new FileInputStream(this.file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

    public String getName() {
        return name;
    }

    public int getSheetId() {
        return sheetId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getPath() {
        return path;
    }

    public MusicSheet getSheet() {
        return sheet;
    }

    public File getFile() {
        return file;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public String getSinger() {
        return singer;
    }

    public String getAlbum() {
        return album;
    }

    public double getDuration() {
        return duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSheetId(int sheetId) {
        this.sheetId = sheetId;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSheet(MusicSheet sheet) {
        this.sheet = sheet;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
