package model;

import db.MusicDataBase;
import db.MusicSheetDataBase;
import util.MD5Utils;
import util.Uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MusicSheet {
    // 主属性 main attributes
    private int id = 0;
    private String name;
    private String dateCreated;
    private String creator;
    private String creatorId;
    private String picture;
    private String uuid;

    // 其他属性  other attributes
    private ArrayList<Music> musicArray = new ArrayList<>();

    @Deprecated
    MusicSheet() {}

    /**
     * 数据库 new MusicSheet
     * @param id          ID
     * @param name        歌单名
     * @param dateCreated 创建时间
     * @param creator     创建者
     * @param creatorId   创建者 ID
     * @param picture     歌单封面图片路径
     * @param uuid        name MD5
     */
    public MusicSheet(int id, String name, String dateCreated, String creator, String creatorId, String picture, String uuid) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.creatorId = creatorId;
        this.picture = picture;
        this.uuid = uuid;

        this.musicArray = MusicDataBase.getMusicsFromMusicSheet(this);
    }

    /**
     * 创建歌单时使用
     * @param name      歌单名
     * @param creator   创建者
     * @param creatorId 创建者 ID
     * @param picture   歌单封面图片路径
     */
    public MusicSheet(String name, String creator, String creatorId, String picture) {
        if (name == null) {
            name = "未命名的歌单";
        }

        this.name = name;
        this.dateCreated = (new Date()).toString();
        this.creator = creator;
        this.creatorId = creatorId;
        this.picture = picture;
        this.uuid = MD5Utils.MD5Encode(Integer.toString(this.id), "utf-8");
    }

    /**
     * 获取下载页歌单
     * @return 已下载歌曲的歌单 MusicSheet
     */
    public static MusicSheet getDownloadedMusicSheet() {
        MusicSheet sheet = new MusicSheet();

        String MUSIC_FOLDER = "MusicDownload";

        File folder = new File(MUSIC_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (File file: folder.listFiles()) {
            sheet.getMusicArray().add(new Music(file));
        }

        return sheet;
    }



    /**
     * 获取所有歌单 get all of MusicSheet
     * @return 歌单列表 array of MusicSheet
     */
    public static ArrayList<MusicSheet> getAllMusicSheets() {
        return MusicSheetDataBase.getAllMusicSheets();
    }

    public static MusicSheet getMusicSheet(int id) {
        return MusicSheetDataBase.getMusicSheet(id);
    }

    /**
     * 获取当前歌单的所有歌曲
     * @return 歌曲列表 array of music
     */
    public ArrayList<Music> getMusicsFromMusicSheet() {
        return MusicDataBase.getMusicsFromMusicSheet(this);
    }

    /**
     * 保存当前歌单到数据库
     */
    public void saveMusicSheetInDatabase() {
        if (this.id == 0) {
            MusicSheetDataBase.insertMusicSheet(this);
        }
    }

    /**
     * 删除当前歌单歌单
     */
    public void deleteMusicSheet() {
        if (Player.getInstance().getCurrentMusicSheet() == this) {
            Player.getInstance().dispose();
        }

        MusicSheetDataBase.deleteMusicSheet(this);
    }


    /**
     * 删除当前歌单中指定索引的歌曲
     * @param index 当前歌单中歌曲的索引
     */
    public void deleteMusicFromMusicSheet(int index) {
        if (0 <= index && index < this.musicArray.size()) {
            MusicDataBase.deleteMusicFromMusicSheet(this.musicArray.get(index));
            this.musicArray.remove(index);

            if (Player.getInstance().getCurrentMusicSheet() == this && Player.getInstance().getIndex() == index) {
                Player.getInstance().selectMusic(index);
            }
        }
    }

    /**
     * 向当前歌单中添加歌曲
     * @param music 歌曲
     */
    public void addMusicIntoMusicSheet(Music music) {
        MusicDataBase.insertMusicIntoMusicSheet(music, this);
        this.musicArray.add(music);
    }

    /**
     * 向当前歌单中添加歌曲
     * @param musics 歌曲数组
     */
    public void addMusicIntoMusicSheet(Music[] musics) {
        if (musics != null) {
            for (Music music: musics) {
                MusicDataBase.insertMusicIntoMusicSheet(music, this);
                this.musicArray.add(music);
            }
        }
    }

    /**
     * 向当前歌单中添加歌曲
     * @param musics 列表
     */
    public void addMusicIntoMusicSheet(ArrayList<Music> musics) {
        if (musics != null) {
            for (Music music: musics) {
                MusicDataBase.insertMusicIntoMusicSheet(music, this);
                this.musicArray.add(music);
            }
        }
    }

    /**
     * 上传当前歌单到服务器
     */
    public void uploadMusicSheet() {
        Uploader.uploadMusicSheet(this);
    }

    /**
     * 更新歌单封面
     * @param file 歌单封面文件
     */
    public void updateMusicSheetPicture(File file) {
        this.picture = file.getAbsolutePath();
        MusicSheetDataBase.updateMusicSheetPicture(this, this.picture);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getPicture() {
        return picture;
    }

    public String getUuid() {
        return uuid;
    }

    public ArrayList<Music> getMusicArray() {
        return musicArray;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setMusicArray(ArrayList<Music> musicArray) {
        this.musicArray = musicArray;
    }

    public static void main(String[] args) throws InterruptedException {
        com.sun.javafx.application.PlatformImpl.startup(()->{});

        ArrayList<Music> arrayList = getDownloadedMusicSheet().getMusicArray();
        Thread.sleep(500);
        System.out.println(arrayList.get(0).getName());
    }
}
