package util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class MD5Utils {
    private static final String[] hexDigIts = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * MD5加密
     * @param origin 字符
     * @param charset 编码
     * @return MD5 string
     */
    public static String MD5Encode(String origin, String charset){
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charset || "".equals(charset)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charset)));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultString;
    }

    /**
     *
     * @param file 文件
     * @return MD5 string
     */
    public static String MD5Encode(File file) {
        String resultString = null;

        try {
            resultString = DigestUtils.md5Hex(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultString;
    }


    public static String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    public static String byteToHexString(byte b){
        int n = b;
        if (n < 0) {
            n += 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigIts[d1] + hexDigIts[d2];
    }

    public static void main(String[] args) {
        System.out.print(MD5Encode("test", "utf-8"));
    }
}
