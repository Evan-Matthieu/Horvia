package com.horvia.horvia.models;

import com.horvia.horvia.R;

public enum Civility {
     MADAM(R.string.madam), MISS(R.string.miss), MISTER(R.string.mister), OTHER(R.string.other);

    private final int label;

    Civility(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }
}

