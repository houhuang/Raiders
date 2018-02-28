package com.jd.raiders2.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by houhuang on 18/2/28.
 */
public class FileUtils {

    /**
     *  网SDCard中存储数据，需要动态申请权限
     *  在非UI线程中使用FileUtil存储数据会出问题
     * */

    //File.separator为文件分隔符”/“,方便之后在目录下创建文件
    private static String SDCardRoot = Environment.getExternalStorageDirectory() + File.separator;

    //在SDCard上创建文件
    public static File createFileInSDCard(String fileName, String dir) throws IOException
    {
        File file = new File(SDCardRoot + dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    //在SDCard上创建目录
    public static File createSDDir(String dir) throws IOException
    {
        File dirFile = new File(SDCardRoot + dir);
        dirFile.mkdir(); //mkdir()只能创建一层文件目录，mkdirs()可以创建多层文件目录
        return dirFile;
    }

    //判断文件是否存在
    public static boolean isFileExist(String fileName, String dir)
    {
        File file = new File(SDCardRoot + dir + File.separator + fileName);
        return file.exists();
    }

    //讲一个InputStream的数据写到SDCard中
    public static File write2SDFromInput(String fileName, String dir, InputStream inputStream)
    {
        File file = null;
        OutputStream outputStream = null;
        try {
            createSDDir(dir);
            file = createFileInSDCard(fileName, dir);
            outputStream = new FileOutputStream(file);
            byte buffer[] = new byte[4*1024]; //每次存4K

            int temp;
            while ((temp = inputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, temp);
            }

            outputStream.flush();

        }catch (Exception e)
        {
            System.out.println("写数据异常："+e);
        }finally {
            try {
                outputStream.close();
            }catch (Exception e2) {
                System.out.println(e2);
            }
        }
        return file;
    }

    public static boolean writeSDFromString(String fileName, String dir, String content)
    {
        File file = null;
        try {
            createSDDir(dir);
            file = createFileInSDCard(fileName, dir);
            FileOutputStream output = new FileOutputStream(file);
            output.write(content.getBytes());
            output.close();

            return true;
        }catch (Exception e){

        }

        return false;
    }

    public static String readSDCardFile(String fileName, String dir)
    {
        if (isFileExist(fileName, dir))
        {
            File file = new File(SDCardRoot + dir + File.separator + fileName);
            try {
                FileInputStream inputStream = new FileInputStream(file);

//                byte[] buffer = new byte[inputStream.available()];
//                inputStream.read(buffer);
//                return buffer.toString();

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null)
                {
                    sb.append(temp);
                }
                inputStream.close();
                return sb.toString();

            }catch (Exception e){
                System.out.print("读取失败！");
            }
        }

        return "";
    }


    /**
     *  往data/data下存储数据
     * */

    public static boolean saveFileTo_datadata(Context context, String fileName, String content)
    {


        try {
            FileOutputStream output = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            output.write(content.getBytes());
            output.close();

            return true;
        }catch (Exception e)
        {

        }

        return false;
    }


    public static String readFileFrom_datadata(Context context, String fileName)
    {
        try {
            FileInputStream inStream = context.openFileInput(fileName);//只需传文件名
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();//输出到内存

            int len=0;
            byte[] buffer = new byte[1024];
            while((len=inStream.read(buffer))!=-1){
                outStream.write(buffer, 0, len);//
            }

            byte[] content_byte = outStream.toByteArray();
            String content = new String(content_byte);

            return content;
        }catch (Exception e)
        {

        }

        return "";
    }

}
