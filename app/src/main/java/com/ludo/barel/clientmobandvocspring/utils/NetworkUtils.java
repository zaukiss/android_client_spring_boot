package com.ludo.barel.clientmobandvocspring.utils;

/**
 * Created by iosdev on 06/03/2018.
 */

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkUtils {

    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {

            InetAddress candidateAddress = null;
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();

                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {

                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {

                        if (inetAddr.isSiteLocalAddress()) {

                            return inetAddr;
                        }
                        else if (candidateAddress == null) {

                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {

                return candidateAddress;
            }

            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {

            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;

        }
    }

}
