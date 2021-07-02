package com.yuyadiyu.template1.util;

import java.io.*;

public class FileChargeUtil {

    public static byte[] fileToByteArray(String filePath) throws FileNotFoundException {
        //先将图片以字节流的方式输入到程序中
        File src = new File(filePath);
        byte[] dest = null; //这个目的地可有可无
        InputStream is = new FileInputStream(src);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// 有新增方法不能发生多态
        byte[] flush = new byte[1024 * 10];//定义了一个字节数组
        int len = -1;
        try {
            while ((len = is.read(flush)) != -1) {//把文件写入到程序中了(这里是通过)
//						String str=new String(flush,0,len);
//						System.out.println(str);
                baos.write(flush, 0, len);  //从程序写出到字节数组中
            }
            baos.flush();
            return baos.toByteArray();      //返回baos的字节数组
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;  //这里记得写

    }

    // 字节数组写出到图片 需要字节数组的数据源,以及文件的路径
    //1.字节数组读取到程序中(ByteArrayInputStream)2.程序写出到文件(FileOutputStream)
    public static void byteArrayToFile(byte[] src, String filePath) {
        File dest=new File(filePath);//输出图片的目的地，这里是文件写出的路径
        ByteArrayInputStream is=new ByteArrayInputStream(src);   //字节数组的流，先让它写到程序   src是数据源
        OutputStream os=null;
        try {
            os=new FileOutputStream(dest);
            byte[] flush=new byte[5];
            int len=-1;
            while((len=is.read(flush))!=-1){//这里是写入程序
                os.write(flush,0,len);//这一步是将程序写入到文件    这里一定要记住文件流一定要释放
            }
            os.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                if(os!=null) {
                    os.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
