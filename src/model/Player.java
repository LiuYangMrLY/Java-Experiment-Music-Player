package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    // 单例 player
    public static Player player = null;

    static {
        Player.player = new Player();
    }

    private Player() {}

    /**
     * 获取 Player
     * @return 单例 Player
     */
    public static Player getInstance() {
        return Player.player;
    }

    private Media media = null;
    private MediaPlayer mediaPlayer = null;

    private double volume = 0.7;  // default 0.7  |  0.0 (静音) ~ 1.0 (最大音量)
    private double duration = Duration.seconds(0).toSeconds();

    private enum Mode {ORDER, RANDOM, SINGLE}
    private Mode mode = Mode.ORDER;

    private MusicSheet musicSheet = null;
    private int index = 0;

    /**
     * 选择歌单 歌曲 进行播放
     * @param sheet 歌单
     * @param index 歌曲在歌单中的索引
     */
    public void selectMusicSheetAndMusic(MusicSheet sheet, int index) {
        this.musicSheet = sheet;

        this.selectMusic(index);
    }

    /**
     * 选择歌曲进行播放
     * @param index 歌曲在歌单中的索引
     */
    public void selectMusic(int index) {
        if (index < 0) {
            this.index = 0;
        }

        if (index > this.musicSheet.getMusicArray().size() - 1) {
            this.index = this.musicSheet.getMusicArray().size() - 1;
        }

        this.media = null;
        if (this.mediaPlayer != null) {
            this.mediaPlayer.dispose();
            this.mediaPlayer = null;
        }

        try {
            URL url = new File(this.musicSheet.getMusicArray().get(index).getPath()).toURI().toURL();

            this.media = new Media(url.toExternalForm());
            this.mediaPlayer = new MediaPlayer(this.media);

            // 保持之前的音量
            this.mediaPlayer.setVolume(this.volume);
            // music 从头开始
            this.mediaPlayer.setStartTime(Duration.seconds(0));
            // music 自动播放
            this.mediaPlayer.setAutoPlay(true);

            this.mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    duration = media.getDuration().toSeconds();
                }
            });

            this.mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    Player.getInstance().next();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前选择歌单中的所有歌曲
     * @return 歌曲列表
     */
    public ArrayList<Music> getCurrentMusicList() {
        return this.musicSheet.getMusicArray();
    }

    /**
     * 获取当前的歌单
     * @return 歌单 MusicSheet
     */
    public MusicSheet getCurrentMusicSheet() {
        return this.musicSheet;
    }

    /**
     * 获取当前播放的歌曲
     * @return 歌曲
     */
    public Music getCurrentMusic() {
        return this.musicSheet.getMusicArray().get(this.index);
    }

    /**
     * 获取当前正在播放歌曲在歌单中的索引位置
     * @return 索引
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * 播放音乐
     */
    public void play() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.play();
        }
    }

    /**
     * 从头播放当前歌曲
     */
    public void replay() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.setStartTime(Duration.seconds(0));
            this.mediaPlayer.stop();
            this.mediaPlayer.play();
        }
    }

    /**
     * 暂停音乐
     */
    public void pause() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.pause();
        }
    }

    /**
     * 清空当前播放器
     */
    public void dispose() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
            this.mediaPlayer.dispose();

            this.media = null;
            this.musicSheet = null;
            this.duration = Duration.seconds(0).toSeconds();
        }
    }

    /**
     * 当前是否在播放音乐
     * @return true   Playing
     *         false  No selected music
     *                Stop
     */
    public boolean isPlaying() {
        if (this.media == null || this.mediaPlayer == null) {
            return false;
        }

        if (this.mediaPlayer.getStatus().toString().equals("PLAYING") &&
                this.mediaPlayer.getCurrentTime().toSeconds() != this.duration) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前歌曲 music 的时间 (秒)
     * @return 秒数 (s)
     */
    public double getCurrentTime() {
        if (this.mediaPlayer != null) {
            return mediaPlayer.getCurrentTime().toSeconds();
        } else {
            return 0.0;
        }
    }

    /**
     * 获取当前歌曲 music 在总时间的比例
     * @return 比例数 0.0 ~ 1.0
     */
    public double getCurrentScaleOfDuration() {
        return this.getCurrentTime() / this.duration;
    }

    /**
     * 当前歌曲跳到指定的秒数 (s)
     *  若 当前歌曲 是播放状态，跳跃后会继续播放
     *  若 当前歌曲 是暂停状态，跳跃后依然暂停
     * @param second 要跳的秒数 (s)
     */
    public void jumpTo(int second) {
        if (second < 0 || second > (int) this.duration) {
            return;
        }

        boolean isPlaying = this.mediaPlayer.getStatus().toString().equals("PLAYING");

        this.mediaPlayer.stop();
        this.mediaPlayer.setStartTime(Duration.seconds(second));

        if (isPlaying) {
            this.play();
        }
    }

    /**
     * 当前歌曲跳到指定的比例
     * @param scale 比例数 0.0 ~ 1.0
     */
    public void jumpToTheScaleOfDuration(double scale) {
        double s = scale;
        if (s < 0.0) {
            s = 0.0;
        }

        if (s > 1.0) {
            s = 1.0;
        }

        this.jumpTo((int) (this.getDuration() * s));
    }

    /**
     * 获取歌曲 music 的总时长 (秒)
     * @return 秒数 (s)
     */
    public double getDuration() {
        return this.duration;
    }

    /**
     * 获取当前的音量
     * @return 0.0 (静音) ~ 1.0 (最大)
     */
    public double getVolume() {
        return this.volume;
    }

    /**
     * 设置音量
     * @param volume 音量 0.0 (静音) ~ 1.0 (最大)
     */
    public void setVolume(double volume) {
        double v = volume;
        if (volume < 0.0) {
            v = 0.0;
        }

        if (volume > 1.0) {
            v = 1.0;
        }

        this.volume = v;
        this.mediaPlayer.setVolume(v);
    }

    /**
     * 获取当前的播放模式
     * @return 播放模式 Mode
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * 设置播放模式为 顺序播放
     */
    public void setModeOrder() {
        this.mode = Mode.ORDER;
    }

    /**
     * 设置播放模式为 随机播放
     */
    public void setModeRandom() {
        this.mode = Mode.RANDOM;
    }

    /**
     * 设置播放模式为 单曲循环
     */
    public void setModeSingle() {
        this.mode = Mode.SINGLE;
    }

    /**
     * 切换到下一首歌曲
     */
    public void next() {
        if (this.musicSheet == null) {
            return;
        }

        // 顺序播放
        if (this.mode == Mode.ORDER) {
            int toPlayIndex = (this.index + 1) % this.musicSheet.getMusicArray().size();

            this.selectMusic(toPlayIndex);
            return;
        }

        // 随机播放
        if (this.mode == Mode.RANDOM) {
            Random random = new Random();
            int toPlayIndex = random.nextInt(this.musicSheet.getMusicArray().size());

            this.selectMusic(toPlayIndex);
            return;
        }

        // 单曲循环
        if (this.mode == Mode.SINGLE) {
            this.selectMusic(this.index);
            return;
        }
    }

    /**
     * 切换到上一首歌曲
     */
    public void pre() {
        if (this.musicSheet == null) {
            return;
        }

        int toPlayIndex = this.index - 1;
        if (toPlayIndex < 0) {
            toPlayIndex = 0;
        }

        this.selectMusic(toPlayIndex);
        return;
    }
}
