package com.robot.gs.ticket.utils;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.net.ssl.*;

@Slf4j
public final class HttpUtil {
  public static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");
  private static final String EMPTY = "";

  private HttpUtil() {

  }

  private static final OkHttpClient client = new OkHttpClient.Builder()
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(180, TimeUnit.SECONDS)
      .writeTimeout(180, TimeUnit.SECONDS)
      .sslSocketFactory(SSLUtils.getSSLSocketFactory(), SSLUtils.getX509TrustManager())
      .hostnameVerifier(SSLUtils.getHostnameVerifier())
      .build();

  /**
   * get 请求方法.
   *
   * @param request 请求对象
   * @return
   */
  public static String get(final Request request) {
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      log.info("http response code is {},body is {}", response.code(), resp);
      return resp;
    } catch (IOException ioe) {
      log.error("request http resource is error", ioe);
    }
    return EMPTY;
  }

  /**
   * post请求方法.
   *
   * @param json 发送post 请求jsonBody
   * @param url  请求url
   * @return
   */
  public static String post(final String json, final String url) {
    log.info("http request param is {} ,url {}", json, url);
    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      //log.info("http response code is {},body is {}", response.code(), resp);
      return resp;
    } catch (IOException ioe) {
      log.error("request http resource is error", ioe);
    }
    return EMPTY;
  }

  public static String post(File file, final String url) {
    MultipartBody.Builder builder =
        new MultipartBody.Builder().setType(MediaType.parse("multipart/form-data"));
    builder.addFormDataPart("file", file.getName(),
        RequestBody.create(MediaType.parse("application/octet-stream"), file));
    final Request request = new Request.Builder()
        .url(url)
        .post(builder.build())
        .build();
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      log.info("http response code is {},body is {}", response.code(), resp);
      return resp;
    } catch (IOException ioe) {
      log.error("request http resource is error", ioe);
    } finally {
      file.delete();
    }
    return EMPTY;
  }

  public static String post(final String json, final String url, final HashMap <String,String> headers) {
    log.info("http request param is {} ,url {}", json, url);
    RequestBody body = RequestBody.create(JSON, json);
    Iterator<String> iterator = headers.keySet().iterator();
    Request.Builder builder = new Request.Builder();
    while (iterator.hasNext()){
      String key = iterator.next();
      String value = headers.get(key);
      builder.addHeader(key,value);
    }
    Request request = builder.url(url)
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      //log.info("http response code is {},body is {}", response.code(), resp);
      return resp;
    } catch (IOException ioe) {
      log.error("request http resource is error", ioe);
    }
    return EMPTY;
  }

  public static String put(final String json, final String url) {
    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .put(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      log.info("http response code is {},body is {}", response.code(), resp);
      return resp;
    } catch (IOException ioe) {
      log.error("request http resource is error", ioe);
    }
    return EMPTY;
  }

  public static String delete(final Request request) {
    try (Response response = client.newCall(request).execute()) {
      String resp = response.body().string();
      log.info("http response code is {},body is {}", response.code(), resp);
      return resp;
    } catch (IOException ioe) {
      log.error("request http resource is error", ioe);
    }
    return EMPTY;
  }

}
