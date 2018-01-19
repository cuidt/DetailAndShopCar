package bwie.com.cuidongtao20180108.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import bwie.com.cuidongtao20180108.R;
import bwie.com.cuidongtao20180108.adapter.ElvAdapter;
import bwie.com.cuidongtao20180108.bean.ThirdBean;
import bwie.com.cuidongtao20180108.present.ThirdPresenter;
import bwie.com.cuidongtao20180108.view.IThirdActivity;

public class ThirdActivity extends AppCompatActivity  implements IThirdActivity {

    private ExpandableListView mElv;
    /**
     * 全选
     */
    private CheckBox mCb;
    /**
     * 合计:
     */
    private TextView mTvTotal;
    /**
     * 去结算(0)
     */
    private TextView mTvCount;
    private ElvAdapter adapter;
    private ThirdPresenter thirdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_third);
        initView();
        thirdPresenter = new ThirdPresenter(this);
        thirdPresenter.getBean(getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", "71"));


        //给全选设置点击事件
        mCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.setAllChecked(mCb.isChecked());
                }
            }
        });
    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mElv.setGroupIndicator(null);
        mCb = (CheckBox) findViewById(R.id.cb);
        mTvTotal = (TextView) findViewById(R.id.tvTotal);
        mTvCount = (TextView) findViewById(R.id.tvCount);
    }

    @Override
    public void show(List<ThirdBean.DataBean> group, List<List<ThirdBean.DataBean.ListBean>> child) {
        //设置适配器
        adapter = new ElvAdapter(this, group, child);
        mElv.setAdapter(adapter);

        for (int i = 0; i < group.size(); i++) {
            mElv.expandGroup(i);
        }
    }
    public void setMoney(double price) {
        mTvTotal.setText("合计：" + price);
    }

    public void setCount(int count) {
        mTvCount.setText("去结算(" + count + ")");
    }

    public void setAllSelect(boolean bool) {
        mCb.setChecked(bool);
    }


}
