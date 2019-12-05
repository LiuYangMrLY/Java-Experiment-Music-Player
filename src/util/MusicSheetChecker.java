package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Music;
import model.MusicSheet;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MusicSheetChecker {
    private static String MUSIC_SHEET_RUL = "http://service.uspacex.com/music.server/queryMusicSheets?type=all";

    private static String sheetResponseJson = null;
    
    static {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(MUSIC_SHEET_RUL).build();
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            JsonParser parser = new JsonParser();

            sheetResponseJson = Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MusicSheet> getMusicSheetList() {
        JsonParser parser = new JsonParser();
        JsonElement mainElement = parser.parse(sheetResponseJson);
        JsonObject mainObject = mainElement.getAsJsonObject();
        JsonArray sheetJsonArray = mainObject.getAsJsonArray("musicSheetList");

        ArrayList<MusicSheet> array = new ArrayList<>();

        for (JsonElement element: sheetJsonArray) {
            MusicSheet sheet = new MusicSheet("网络歌单", "网络", "Internet", "");
            try {
                sheet.setCreator(element.getAsJsonObject().get("creator").getAsString());
                sheet.setCreatorId(element.getAsJsonObject().get("creatorId").getAsString());
                sheet.setDateCreated(element.getAsJsonObject().get("dateCreated").getAsString());
                sheet.setId(element.getAsJsonObject().get("id").getAsInt());
                sheet.setName(element.getAsJsonObject().get("name").getAsString());
                sheet.setPicture(element.getAsJsonObject().get("picture").getAsString());
                sheet.setUuid(element.getAsJsonObject().get("uuid").getAsString());
            } catch (UnsupportedOperationException e) {
                // e.printStackTrace();
            }

            JsonObject musicItem = element.getAsJsonObject().get("musicItems").getAsJsonObject();
            for (String key: musicItem.keySet()) {
                sheet.getMusicArray().add(new Music(musicItem.get(key).getAsString(), key));
            }

            array.add(sheet);
        }

        return array;
    }


    public String getSheetResponseJson() {
        return sheetResponseJson;
    }
    
    public static void main(String[] args) {

//        System.out.println(MusicSheetChecker.sheetResponseJson);
        System.out.println(getMusicSheetList());


//        JsonParser parser = new JsonParser();
//        JsonElement mainElement = parser.parse(sheetResponseJson);
//        JsonObject mainObject = mainElement.getAsJsonObject();
//        JsonArray sheetJsonArray = mainObject.getAsJsonArray("musicSheetList");
//        System.out.println(sheetJsonArray.get(98).toString());


//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().get().url(MUSIC_SHEET_RUL).build();
//        Call call = client.newCall(request);
//
//        try {
//            Response response = call.execute();
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(response.body().string());
//
//            JsonObject object = element.getAsJsonObject();
//
//            System.out.println(object.get("musicSheetList").getAsJsonArray().size());
//            // System.out.println(object.get("musicSheetList").getAsJsonArray().get(0).getAsJsonObject().get("musicItems").getAsJsonObject().keySet());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
