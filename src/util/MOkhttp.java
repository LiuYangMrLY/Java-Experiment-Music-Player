package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.regexp.internal.RE;
import model.MusicSheet;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MOkhttp {


//    public static ArrayList<MusicSheet> musicSheets = new ArrayList<>();
//
//    public static ArrayList<MusicSheet> getMusicSheetWithOkHttp(){
//
//
//
//        Request request = new Request.Builder()
//                .url("http://service.uspacex.com/music.server/queryMusicSheets?type=all")
//                .build();
//
//        OkHttpClient client = new OkHttpClient();
//
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        //异步请求
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //System.out.println(response.body().string());
//                musicSheets = parseJSONWithGSON(response.body().string());
//                System.out.println(musicSheets);
//                response.body().close();
//            }
//        });
//
//
//
//        //开启现线程发起网络请求
////        new Thread(new Runnable(){
////            @Override
////            public void run(){
////                try{
////                    OkHttpClient client = new OkHttpClient.Builder()
////                            .retryOnConnectionFailure(true)  //网查解决end of the stream问题
////                            .connectTimeout(10, TimeUnit.SECONDS)
////                            .readTimeout(20,TimeUnit.SECONDS)
////                            .build();
//////                    FormBody.Builder builder = new FormBody.Builder();
//////                    Log.d("联网时的新闻id",newsId);
//////                    builder.add("id", newsId);
//////                    builder.add("operaType", "1");
//////                    builder.add("session", session);
//////                    RequestBody requestBody = builder.build();
////
////                    Request request = new Request.Builder()
////                            .url("http://service.uspacex.com/music.server/queryMusicSheets?type=all")
////                            .build();
////
////                    Response response = client.newCall(request).execute();
////                    String responseData = response.body().string();
////
////
////                    JSONObject jsonObject = new JSONObject(responseData);
////                    String state = jsonObject.getString("message");
////                    String result = jsonObject.getString("musicSheetList");
////
////                    System.out.println(result);
////                    System.out.println(state);
////
////                    parseJSONWithGSON(result,musicSheets);
////
////                }catch(final Exception e){
////
////                }
////            }
////        }).start();
//
//        return musicSheets;
//    }
//
//    private static ArrayList<MusicSheet> parseJSONWithGSON(final String response){
//
//        ArrayList<MusicSheet> musicSheets = new ArrayList<>();
//        Gson gson = new Gson();
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            String list = jsonObject.getJSONArray("musicSheetList").toString();
//            //JSONArray jsonArray = new JSONArray(response);
//            musicSheets = gson.fromJson(list, new TypeToken<List<MusicSheet>>(){}.getType());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return musicSheets;
//    }
//
//    public static ArrayList<MusicSheet> getMusicSheets(){
//        return musicSheets;
//
//    }
}
