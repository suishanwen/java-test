package executor;

import ftp.FtpUtil;
import util.IoUtil;

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * Created by sw on 2016/8/12.
 */
public class MultiThreadOperation implements Callable {

    private String path;
    private String name;


    public MultiThreadOperation(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public InputStream call() throws Exception {
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.getConn("ftp://meddoc:meddoc@192.168.1.63:2121/FtpFile");
        return IoUtil.ByteArray2InputStream(ftpUtil.fileGet(path, name).toByteArray());
    }
}
