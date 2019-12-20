package util;

import javafx.scene.input.DataFormat;
import model.Music;
import model.MusicSheet;
import okhttp3.*;
import view.CenterDownloadView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;

public class Downloader {
    /**
     * 下载歌曲到 MusicDownload 文件夹
     * @param music 歌曲 Music
     */
    public static void downloadMusic(Music music) {
        String DOWNLOAD_MUSIC_URL_PREFIX = "http://service.uspacex.com/music.server/downloadMusic?md5=";
        String MUSIC_FOLDER = "MusicDownload";

        download(DOWNLOAD_MUSIC_URL_PREFIX + music.getUuid(), MUSIC_FOLDER, new Downloader.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                music.setPath(file.getAbsolutePath());
                CenterDownloadView.setStatus(music);
                music.setDownloading(false);
            }

            @Override
            public void onDownloading(int progress) {
                CenterDownloadView.setProgressBar(music,progress);
                //System.out.println("progress:" + progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {

            }
        });
    }

    public static void downloadMusicSheetPicture(MusicSheet sheet) {
        String DOWNLOAD_MUSIC_SHEET_PICTURE_RUL_PREFIX = "http://service.uspacex.com/music.server/downloadPicture?uuid=";
        String MUSIC_SHEET_PICTURE_FOLDER = "MusicSheetPicture";

        download(DOWNLOAD_MUSIC_SHEET_PICTURE_RUL_PREFIX + sheet.getUuid(), MUSIC_SHEET_PICTURE_FOLDER, new OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                sheet.updatePicture(file.getAbsolutePath());
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed(Exception e) {

            }
        });
    }

    /**
     * 下载
     * @param url          URL
     * @param destFileDir  文件夹
     * @param listener     Listener
     */
    public static void download(final String url, final String destFileDir, final Downloader.OnDownloadListener listener) {
        String fileName = null;

        Request request = new Request.Builder()
                .addHeader("Accept-Encoding", "identity")
                .url(url)
                .build();

        // 获取文件名
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();

            String key = "filename=";
            String contentDisposition = response.header("Content-Disposition");
            // 重新编码
            if (contentDisposition == null) {
                return;
            }
            fileName = URLDecoder.decode(contentDisposition.substring(contentDisposition.lastIndexOf(key) + key.length()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 进行下载
        OkHttpClient okHttpClient = new OkHttpClient();
        // 异步请求
        String finalFileName = fileName;
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                // 储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(dir, finalFileName);

                try {

                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    System.out.println("total:" + total);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中更新进度条
                        listener.onDownloading(progress);
                    }

                    fos.flush();
                    //下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e);
                }finally {

                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }
                }
            }
        });
    }

    public interface OnDownloadListener{
        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */
        void onDownloadFailed(Exception e);
    }

    public static void main(String[] args) {

    }
}
