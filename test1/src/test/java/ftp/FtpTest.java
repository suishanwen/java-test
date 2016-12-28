package ftp;

import util.IoUtil;

import java.io.ByteArrayOutputStream;

/**
 * Created by sw on 2016/9/13.
 */
public class FtpTest {
    public static void main(String[] args) {
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.getConn("ftp://meddoc:meddoc@192.168.1.63:2121/FtpFile");
        ByteArrayOutputStream byteArrayOutputStream = ftpUtil.fileGet("/Inp/DOCTOR_MR_EXTERNAL_DATA/20151200002", "20160729000001");
        IoUtil.writeFile(byteArrayOutputStream, "E:/testFtp");
        ftpUtil.ftpDisconnect();
    }
}
