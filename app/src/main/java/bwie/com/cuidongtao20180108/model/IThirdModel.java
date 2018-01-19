package bwie.com.cuidongtao20180108.model;

import java.util.Map;

import bwie.com.cuidongtao20180108.Util.OnNetLisenter;
import bwie.com.cuidongtao20180108.bean.ThirdBean;

public interface IThirdModel {
    public void getBean(Map<String,String> params, OnNetLisenter<ThirdBean> onNetLisenter);
}