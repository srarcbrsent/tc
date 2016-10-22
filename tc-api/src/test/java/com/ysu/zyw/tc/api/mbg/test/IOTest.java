package com.ysu.zyw.tc.api.mbg.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOTest {

    @Test
    public void test01() throws IOException {
        File file = new File("/home/zhangyaowu/local/docs/1");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (channel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            System.out.println(byteBuffer);
            System.out.println(new String(byteBuffer.array()));
            byteBuffer.clear();
        }
    }

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
                Thread.sleep(RandomUtils.nextInt(5000));
                System.out.println(bufferedReader.readLine());
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test02() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try (ServerSocket serverSocket = new ServerSocket(10180)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new Worker(socket));
            }
        }
    }

    @Test
    public void test03() throws IOException {
        for (int i = 0; i < 50; i++) {
            try (Socket socket = new Socket("rdb.tc.com", 10180);
                 OutputStream outputStream = socket.getOutputStream();
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                System.out.println("new socket ...");
                bufferedWriter.write(UUID.randomUUID().toString() + "_" + new Date());
            }
        }
    }

}
