package com.horvia.horvia.models;

import com.horvia.horvia.R;

public enum Civility {
    MISTER(R.string.mister), MADAM(R.string.madam), MISS(R.string.miss), OTHER(R.string.other);

    private final int label;

    Civility(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }
}

