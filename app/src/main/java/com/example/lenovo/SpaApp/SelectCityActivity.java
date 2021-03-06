package com.example.lenovo.SpaApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.example.lenovo.SpaApp.Adapters.SelectCityAdapter;
import com.example.lenovo.SpaApp.HomeActivityMVC.HomeActivityController;
import com.example.lenovo.SpaApp.Interfaces.RecyclerViewClick;
import com.example.lenovo.SpaApp.Models.SelectCityModel;
import com.example.lenovo.SpaApp.Utils.CallWebService;
import com.example.lenovo.SpaApp.Utils.CommonFunctions;
import com.example.lenovo.SpaApp.Utils.Constants;
import com.example.lenovo.SpaApp.Utils.MySharedPereference;
import com.example.lenovo.SpaApp.Utils.ParsingResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import GlobalClasses.GlobalActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by divyanshu on 4/3/2016.
 */

public class SelectCityActivity extends GlobalActivity implements RecyclerViewClick {

    @InjectView(R.id.cityRV)
    RecyclerView cityRV;
    ArrayList<SelectCityModel> selectCityModels;
    SelectCityAdapter selectCityAdapter;
    private CardView card;
    private String selectedCityID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city_activity);
        ButterKnife.inject(this);

        InitViews();
    }

    private void InitViews() {
        selectCityModels = new ArrayList<>();
        cityRV.setLayoutManager(new LinearLayoutManager(this));

        CallWebService.getInstance(this, true).hitWithJSONObjectVolleyWebService(CallWebService.GET, Constants.WebServices.GET_CITY, null, this);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject object) {
        super.onJsonObjectSuccess(object);
        try {
            selectCityModels = ParsingResponse.getInstance().parseJsonArrayWithJsonObject(object.getJSONArray(Constants.DATA), SelectCityModel.class);
            selectCityAdapter = new SelectCityAdapter(this, this, selectCityModels);
            cityRV.setAdapter(selectCityAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ConfirmCity(View view) {

        if (selectedCityID.length() > 0) {
            MySharedPereference.getInstance().setString(this, Constants.CITY_ID, selectedCityID);
            int pos = getIntent().getIntExtra("pos", -1);
            Intent i = new Intent(this, HomeActivityController.class);
            if (pos > -1) {
                i = new Intent(this, MainActivity.class);
                i.putExtra("pos", pos);
            }
            startActivity(i);
            finish();
        } else {
            CommonFunctions.showSnackBarWithoutAction(cityRV, getString(R.string.select_city_alert));
        }
    }

    @Override
    public void onClickItem(int position, View view) {
        if (card != null)
            card.setCardBackgroundColor(getResources().getColor(R.color.white));
        card = (CardView) view;
        card.setCardBackgroundColor(getResources().getColor(R.color.background_bottom_color));

        selectedCityID = selectCityModels.get(position).getCity_id();

        if (selectedCityID.length() > 0) {
            MySharedPereference.getInstance().setString(this, Constants.CITY_ID, selectedCityID);
            int pos = getIntent().getIntExtra("pos", -1);
            Intent i = new Intent(this, HomeActivityController.class);
            if (pos > -1) {
                i = new Intent(this, MainActivity.class);
                i.putExtra("pos", pos);
            }
            startActivity(i);
            finish();
        } else {
            CommonFunctions.showSnackBarWithoutAction(cityRV, getString(R.string.select_city_alert));
        }
    }
}
