package ftp;

/**
 * Created by sw on 2016/9/13.
 */

import exception.BusinessException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Properties;

public class FtpUtil {
    private String url;
    private String port;
    private String username;
    private String password;
    private String dir;
    private String config = "";
    private static String OS = System.getProperty("os.name").toLowerCase();
    private FTPClient ftpClient;

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }


    public FtpUtil() {
        ftpClient = new FTPClient();
        ftpClient.setBufferSize(1024);
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setDefaultTimeout(300000);
        ftpClient.setConnectTimeout(300000);
        ftpClient.setDataTimeout(300000);
        ftpClient.configure(getClientConfig());
        getConn();
    }

    private void getConn() {
        System.out.println("reading FTP Config...");
        Properties prop = new Properties();
        InputStream in = FtpUtil.class.getClassLoader().getResourceAsStream("ftp.properties");
        try {
            prop.load(in);
            url = prop.getProperty("url").trim();
            port = prop.getProperty("port").trim();
            username = prop.getProperty("username").trim();
            password = prop.getProperty("password").trim();
            dir = prop.getProperty("dir").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dir.equals("")) {
            config = "ftp://" + username + ":" + password + "@" + url + ":" + port;
        } else {
            config = "ftp://" + username + ":" + password + "@" + url + ":" + port + "/" + dir;
        }
        System.out.println(config);
    }


    public void ftpConnect(String url, String port, String username, String password) {
        System.out.println("连接" + config);
        try {
            ftpClient.connect(url, Integer.parseInt(port));
            boolean loginResult = ftpClient.login(username, password);
            int returnCode = ftpClient.getReplyCode();
            if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {
                System.out.println("ftp连接成功");
            } else {
                throw new BusinessException("FTP连接失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("FTP客户端出错或已关闭！", e);
        }
    }


    public void ftpDisconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
                System.out.println("关闭ftp连接");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("关闭FTP连接发生异常！", e);
        }
    }

    public boolean fileUpload(String remotePath, String fileName, InputStream fis) {
        if (!ftpClient.isConnected()) {
            ftpConnect(url, port, username, password);
        }
        try {
            System.out.println("上传'" + fileName + "'到" + remotePath);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.makeDirectory(dir + remotePath);
            ftpClient.changeWorkingDirectory(dir + remotePath);
            ftpClient.enterLocalPassiveMode();
            System.out.println("uploading...");
            boolean returnMessage = ftpClient.storeFile(fileName, fis);
            if (returnMessage) {
                System.out.println("上传成功...");
            }
            return returnMessage;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("FTP文件上传发生异常！", e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    public boolean fileRename(String path, String oldName, String newName) {
        if (!ftpClient.isConnected()) {
            ftpConnect(url, port, username, password);
        }
        try {
            System.out.println("文件重命名");
            ftpClient.changeWorkingDirectory(dir + path);
            ftpClient.enterLocalPassiveMode();
            return ftpClient.rename(oldName, newName);
        } catch (IOException e) {
            throw new BusinessException("FTP文件重命名发生异常！", e);
        } finally {
//            ftpDisconnect();
        }
    }


    public boolean fileDelete(String pathName) {
        if (!ftpClient.isConnected()) {
            ftpConnect(url, port, username, password);
        }
        try {
            System.out.println("删除" + pathName);
            ftpClient.enterLocalPassiveMode();
            boolean returnMessage = ftpClient.deleteFile(dir + pathName);
            if (returnMessage) {
                System.out.println("删除成功！");
            }
            return returnMessage;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("FTP文件删除发生异常！", e);
        } finally {
//            ftpDisconnect();
        }
    }


    public ByteArrayOutputStream fileGet(String path, String fileName) {
        if (!ftpClient.isConnected()) {
            ftpConnect(url, port, username, password);
        }
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(dir + path);
            ftpClient.enterLocalPassiveMode();
            System.out.println("读取文件信息:" + path + "/" + fileName);
            InputStream ins = ftpClient.retrieveFileStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = ins.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            ins.close();
            System.out.println("文件读取完成");
            return baos;
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new BusinessException("文件不存在！", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("FTP文件读取发生异常！", e);
        } finally {
            ftpDisconnect();
        }
    }

    private static FTPClientConfig getClientConfig() {
        String sysType = null;
        if (isLinux()) {
            sysType = FTPClientConfig.SYST_UNIX;
        } else if (isWindows()) {
            sysType = FTPClientConfig.SYST_NT;
        }
        FTPClientConfig config = new FTPClientConfig(sysType);
        config.setRecentDateFormatStr("yyyy-MM-dd HH:mm");
        return config;
    }

    private static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    private static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }
}

