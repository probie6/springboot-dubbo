package com.newbie.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/****
 * 文件批量变更类
 */
@Slf4j
public class FileOperate {
    private static final String UTF8 = "utf-8";
    private String message;

    public FileOperate() {
        log.info("init 4 constructor");
    }

    /*****
     * 工程 关键字修改,包名变更规则配置
     * @param args
     */
    public static void main(String[] args) {

        FileOperate fo = new FileOperate();
        Map<String, String> currMap = new LinkedHashMap<>();
        /** 需要还原的关键字****/
        /** 需要还原的关键字****/
        currMap.put("zkhy", "oui");//oursui属于关键字
        currMap.put("Zkhy", "Oui");


        /** 开始替换****/
        currMap.put("ZKHY", "OUI"); //如替换 指定目录下含有HelloWord的文本内容的文件或者文件名，全部替换为HelloDemo


        /*** 指定目录 开始批量替换*******/
        fo.renameBatch("D:\\web-html-server\\web", currMap);


    }

    public void renameBatch(String folder, Map<String, String> matchMap) {

        String currFolder = folder.replace("/", "\\");
        if (currFolder.endsWith("\\")) {
            currFolder = currFolder.substring(0, currFolder.lastIndexOf('\\'));
        }
        String tempBak = currFolder + "_temp_bak_";
        /**根据规则批量修改目录和文件中内容，并放到临时目录中 ****/
        this.createFolder(tempBak);
        this.rename(currFolder, tempBak, matchMap);
        /** 删除当前目录****/
        log.info("删除目录:" + currFolder);
        this.delFolder(currFolder);
        /** 复制临时目录到当前目录*****/
        log.info("从临时目录" + tempBak + "\n 复制到当前目录:" + currFolder);
        this.copyFolder(tempBak, currFolder);

        log.info("删除临时目录" + tempBak);
        /** 删除临时目录*****/
        this.delFolder(tempBak);
        //完成替换
    }

    /****
     * 替换路径
     * @param content
     * @param replaceMap
     * @return
     */
    public String replacContent(String content, Map<String, String> replaceMap) {
        String temp = content;
        String[] keys = replaceMap.keySet().toArray(new String[replaceMap.keySet().size()]);
        for (String key : keys) {
            temp = temp.replace(key, replaceMap.get(key) == null ? "" : replaceMap.get(key));
        }
        return temp;
    }

    /***
     * 替换文件
     * @param curr
     * @param path
     * @param newPath
     * @param replaceMap
     */
    public void renameByFile(File curr, String path, String newPath, Map<String, String> replaceMap) {

        String updatePath = curr.getAbsolutePath().replace(path, newPath);
        updatePath = replacContent(updatePath, replaceMap);
        log.info("从" + curr.getAbsolutePath());
        log.info("复制到" + updatePath);
        if (curr.isDirectory()) {
            if (curr.getAbsolutePath().endsWith(".svn")) {
                return;
            }
            this.createFolder(updatePath);
            renameByFile(curr.listFiles(), path, newPath, replaceMap);
        }
        if (curr.isFile()) {
            String text = "";
            if (curr.getAbsolutePath().endsWith(".jar") ||
                    curr.getAbsolutePath().endsWith(".png") ||
                    curr.getAbsolutePath().endsWith(".jpg") ||
                    curr.getAbsolutePath().endsWith(".otf") ||
                    curr.getAbsolutePath().endsWith(".eot") ||
                    curr.getAbsolutePath().endsWith(".ttf") ||
                    curr.getAbsolutePath().endsWith(".woff") ||
                    curr.getAbsolutePath().endsWith(".woff2")

            ) {
                try {
                    this.copyFile(curr.getAbsolutePath(), updatePath);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.info("从" + curr.getAbsolutePath() + "拷贝失败" + updatePath);
                }
            } else {
                /* 替换内容**/
                try {
                    text = this.readTxt(curr.getAbsolutePath(), UTF8);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                text = replacContent(text, replaceMap);
                this.createFile(updatePath, text, UTF8);
            }
        }
    }

    /***
     * 批量替换文件
     * @param files
     * @param path
     * @param newPath
     * @param replaceMap
     */
    public void renameByFile(File[] files, String path, String newPath, Map<String, String> replaceMap) {
        for (File curr : files) {
            renameByFile(curr, path, newPath, replaceMap);
        }
    }

    /**
     * 修改文件名或者文件夹名
     **/
    public void rename(String path, String newPath, Map<String, String> replaceMap) {
        log.info("从" + path);
        log.info("迁移到:" + newPath);
        this.delFolder(newPath);
        this.createFolders(newPath);
        File file = new File(path);
        this.renameByFile(file.listFiles(), path, newPath, replaceMap);
    }


    /***获取文件内容
     * @throws IOException **/
    public String readText(File file) throws IOException {
        return readTxt(file.getAbsolutePath(), UTF8);
    }

    public String readLine( BufferedReader br){
        StringBuilder str = new StringBuilder();
        try {
            String data = "";
            while ((data = br.readLine()) != null) {
                str.append(data + "\n");
            }
            return str.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "";
    }
    /**
     * 读取文本文件内容
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding        文本文件打开的编码方式
     * @return 返回文本文件的内容
     */
    public String readTxt(String filePathAndName, String encoding) throws IOException {

        String tempEncoding = encoding;
        String st = "";
        if (StringUtil.isNullOrEmpty(tempEncoding)) {
            tempEncoding = UTF8;
        }
        try (
                InputStreamReader isr = new InputStreamReader(new FileInputStream(filePathAndName), tempEncoding);
                BufferedReader br = new BufferedReader(isr)
        ) {
            st = readLine(br);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        }
        return st;
    }

    /**
     * 新建目录
     *
     * @param folderPath 目录
     * @return 返回目录创建后的路径
     */
    public String createFolder(String folderPath) {
        String txt = folderPath;
        try {
            File myFilePath = new File(txt);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            message = "创建目录操作出错";
        }
        return txt;
    }

    /**
     * 多级目录创建
     */
    public String createFolders(String folderPath) {

        try {
            String temp = folderPath.replace("\\", "/");
            String[] arr = temp.split("/");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                stringBuilder.append("/" + arr[i]);
                if (arr[i].trim().equals("")) {
                    continue;
                }
                this.createFolder(stringBuilder.toString());
            }
        } catch (Exception e) {
            message = "创建目录操作出错！";
        }
        return folderPath;
    }

    /**
     * 新建文件
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent     文本文件内容
     * @return
     */
    public void createFile(String filePathAndName, String fileContent) {
        String filePath = filePathAndName;
        File myFilePath = new File(filePath);
        try {

            if ((!myFilePath.exists()) && myFilePath.createNewFile()) {
                message = "创建文件成功";
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            message = e.getMessage();
            return;
        }
        try (FileWriter resultFile = new FileWriter(myFilePath)) {
            try (PrintWriter myFile = new PrintWriter(resultFile)) {
                String strContent = fileContent;
                myFile.println(strContent);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            message = e.getMessage();
        }
    }

    /**
     * 有编码方式的文件创建
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent     文本文件内容
     * @param encoding        编码方式 例如 GBK 或者 UTF-8
     * @return
     */
    public void createFile(String filePathAndName, String fileContent,
                           String encoding) {
        String filePath = filePathAndName;
        File myFilePath = new File(filePath);
        try {
            if ((!myFilePath.exists()) && myFilePath.createNewFile()) {
                message = "创建文件成功";
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            message = "创建文件失败";
            return;
        }

        try (PrintWriter myFile = new PrintWriter(myFilePath, encoding)) {
            String strContent = fileContent;
            myFile.println(strContent);
        } catch (Exception e) {
            message = "创建文件操作出错";
        }
    }

    /**
     * 删除文件
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @return Boolean 成功删除返回true遭遇异常返回false
     */
    public boolean delFile(String filePathAndName) {
        boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                Files.delete(Paths.get(filePath));
                bea = true;
            } else {
                message = (filePathAndName + "删除文件操作出错");
            }
        } catch (Exception e) {
            message = e.toString();
        }
        return bea;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     * @return
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            Files.delete(Paths.get(filePath));
        } catch (Exception e) {
            message = ("删除文件夹操作出错");
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public boolean delAllFile(String path) {
        boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        }
        if (!file.isDirectory()) {
            return bea;
        }
        String[] tempList = file.list();
        File temp = null;
        for (String curr : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + curr);
            } else {
                temp = new File(path + File.separator + curr);
            }
            if (temp.isFile()) {
                try {
                    Files.delete(Paths.get(temp.getAbsolutePath()));
                } catch (IOException e) {
                    log.error(e.getMessage());
                    message = "删除失败";
                }
            } else if (temp.isDirectory()) {
                delAllFile(path + "/" + curr);// 先删除文件夹里面的文件
                delFolder(path + "/" + curr);// 再删除空文件夹
                bea = true;
            }
        }

        return bea;
    }

    /**
     * 复制单个文件
     *
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名
     * @return
     */
    public void copyFile(String oldPathFile, String newPathFile) {
        File oldfile = new File(oldPathFile);
        if (!oldfile.exists()) {
            return;
        }
        try (
                InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPathFile)
        ) {
            int byteread = 0;
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } catch (Exception e) {
            message = ("复制单个文件操作出错");
        }
    }

    public void closeInputStream(InputStream ins) {
        if (ins != null) {
            try {
                ins.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void closeFileOutputStream(FileOutputStream fs) {
        if (fs != null) {
            try {
                fs.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void writeFile(FileInputStream input, FileOutputStream output){
        try{
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }
    /***
     * 复制文件
     * @param temp
     * @param newPath
     */
    public void copyFile(File temp, String newPath) {
        try(FileInputStream input = new FileInputStream(temp);
            FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()))
                ){
                    writeFile(input,output);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 复制整个文件夹的内容
     *
     * @param oldPath 准备拷贝的目录
     * @param newPath 指定绝对路径的新目录
     * @return
     */
    public void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    copyFile(temp, newPath);
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            message = "复制整个文件夹内容操作出错";
        }
    }

    /**
     * 移动文件
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    /**
     * 移动目录
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public String getMessage() {
        return this.message;
    }
}

