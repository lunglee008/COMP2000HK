package com.kong.cw1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kong.cw1.databinding.FragmentSecondBinding;

import org.json.JSONArray;
import org.json.JSONException;

public class SecondFragment extends Fragment {

private FragmentSecondBinding binding;

    TextView adminTypeTxt;
    TextView eidTxt;
    TextView nameTxt;
    FloatingActionButton settingBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        adminTypeTxt = binding.adminType;
        eidTxt = binding.eid;
        nameTxt = binding.name;
        //nameTxt.setText();
        adminTypeTxt.setText("");
        eidTxt.setText("");
        nameTxt.setText("");

        try {
            nameTxt.setText(Setting.userJasonObject.get("name").toString());
            eidTxt.setText(Setting.userJasonObject.get("eid").toString());
            Boolean isAdmin = Setting.userJasonObject.get("isAdmin").toString().equals("1");
            if(isAdmin){
                adminTypeTxt.setText(Setting.userJasonObject.get("adminType").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        settingBtn = binding.settingBtn;
        settingBtn.setOnClickListener(settingBtnClick);
        settingBtn.setEnabled(true);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }

    private View.OnClickListener settingBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_settingFragment);
        }
    };

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}