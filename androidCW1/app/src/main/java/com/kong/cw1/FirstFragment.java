package com.kong.cw1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kong.cw1.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FirstFragment extends Fragment {

private FragmentFirstBinding binding;

    Button submitBtn;
    EditText eidTxt;
    EditText epasswordTxt;
    View view;
    JSONArray jsonData = new JSONArray();
    FloatingActionButton settingBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        eidTxt = binding.eidTxt;
        epasswordTxt = binding.epasswordTxt;
        submitBtn = binding.buttonFirst;
        submitBtn.setOnClickListener(loginClick);
        submitBtn.setEnabled(true);
        settingBtn = binding.settingBtn;
        settingBtn.setOnClickListener(settingBtnClick);
        settingBtn.setEnabled(true);

        try {
            jsonData = new JSONArray("[{\"eid\": \"000123456\",\"epassword\": \"cd8f90392140dc4473d23645c838e243632a921b31fb399981b13654a526f98b\",\"name\": \"Jack Chan\";\"isActive\": 1,\"isAdmin\": 0,\"adminType\":\"\",\"picture\": \"http://placehold.it/32x32\",\"age\": 27,\"gender\": \"male\",\"email\": \"jack.chan@abccompany.com\"},{\"eid\": \"000987654\",\"epassword\": \"c088ffde0deee028467e010eb04a8d1f0e899c8a5b6cbdd4c87b517563313c3b\",\"name\": \"Annie Ko\";\"isActive\": 1,\"isAdmin\": 1,\"adminType\":\"HR administrator\",\"picture\": \"http://placehold.it/32x32\",\"age\": 36,\"gender\": \"female\",\"email\": \"annie.ko@abccompany.com\"},{\"eid\": \"000123123\",\"epassword\": \"7f5d4a195b4adfcaad6acc9ac70bb74d70ea6f861bf217c7b922254bdb3d1a4f\",\"name\": \"Steven Co\";\"isActive\": 0,\"isAdmin\": 0,\"adminType\":\"\",\"picture\": \"http://placehold.it/32x32\",\"age\": 22,\"gender\": \"male\",\"email\": \"steven.co@abccompany.com\"}]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*try {
            Log.d("test", jsonData.getJSONObject(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        /*try {
            Log.d("test", toHexString(getSHA("5Q6JFkWB5k")));
            //Log.d("test", toHexString(getSHA("rDhL1aaAmB")));
            //Log.d("test", toHexString(getSHA("kmPy8OqAwt")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/

        return binding.getRoot();
    }

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.d("test", eidTxt.getText().toString());
            //Log.d("test", epasswordTxt.getText().toString());
            String eid_string = eidTxt.getText().toString();
            String epassword_String = epasswordTxt.getText().toString();
            Boolean loginSuccess = true;
            if(eid_string.length() == 0) {
                loginSuccess = false;
                callAlertDialog("Please input your Employee ID!");
            }else if(epassword_String.length() == 0) {
                loginSuccess = false;
                callAlertDialog("Please input your Password!");
            }

            if(loginSuccess){
                int loginStatus = checkUser(eid_string,epassword_String);
                switch(loginStatus){
                    case 1:
                        loginSuccess = false;
                        callAlertDialog("Employee ID or Password not correct!");
                        break;
                    case 2:
                        loginSuccess = false;
                        callAlertDialog("Employee account is locked!");
                        break;
                }
            }

            if(loginSuccess) {

                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

                /*try {
                    Log.d("test1", Setting.userJasonObject.get("name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                //Setting.userJasonObject
            }
        }
    };

    private View.OnClickListener settingBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_settingFragment);
        }
    };

    public int checkUser(String __eid, String __epassword){
        String _epassword_SHA = "";
        try {
            _epassword_SHA = toHexString(getSHA(__epassword));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for (int i=0;i<jsonData.length();i++){
            try {
                String _tempEid = jsonData.getJSONObject(i).get("eid").toString();
                String _temppw = jsonData.getJSONObject(i).get("epassword").toString();
                /*Log.d("test1", _tempEid);
                Log.d("test2", __eid);
                Log.d("test3", _temppw);
                Log.d("test4", _epassword_SHA);*/
                if(_tempEid.equals(__eid) && _temppw.equals(_epassword_SHA)){
                    String _tempIsActive = jsonData.getJSONObject(i).get("isActive").toString();
                    if(_tempIsActive.equals("0")){
                        return 2;
                    }else{
                        Setting.userJasonObject = jsonData.getJSONObject(i);
                        submitBtn.setEnabled(false);
                    }
                    return 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    public void callAlertDialog(String __msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getParentFragment().getActivity());
        alertDialog.setTitle("Login fail!");
        alertDialog.setMessage(__msg);
        alertDialog.setNeutralButton("Close",((dialog, which) -> {}));
        AlertDialog dialog = alertDialog.create();
        dialog.show();

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NavHostFragment.findNavController(FirstFragment.this)
                //        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/

    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}