package com.tibco.xpd.wsdl;

import java.security.cert.X509Certificate;

/**
 * Class to track the acceptance of an ssl certificate via callback.
 * 
 * @author pwells
 */
public abstract class CertificateAcceptanceTracker {
    private boolean trusted = false;

    /**
     * Use this object to make requests to a server known to be http
     */
    public static CertificateAcceptanceTracker NULL = new CertificateAcceptanceTracker() {
        @Override
        public String getAlias() {
            throw new UnsupportedOperationException();
        }

        @Override
        public X509Certificate getTrustedCertificate() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean shouldAccept(X509Certificate[] chain, String urlString,
                String[] existingAliases) {
            return false;
        }
    };

    /**
     * Request to ask the user whether to trust or reject a certificate chain
     * presented by an untrusted server.
     * 
     * @param chain
     *            The chain of certificates supplied by the server
     * @param urlString
     *            The url to the server
     * @param existingAliases
     *            A list of existing aliases - if a certificate is accepted it
     *            must be given a unique alias.
     * @return true to accept, false to reject
     */
    public abstract boolean shouldAccept(X509Certificate[] chain,
            String urlString, String[] existingAliases);

    /**
     * If a certificate is accepted then the framework will call this method
     * 
     * @return The alias under which the new certificate should be added to the
     *         keystore
     */
    public abstract String getAlias();

    /**
     * @return The certificate to add to the trust list
     */
    public abstract X509Certificate getTrustedCertificate();

    /**
     * Only the certificate manager should call this method
     */
    final void markTrusted() {
        trusted = true;
    }

    /**
     * To obtain the final outcome after an encounter with an untrusted server.
     * The import should be aborted if the server is not trusted.
     * 
     * @return true if the server is trusted (irrespective of whether there was
     *         any user interaction or not).
     */
    public final boolean isTrusted() {
        return trusted;
    }
}
