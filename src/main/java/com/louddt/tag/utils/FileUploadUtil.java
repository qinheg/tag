package com.louddt.tag.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Calendar;

@Slf4j
public class FileUploadUtil {

    public static String file2String(InputStream in, String fileName) throws Exception {
        InputStreamReader readerin = null;
        BufferedReader br = null;
        String str1 = "";
        try {
            String code = "UTF-8";
            int beginIndex = fileName.lastIndexOf(".");
            String filetype = fileName.substring(beginIndex);
            if (".txt".equals(filetype)) {
                code = "UTF-8";
            }
            String str = "";
            // 从文件系统中的某个文件中获取字节
            readerin = new InputStreamReader(in, code);// InputStreamReader
            // 字节流通向字符流的桥梁,
            br = new BufferedReader(readerin);// 从字符输入流中读取文件中的内容,封装了一个newInputStreamReader的对象
            while ((str = br.readLine()) != null) {
                str1 += str + "\n";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (readerin != null) {
                try {
                    readerin.close();
                } catch (Exception e1) {
                }
            }
        }
        return str1;
    }

    /**
     * 导出文本文件
     * @param response
     * @param jsonStr
     * @param fileName
     */
    public static void writeToTxt(HttpServletResponse response, String jsonStr, String fileName) {//设置响应的字符集
        response.setCharacterEncoding("utf-8");
        //设置响应内容的类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader(
                "Content-Disposition",
                "attachment; filename=" + genAttachmentFileName(fileName,String.valueOf(System.currentTimeMillis()) + ".txt"));//通过后缀可以下载不同的文件格式
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(delNull(jsonStr).getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            log.error("导出文件文件出错，e:{}",e);
        } finally {try {
            buff.close();
            outStr.close();
        } catch (Exception e) {
            log.error("关闭流对象出错 e:{}",e);
        }
        }
    }

    /**
     * 如果字符串对象为 null，则返回空字符串，否则返回去掉字符串前后空格的字符串
     * @param str
     * @return
     */
    public static String delNull(String str) {
        String returnStr = "";
        if (StringUtils.isNotBlank(str)) {
            returnStr = str.trim();
        }
        return returnStr;
    }

    /**
     * 解决中文乱码
     * @param cnName
     * @param defaultName
     * @return
     */
    public static String genAttachmentFileName(String cnName, String defaultName) {
        try {
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }

}
