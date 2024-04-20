package com.horvia.horvia.ui.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;

public class ModifyPasswordFragment extends Fragment {

    private ApiRequest apiRequest;

    private EditText currentPassword, newPassword, newPasswordConfirm;
    private Button submitPasswordModify;

    public ModifyPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_password, container, false);

        currentPassword = view.findViewById(R.id.current_password);
        newPassword = view.findViewById(R.id.new_password);
        newPasswordConfirm = view.findViewById(R.id.new_password_confirm);
        submitPasswordModify = view.findViewById(R.id.submit_modify_password);

        apiRequest = new ApiRequest(getContext());

        submitPasswordModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentPassword.length() == 0){
                    currentPassword.setError(getString(R.string.required_field));
                }
                else if(newPassword.length() == 0){
                    newPassword.setError(getString(R.string.required_field));
                }
                else if(newPasswordConfirm.length() == 0){
                    newPasswordConfirm.setError(getString(R.string.required_field));
                }
                else if(newPassword.getText() != newPasswordConfirm.getText()){
                    newPasswordConfirm.setError(getResources().getString(R.string.password_should_be_same));
                }
                else{
                    apiRequest.ChangePassword(currentPassword.getText().toString(), newPassword.getText().toString(), new ApiRequestListener<Boolean>() {
                        @Override
                        public void onComplete(@Nullable Boolean entity, String error) {
                            if(entity != null && entity){
                                getActivity().getSupportFragmentManager().popBackStack();
                                Toast.makeText(getContext(), R.string.your_password_has_been_modified, Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}