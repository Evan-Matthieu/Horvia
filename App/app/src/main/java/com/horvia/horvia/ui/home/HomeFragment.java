package com.horvia.horvia.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.horvia.horvia.MainActivity;
import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Civility;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.Location;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.login.LoginActivity;
import com.horvia.horvia.utils.BitmapUtil;
import com.horvia.horvia.utils.FarmAdapter;
import com.horvia.horvia.utils.pagination.PaginationParams;
import com.horvia.horvia.utils.pagination.PaginationResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    LinearLayout farmListLinearLayout;
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ApiRequest apiRequest = new ApiRequest(rootView.getContext());

        farmListLinearLayout = rootView.findViewById(R.id.farm_list);

        apiRequest.GetFarms(new PaginationParams(), new ApiRequestListener<PaginationResult<Farm>>() {
            @Override
            public void onComplete(@Nullable PaginationResult<Farm> entity, String error) {
                if(entity != null){
                    FarmAdapter farmAdapter = new FarmAdapter(container.getContext(), entity.Items);

                    for (int i = 0; i < farmAdapter.getCount(); i++) {
                        View item = farmAdapter.getView(i, null, null);
                        farmListLinearLayout.addView(item);
                    }
                }
                else{
                    Toast.makeText(container.getContext(),error, Toast.LENGTH_LONG).show();
                    Log.d("errror de merde", error);
                }
            }
        });
        return rootView;
    }
}