package com.example.simple;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: dy
 * @Date: 2019/2/14 22:35
 * @Description:
 */
public class Test1 {
    @org.junit.Test
    public void test(){
        System.out.println(Color.RED.toString());
        System.out.println(Color.RED.ordinal());
    }

    @org.junit.Test
    public void testNio() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(8088));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            int select = selector.select();
            if(select == 0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                if(next.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    SocketChannel clentChanel = channel.accept();
                    System.out.println("连接ip: " + clentChanel.getRemoteAddress());
                    clentChanel.configureBlocking(false);
                    clentChanel.register(selector, SelectionKey.OP_READ);
                }
                if (next.isReadable()){
                    SocketChannel accept = (SocketChannel)next.channel();
                    accept.read(buffer);
                    String trim = new String(buffer.array()).trim();
                    buffer.clear();
                    accept.write(ByteBuffer.wrap(trim.getBytes()));
                }
                iterator.remove();
            }

        }

    }


}
