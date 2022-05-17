package com.example.secretfriend01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.secretfriend01.databinding.LoadFrameBinding;

public class LoadFrame extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private LoadFrameBinding binging1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binging1 = LoadFrameBinding.inflate(inflater,container,false);
        return binging1.getRoot();
    }
}
