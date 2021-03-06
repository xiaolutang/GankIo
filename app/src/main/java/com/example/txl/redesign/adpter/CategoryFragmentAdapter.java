package com.example.txl.redesign.adpter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.fragment.secondfloor.SecondFloorNewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TXL
 * description :
 */
public class CategoryFragmentAdapter extends FragmentStatePagerAdapter {
    List<BaseFragment> fragmentList;

    public CategoryFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public BaseFragment getItem(int position) {
        if(position >= fragmentList.size()){
            return null;
        }
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if(fragmentList == null){
            return 0;
        }
        return fragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //不销毁二楼fragment
        if(getItem(position) instanceof SecondFloorNewsFragment){
            return;
        }
        super.destroyItem(container, position, object);
    }

    public void setFragmentList(List<BaseFragment> fragmentList){
        if(this.fragmentList != null){
            this.fragmentList.clear();
        }
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void addFragment(BaseFragment fragment){
        if(fragmentList == null){
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragment(int position, BaseFragment fragment){
        if(fragmentList == null){
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(position,fragment);
        notifyDataSetChanged();
    }
}
