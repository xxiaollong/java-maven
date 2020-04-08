package com.example.nio;

import org.junit.Test;

import java.io.*;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * NIO
 *
 *  通道(Channel)：用于源节点与目标节点的连接，在Java NIO中负责缓冲区数据的传输。
 *                Channel本身不存储数据，因此需要配合缓冲区进行传输。
 *
 *  通道的主要实现类：
 *      FileChannel             文件
 *      SocketChannel           网络（TCP）
 *      ServerSocketChannel     网络（TCP）
 *      DatagramChannel         网络（UDP）
 *
 *  获取通道：
 *  1、Java针对支持通道的类提供了getChannel()方法
 *      本地IO
 *          FileInputStream/FileOutputStream
 *          RandomAccessFile
 *      网络IO
 *          Socket
 *          ServerSocket
 *          DatagramSocket
 *
 *  2、在JDK1.7中的NIO2针对各个通道提供了静态方法open()
 *
 *  3、在JDK1.7中的NIO2中的Files工具类的newByteChannel()
 *
 *
 */
public class NIOTest01 {

    // 利用通道完成文件复制(直接缓冲区)--简写
    @Test
    public void test04() throws IOException {
        // 获取通道
        FileChannel inChannel = FileChannel.open(
                Paths.get("E:/file/video/video_1520844575461.mp4"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(
                Paths.get("E:/file/video/video_1102.mp4"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 传输数据
//        inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());


        // 关闭资源
        outChannel.close();
        inChannel.close();

    }

    // 利用通道完成文件复制(直接缓冲区)
    @Test
    public void test03() throws IOException {
        // 获取通道
        FileChannel inChannel = FileChannel.open(
                Paths.get("E:/file/video/video_1520844575461.mp4"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(
                Paths.get("E:/file/video/video_1101.mp4"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 创建直接缓冲区
//        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        MappedByteBuffer inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 复制文件
        byte[] bytes = new byte[inBuffer.limit()];
        inBuffer.get(bytes);
        outBuffer.put(bytes);

        // 关闭资源
        outChannel.close();
        inChannel.close();

    }


    // 利用通道完成文件复制(非直接缓冲区)
    @Test
    public void test02() throws Exception {
        // 创建输入输出流
        FileInputStream inputStream = new FileInputStream("E:/file/video/video_1520844575461.mp4");
        FileOutputStream outputStream = new FileOutputStream("E:/file/video/video_temp.mp4");

        // 获取通道
        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 复制数据
        while (inChannel.read(buffer) != -1){
            // 切换读取模式
            buffer.flip();
            // 读取数据
            outChannel.write(buffer);
            // 清空缓冲区
            buffer.clear();
        }

        // 关闭资源
        outChannel.close();
        inChannel.close();
        outputStream.close();
        inputStream.close();

    }

    // 创建缓冲区
    @Test
    public void test01(){
        // 创建非直接缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        // 创建直接缓冲区
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);

        // 判断缓冲区类型
        System.out.println(allocate.isDirect());
        System.out.println(direct.isDirect());

    }
}
