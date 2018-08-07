package com.example.txl.gankio.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/5
 * description：
 */
public class DownUtils {
    //定义下载资源的路径
    private String path;
    //指定下载文件所保存的位置
    private String targetFile;
    //定义使用多少线程下载该资源
    private int threadNumber;
    private DownThread[] threads;
    private int fileSize;

    public DownUtils(String path, String targetFile, int threadNumber) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNumber = threadNumber;
        threads = new DownThread[threadNumber];
    }

    public void download() throws Exception{
        URL url = new URL( path );
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout( 5*1000 );
        connection.setRequestMethod( "GET" );
        connection.setRequestProperty( "Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                + "application/x-shockwave-flash, application/xaml+xml, "
                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, "
                + "application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        connection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
        // 指定请求uri的源资源地址
//                conn.setRequestProperty("Referer", downloadUrl);
        // 设置 HttpURLConnection的字符编码
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Connection", "Keep-Alive");
        fileSize = connection.getContentLength();
        connection.disconnect();
        int currentPartSize = fileSize/threadNumber +1;
        File mfile = new File( targetFile );
        if(!mfile.exists()){
            mfile.createNewFile();
        }
        RandomAccessFile file = new RandomAccessFile( targetFile,"rw" );
        file.setLength( fileSize );
        file.close();
        for (int i=0; i<threadNumber; i++){
            int startPos = i * currentPartSize;
            RandomAccessFile currentPart = new RandomAccessFile( targetFile,"rw" );
            currentPart.seek( startPos );
            threads[i] = new DownThread( startPos,currentPartSize,currentPart );
            threads[i].start();
        }
    }

    public double getCompleteRate(){
        int sumSize = 0;
        for (int i=0; i<threadNumber; i++){
            sumSize += threads[i].length;
        }
        return sumSize * 1.0/fileSize;
    }


    private class DownThread extends Thread{
        private int startPos;
        //定义当前线程负责下载的文件大小
        private int currentPartSize;
        private RandomAccessFile currentPart;
        //定义该线程已经下载的字节数
        private int length;

        public DownThread(int startPos, int currentPartSize, RandomAccessFile currentPart) {
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }

        @Override
        public void run() {
            try {
                URL url = new URL( path );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout( 5*1000 );
                connection.setRequestMethod( "GET" );
                connection.setRequestProperty( "Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                        + "application/x-shockwave-flash, application/xaml+xml, "
                        + "application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, "
                        + "application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                connection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
                // 指定请求uri的源资源地址
//                conn.setRequestProperty("Referer", downloadUrl);
                // 设置 HttpURLConnection的字符编码
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                InputStream inputStream = connection.getInputStream();
                skipFully( inputStream,this.startPos );
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                while (length < currentPartSize && (hasRead = inputStream.read(buffer))>0){
                    currentPart.write( buffer,0,hasRead );
                    length += hasRead;
                }
                currentPart.close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void skipFully(InputStream inputStream, long bytes) throws IOException{
            long remainning = bytes;
            long len = 0;
            while (remainning > 0){
                len = inputStream.skip( remainning );
                remainning -= len;
            }
        }
    }
}
