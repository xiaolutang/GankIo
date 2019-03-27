package com.example.txl.redesign.api;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ximalaya.ting.android.opensdk.constants.ConstantsOpenSdk;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.Map;

/**
 * @author TXL
 * description :
 */
public class XmlyApi {
    private static final String TAG = "XmlyApi";
    public static void initXMFM(Context context){
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        //考虑搞一个自己的小服务器来存放
        mXimalaya.setAppkey("cac099ed9bf7d296136255aaa3861fa0");
        mXimalaya.setPackid(context.getPackageName());
        mXimalaya.init(context ,"956c9515d1384e084bcc71cc11c5541e");
        ConstantsOpenSdk.isDebug = true;//开启debug模式
        //设置使用https
        CommonRequest.getInstanse().setUseHttps(true);
//        设置不使用https
//        CommonRequest.getInstanse().mNoSupportHttps.add("http://www.baidu.com/request");
    }

    public static void getXmlyCategorys(Map<String, String> specificParams, IDataCallBack<CategoryList> callback){
        CommonRequest.getCategories(specificParams, callback);
    }
}
