package com.smartiecards.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartiecards.R;

/**
 * Created by AnaadIT on 1/25/2018.
 */

public class OtherProductsFragment extends Fragment {

    Button buttonLogin, buttonSignUp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg);

//        buttonLogin = (Button) view.findViewById(R.id.button6546);
//        buttonSignUp = (Button) view.findViewById(R.id.button65462345345);
//
//
//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity() , LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        buttonSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity() , RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//

        return view;
    }
}
