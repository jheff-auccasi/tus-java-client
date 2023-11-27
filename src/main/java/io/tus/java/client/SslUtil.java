package io.tus.java.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SslUtil {

  public static SSLContext getSSLContext(String keystorePath, String keystorePassword, String alias, String keyPassword) {
    try {
      KeyStore keyStore = KeyStore.getInstance("JKS");
      FileInputStream fis = new FileInputStream(keystorePath);
      keyStore.load(fis, keystorePassword.toCharArray());
      KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      try {
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());
      } catch (UnrecoverableKeyException e) {
        throw new RuntimeException(e);
      }
      KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
      TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      trustManagerFactory.init(keyStore);
      TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(keyManagers, trustManagers, null);
      return sslContext;
    } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException |
             IOException | KeyManagementException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void setSSLContext(){
    String keystorePath = "D:\\sunat_certificado.jks";
    String keystorePassword = "weblogic";
    String alias = "weblogic";
    String keyPassword = "weblogic";
    SSLContext sslContext = getSSLContext(keystorePath, keystorePassword, alias, keyPassword);
    if (sslContext != null) {
      SSLContext.setDefault(sslContext);
    } else {
      System.out.println("Error al configurar SSLContext.");
    }
  }

}
