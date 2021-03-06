package bwie.com.cuidongtao20180108.model;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import bwie.com.cuidongtao20180108.API.ApiService;
import bwie.com.cuidongtao20180108.Util.HttpUtils;
import bwie.com.cuidongtao20180108.Util.OnNetLisenter;
import bwie.com.cuidongtao20180108.bean.DetailBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 作者： 崔冬涛
 * 时间： 2018/1/8.
 */

public class SecondModel implements ISecondModel  {
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void getDetail(final OnNetLisenter<DetailBean> onNetLisenter) {
        HttpUtils.getHttpUtils().doGet(ApiService.ONE, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetLisenter.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DetailBean secondBean = new Gson().fromJson(string, DetailBean.class);
                        onNetLisenter.onSuccess(secondBean);
                    }
                });
            }
        });
    }
}
