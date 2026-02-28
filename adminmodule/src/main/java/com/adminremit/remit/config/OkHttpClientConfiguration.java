package com.adminremit.remit.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.adminremit.common.models.Constants;

import okhttp3.OkHttpClient;

@Configuration
public class OkHttpClientConfiguration {
	
	@Value("${server.ssl.trust-store}")
    private String sslTrustStorePath;
	
	@Value("${server.ssl.trust-store-password}")
    private String sslTrustStorePassword;
	
	@Value("${server.ssl.key-store}")
    private String sslKeyStorePath;
	
	@Value("${server.ssl.key-store-password}")
    private String sslKeyStorePassword;
	
	@Autowired
	private ResourceLoader resourceLoader;

	@Bean
	public OkHttpClient sslOkHttpClientFactory() throws KeyManagementException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyStoreException, FileNotFoundException, IOException, CertificateException {
		
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		
		loadTrustStore(trustStore);
		KeyStore keyStore = loadKeyStore();

		KeyManagerFactory kmf = initializeKeyManagerFactory(keyStore);		
		X509TrustManager trustManager = getTrustStoreManager(trustStore);

		SSLContext sslContext = initializeSSLContext(kmf);		
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();
	}

	private SSLContext initializeSSLContext(KeyManagerFactory kmf)
			throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance(Constants.SSL_PROTOCOL);
		sslContext.init(kmf.getKeyManagers(), null, null);
		return sslContext;
	}

	private X509TrustManager getTrustStoreManager(KeyStore trustStore)
			throws NoSuchAlgorithmException, KeyStoreException {
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(trustStore);
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
		return trustManager;
	}

	private KeyManagerFactory initializeKeyManagerFactory(KeyStore keyStore)
			throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(keyStore, sslKeyStorePassword.toCharArray());
		return kmf;
	}

	private KeyStore loadKeyStore() {
		KeyStore keyStore = null;
		
		try {
			keyStore = KeyStore.getInstance(Constants.KEYSTORE_TYPE);
			
			Resource resource = resourceLoader.getResource(sslKeyStorePath);
			InputStream inputStream = resource.getInputStream();
			
			keyStore.load(inputStream, sslKeyStorePassword.toCharArray());
		} catch (Exception exception) {
			System.out.println("Exception occured while creating rest template::" + exception);
			exception.printStackTrace();
		}
		return keyStore;
	}

	private void loadTrustStore(KeyStore trustStore)
			throws IOException, NoSuchAlgorithmException, CertificateException, FileNotFoundException {
		
		Resource resource = resourceLoader.getResource(sslTrustStorePath);
		
		try (InputStream instream = resource.getInputStream()) {
			trustStore.load(instream, sslTrustStorePassword.toCharArray());
		}
	}

}
