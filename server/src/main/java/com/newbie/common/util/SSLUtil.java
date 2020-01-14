package com.newbie.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * SSL连接工具类
 * @date 2012-3-14下午03:53:08
 */
@Slf4j
public final class SSLUtil {

    /**
     * Hostname verifier.
     */
    private static HostnameVerifier hostnameVerifier;

    /**
     * Thrust managers.
     */
    private static TrustManager[] trustManagers;
    
    public static void trustAll(){
        trustAllHttpsCertificates();
        trustAllHostnames();
    }

    /**
     * Set the default Hostname Verifier to an instance of a fake class that
     * trust all hostnames.
     */
    public static void trustAllHostnames() {
        // Create a trust manager that does not validate certificate chains
        if(hostnameVerifier == null) {
            hostnameVerifier = new FakeHostnameVerifier();
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }

    /**
     * Set the default X509 Trust Manager to an instance of a fake class that
     * trust all certificates, even the self-signed ones.
     */
    public static void trustAllHttpsCertificates() {
        SSLContext context;
        if(trustManagers == null) {
            trustManagers = new TrustManager[]{new FakeX509TrustManager()};
        }
        try {

            context = SSLContext.getInstance("TLSv1.2"); // 之前的写法为 "SSL"
            context.init(null, trustManagers, new SecureRandom());
        } catch(GeneralSecurityException gse) {
            throw new IllegalStateException(gse.getMessage());
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }

    /**
     * This class implements a fake hostname verificator, trusting any host
     * name.
     */
    public static class FakeHostnameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
           return false;
        }
    }

    /**
     * This class allow any X509 certificates to be used to authenticate the
     * remote side of a secure socket, including self-signed certificates.
     */
    public static class FakeX509TrustManager implements X509TrustManager {

        /**
         * Empty array of certificate authority certificates.
         */
        private static final X509Certificate[] acceptedIssuers = new X509Certificate[]{};


        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            // do somthing checkClientTrusted
            throw new CertificateException("checkClientTrusted");
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            //do something checkServerTrusted
            throw new CertificateException("checkServerTrusted");
        }

        public X509Certificate[] getAcceptedIssuers() {
            return acceptedIssuers;
        }
    }

    public static void main(String[] args) throws Exception {
        SSLUtil.trustAll();
        URL url = new URL("https://127.0.0.1:8443/ProductSystem/index.do");
        try(InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            String msg;
            StringBuilder sb = new StringBuilder("");
            while((msg = reader.readLine()) != null) {
                sb.append(msg);
            }
            msg = sb.toString();
            msg = msg.trim();
            log.info(msg);
            
        }
    }
}

