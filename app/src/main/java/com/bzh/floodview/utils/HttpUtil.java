package com.bzh.floodview.utils;

import com.bzh.floodview.file.property;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    private static OkHttpClient client = new OkHttpClient()
            .newBuilder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(30, TimeUnit.SECONDS)//设置写入超时时间
            .cookieJar(new CookieJar() {
                private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(HttpUrl.parse(property.Route + "user/userLog"), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = null;
                    if(url.toString().equals(property.Route + "user/userLog")){
                        cookieStore.clear();
                        System.out.println("清除");
                    }else {
                        cookies = cookieStore.get(HttpUrl.parse(property.Route + "user/userLog"));
                        System.out.println("保留");
                    }
                    //List<Cookie> cookies = cookieStore.get(url);
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();//OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理}  8441AE51B02F2EF565448A5A28883FB9

    /**
     * 网络操作部分  get请求  异步
     *
     * @param address
     * @param callback
     */
    public static void sendGetOKHttpRequest(String address, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(address)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 网络操作部分  post请求  异步
     *
     * @param address
     * @param callback
     */
    public static void sendPostOKHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 载入证书
     */
    public static SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        try {
            //用我们的证书创建一个keystore
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = "server" + Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //创建一个trustmanager，只信任我们创建的keystore
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 网络操作部分  get请求  同步
     *
     * @param address
     */
    public static Response sendGetOKHttpRequestBySync(String address) throws IOException {
        Request request = new Request.Builder()
                .url(address)
                .get()
                .build();
        return client.newCall(request).execute();
    }

    /**
     * 网络操作部分  post请求  同步
     *
     * @param address
     */
    public static Response sendPostOKHttpRequestBySync(String address, RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return client.newCall(request).execute();
    }

}
