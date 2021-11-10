package com.example.h2newsjava11.utils;

import com.example.h2newsjava11.model.Article;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils {

    private static final int BUFFER_SIZE = 4096;

    /**
 * This method get information from txt file and put it into Article object.
 * @param path directrory to txt file,
 * @param txtFileName           name of txt file
 * @return Article object with filed head and body
 */

    public static Article txtToArticle (String path, String txtFileName){
        StringBuilder sbHead = new StringBuilder("");
        StringBuilder sbBody = new StringBuilder("");
        try {
            //after unzip we have articles.txt
            Scanner in = new Scanner(new FileReader(path + txtFileName));
            sbHead.append(in.nextLine());
            while (in.hasNextLine()) {
                sbBody.append(in.nextLine());
                sbBody.append('\n');
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        //System.out.println(sbHead.toString());
        //System.out.println(sbBody.toString());
        return new Article(sbHead.toString(), sbBody.toString());
    }

    /**
     * void method "unzipFile" take ZipInputStream and unzip data intro directory
     * @param zip input stream
     * @param path dir where zip file is located, and where unzip file will be
     * @param fileName name of zip file
     *
     */
    public static void unzipFile(ZipInputStream zip, String path,String fileName){
        //save file on hard disk
        try {
            //ZipInputStream zipIn = new ZipInputStream(new FileInputStream(path+fileName));
            ZipInputStream zipIn = zip;
            ZipEntry entity = zipIn.getNextEntry();
            String filePath = path + File.separator + entity.getName();
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath)
            );
            byte [] bytesIn = new byte[BUFFER_SIZE];
            int cnt = 0;
            while ((cnt=zipIn.read(bytesIn)) != -1){
                bos.write(bytesIn,0,cnt);
            }
            bos.close();
            zipIn.closeEntry();
            zipIn.close();
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
