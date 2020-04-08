package com.example.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.SortedMap;

/**
 * 分散读取与聚集写入
 *
 *  分散读取(Scattering Reads): 将通道中的数据分散到多个缓冲区中
 *  聚集写入(Gathering Writes): 将多个缓冲区的数据聚集到通道中
 *
 *
 * 字符集
 *  编码：字符串 -> 字节数组
 *  解码：字节数组 -> 字符串
 *
 */
public class NIOTest02 {

    // 编解码
    @Test
    public void test3() throws Exception {
        Charset charset = Charset.forName("GBK");

        // 获取编码器
        CharsetEncoder encoder = charset.newEncoder();

        // 获取解码器
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("天气不太好");
        buffer.flip();

        // 编码
        ByteBuffer encode = encoder.encode(buffer);
        for (byte b : encode.array()) {
            System.out.println(b);
        }

        // 解码
        CharBuffer decode = decoder.decode(encode);
        System.out.println(decode.toString());

    }


    // 所有字符集
    @Test
    public void test2(){
        SortedMap<String, Charset> charsets = Charset.availableCharsets();

        for (Map.Entry<String, Charset> entry : charsets.entrySet()) {
            System.out.println(entry.getKey() +" = "+entry.getValue());
        }
    }


    // 分散和聚集
    @Test
    public void test1() throws Exception {
        RandomAccessFile read = new RandomAccessFile("E:/file/bbb.txt", "rw");

        // 获取管道
        FileChannel readChannel = read.getChannel();

        // 创建多个缓冲区
        ByteBuffer bf1 = ByteBuffer.allocate(100);
        ByteBuffer bf2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bfs = {bf1, bf2};

        // 分散读取
        readChannel.read(bfs);

        // 将缓冲区转为可读模式
        for (ByteBuffer bf : bfs) {
            bf.flip();
        }

        // 打印缓冲区中的数据
//        System.out.println(new String(bfs[0].array(), 0, bfs[0].limit()));
//        System.out.println("===========================================");
//        System.out.println(new String(bfs[1].array(), 0, bfs[1].limit()));

        // 聚集写入
        RandomAccessFile write = new RandomAccessFile("E:/file/bbb1.txt", "rw");
        // 获取通道
        FileChannel writeChannel = write.getChannel();

        // 写出数据
        writeChannel.write(bfs);

        // 关闭通道
        writeChannel.close();
        readChannel.close();
        write.close();
        read.close();

    }

}
