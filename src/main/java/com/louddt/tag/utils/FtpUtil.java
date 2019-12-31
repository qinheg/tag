package com.louddt.tag.utils;

import com.louddt.tag.config.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@Component
public class FtpUtil {

    @Autowired
    private FtpConfig ftpConfig;

    /**
     * 获取ftp连接
     *
     * @return
     */
    public FTPClient getFTPClient() {
        FTPClient ftpClient = new FTPClient();
        String password = AESCrptography.getRealWord(ftpConfig.getPassword());
        try {
            ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort());
            ftpClient.login(ftpConfig.getUsername(), password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("GBK");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 关闭连接
     * @param ftpClient
     */
    public void CloseFtpClient(FTPClient ftpClient) {
        if(ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean downloadFtpFile(FTPClient ftpClient,String ftpPath, String localPath, String fileName) throws IOException {
        boolean falg = false;
        ftpClient.setControlEncoding("UTF-8"); // 中文支持
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        int endIndex = ftpPath.lastIndexOf("/") + 1;
        String ftpFileName = ftpPath.substring(endIndex);
        ftpPath = ftpPath.substring(0, ftpPath.lastIndexOf("/") + 1);
        ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes("UTF-8"), "ISO-8859-1"));

        FTPFile[] fs = null;
        fs = ftpClient.listFiles(new String((ftpFileName).getBytes("UTF-8"), "ISO-8859-1"));
        if (fs.length == 0 || fs == null) {

            fs = ftpClient.listFiles(new String((fileName).getBytes("UTF-8"), "ISO-8859-1"));
            if (fs.length != 0 && fs != null) {
                ftpFileName = fileName;
            }
        }
        if (fs.length == 0 || fs == null) {
            fs = ftpClient.listFiles(new String(ftpPath.getBytes("UTF-8"), "ISO-8859-1"));
        }
        for (FTPFile ff : fs) {
            String name = ff.getName();
            if (!name.equals(ftpFileName) && name.equals(fileName)) {
                ftpFileName = fileName;
            }
            if (name.equals(ftpFileName)) {
                log.info("文件名：" + ff.getName());
                long lRemoteSize = ff.getSize();
                long localSize = 0;
                File localFile = new File(localPath + "/" + ftpFileName);
                if (localFile.exists()) {
                    localSize = localFile.length();
                    if (localSize == lRemoteSize) {
                        return true;
                    } else {
                        localFile.delete();
                    }
                }
                OutputStream os = new FileOutputStream(localFile);
                falg = ftpClient.retrieveFile(new String((ftpFileName).getBytes("UTF-8"), "ISO-8859-1"), os);
                os.flush();
                os.close();
                System.out.println("falg :" + falg);
                log.info("ftp下载成功");
                break;
            }
        }
        ftpClient.logout();
        return falg;
    }

    public static void downloadFileUtil(InputStream is, String fileName, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=\"" + encode(request, fileName) + "\"");
        // 创建输出流//
        OutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区当中
        while ((len = is.read(buffer)) > 0) {
            // 输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        // 关闭文件输入流
        is.close();
        // 关闭输出流
        out.close();
    }

    public static String encode(HttpServletRequest request, String str) throws UnsupportedEncodingException {

        if (isMSBrowser(request)) {
            str = URLEncoder.encode(str, "UTF-8");
        } else {
            str = new String(str.getBytes("UTF-8"), "ISO-8859-1");
        }
        return str;
    }
    private static String[] IEBrowserSignals = { "MSIE", "Trident", "Edge" };
    public static boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        for (int i = 0; i < IEBrowserSignals.length; i++) {
            if (userAgent.contains(IEBrowserSignals[i]))
                return true;
        }
        return false;
    }
}
