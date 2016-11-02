package com.ysu.zyw.tc.components.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang.math.RandomUtils;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.Channels;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NettyTest {

    private final String pathname = "/Users/zerur/Documents/logs/data";

    private static boolean continueLoop = true;

    private final ChannelPipelineFactory channelPipelineFactory = () -> Channels.pipeline(
            new SimpleChannelHandler() {
                @Override
                public void messageReceived(ChannelHandlerContext ctx,
                                            MessageEvent e) throws Exception {
                    System.out.println(1);
                    super.messageReceived(ctx, e);
                }

                @Override
                public void channelConnected(ChannelHandlerContext ctx,
                                             ChannelStateEvent e) throws Exception {
                    System.out.println(2);
                    super.channelConnected(ctx, e);
                }

                @Override
                public void channelDisconnected(ChannelHandlerContext ctx,
                                                ChannelStateEvent e) throws Exception {
                    System.out.println(3);
                    super.channelDisconnected(ctx, e);
                }
            });

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Worker implements Runnable {

        private Socket socket;

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                Thread.sleep(RandomUtils.nextInt(1000));
                System.out.println(bufferedReader.readLine());
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test_bio() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathname)))) {
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                System.out.println(tmp);
            }
        }
    }

    @Test
    public void test_bio_net_server() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(60);
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            continueLoop = false;
            // // FIXME not a perfect stop method.
            try {
                Socket socket = new Socket("rdb.tc.com", 10180);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("start ...");
        try (ServerSocket serverSocket = new ServerSocket(10180)) {
            while (continueLoop) {
                Socket socket = serverSocket.accept();
                executorService.execute(new Worker(socket));
            }
        }

        executorService.shutdown();
        System.out.println("stop ...");
    }

    @Test
    public void test_bio_net_client() throws IOException {
        for (int i = 0; i < 50; i++) {
            try (BufferedWriter bufferedWriter =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new Socket("rdb.tc.com", 10180).getOutputStream()
                                 )
                         )) {
                String identifier = i + "_" + UUID.randomUUID().toString() + "_" + new Date();
                System.out.printf("new socket [%s] ...%n", identifier);
                bufferedWriter.write(identifier);
            }
        }
    }

    @Test
    public void test_nio_r() throws IOException {
        File file = new File(pathname);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            ByteBuffer bf = ByteBuffer.allocate(4);
            int bytesRead;
            while ((bytesRead = fileChannel.read(bf)) != -1) {
                bf.flip();
                byte[] bytes = new byte[bytesRead];
                // bf.get(bytes, bf.position(), bf.limit());
                bf.get(bytes, 0, bytesRead);
                baos.write(bytes, 0, bytes.length);
                bf.clear();
            }
        }
        System.out.print(baos.toString());
    }

    @Test
    public void test_nio_w() throws IOException {
        File file = new File(pathname);
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel channel = fos.getChannel();
        ByteBuffer byteBuffer = Charset.defaultCharset().encode("人生如剑" + new Date());
        while (channel.write(byteBuffer) != 0) {
        }
    }

    @Test
    public void test_nio_buffer() {
        // Capacity 容量 初始化的时候决定 可以放入多少个byte
        ByteBuffer bf = ByteBuffer.allocate(11);
        // 初始化的ByteBuffer为可写状态 position = 0, limit = max, capacity = max
        System.out.printf("init：position=%d limit=%d capacity=%d%n", bf.position(), bf.limit(), bf.capacity());
        for (int i = 0; i < 6; i++) {
            bf.put((byte) 'H');
        }
        // 写入之后 position 上升 position = 6, limit = max, capacity = max
        System.out.printf("puts：position=%d limit=%d capacity=%d%n", bf.position(), bf.limit(), bf.capacity());
        bf.flip();
        // flip之后变为可读状态 position = 0, limit = position_bak, capacity = max
        System.out.printf("flip：position=%d limit=%d capacity=%d%n", bf.position(), bf.limit(), bf.capacity());
        for (int i = bf.position(); i < bf.limit(); i++) {
            System.out.print((char) bf.get(i));
        }
        System.out.println();
        bf.clear();
        // clear之后清空bf变为可写状态 position = 0, limit = max, capacity = max
        System.out.printf("flip：position=%d limit=%d capacity=%d%n", bf.position(), bf.limit(), bf.capacity());
    }

    @Test
    public void test_nio_net_server() throws IOException {
        int count = 0;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(18002));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            int k = 0;
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    System.out.println(3);
                    SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    int i = sc.read(ByteBuffer.allocate(20));
                    if (i < 0) {
                        System.out.println(count++);
                        System.out.println(k++);
                    }
                    if (sc.isOpen()) {
                        sc.shutdownInput();
                        sc.shutdownOutput();
                        sc.close();
                    }
                }
            }
        }
    }

    @Test
    public void test_nio_net_client() throws IOException {
//        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("rdb.tc.com", 18002))) {
//            socketChannel.configureBlocking(false);
//            ByteBuffer buffer = ByteBuffer.allocate(20);
//            buffer.put("1".getBytes());
//            socketChannel.write(buffer);
//            while (true) {
//                int i = socketChannel.read(buffer);
//                if (i > 0) {
//                    byte[] b = buffer.array();
//                    System.out.println("接收数据: " + new String(b));
//                    socketChannel.close();
//                    System.out.println("连接关闭!");
//                    break;
//                }
//            }
//        }
        long l = System.currentTimeMillis();
        for (int j = 0; j < 50000; j++) {
            try {
                Socket socket = new Socket("localhost", 18002);
                socket.close();
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void test_netty_server() throws InterruptedException {
//        ServerBootstrap bootstrap = new ServerBootstrap(
//                new NioServerSocketChannelFactory(
//                        Executors.newCachedThreadPool(),
//                        Executors.newCachedThreadPool()
//                )
//        );
//        bootstrap.setPipelineFactory(channelPipelineFactory);
//        bootstrap.bind(new InetSocketAddress(8000));
//        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void test_netty_client() throws InterruptedException {
//        ClientBootstrap bootstrap = new ClientBootstrap(
//                new NioClientSocketChannelFactory(
//                        Executors.newCachedThreadPool(),
//                        Executors.newCachedThreadPool()
//                )
//        );
//        bootstrap.setPipelineFactory(channelPipelineFactory);
//        bootstrap.connect(new InetSocketAddress("www.tc.com", 8000));
//        TimeUnit.SECONDS.sleep(5);
    }

}
