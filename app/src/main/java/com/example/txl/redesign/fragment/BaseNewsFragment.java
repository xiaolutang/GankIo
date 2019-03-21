package com.example.txl.redesign.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;

/**
 * @author TXL
 * description :
 */
public class BaseNewsFragment extends BaseFragment {

    @Override
    protected String getFragmentName() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_base_news,container,false);
        return rootView;
    }
}
