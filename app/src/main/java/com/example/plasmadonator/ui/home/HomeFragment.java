package com.example.plasmadonator.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasmadonator.adapter.PlasmaRequestAdapter;
import com.example.plasmadonator.viewmodels.CustomUserData;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plasmadonator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private EditText search,searchCity;
    private View view;
    private RecyclerView recentPosts;

    private DatabaseReference donor_ref;
    FirebaseAuth mAuth;
    private PlasmaRequestAdapter restAdapter;
    private List<CustomUserData> postLists;
    private ProgressDialog pd;


    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recentPosts = (RecyclerView) root.findViewById(R.id.recyleposts);

        recentPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        search=root.findViewById(R.id.search);
        searchCity=root.findViewById(R.id.searchCity);
        donor_ref = FirebaseDatabase.getInstance().getReference();
        postLists = new ArrayList<>();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        mAuth = FirebaseAuth.getInstance();


        restAdapter = new PlasmaRequestAdapter(postLists);
        RecyclerView.LayoutManager pmLayout = new LinearLayoutManager(getContext());
        recentPosts.setLayoutManager(pmLayout);
        recentPosts.setItemAnimator(new DefaultItemAnimator());
        recentPosts.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recentPosts.setAdapter(restAdapter);

        AddPosts();


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                if(editable.toString().length()>0 && searchCity.getText().toString().length()==0){

                    filter(editable.toString());

                }
                else if(editable.toString().length()==0 && searchCity.getText().toString().length()==0)
                {
                    AddPosts();
                }
                else if(editable.toString().length()>0 && searchCity.getText().toString().length()>0)
                {
                    filterboth(editable.toString(),searchCity.getText().toString());

                }
                else
                {
                    filtercity(searchCity.getText().toString());
                }

            }
        });

        searchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0 && search.getText().toString().length()==0){

                    filtercity(editable.toString());

                }
                else if(editable.toString().length()==0 && search.getText().toString().length()==0)
                {
                    AddPosts();
                }
                else if(editable.toString().length()>0 && search.getText().toString().length()>0)
                {
                    filterboth(search.getText().toString(),editable.toString());

                }
                else{
                    filter(search.getText().toString());
                }

            }
        });


        return root;

    }
    private void AddPosts()
    {
        Query allposts = donor_ref.child("posts");
        pd.show();
        allposts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    postLists.clear();

                    for (DataSnapshot singlepost : dataSnapshot.getChildren()) {
                        CustomUserData customUserData = singlepost.getValue(CustomUserData.class);
                        postLists.add(customUserData);
                      //  restAdapter.notifyDataSetChanged();
                        Log.e("doo","doo");
                    }
                    restAdapter =new PlasmaRequestAdapter(postLists);
                    recentPosts.setAdapter(restAdapter);

                    pd.dismiss();
                }
                else
                {
                    Toast.makeText(getActivity(), "Database is empty now!",
                            Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("User", databaseError.getMessage());

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<CustomUserData> filterdNames = new ArrayList<>();

        //looping through existing elements
        for ( CustomUserData s : postLists) {
            //if the existing elements contains the search input

            if (s.getBloodGroup().toLowerCase().equals(text.toLowerCase())) {

                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        restAdapter =new PlasmaRequestAdapter(filterdNames);
        recentPosts.setAdapter(restAdapter);
        //calling a method of the adapter class and passing the filtered list

    }

    private void filterboth(String bg,String city) {
        //new array list that will hold the filtered data
        List<CustomUserData> filterdNames = new ArrayList<>();

        //looping through existing elements
        for ( CustomUserData s : postLists) {
            //if the existing elements contains the search input

            if (s.getBloodGroup().toLowerCase().equals(bg.toLowerCase()) && s.getDivision().toLowerCase().contains(city.toLowerCase())) {

                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        restAdapter =new PlasmaRequestAdapter(filterdNames);
        recentPosts.setAdapter(restAdapter);
        //calling a method of the adapter class and passing the filtered list

    }

    private void filtercity(String text) {
        //new array list that will hold the filtered data
        List<CustomUserData> filterdNames = new ArrayList<>();

        //looping through existing elements
        for ( CustomUserData s : postLists) {
            //if the existing elements contains the search input

            if (s.getDivision().toLowerCase().contains(text.toLowerCase())) {

                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        restAdapter =new PlasmaRequestAdapter(filterdNames);
        recentPosts.setAdapter(restAdapter);
        //calling a method of the adapter class and passing the filtered list

    }
}