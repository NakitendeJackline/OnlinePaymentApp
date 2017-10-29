package com.pegasus.pegpay;

/**
 * Created by Jackie on 18-Oct-17.
 */
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class DupedCertificate implements X509TrustManager {

    private static TrustManager[] trustManagers;

    private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        // TODO Auto-generated method stub

    }

    public boolean isClientTrusted(X509Certificate[] chain) {
        return (true);
    }

    public boolean isServerTrusted(X509Certificate[] chain) {
        return (true);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return (_AcceptedIssuers);
    }

}
