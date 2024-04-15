package com.horvia.horvia.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ApiRequestListener<T> {
    void onComplete(@Nullable T entity,String error);
}


