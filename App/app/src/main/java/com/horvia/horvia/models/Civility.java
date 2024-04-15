package com.horvia.horvia.models;

import com.horvia.horvia.R;

public enum Civility {
     MADAM(R.string.madam), MISTER(R.string.mister);

    private final int label;

    Civility(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }
}

