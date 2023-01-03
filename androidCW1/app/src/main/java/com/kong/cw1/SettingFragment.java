package com.kong.cw1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kong.cw1.databinding.FragmentSettingBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    Button backBtn;
    Button logoutBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        backBtn = binding.buttonBack;
        logoutBtn = binding.buttonLogout;

        /*try {
            nameTxt.setText(Setting.userJasonObject.get("name").toString());
            eidTxt.setText(Setting.userJasonObject.get("eid").toString());
            Boolean isAdmin = Setting.userJasonObject.get("isAdmin").toString().equals("1");
            if(isAdmin){
                adminTypeTxt.setText(Setting.userJasonObject.get("adminType").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        logoutBtn.setVisibility((Setting.userJasonObject.length() == 0)?View.INVISIBLE:View.VISIBLE);

        backBtn.setOnClickListener(backBtnClick);
        logoutBtn.setOnClickListener(logoutBtnClick);

        return binding.getRoot();

    }

    private View.OnClickListener backBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(Setting.userJasonObject.length() == 0){
                NavHostFragment.findNavController(SettingFragment.this).navigate(R.id.action_settingFragment_to_FirstFragment);
            }else{
                NavHostFragment.findNavController(SettingFragment.this).navigate(R.id.action_settingFragment_to_SecondFragment);
            }

        }
    };

    private View.OnClickListener logoutBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Setting.userJasonObject = new JSONObject();
            NavHostFragment.findNavController(SettingFragment.this).navigate(R.id.action_settingFragment_to_FirstFragment);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}