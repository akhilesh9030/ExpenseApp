package com.homework02.mad.homework02;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by sures on 9/13/2016.
 */
public class Expense implements Serializable  {
    String name;
    String category;
    Double amt;
    String date;
    Uri receipt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Uri getReceipt() {
        return receipt;
    }

    public void setReceipt(Uri receipt) {
        this.receipt = receipt;
    }
}
