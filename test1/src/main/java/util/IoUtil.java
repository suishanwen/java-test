package util;

import java.io.*;

/**
 * Created by sw on 2016/9/13.
 */
public class IoUtil {
    public static byte[] inputStream2ByteArray(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        try {
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static InputStream ByteArray2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void writeFile(ByteArrayOutputStream byteArrayOutputStream,String pathName){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(pathName));
            byteArrayOutputStream.writeTo(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
