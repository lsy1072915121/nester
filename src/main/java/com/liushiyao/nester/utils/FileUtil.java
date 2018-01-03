package com.liushiyao.nester.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {  
    
    // /**  
    // * 获取获取系统根目录下xml文件的路径的方法；  
    // */  
    // @SuppressWarnings("unused")  
    // private String projectFilePath = new File("").getAbsolutePath()  
    // + "\\config.xml";  
    //  
    // /**  
    // * 获取Web项目发布后(源码中src下)classes目录下某个xml路径的方法；  
    // */  
    // @SuppressWarnings("unused")  
    // private String srcFilePath = getClass().getClassLoader().getResource(  
    // "config.xml").getPath();  
  
    File file = null;  
    boolean flag = false;  
  
    /**  
     * 写入txt文件，可以在原文件内容的基础上追加内容(并判断目录是否存在，不存在则生成目录)  
     *   
     * @param value  
     *            写入文件内容  
     * @param fileCatage  
     *            文件父目录；  
     * @param fileName  
     *            文件名字；  
     * @param code  
     *            文件的编码；  
     * @throws IOException  
     */  
    public void WriteFile(String value, String fileCatage, String fileName,  
            String code) {  
        File file = null;  
        try {  
            file = new File(fileCatage);  
            if (!file.isDirectory())  
                file.mkdir();  
            else {  
                file = new File(fileCatage + fileName);  
                if (!file.exists())  
                    file.createNewFile();  
                FileOutputStream out = new FileOutputStream(file, true);  
                out.write(value.getBytes(code));  
                out.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /***  
     * 覆盖原来的内容；  
     *   
     * @param filePath  
     *            文件的路径  
     * @param content  
     *            保存的内容；  
     * @return  
     */  
    public boolean saveFile(String filePath, String content) {  
        boolean successful = true;  
        FileOutputStream fout = null;  
        try {  
            fout = new FileOutputStream(new File(filePath), false);  
            fout.write(content.getBytes());  
        } catch (FileNotFoundException e1) {  
            successful = false;  
        } catch (IOException e) {  
            successful = false;  
        } finally {  
            if (fout != null) {  
                try {  
                    fout.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
        return successful;  
    }  
  
    /**  
     * 删除文件的综合操作( 根据路径删除指定的目录或文件，无论存在与否)  
     *   
     * @param sPath  
     *            要删除的目录或文件  
     *@return 删除成功返回 true，否则返回 false。  
     */  
    public boolean DeleteFolder(String sPath) {  
        flag = false;  
        file = new File(sPath);  
        // 判断目录或文件是否存在  
        if (!file.exists()) { // 不存在返回 false  
            return flag;  
        } else {  
            // 判断是否为文件  
            if (file.isFile()) { // 为文件时调用删除文件方法  
                return deleteFile(sPath);  
            } else { // 为目录时调用删除目录方法  
                return deleteDirectory(sPath);  
            }  
        }  
    }  
  
    /**  
     * 删除单个文件  
     *   
     * @param sPath  
     *            被删除文件的文件名  
     * @return 单个文件删除成功返回true，否则返回false  
     */  
    public boolean deleteFile(String sPath) {  
        flag = false;  
        file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    }  
  
    /**  
     * 删除目录（文件夹）以及目录下的文件  
     *   
     * @param sPath  
     *            被删除目录的文件路径  
     * @return 目录删除成功返回true，否则返回false  
     */  
    public boolean deleteDirectory(String sPath) {  
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        // 如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        flag = true;  
        // 删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            // 删除子文件  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag)  
                    break;  
            } // 删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag)  
                    break;  
            }  
        }  
        if (!flag)  
            return false;  
        // 删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
  
    /**  
     * 按字节【读】取文件的内容；  
     *   
     * @param Offset  
     *            读取内容的开始出  
     * @param length  
     *            内容的长度；  
     * @param filePath  
     *            文件的路径；  
     * @param code  
     *            编码；  
     * @return 返回相应的内容；  
     * @throws Exception  
     */  
    public String readFileByByte(int Offset, int length, String filePath,  
            String code) {  
        File file = new File(filePath);  
        FileInputStream fis = null;  
        try {  
            fis = new FileInputStream(file);  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return null;  
        }  
        try {  
            fis.skip(Offset);  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        }  
        byte[] bytes = new byte[length];  
        try {  
            fis.read(bytes);  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        }  
        try {  
            fis.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        }  
        try {  
            return new String(bytes, code);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /**  
     * 将流中的文本读入一个 BufferedReader 中  
     *   
     * @param filePath  
     *            文件路径  
     * @param code  
     *            编码格式  
     * @return  
     * @throws IOException  
     */  
  
    public BufferedReader readToBufferedReader(String filePath, String code)  
            throws IOException {  
        BufferedReader bufferedReader = null;  
        File file = new File(filePath);  
        if (file.isFile() && file.exists()) { // 判断文件是否存在  
            InputStreamReader read = new InputStreamReader(new FileInputStream(  
                    file), code);// 考虑到编码格式  
            bufferedReader = new BufferedReader(read);  
        }  
        return bufferedReader;  
    }  
  
    /**  
     * 将流中的文本读入一个 StringBuffer 中  
     *   
     * @param filePath  
     *            文件路径  
     * @throws IOException  
     */  
    public StringBuffer readToBuffer(String filePath, String code) {  
        StringBuffer buffer = new StringBuffer();  
        InputStream is;  
        try {  
            File file = new File(filePath);  
            if (!file.exists())  
                return null;  
            is = new FileInputStream(filePath);  
            String line; // 用来保存每行读取的内容  
            BufferedReader reader = new BufferedReader(new InputStreamReader(  
                    new FileInputStream(file), code));  
            line = reader.readLine(); // 读取第一行  
            while (line != null) { // 如果 line 为空说明读完了  
                buffer.append(line); // 将读到的内容添加到 buffer 中  
                // buffer.append("\n"); // 添加换行符  
                line = reader.readLine(); // 读取下一行  
            }  
            reader.close();  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
  
    public String loadFile(String filePath, String charset) {  
        FileInputStream fin = null;  
        StringBuffer sb = new StringBuffer();  
        try {  
            fin = new FileInputStream(new File(filePath));  
            byte[] buffer = new byte[1024/*Integer.MAX_VALUE*/];  
            int start = -1;  
            while ((start = fin.read(buffer)) != -1) {  
                sb.append(new String(buffer, 0, start, charset));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fin != null) {  
                try {  
                    fin.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
        return sb.toString();  
    }  
  
    /**  
     * 获取某个目录下所有文件或者获取某个文件的大小； 单位：MB  
     *   
     * @param file  
     * @return  
     */  
    public static double getDirSize(File file) {  
        // 判断文件是否存在  
        if (file.exists()) {  
            // 如果是目录则递归计算其内容的总大小  
            if (file.isDirectory()) {  
                File[] children = file.listFiles();  
                double size = 0;  
                for (File f : children)  
                    size += getDirSize(f);  
                return size;  
            } else {// 如果是文件则直接返回其大小,以“兆”为单位  
                double size = (double) file.length() / 1024 / 1024;  
                return size;  
            }  
        } else {  
            System.out.println("获取文件大小错误！！文件或者文件夹不存在，请检查路径是否正确！");  
            return 0.0;  
        }  
    }  
  
    /**  
     * 获取某个目录下所有的文件的全路径和文件名的集合；  
     *   
     * @return  
     */  
    public List<List<String>> getAllFile(String mulu) {  
        File file = new File(mulu);  
        File[] files = file.listFiles();  
        List<List<String>> ret = new ArrayList<List<String>>();  
        List<String> allFilePath = new ArrayList<String>();  
        List<String> allFileName = new ArrayList<String>();  
        for (int i = 0; i < files.length; i++) {  
            if (files[i].isDirectory()) {  
                allFilePath.add(files[i].toString());  
                allFileName.add(files[i].getName());  
            }  
        }  
        ret.add(allFilePath);  
        ret.add(allFileName);  
        return ret;  
    }  
  
} 