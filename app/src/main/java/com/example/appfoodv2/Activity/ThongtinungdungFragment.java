package com.example.appfoodv2.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appfoodv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongtinungdungFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongtinungdungFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ThongtinungdungFragment() {
    }

    public static ThongtinungdungFragment newInstance(String param1, String param2) {
        ThongtinungdungFragment fragment = new ThongtinungdungFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongtinungdung, container, false);
    }
}