package com.tibco.xpd.wsdl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.core.runtime.Assert;


/**
 * Stateless collection of methods for helping with operations involving
 * certificates
 * 
 * @author pwells
 * 
 */
public class CertificateManager {
    public static final String DEFAULT_PASSWORD = "changeit"; //$NON-NLS-1$
    private static final String HTTPS = "https"; //$NON-NLS-1$

    /**
     * Hidden constructor, all methods static
     */
    private CertificateManager() {
    }

    private static class WsdlTrustManager implements X509TrustManager {
        private KeyStore keyStore;
        private URI uri;

        WsdlTrustManager(KeyStore keyStore, URI uri) {
            this.keyStore = keyStore;
            this.uri = uri;
        }

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // this is the client
            throw new UnsupportedOperationException();
        }

        public void checkServerTrusted(X509Certificate[] candidates, String arg1)
                throws CertificateException {
            X509Certificate[] knownCertificates = getAcceptedIssuers();
            for (X509Certificate thisKnownCertificate : knownCertificates) {
                for (X509Certificate thisCandidateCertificate : candidates) {
                    if (areSameCertificate(thisKnownCertificate,
                            thisCandidateCertificate)) {
                        return;
                    }
                }
            }

            throw new CertificateException(uri
                    + " not listed as having a trusted certificate."); //$NON-NLS-1$
        }

        /**
         * @return true if the two certificates are identical
         */
        private boolean areSameCertificate(X509Certificate first,
                X509Certificate second) throws CertificateEncodingException {
            byte[] firstBytes = first.getTBSCertificate();
            byte[] secondBytes = second.getTBSCertificate();
            if (firstBytes.length != secondBytes.length) {
                return false;
            }
            for (int index = 0; index < firstBytes.length; index++) {
                if (firstBytes[index] != secondBytes[index])
                    return false;
            }
            return true;
        }

        public X509Certificate[] getAcceptedIssuers() {
            try {
                ArrayList<X509Certificate> holdingList = new ArrayList<X509Certificate>();
                Enumeration<?> aliases = keyStore.aliases();
                while (aliases.hasMoreElements()) {
                    holdingList.add((X509Certificate) keyStore
                            .getCertificate((String) aliases.nextElement()));
                }
                X509Certificate[] result = new X509Certificate[holdingList
                        .size()];
                holdingList.toArray(result);
                return result;
            } catch (Throwable exception) {
                return new X509Certificate[] {};
            }
        }
    };

    /**
     * Get the chain of certificates from a URI pointing to a service accessible
     * through https
     * 
     * @param uri
     *            The java.net.URI to the service whose certificate we want
     * @return The chain of certificates or null if the service has no
     *         certificate
     */
    public static X509Certificate[] getCertificateChain(final URI uri)
            throws Exception {
        /**
         * In order to discover the certificate chain of a server, one way is to
         * start a handshake with that server, then grab the certificate chain
         * which it offers in response. To do this we need our own trust
         * manager.
         */
        class CertificateCollectingTrustManager implements X509TrustManager {
            private X509Certificate[] certificateChain;

            public X509Certificate[] getAcceptedIssuers() {
                throw new UnsupportedOperationException();
            }

            public void checkClientTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
                throw new UnsupportedOperationException();
            }

            public void checkServerTrusted(X509Certificate[] chain,
                    String authType) throws CertificateException {
                this.certificateChain = chain;
            }
        }

        final String host = uri.getHost();
        final int port = uri.getPort();

        // any context will do
        final SSLContext sslContext = SSLContext.getInstance("TLS"); //$NON-NLS-1$

        // make a certificate collecting trust manager and bind it to the ssl
        // context
        final CertificateCollectingTrustManager certificateCollectingTrustManager = new CertificateCollectingTrustManager();
        sslContext.init(null,
                new TrustManager[] { certificateCollectingTrustManager }, null);

        // use the ssl context to make a socket factory and then a socket
        final SSLSocketFactory factory = sslContext.getSocketFactory();
        final SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
        socket.setSoTimeout(10000);

        // make the actual call
        try {
            socket.startHandshake();
        } catch (SSLException e) {
            // this is expected to happen
        }
        socket.close();
        return certificateCollectingTrustManager.certificateChain;
    }

    /**
     * Public for testing
     * 
     * @param uri
     *            the source of data to the stream
     * @param keyStoreFile
     *            a persisted keystore of trusted certificates
     * @return the open stream - it's up to the caller to shut it when finished
     *         with
     * @throws Exception
     *             for any one of a number of reasons, but the caller is in a
     *             better position to handle it gracefully
     */
    public static InputStream getSSLInputStream(final URI uri,
            final File keyStoreFile) throws Exception {
        URLConnection urlConnection = uri.toURL().openConnection();
        if (urlConnection instanceof HttpsURLConnection) // which it should
                                                            // be
        {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
            final KeyStore keyStore = loadKeyStore(keyStoreFile);

            final String keyManagerAlgorithm = KeyManagerFactory
                    .getDefaultAlgorithm();
            final KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance(keyManagerAlgorithm);
            keyManagerFactory.init(keyStore, DEFAULT_PASSWORD.toCharArray());
            final SSLContext sslContext = SSLContext.getInstance("TLS"); //$NON-NLS-1$
            final KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            sslContext.init(keyManagers,
                    new TrustManager[] { new WsdlTrustManager(keyStore, uri) },
                    null);
            final SSLSocketFactory factory = sslContext.getSocketFactory();
            httpsURLConnection.setSSLSocketFactory(factory);
            return httpsURLConnection.getInputStream();
        }
        Assert.isTrue(false,
                "Can only get an SSL input stream for an https url."); //$NON-NLS-1$
        return urlConnection.getInputStream();
    }

    /**
     * Make a keystore of the default type and populate with persisted
     * certificates
     * 
     * @param keyStoreFile
     *            the persisted contents of a keystore from a previous session
     * @return the loaded keystore
     */
    private static KeyStore loadKeyStore(File keyStoreFile) throws Exception {
        final KeyStore userKeyStore = KeyStore.getInstance(KeyStore
                .getDefaultType());
        if (keyStoreFile.exists()) {
            final InputStream inputStream = new FileInputStream(keyStoreFile);
            try {
                userKeyStore.load(inputStream, DEFAULT_PASSWORD.toCharArray());
            } finally {
                inputStream.close();
            }
        } else {
            // If you want an empty keystore you still have to initialize it by
            // telling it to load from null.
            userKeyStore.load(null, DEFAULT_PASSWORD.toCharArray());
        }
        return userKeyStore;
    }

    /**
     * see overload
     */
    public static boolean addToPersistedUserTrustedCertificateList(
            final String alias, final X509Certificate trustedCertificate) {
        final String userHomeDirectoryString = System.getProperty("user.home"); //$NON-NLS-1$
        final File keyStoreFileParent = new File(userHomeDirectoryString);
        return addToPersistedUserTrustedCertificateList(alias,
                trustedCertificate, keyStoreFileParent);
    }

    /**
     * To add a certificate to the user's list of trusted certificates which is
     * persisted in the user's home directory
     * 
     * @param alias
     *            an identifier for this additional certificate
     * @param certificate
     *            the certificate to add to the keystore - will replace an
     *            existing certificate if one already exists for the given alias
     * @param keyStoreFile
     *            the keystore file to be created/updated. This would normally
     *            be a file called 'bsKeystore' located in the user's home
     *            directory. This parameter is added for ease of testing.
     * 
     * @return true if the addition to the persisted keystore was successfull
     */
    public static boolean addToPersistedUserTrustedCertificateList(
            final String alias, final X509Certificate trustedCertificate,
            final File keyStoreFile) {
        try {
            final KeyStore userKeyStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());

            // load the key store
            if (keyStoreFile.exists()) {
                final InputStream inputStream = new FileInputStream(
                        keyStoreFile);
                userKeyStore.load(inputStream, DEFAULT_PASSWORD.toCharArray());
                inputStream.close();
            } else {
                userKeyStore.load(null, DEFAULT_PASSWORD.toCharArray());
            }
            userKeyStore.setCertificateEntry(alias, trustedCertificate);
            FileOutputStream outputStream = new FileOutputStream(keyStoreFile);
            userKeyStore.store(outputStream, DEFAULT_PASSWORD.toCharArray());
            outputStream.close();
            return true;
        } catch (Exception exception) {
            Activator
                    .getDefault()
                    .getLogger()
                    .error(
                            exception,
                            "There was a problem while attempting to add a certificate to the user's persisted list of trusted certificates"); //$NON-NLS-1$
            return false;
        }
    }

    /**
     * Call this method to determine whether to proceed with an import from a
     * secure server.
     * 
     * @param javaDotNetSourceURI
     *            the source of the file to import
     * @param keyStoreFile
     *            a file listing trusted certificates
     * @param certificateAcceptanceTracker
     *            UI to prompt for confirmation
     */
    public static void checkCertificate(URI javaDotNetSourceURI,
            final File keyStoreFile,
            CertificateAcceptanceTracker certificateAcceptanceTracker)
            throws Exception {
        if (!javaDotNetSourceURI.toURL().getProtocol().equals(HTTPS)) {
            // this import will fail because there was no confirmation
            return;
        }
        // just reload the keyStoreFile each time -  
        // we can cache the keystore if this becomes a bottleneck
        final KeyStore keyStore = loadKeyStore(keyStoreFile);
        final WsdlTrustManager trustManager = new WsdlTrustManager(keyStore,
                javaDotNetSourceURI);
        final X509Certificate[] certificateChain = getCertificateChain(javaDotNetSourceURI);
        try {
            trustManager.checkServerTrusted(certificateChain, null);
            certificateAcceptanceTracker.markTrusted();
        } catch (CertificateException exception) {
            if (certificateAcceptanceTracker.shouldAccept(certificateChain,
                    javaDotNetSourceURI.toString(), getAliasesOf(keyStore))) {
                if (!addToPersistedUserTrustedCertificateList(
                        certificateAcceptanceTracker.getAlias(),
                        certificateAcceptanceTracker.getTrustedCertificate(),
                        keyStoreFile)) {
                    throw new Exception(
                            "Problem while saving a keystore - see log");
                }
                certificateAcceptanceTracker.markTrusted();
            }
        }
    }

    /**
     * @param keyStore
     * @return an array of Strings listing the aliases found in the keystore
     */
    private static String[] getAliasesOf(KeyStore keyStore) {
        try {
            ArrayList<String> arrayList = new ArrayList<String>();
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                arrayList.add(enumeration.nextElement());
            }
            String[] result = new String[arrayList.size()];
            arrayList.toArray(result);
            return result;
        } catch (KeyStoreException exception) {
            Activator
                    .getDefault()
                    .getLogger()
                    .error(exception,
                            "Error while building a list of aliases found in a keystore");
            return new String[] {};
        }
    }
}
