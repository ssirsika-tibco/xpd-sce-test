package com.tibco.xpd.deploy.webdav;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.webdav.http.client.ISocketFactory;

/**
 * SocketFactory which is aware of SSL protocol and create standard java SSL
 * socket implementation in case of 'https' protocol.
 * 
 * @author Jan Arciuchiewicz
 */
public final class SSLAwareSocketFactory implements ISocketFactory {

    private static final SSLAwareSocketFactory instance =
            new SSLAwareSocketFactory();

    /**
     * Use #getInstance() to get factory.
     */
    private SSLAwareSocketFactory() {
    }

    /**
     * 
     * @return the instance of ISocketFactory.
     */
    public static ISocketFactory getInstance() {
        return instance;
    }

    @Override
    public Socket createSocket(String protocol, String host, int port)
            throws IOException {
        if ("https".equals(protocol)) { //$NON-NLS-1$
            Socket sslSocket =
                    SSLSocketFactory.getDefault().createSocket(host, port);
            return sslSocket;
        }
        return new Socket(host, port);
    }
}