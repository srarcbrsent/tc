package com.ysu.zyw.tc.components.mq.amq;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.NetworkInterface;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcAmqClientIdHolder {

    @Getter
    @Setter
    private String application;

    @Getter
    @Setter
    private String number;

    public String uniqueClientId() {
        checkNotNull(application);
        checkNotNull(number);
        String mac = findMacAddress();
        return "_tc_" + mac + "_" + application + "_" + number;
    }

    @SneakyThrows
    private String findMacAddress() {
        byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0").append(str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

}
