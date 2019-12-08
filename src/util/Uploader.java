package util;

import model.Music;
import model.MusicSheet;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class Uploader {
    /**
     * 上传歌单以及歌单中歌曲
     * @param sheet 歌单 MusicSheet
     */
    public static void uploadMusicSheet(MusicSheet sheet) {
        String UPLOAD_URL = "http://service.uspacex.com/music.server/upload";

        OkHttpClient client = new OkHttpClient();

        MediaType Multipart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");

        MultipartBody.Builder requestBodyBuilder =  new MultipartBody.Builder();

        // text
        requestBodyBuilder
                .addFormDataPart("musicSheetUuid", sheet.getUuid())
                .addFormDataPart("musicSheetName", sheet.getName())
                .addFormDataPart("musicSheetCreatorId", sheet.getCreatorId())
                .addFormDataPart("musicSheetCreator", sheet.getCreator())
                .addFormDataPart("musicSheetDateCreated", sheet.getDateCreated())
                .addFormDataPart("musicSheetPicture", sheet.getPicture());

        // picture
        File pictureFile = new File(sheet.getPicture());
        requestBodyBuilder.addFormDataPart(
                "musicSheetPicture",
                pictureFile.getName(),
                RequestBody.create(Multipart_Form_Data, pictureFile)
        );

        // music
        for (Music music: sheet.getMusicArray()) {
            requestBodyBuilder.addFormDataPart(
                    music.getUuid(),
                    music.getFile().getName(),
                    RequestBody.create(Multipart_Form_Data, music.getFile())
            );
        }

        Request request = new Request.Builder()
                .url(UPLOAD_URL)
                .post(requestBodyBuilder.build())
                .build();

        // 异步请求
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(response.body().string());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
