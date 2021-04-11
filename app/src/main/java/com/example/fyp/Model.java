package com.example.fyp;

public class Model {


    String mDrinkName;
    String mDrinkDetail;
    int mDrinkPhoto;

    public Model(String mDrinkName, String mDrinkDetail, int mDrinkPhoto) {
        this.mDrinkName = mDrinkName;
        this.mDrinkDetail = mDrinkDetail;
        this.mDrinkPhoto = mDrinkPhoto;
    }

    public Model(String green_tea, int greentea) {
    }


    public String getmDrinkName() {
        return mDrinkName;
    }

    public String getmDrinkDetail() {
        return mDrinkDetail;
    }

    public int getmDrinkPhoto() {
        return mDrinkPhoto;
    }
}
