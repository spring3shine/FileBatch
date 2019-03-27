package utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    /**
     * 批量修改文件编码方式
     *
     * 找到path文件夹下，后缀为suffix的所有文件
     * 并将原编码"sourceCode" 转变为"targetCode"
     *
     * @para path,suffix,sourceCode,targetCode
     * @return null
     * @exception null
     */
    public void recode(String path, String suffix, String fromCharsetName, String toCharsetName) throws Exception {
        List<File> files = findFiles(path);
        files = filterSuffix(files, suffix);
        resetCharset(files, fromCharsetName, toCharsetName);
    }

    /**
     * 找到path文件夹下的所有文件
     *
     * @para path
     * @return List<File>
     * @exception null
     */
    public List<File> findFiles(String path){
        File files = new File(path);
        List<File> fileArray = Arrays.asList(files.listFiles());
        for (File file: fileArray
            ) {
            System.out.println(file.getName());
            System.out.println(file);
            System.out.println(file.getPath());
        }
        return fileArray;
    }

    /**
     * 过滤List<File> files，留下所有后缀为suffix的文件
     *
     * @para path
     * @return List<File>
     * @exception null
     */
    public List<File> filterSuffix(List<File> files, String suffix) {
        List<File> ret = new ArrayList<>();
        for (File file : files
        ) {
            String name = file.getName();
            if (name.endsWith(suffix)) {
                ret.add(file);
            }
        }
        return ret;
    }

    /**
     * 过滤List<File> files，留下所有后缀为suffix的文件
     *
     * @para path
     * @return List<File>
     * @exception Exception
     */
    public void resetCharset(List<File> files, String fromCharsetName, String toCharsetName) throws Exception {
        for (File file: files
             ) {
            String fileContent = getFileContentFromCharset(file,
                    fromCharsetName);
            saveFile2Charset(file, toCharsetName, fileContent);
        }
    }

    /**
     * 以指定编码方式读取文件，返回文件内容
     *
     * @param file
     *            要转换的文件
     * @param fromCharsetName
     *            源文件的编码
     * @return
     * @throws Exception
     */
    public static String getFileContentFromCharset(File file,
                                                   String fromCharsetName) throws Exception {
        if (!Charset.isSupported(fromCharsetName)) {
            throw new UnsupportedCharsetException(fromCharsetName);
        }
        InputStream inputStream = new FileInputStream(file);
//        InputStreamReader reader = new InputStreamReader(inputStream);
        InputStreamReader reader = new InputStreamReader(inputStream,
                fromCharsetName);
        char[] chs = new char[(int) file.length()];
        reader.read(chs);
        String str = new String(chs).trim();
        reader.close();
        return str;
    }

    /**
     * 以指定编码方式写文本文件，存在会覆盖
     *
     * @param file
     *            要写入的文件
     * @param toCharsetName
     *            要转换的编码
     * @param content
     *            文件内容
     * @throws Exception
     */
    public static void saveFile2Charset(File file, String toCharsetName,
                                        String content) throws Exception {
        if (!Charset.isSupported(toCharsetName)) {
            throw new UnsupportedCharsetException(toCharsetName);
        }
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outWrite = new OutputStreamWriter(outputStream,
                toCharsetName);
        outWrite.write(content);
        outWrite.close();
    }

    /**
     * 将path下的文件重命名后移动到toPath
     *
     * @param path
     *            要写入的文件
     * @param toPath
     *            要转换的编码
     *
     */
    public void renameTo(String path, String toPath) {
        List<File> files = findFiles(path);
        for (File file : files
        ) {
            String name = file.getName();
            String newName = name.substring(name.lastIndexOf("_")+1);
            if(newName.length()==1){
                file.renameTo(new File(toPath+"\\" + "00"+newName));
            }else if(newName.length()==2){
                file.renameTo(new File(toPath+"\\" + "0"+newName));
            }
            file.renameTo(new File(toPath+"\\" + newName));
        }
    }

    /**
     * 将path下的txt文件，按顺序合并后，重命名为toPath（路径+文件名）
     *
     * @param path
     *            要写入的文件
     * @param toPath
     *            要转换的编码
     *
     */
    public void mergeTxt(String path, String toPath) throws Exception {
        List<File> files = findFiles(path);
        File targetFile = new File(toPath);
        OutputStream outputStream = new FileOutputStream(targetFile);
        OutputStreamWriter outWrite = new OutputStreamWriter(outputStream,"utf-8");
        for (File file : files
        ) {
            String content=getFileContentFromCharset(file,"utf-8");
            outWrite.write(content);
        }outWrite.close();
    }

}
