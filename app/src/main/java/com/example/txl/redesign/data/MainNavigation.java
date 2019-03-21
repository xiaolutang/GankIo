package com.example.txl.redesign.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.txl.redesign.utils.GlobalCacheUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author TXL
 * description :首页数据构建
 */
public class MainNavigation extends Navigation<String>  implements Serializable, Parcelable {

    public MainNavigation(){}

    public MainNavigation(Parcel in) {
        category = in.readString();
        childList = in.createStringArrayList();
    }

    public static final Creator<MainNavigation> CREATOR = new Creator<MainNavigation>() {
        @Override
        public MainNavigation createFromParcel(Parcel in) {
            return new MainNavigation(in);
        }

        @Override
        public MainNavigation[] newArray(int size) {
            return new MainNavigation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeStringList(childList);
    }

    public static MainNavigation buildMainNavigation(){
        MainNavigation mainNavigation = new MainNavigation();
        mainNavigation.category = CATEGORY_MAIN;
        mainNavigation.childList = new ArrayList<>();
        mainNavigation.childList.add(GlobalCacheUtils.KEY_TODAY);
        mainNavigation.childList.add(GlobalCacheUtils.KEY_FU_LI);
        mainNavigation.childList.add(GlobalCacheUtils.KEY_ANDROID);
        mainNavigation.childList.add(GlobalCacheUtils.KEY_IOS);
        mainNavigation.childList.add(GlobalCacheUtils.KEY_EXPANDING_RESUORCES);
        mainNavigation.childList.add(GlobalCacheUtils.KEY_FRONT);
        mainNavigation.childList.add(GlobalCacheUtils.KEY_ALL);
        return mainNavigation;
    }
}
