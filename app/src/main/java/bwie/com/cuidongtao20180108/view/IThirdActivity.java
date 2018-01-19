package bwie.com.cuidongtao20180108.view;

import java.util.List;

import bwie.com.cuidongtao20180108.bean.ThirdBean;

public interface IThirdActivity {
    public void show(List<ThirdBean.DataBean> group, List<List<ThirdBean.DataBean.ListBean>> child);
}