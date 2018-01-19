package bwie.com.cuidongtao20180108.present;

import bwie.com.cuidongtao20180108.Util.OnNetLisenter;
import bwie.com.cuidongtao20180108.bean.DetailBean;
import bwie.com.cuidongtao20180108.model.ISecondModel;
import bwie.com.cuidongtao20180108.model.SecondModel;
import bwie.com.cuidongtao20180108.view.IScondActivity;

/**
 * 作者： 崔冬涛
 * 时间： 2018/1/8.
 */

public class SecondPresenter {
    private ISecondModel iSecondModel;
    private IScondActivity iScondActivity;

    public SecondPresenter(IScondActivity iScondActivity) {
        this.iScondActivity = iScondActivity;
        this.iSecondModel = new SecondModel();
    }



    public void getDetail(){
        iSecondModel.getDetail(new OnNetLisenter<DetailBean>() {
            @Override
            public void onSuccess(DetailBean secondBean) {
                if (iScondActivity != null){
                    iScondActivity.show(secondBean.getData());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
