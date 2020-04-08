package com.example.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


/**
 * 网络NIO通信的三个核心
 *  1.通道（Channel）：负责连接
 *      SelectableChannel
 *          |--AbstractSelectableChannel
 *              |--SocketChannel
 *              |--ServerSocketChannel
 *              |--DatagramChannel
 *
 *              |--Pipe.SourceChannel
 *              |--Pipe.SinkChannel
 *
 *  2.缓冲区（Buffer）：负责数据的存取
 *  3.选择器（Selector）：是SelectableChannel的多路复用器，用于监控SelectableChannel的IO状态
 *
 */
public class NIOTest03 {

    // Pipe: 管道
    @Test
    public void test_pipe() throws IOException {
        // 创建管道
        Pipe pipe = Pipe.open();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 获取管道发送端
        Pipe.SinkChannel sink = pipe.sink();

        // 往管道中写数据
        buffer.put("这是数据".getBytes());
        buffer.flip();
        sink.write(buffer);
        buffer.clear();

        // 获取管道输出端
        Pipe.SourceChannel source = pipe.source();

        // 从管道中读取数据
        int len = source.read(buffer);
        buffer.flip();
        System.out.println(new String(buffer.array(),0, len));

        // 关闭资源
        source.close();
        sink.close();

    }

    // 非阻塞式服务端（UDP）
    @Test
    public void server_nio_2() throws IOException {
        // 获取网络通道
        DatagramChannel datagramChannel = DatagramChannel.open();

        // 绑定连接
        datagramChannel.bind(new InetSocketAddress(9898));

        // 设置为非阻塞流
        datagramChannel.configureBlocking(false);

        // 创建选择器
        Selector selector = Selector.open();

        // 将服务端通道注册到选择器上,并指定监听事件
        datagramChannel.register(selector, SelectionKey.OP_READ);

        // 轮询获取注册器上的事件并处理
        while (selector.select() > 0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();

                if (key.isReadable()) {     // 读事件
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    datagramChannel.receive(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array(),0,buffer.limit()));
                    buffer.clear();
                }
            }

            iterator.remove();
        }

        // 关闭资源
        datagramChannel.close();

    }

    // 非阻塞式客户端（UDP）
    @Test
    public void click_nio_2() throws IOException, InterruptedException {
        // 获取网络通道
        DatagramChannel channel = DatagramChannel.open();

        // 设置为非阻塞流
        channel.configureBlocking(false);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 发送数据
        for (int i = 0; i < 10; i++) {
            buffer.put((new Date().toString() +" -- "+ i).getBytes());
            buffer.flip();
            channel.send(buffer, new InetSocketAddress("127.0.0.1", 9898));
            buffer.clear();
            Thread.sleep(1000);
        }

        // 关闭资源
        channel.close();

    }

    // 非阻塞式服务端
    @Test
    public void server_nio_1() throws IOException {
        // 创建服务端网络通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        // 设置为非阻塞模式
        socketChannel.configureBlocking(false);

        // 绑定连接
        socketChannel.bind(new InetSocketAddress(9898));

        // 创建选择器
        Selector selector = Selector.open();

        // 将通道注册到选择器上,指定监听事件
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 轮询获取选择器上注册的事件
        while (selector.select() > 0) {
            // 获取当前选择器上所有注册的已就绪的监听事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                // 获取已就绪的事件
                SelectionKey sk = iterator.next();

                // 判断具体是那种类型的事件并作出相应的处理
                if (sk.isAcceptable()) {    // 接受事件
                    // 获取客户端连接通道
                    SocketChannel accept = socketChannel.accept();

                    // 切换非阻塞模式
                    accept.configureBlocking(false);

                    // 将通道注册到选择器上
                    accept.register(selector, SelectionKey.OP_READ);
                }else if (sk.isReadable()){     //读事件
                    // 获取读取状态通道
                    SocketChannel channel = (SocketChannel) sk.channel();

                    // 创建缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    // 读取数据
                    int len = 0;
                    while ((len = channel.read(buffer)) > 0) {
                        System.out.println(new String(buffer.array(), 0, len));
                    }
                }

                // 删除选择键
                iterator.remove();
            }

        }
    }

    // 非阻塞式客户端
    @Test
    public void click_nio_1() throws IOException, InterruptedException {
        // 获取网络通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        // 设置为非阻塞模式
        socketChannel.configureBlocking(false);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 数据传输
        for (int i = 0; i < 10; i++) {
            buffer.put((LocalDateTime.now().toString() +" -- "+ i).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            Thread.sleep(1000);
        }

        // 关闭资源
        socketChannel.close();
    }


    // 阻塞式服务端--双向
    @Test
    public void server2() throws IOException {
        // 获取网络通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 绑定端口号
        socketChannel.bind(new InetSocketAddress(9898));

        // 获取客户端连接通道
        SocketChannel accept = socketChannel.accept();

        // 获取文件通道
        FileChannel fileChannel = FileChannel.open(Paths.get("E:/file/2017Tmp.png"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 传输数据
        while (accept.read(buffer) != -1) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }

        // 发送反馈给客户端
        buffer.put("服务端完成接收数据".getBytes());
        buffer.flip();
        accept.write(buffer);
        buffer.clear();

        // 关闭资源
        fileChannel.close();
        accept.close();
        socketChannel.close();

    }

    // 阻塞式客户端--双向
    @Test
    public void click2() throws IOException {
        // 获取网络通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        // 获取文件通道
        FileChannel fileChannel = FileChannel.open(Paths.get("E:/file/20170703102326.png"), StandardOpenOption.READ);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 数据传输
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        // 数据传输完成，关闭通道
        socketChannel.shutdownOutput();


        // 接受服务端数据反馈
        int len = 0;
        while ((len = socketChannel.read(buffer)) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
            buffer.clear();
        }

        // 关闭通道
        socketChannel.close();
        fileChannel.close();

    }

    // 阻塞式服务端--单向
    @Test
    public void server1() throws IOException {
        // 获取网络通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 绑定端口号
        socketChannel.bind(new InetSocketAddress(9898));

        // 获取客户端连接通道
        SocketChannel accept = socketChannel.accept();

        // 获取文件通道
        FileChannel fileChannel = FileChannel.open(Paths.get("E:/file/2017Tmp.png"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 传输数据
        while (accept.read(buffer) != -1) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }

        // 关闭资源
        fileChannel.close();
        accept.close();
        socketChannel.close();

    }

    // 阻塞式客户端--单向
    @Test
    public void click1() throws IOException {
        // 获取网络通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        // 获取文件通道
        FileChannel fileChannel = FileChannel.open(Paths.get("E:/file/20170703102326.png"), StandardOpenOption.READ);

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 数据传输
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        // 关闭通道
        socketChannel.close();
        fileChannel.close();

    }

}
