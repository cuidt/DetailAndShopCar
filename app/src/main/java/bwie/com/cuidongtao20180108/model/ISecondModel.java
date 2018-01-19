package bwie.com.cuidongtao20180108.model;

import bwie.com.cuidongtao20180108.Util.OnNetLisenter;
import bwie.com.cuidongtao20180108.bean.DetailBean;

/**
 * 作者： 崔冬涛
 * 时间： 2018/1/8.
 */

public interface ISecondModel {
    public void getDetail(final OnNetLisenter<DetailBean> onNetLisenter);
}
