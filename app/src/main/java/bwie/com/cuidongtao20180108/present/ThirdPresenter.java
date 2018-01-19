package bwie.com.cuidongtao20180108.present;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.cuidongtao20180108.Util.OnNetLisenter;
import bwie.com.cuidongtao20180108.bean.ThirdBean;
import bwie.com.cuidongtao20180108.model.IThirdModel;
import bwie.com.cuidongtao20180108.model.ThirdModel;
import bwie.com.cuidongtao20180108.view.IThirdActivity;

public class ThirdPresenter {
    private IThirdModel iThirdModel;
    private IThirdActivity iThirdActivity;

    public ThirdPresenter(IThirdActivity iThirdActivity) {
        this.iThirdActivity = iThirdActivity;
        iThirdModel = new ThirdModel();
    }

    public void getBean(String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "71");

        params.put("source", "android");
        iThirdModel.getBean(params, new OnNetLisenter<ThirdBean>() {
            @Override
            public void onSuccess(ThirdBean thirdBean) {
                List<List<ThirdBean.DataBean.ListBean>> child = new ArrayList<>();
                for (int i = 0; i < thirdBean.getData().size(); i++) {
                    child.add(thirdBean.getData().get(i).getList());
                }
                if (iThirdActivity != null) {
                    iThirdActivity.show(thirdBean.getData(), child);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }

        });

    }
}