package bwie.com.cuidongtao20180108;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import bwie.com.cuidongtao20180108.activity.ThirdActivity;
import bwie.com.cuidongtao20180108.bean.DetailBean;
import bwie.com.cuidongtao20180108.present.SecondPresenter;
import bwie.com.cuidongtao20180108.view.IScondActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IScondActivity {
    private ImageView mBackImage;
    private RelativeLayout mRelative01;
    private View mView;
    private ImageView mProductImage;
    private RelativeLayout mRelative;
    private View mView01;
    private TextView mTitle;
    private TextView mYuanJia;
    private TextView mYouHui;
    private LinearLayout mLine1;
    /**
     * 购物车
     */
    private Button mGoToCart;
    /**
     * 加入购物车
     */
    private Button mAddCart;
    private LinearLayout mLine2;
    private SecondPresenter secondPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        secondPresenter = new SecondPresenter(this);
        secondPresenter.getDetail();

    }
    private void initView() {
        mRelative01 = (RelativeLayout) findViewById(R.id.relative01);
        mView = (View) findViewById(R.id.view);
        mProductImage = (ImageView) findViewById(R.id.ProductImage);
        mRelative = (RelativeLayout) findViewById(R.id.relative);
        mView01 = (View) findViewById(R.id.view01);
        mTitle = (TextView) findViewById(R.id.title);
        mYuanJia = (TextView) findViewById(R.id.yuanJia);
        mYouHui = (TextView) findViewById(R.id.youHui);
        mLine1 = (LinearLayout) findViewById(R.id.line1);
        mGoToCart = (Button) findViewById(R.id.goToCart);
        mGoToCart.setOnClickListener(this);
        mAddCart = (Button) findViewById(R.id.addCart);
        mAddCart.setOnClickListener(this);
        mLine2 = (LinearLayout) findViewById(R.id.line2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.goToCart:
                Intent intent1 = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent1);
                break;
            case R.id.addCart:
                //路径
                String path = "https://www.zhaoapi.cn/product/addCart?uid=71&pid=1";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String body = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //吐司加入购物车成功
                                Toast.makeText(MainActivity.this, "购物车加入商品成功" + body, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
    }


    @Override
    public void show(DetailBean.DataBean secondBean) {
        String imgs = secondBean.getImages();
        String[] split = imgs.split("\\|");
        Glide.with(MainActivity.this).load(split[0]).into(mProductImage);
        //设置商品信息显示
        mTitle.setText(secondBean.getTitle());
        mYuanJia.setText("原价:￥" + secondBean.getPrice());
        //设置原价中间横线（删除线）
        mYuanJia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mYouHui.setText("优惠价:" + secondBean.getBargainPrice());
    }
}
