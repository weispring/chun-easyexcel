//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.excel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

public class FileUtil {
    private static final int BUF = 4096;

    public FileUtil() {
    }

    public static boolean writeFile(File file, InputStream stream) throws FileNotFoundException {
        FileOutputStream o = null;

        try {
            makeDirs(file.getAbsolutePath());
            if (!file.exists()) {
                file.createNewFile();
            }

            o = new FileOutputStream(file);
            byte[] data = new byte[1024];
            boolean var4 = true;

            int length;
            while((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }

            o.flush();
            boolean var5 = true;
            return var5;
        } catch (FileNotFoundException var15) {
            throw new RuntimeException("FileNotFoundException occurred. ", var15);
        } catch (IOException var16) {
            throw new RuntimeException("IOException occurred. ", var16);
        } finally {
            try {
                o.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }
    }

    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (folderName != null && !"".equals(folderName)) {
            File folder = new File(folderName);
            return folder.exists() && folder.isDirectory() ? true : folder.mkdirs();
        } else {
            return false;
        }
    }

    public static String getFolderName(String filePath) {
        if (filePath != null && !"".equals(filePath)) {
            int filePosi = filePath.lastIndexOf(File.separator);
            return filePosi == -1 ? "" : filePath.substring(0, filePosi);
        } else {
            return filePath;
        }
    }

    public static boolean doUnZip(String path, File file) throws IOException {
        ZipFile zipFile = new ZipFile(file, "utf-8");
        Enumeration en = zipFile.getEntries();

        while(en.hasMoreElements()) {
            ZipArchiveEntry ze = (ZipArchiveEntry)en.nextElement();
            File f = new File(path, ze.getName());
            if (ze.isDirectory()) {
                f.mkdirs();
            } else {
                f.getParentFile().mkdirs();
                InputStream is = zipFile.getInputStream(ze);
                OutputStream os = new FileOutputStream(f);
                IOUtils.copy(is, os, 4096);
                is.close();
                os.close();
            }
        }

        zipFile.close();
        return true;
    }

    public static void deletefile(String delpath) {
        File file = new File(delpath);
        if (!file.isDirectory()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] filelist = file.list();

            for(int i = 0; i < filelist.length; ++i) {
                File delfile = new File(delpath + File.separator + filelist[i]);
                if (!delfile.isDirectory()) {
                    delfile.delete();
                } else if (delfile.isDirectory()) {
                    deletefile(delpath + File.separator + filelist[i]);
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(File.separator);
    }
}
