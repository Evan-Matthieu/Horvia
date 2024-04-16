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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private EditText farmName;
    private EditText farmDescription;
    private EditText farmAddress;
    private EditText farmZipCode;
    private EditText farmCity;
    private EditText farmCategory;
    private Bitmap picture;
    private ApiRequest apiRequest;

    private Button selectImageButton;

    private ImageView imageView;

    private ActivityResultLauncher<Intent> launchGallery;

    private TextView errorCreateFarmTextView;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialisation des vues
        farmName = rootView.findViewById(R.id.farmName);
        farmDescription = rootView.findViewById(R.id.farmDescription);
        farmAddress = rootView.findViewById(R.id.farmAddress);
        farmZipCode = rootView.findViewById(R.id.farmZipCode);
        farmCity = rootView.findViewById(R.id.farmCity);
        farmCategory = rootView.findViewById(R.id.farmCategory);
        selectImageButton = rootView.findViewById(R.id.selectImageButton);
        Button createFarmButton = rootView.findViewById(R.id.createFarm_button);
        imageView = rootView.findViewById(R.id.imageView);

        launchGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        Uri imageUri = data.getData();

                        imageView.setImageURI(imageUri);

                        //stocker l'image
                        imageView.buildDrawingCache();
                        picture = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    }
                });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                launchGallery.launch(gallery);
            }
        });
        // Initialisation du TextView d'erreur
        errorCreateFarmTextView = rootView.findViewById(R.id.errorCreateFarmTextView);

        apiRequest = new ApiRequest(container.getContext());


        createFarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitCreateFarm(v);
            }
        });

        return rootView;
    }






    public void onSubmitCreateFarm(View view){



        Location location = new Location();
        location.Address = farmAddress.getText().toString();
        location.ZipCode = farmZipCode.getText().toString();
        location.City = farmCity.getText().toString();

        Farm farm = new Farm();
        farm.Name = farmName.getText().toString();
        farm.Description = farmDescription.getText().toString();
        farm.Location = location;
        farm.Picture = picture;

            apiRequest.CreateFarm(farm, new ApiRequestListener<String>() {
                @Override
                public void onComplete(@Nullable String entity, String error) {
                    Intent interfaceActivity = new Intent(view.getContext(), MainActivity.class);
                    startActivity(interfaceActivity);
                }
            });
    }
}