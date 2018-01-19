package bwie.com.cuidongtao20180108.Util;

/**
 * 作者： 崔冬涛
 * 时间： 2018/1/8.
 */

public interface OnNetLisenter<T> {

    public void onSuccess(T t);

    public void onFailure(Exception e);
}
