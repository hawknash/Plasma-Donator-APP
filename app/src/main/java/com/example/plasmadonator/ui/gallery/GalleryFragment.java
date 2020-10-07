package com.example.plasmadonator.ui.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.plasmadonator.Dashboard;
import com.example.plasmadonator.R;
import com.example.plasmadonator.viewmodels.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GalleryFragment extends Fragment {

    private EditText inputemail, inputpassword, retypePassword, fullName, address, contact,gender, bloodgroup, division;
    private FirebaseAuth mAuth;
    private Button btnSignup;
    private ProgressDialog pd;


    private boolean isUpdate = false;

    private DatabaseReference db_ref, donor_ref;
    private FirebaseDatabase db_User;
    private CheckBox isDonor;


    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        db_User = FirebaseDatabase.getInstance();
        db_ref = db_User.getReference("users");
        donor_ref = db_User.getReference("donors");
        mAuth = FirebaseAuth.getInstance();


        fullName = root.findViewById(R.id.input_fullName);
        gender = root.findViewById(R.id.gender);
        address = root.findViewById(R.id.inputAddress);
        division = root.findViewById(R.id.city);
        bloodgroup = root.findViewById(R.id.bloodgroup);
        contact = root.findViewById(R.id.inputMobile);
        isDonor = root.findViewById(R.id.checkbox);

        btnSignup = root.findViewById(R.id.button_register);



        if (mAuth.getCurrentUser() != null) {


            pd.dismiss();
            /// getActionBar().setTitle("Profile");


            isUpdate = true;

            Query Profile = db_ref.child(mAuth.getCurrentUser().getUid());
            Profile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    UserData userData = dataSnapshot.getValue(UserData.class);

                    if (userData != null) {
                        pd.show();
                        fullName.setText(userData.getName());
                        gender.setText(userData.getGender());
                        address.setText(userData.getAddress());
                        contact.setText(userData.getContact());
                        bloodgroup.setText(userData.getBloodGroup());
                        division.setText(userData.getDivision());
                        Query donor = donor_ref.child(division.getText().toString())
                                .child(bloodgroup.getText().toString())
                                .child(mAuth.getCurrentUser().getUid());

                        donor.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists())
                                {
                                    isDonor.setChecked(true);
                                    isDonor.setText("Unmark this to leave from donors");
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Your are not a donor! Be a donor and save life by donating blood.",
                                            Toast.LENGTH_LONG).show();
                                }
                                pd.dismiss();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("User", databaseError.getMessage());
                            }

                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("User", databaseError.getMessage());
                }
            });


        } else pd.dismiss();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Name = fullName.getText().toString();
                final String Gender = gender.getText().toString();
                final String Contact = contact.getText().toString();
                final String BloodGroup = bloodgroup.getText().toString();
                final String Address = address.getText().toString();
                final String Division = division.getText().toString();
                final String blood = bloodgroup.getText().toString();
                final String div   = division.getText().toString();

                try {

                    if (Name.length() <= 2) {
                        ShowError("Name");
                        fullName.requestFocusFromTouch();
                    } else if (Contact.length() < 11) {
                        ShowError("Contact Number");
                        contact.requestFocusFromTouch();
                    } else if (Address.length() <= 2) {
                        ShowError("Address");
                        address.requestFocusFromTouch();
                    } else {
                        if (!isUpdate) {


                        } else {

                            String id = mAuth.getCurrentUser().getUid();
                            db_ref.child(id).child("Name").setValue(Name);
                            db_ref.child(id).child("Gender").setValue(Gender);
                            db_ref.child(id).child("Contact").setValue(Contact);
                            db_ref.child(id).child("BloodGroup").setValue(BloodGroup);
                            db_ref.child(id).child("Address").setValue(Address);
                            db_ref.child(id).child("Division").setValue(Division);

                            if(isDonor.isChecked())
                            {
                                donor_ref.child(div).child(blood).child(id).child("UID").setValue(id).toString();
                                donor_ref.child(div).child(blood).child(id).child("LastDonate").setValue("Don't donate yet!");
                                donor_ref.child(div).child(blood).child(id).child("TotalDonate").setValue(0);
                                donor_ref.child(div).child(blood).child(id).child("Name").setValue(Name);
                                donor_ref.child(div).child(blood).child(id).child("Contact").setValue(Contact);
                                donor_ref.child(div).child(blood).child(id).child("Address").setValue(Address);

                            }
                            else
                            {

                                donor_ref.child(div).child(blood).child(id).removeValue();

                            }
                            Toast.makeText(getActivity(), "Your account has been updated!", Toast.LENGTH_LONG)
                                    .show();
                            Intent intent = new Intent(getActivity(), Dashboard.class);
                            startActivity(intent);

                        }
                        pd.dismiss();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });




        return root;
    }
    private void ShowError(String error) {

        Toast.makeText(getActivity(), "Please, Enter a valid "+error,
                Toast.LENGTH_LONG).show();
    }
}