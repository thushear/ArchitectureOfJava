package com.github.thushear.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by kongming on 2016/9/29.
 */
public class HttpClientLearn {


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
        // Increase max connections for localhost:80 to 50
        HttpHost localhost = new HttpHost("localhost",80);
        cm.setMaxPerRoute(new HttpRoute(localhost),50);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm).build();


        String url = "https://kmg343.gitbooks.io/httpcl-ient4-4-no2/content/222_an_quan_http_lian_jie.html";
        String[] uri = {url,url,url,url,url,url,url,url,url,url,url,url,url,url,url};

        GetThread[] threads = new GetThread[uri.length];
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpGet = new HttpGet(uri[i]);
            threads[i] = new GetThread(httpClient,httpGet);
        }

        for (GetThread thread : threads) {
            thread.start();
        }

        for (GetThread thread : threads) {
            thread.join();
        }

        long  end = System.currentTimeMillis();
        System.out.println("cost time:" + (end - start) + " ms");

        Thread.currentThread().join();
    }
}
class GetThread extends Thread{

    private final CloseableHttpClient httpClient;

    private final HttpContext context;

    private final HttpGet httpGet;

    public GetThread(CloseableHttpClient httpClient, HttpGet httpGet) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpGet = httpGet;
    }

    @Override
    public void run() {

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet,context);

            try {
                HttpEntity entity = response.getEntity();
                System.out.println("entity = " + entity);

            } finally {
                response.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
}