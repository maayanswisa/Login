package com.example.fragapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fragapp.R;
import com.example.fragapp.activitys.MainActivity;
import com.example.fragapp.model.Item;

import java.util.ArrayList;
import java.util.List;
public class AddItemFragment extends Fragment {

    private ItemAdapter adapter;
    private List<Item> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Fetch the itemList from the bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            itemList = bundle.getParcelableArrayList("itemList");
        }

        if (itemList == null) {
            itemList = new ArrayList<>();
        }

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getUsername();

        adapter = new ItemAdapter((ArrayList<Item>) itemList);
        recyclerView.setAdapter(adapter);

        Button btnAdd = view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                // Fetch item details from EditText
                EditText itemNameEditText = view.findViewById(R.id.editTextText2);
                String itemName = itemNameEditText.getText().toString();

                EditText quantityEditText = view.findViewById(R.id.editTextText3);
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(quantityEditText.getText().toString());
                    // Continue processing with the valid integer 'quantity'
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    // You might want to show an error message to the user
                    e.printStackTrace(); // Log the exception for debugging
                }

                // Create a new Item
                Item newItem = new Item(itemName, quantity);

                // Add the new item to the list
                itemList.add(newItem);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
