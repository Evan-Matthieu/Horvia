package com.horvia.horvia.models;

import android.content.Context;

import com.horvia.horvia.R;

public enum OrderStatus {
    ONGOING(R.string.ongoing), COMPLETED(R.string.completed);

    private final int label;

    OrderStatus(int label) {
        this.label = label;
    }

    public String getLabel(Context context) {
        return context.getString(label);
    }
}
