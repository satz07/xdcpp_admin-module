package com.adminremit.disbursement.api;

import java.io.IOException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@Slf4j
public class FYServiceCallClient {
	
	/*
	 * @EventListener(ApplicationReadyEvent.class) public static void
	 * trustSelfSignedSSL() { log.info("Self signed was called"); try { SSLContext
	 * ctx = SSLContext.getInstance("TLS"); X509TrustManager tm = new
	 * X509TrustManager() {
	 * 
	 * public void checkClientTrusted(X509Certificate[] xcs, String string) { }
	 * 
	 * public void checkServerTrusted(X509Certificate[] xcs, String string) { }
	 * 
	 * public X509Certificate[] getAcceptedIssuers() { return null; } };
	 * ctx.init(null, new TrustManager[]{tm}, null); SSLContext.setDefault(ctx); }
	 * catch (Exception ex) { ex.printStackTrace(); } }
	 */
	private static OkHttpClient getUnsafeOkHttpClient() {
		  try {
		    // Create a trust manager that does not validate certificate chains
		    final TrustManager[] trustAllCerts = new TrustManager[] {
		        new X509TrustManager() {
		          @Override
		          public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
		          }

		          @Override
		          public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
		          }

		          @Override
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return new java.security.cert.X509Certificate[]{};
		          }
		        }
		    };

		    // Install the all-trusting trust manager
		    final SSLContext sslContext = SSLContext.getInstance("SSL");
		    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		    // Create an ssl socket factory with our all-trusting manager
		    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		    OkHttpClient.Builder builder = new OkHttpClient.Builder();
		    builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
		    builder.hostnameVerifier(new HostnameVerifier() {
		      @Override
		      public boolean verify(String hostname, SSLSession session) {
		        return true;
		      }
		    });

		    OkHttpClient okHttpClient = builder.build();
		    return okHttpClient;
		  } catch (Exception e) {
		    throw new RuntimeException(e);
		  }
		}
		

	public Map<String,Object> connectOk(String _endPoint, Object _payload, MultiValueMap<String, String> _bodyValues, Map<String, String> header, String requestMethod) throws IOException {
	{
		Map<String,Object> result = new HashedMap<String,Object>();
		OkHttpClient client = getUnsafeOkHttpClient();
				Headers headerbuild = Headers.of(header);				
				okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
				RequestBody body = null;
				if(_payload !=null)
					body = RequestBody.create(_payload.toString(),mediaType);
				Request request = new Request.Builder()
				  .url(_endPoint)				  
				  .method(requestMethod, body)
				  .headers(headerbuild)
				  .build();				
				Response response = client.newCall(request).execute();
				result.put("status", response.code());
				result.put("successful", response.isSuccessful());
				result.put("rbody", response.body().string());				
				return result;	
	}	
	}
}
