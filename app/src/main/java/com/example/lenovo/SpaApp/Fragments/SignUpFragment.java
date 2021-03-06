package com.example.lenovo.SpaApp.Fragments;

/**
 * Created by Mangal on 2/5/2016.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.android.volley.Request;
import com.example.lenovo.SpaApp.Interfaces.UpdateViewPagerPosition;
import com.example.lenovo.SpaApp.MyApplication;
import com.example.lenovo.SpaApp.R;
import com.example.lenovo.SpaApp.Utils.CallWebService;
import com.example.lenovo.SpaApp.Utils.CommonFunctions;
import com.example.lenovo.SpaApp.Utils.ConnectionDetector;
import com.example.lenovo.SpaApp.Utils.Constants;
import com.neopixl.pixlui.components.edittext.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import GlobalClasses.GlobalFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class SignUpFragment extends GlobalFragment implements View.OnClickListener {


    SharedPreferences preferences;
    Button signup;
    ConnectionDetector cdr;
    EditText edtxtName, edtemail, edtpassword, cnfedtpassword;
    //TextInputLayout inpName, tilEmail, tilpassword, tilcnfpassword;
    View v;
    CommonFunctions commonFunctions;
    @InjectView(R.id.edtxtNumber)
    EditText edtxtNumber;
    UpdateViewPagerPosition updateViewPagerPosition;
    @InjectView(R.id.addressET)
    EditText addressET;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public SignUpFragment(UpdateViewPagerPosition updateViewPagerPosition) {
        this.updateViewPagerPosition = updateViewPagerPosition;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = MyApplication.preference;
        cdr = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.signup_layout, container, false);
        signup = (Button) v.findViewById(R.id.signup);
        commonFunctions = new CommonFunctions(getActivity());
        edtxtName = (EditText) v.findViewById(R.id.edtxtName);
        edtemail = (EditText) v.findViewById(R.id.edtxtEmail);
        edtpassword = (EditText) v.findViewById(R.id.edtxtpassword);
        cnfedtpassword = (EditText) v.findViewById(R.id.cnfedtxtpassword);

        //inpName = (TextInputLayout) v.findViewById(R.id.inpName);
        //tilEmail = (TextInputLayout) v.findViewById(R.id.inpEmail);
        //tilpassword = (TextInputLayout) v.findViewById(R.id.inppassword);
        //tilcnfpassword = (TextInputLayout) v.findViewById(R.id.inpcnfpassword);
        edtxtName.addTextChangedListener(new MyTextWatcher(edtxtName));
        edtemail.addTextChangedListener(new MyTextWatcher(edtemail));
        edtpassword.addTextChangedListener(new MyTextWatcher(edtpassword));
        cnfedtpassword.addTextChangedListener(new MyTextWatcher(cnfedtpassword));
        signup.setOnClickListener(this);
        signup.setEnabled(true);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onJsonObjectSuccess(JSONObject object) {

        /*SharedPreferences.Editor editor = preferences.edit();

        try {
            JSONObject new_user = object.getJSONObject(Constants.USER);
            editor.putString(Constants.USER_ID, new_user.getString(Constants.USER_ID));
            editor.putString(Constants.NAME, new_user.getString(Constants.NAME));
            editor.putString(Constants.EMAIL, new_user.getString(Constants.EMAIL));
            editor.putString(Constants.ACCESS_TOKEN, new_user.getString(Constants.ACCESS_TOKEN));
            editor.putString(Constants.LOGGED_IN, Constants.LOGGED_IN);
            if (new_user.has(Constants.GENDER))
                editor.putString(Constants.GENDER, new_user.getString(Constants.GENDER));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.apply();*/
        updateViewPagerPosition.updatePosition(0);
       /* MySharedPereference.getInstance().setBoolean(getActivity(), Constants.LOGGED_IN, true);
        Intent i = new Intent(getActivity(), HomeActivityController.class);
        startActivity(i);
        getActivity().finish();*/

    }

    @Override
    public void onJsonArrarSuccess(JSONArray array) {

    }

    @Override
    public void onFailure(String str) {

        // Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

    }

    private boolean validateEmail() {
        String email = edtemail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            edtemail.setError(getString(R.string.err_msg_email));
            requestFocus(edtemail);
            return false;
        } else {
            edtemail.setError(null);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword() {
        if (edtpassword.getText().toString().trim().isEmpty()) {
            edtpassword.setError(getString(R.string.err_msg_password));
            requestFocus(edtpassword);
            return false;
        } else {
            edtpassword.setError(null);
        }

        return true;
    }

    private boolean validatePasswords() {
        if (!edtpassword.getText().toString().trim().equals(cnfedtpassword.getText().toString())) {
            cnfedtpassword.setError(getString(R.string.mismatch_password));
            //requestFocus(cnfedtpassword);
            return false;
        } else {
            cnfedtpassword.setError(null);
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edtxtName:
                    commonFunctions.validateName(edtxtName);
                    break;
                case R.id.edtxtEmail:
                    validateEmail();
                    break;
                case R.id.edtxtpassword:
                    validatePassword();
                    break;
                case R.id.cnfedtxtpassword:
                    validatePasswords();
                    break;
                case R.id.edtxtNumber:
                    commonFunctions.validatePhone(edtxtNumber);
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
       /* Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();*/
        submitForm();
    }

    private void submitForm() {

        if (!commonFunctions.validateName(edtxtName))
            return;
        if (!validateEmail()) {
            return;
        }
        if (!commonFunctions.validatePhone(edtxtNumber)) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

        if (!validatePasswords()) {
            return;
        }
        if (!commonFunctions.validateAddress(addressET)) {
            return;
        }


        CallWebService.getInstance(getActivity(), true).hitJSONObjectVolleyWebService(Request.Method.POST, Constants.WebServices.SIGN_UP, createMapForPostingData(), SignUpFragment.this);

        //Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private HashMap<String, String> createMapForPostingData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.EMAIL, edtemail.getText().toString());
        hashMap.put(Constants.PASSWORD, edtpassword.getText().toString());
        hashMap.put(Constants.NAME, edtxtName.getText().toString());
        hashMap.put(Constants.PHONE_NUMBER, edtxtNumber.getText().toString());
        hashMap.put(Constants.ADDRESS, addressET.getText().toString());

        return hashMap;
    }
}