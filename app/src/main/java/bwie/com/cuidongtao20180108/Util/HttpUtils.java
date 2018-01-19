package bwie.com.cuidongtao20180108.Util;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 作者： 崔冬涛
 * 时间： 2018/1/8.
 */

public class HttpUtils {
    private static volatile HttpUtils httpUtils;
    private final OkHttpClient client;

    private HttpUtils() {
        client = new OkHttpClient.Builder().build();
    }

    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    /**
     * GET请求
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void doGet(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public void doPost(String url, Map<String, String> params, Callback callback) {
        //这里可以加网络判断

        /*//判断参数
        if (params == null || params.size() ==0){
            //运行时异常
            throw new RuntimeException("params is null！！！");
        }*/
        //创建Request
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
