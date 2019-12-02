package util;

import model.MusicSheet;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MusicSheetUploader {
    private static String UPLOAD_URL = "http://service.uspacex.com/music.server/upload";

    private static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap,
                                     String contentType) {

        String res = "";
        HttpURLConnection conn = null;
        // boundary是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------13708983877";

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");  // 原来没有指定字符集为UTF-8
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // TEXT
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }

                out.write(strBuf.toString().getBytes());

            }
            // File
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    /**
                     * 没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                     */
                    contentType = new MimetypesFileTypeMap().getContentType(file);

                    /**
                     * contentType非空采用filename匹配默认的图片类型
                     */
                    if (!"".equals(contentType)) {
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")
                                || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        } else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        } else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    System.out.println("file:" + file);
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("Send POST Request Error: " + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }


    public static void main(String[] args) {
        MusicSheet musicSheet = MusicSheet.getSheets().get(0);
        ArrayList<String> musicFilePaths = new ArrayList<>();
        musicFilePaths.add("/Users/leo/items/IntelliJ/MusicPlayer/邓超-无敌.mp3");

        Map<String, String> textMap = new HashMap<String, String>();
        textMap.put("musicSheetUuid", musicSheet.getUuid());
        textMap.put("musicSheetName", musicSheet.getName());
        textMap.put("musicSheetCreatorId", musicSheet.getCreatorId());
        textMap.put("musicSheetCreator", musicSheet.getCreator());
        textMap.put("musicSheetDateCreated", musicSheet.getDateCreated());
        textMap.put("musicSheetPicture", musicSheet.getPicture());

        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("musicSheetPicture", musicSheet.getPicture());

        Map<String, String> musicFileMap = new HashMap<String, String>();

        FileInputStream fis = null;
        String fileMd5;

        for (String filePath : musicFilePaths) {
            try {
                fis = new FileInputStream(filePath);
                fileMd5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
                fileMap.put(fileMd5, filePath);
                musicFileMap.put(fileMd5, filePath);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }

//        musicSheet.setMusicItems(musicFileMap);
        String contentType = null;
        String ret = formUpload(UPLOAD_URL, textMap, fileMap, contentType);

        System.out.println(ret);

    }


}
