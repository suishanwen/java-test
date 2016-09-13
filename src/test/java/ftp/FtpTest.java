package ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by sw on 2016/9/13.
 */
public class FtpTest {
    public static void main(String [] args){
        FtpUtil ftpUtil=new FtpUtil();
        ByteArrayOutputStream byteArrayOutputStream= ftpUtil.fileGet("/Inp/DOCTOR_MR_EXTERNAL_DATA/20151200002/","20160729000001");
        FileOutputStream fos = null;
        try {
            fos=new FileOutputStream(new File("E:/testFtp"));
            byteArrayOutputStream.writeTo(fos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
