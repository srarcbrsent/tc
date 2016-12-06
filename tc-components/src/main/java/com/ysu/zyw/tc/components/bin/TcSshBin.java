package com.ysu.zyw.tc.components.bin;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@UtilityClass
public class TcSshBin {

    @SneakyThrows
    public static int exec(@Nonnull String host,
                           int port,
                           @Nonnull String username,
                           @Nonnull String password,
                           @Nonnull String cmd) {
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
        channel.connect();

        log.info("# successful connect to server [{}:{}]", host, port);

        log.info("# exec cmd [{}]", cmd);

        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int exitStatus;
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
        if (StringUtils.isNotEmpty(sb)) {
            log.info("# cmd-rs \n" + sb);
        }
        channel.disconnect();
        session.disconnect();

        log.info("# successful disconnect to server [{}:{}]", host, port);

        return exitStatus;
    }

    public static void main(String[] args) {

        final String[] HOSTS = {
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

        final String USERNAME = "****";

        final String PASSWORD = "****";

        Arrays.stream(HOSTS).forEach(host -> exec(host, 22, USERNAME, PASSWORD, "hostname"));
    }

}
