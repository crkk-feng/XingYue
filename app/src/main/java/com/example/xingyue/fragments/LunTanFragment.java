package com.example.xingyue.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.xingyue.R;
import com.example.xingyue.base.BaseFragment;

public class LunTanFragment extends Fragment {
    private View rootView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_luntan_view,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
