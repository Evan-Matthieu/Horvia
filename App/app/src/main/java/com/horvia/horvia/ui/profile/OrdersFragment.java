package com.horvia.horvia.ui.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.Order;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.farms.FarmDetailFragment;
import com.horvia.horvia.utils.adapter.FarmAdapter;
import com.horvia.horvia.utils.adapter.OrderAdapter;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private LinearLayout ordersList;
    private ApiRequest apiRequest;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ordersList = view.findViewById(R.id.orders_list);

        apiRequest = new ApiRequest(getContext());

        apiRequest.GetOrders(new ApiRequestListener<ArrayList<Order>>() {
            @Override
            public void onComplete(@Nullable ArrayList<Order> entity, String error) {
                if(entity != null){
                    OrderAdapter orderAdapter = new OrderAdapter(container.getContext(), entity);

                    for (int i = 0; i < orderAdapter.getCount(); i++) {
                        View item = orderAdapter.getView(i, null, null);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(0,0,0,40);
                        item.setLayoutParams(layoutParams);

                        ordersList.addView(item);
                        Order order = orderAdapter.getItem(i);

                        item.setOnClickListener(view -> {
                            OrderDetailsFragment fragment = new OrderDetailsFragment();
                            Bundle args = new Bundle();
                            args.putInt("id", order.Id);
                            fragment.setArguments(args);

                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.mainContentFragment, fragment)
                                    .addToBackStack(OrdersFragment.class.toString())
                                    .commit();
                        });
                    }
                }
                else{
                    Toast.makeText(container.getContext(),error, Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}