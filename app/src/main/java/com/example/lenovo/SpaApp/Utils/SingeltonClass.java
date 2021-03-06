package com.example.lenovo.SpaApp.Utils;

import com.example.lenovo.SpaApp.Models.ProductModel;
import com.example.lenovo.SpaApp.Models.ServiceModel;

import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 6/2/2016.
 */
public class SingeltonClass {

    private static SingeltonClass ourInstance = new SingeltonClass();

    public static SingeltonClass getInstance() {
        return ourInstance;
    }

    public static ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();

    public ArrayList<ProductModel> productModels = new ArrayList<>();

    public ServiceModel serviceModel;

    public ProductModel productModel = null;

    public int AFTER_LOGIN_ACTION = 0; //***1 for go back to previous screen*****//

    private SingeltonClass() {
    }

    public ArrayList<ProductModel> getProductsArrayList(int pos) {
        if (serviceModelArrayList != null && serviceModelArrayList.size() > 0) {
            serviceModel = serviceModelArrayList.get(pos);
            productModels = serviceModel.getProducts();
        }
        return productModels;
    }

    public ProductModel getProductModel(int pos) {

        if (productModels != null && productModels.size() > 0)
            productModel = productModels.get(pos);
        return productModel;
    }


}
