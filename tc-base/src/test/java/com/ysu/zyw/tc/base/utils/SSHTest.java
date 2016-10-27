package com.ysu.zyw.tc.base.utils;

import com.jcraft.jsch.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SSHTest {

    public static final String[] HOSTS =
            {
                    "172.16.75.40",
                    "172.16.75.41",
                    "172.16.75.42",
                    "172.16.75.43",
                    "172.16.75.44",
                    "172.16.75.45",
                    "172.16.63.245",
                    "172.16.63.234",
                    "172.16.63.248",
                    "172.16.75.20",
                    "172.16.75.21",
                    "172.16.75.22",
                    "172.16.75.23",
                    "172.16.75.24",
                    "172.16.75.25",
                    "172.16.75.26",
                    "172.16.75.27",
                    "172.16.75.28",
                    "172.16.75.29",
                    "172.16.75.30",
                    "172.16.75.31",
                    "172.16.75.32"
            };

    public static final String USERNAME = "*****";

    public static final String PASSWORD = "*****";

    @Test
    public void test1() throws JSchException, IOException, InterruptedException {
        Arrays.stream(HOSTS).forEach(host -> {
            try {
                exec(host, 22, USERNAME, PASSWORD, "date");
            } catch (JSchException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void exec(String host,
                     int port,
                     String username,
                     String password,
                     String cmd) throws JSchException, IOException, InterruptedException {
        JSch jSch = new JSch();

        Session session = jSch.getSession(username, host, port);
        session.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(cmd);
        ((ChannelExec) channel).setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);
        InputStream inputStream = channel.getInputStream();

        System.out.println("# connect-server [" + host + "]");

        channel.connect();

        System.out.println("> exec-cmd [" + cmd + "]");

        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int exitStatus = -99;
        while (true) {
            while (inputStream.available() > 0) {
                int i = inputStream.read(bytes, 0, 1024);
                if (i < 0)
                    break;
                sb.append(new String(bytes, 0, i));
            }
            if (channel.isClosed()) {
                if (inputStream.available() > 0)
                    continue;
                exitStatus = channel.getExitStatus();
                break;
            }
            Thread.sleep(1000);
        }
        System.out.print("> cmd-rs \n" + sb);
        System.out.println("# exit-status [" + channel.getExitStatus() + "]");
        System.out.println();
        channel.disconnect();
        session.disconnect();
    }

}
