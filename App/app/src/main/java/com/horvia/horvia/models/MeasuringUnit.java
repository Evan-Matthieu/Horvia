package com.horvia.horvia.models;

import android.content.Context;

import com.horvia.horvia.R;

public enum MeasuringUnit {
    LITRE(R.string.litre), WEIGHT(R.string.weight), NUMBER(R.string.number);

    private final int label;

    MeasuringUnit(int label) {
        this.label = label;
    }

    public String getLabel(Context context) {
        return context.getString(label);
    }
}
