package com.skymanlab.weighttraining.management.project.fragment.More;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.FitnessCenter.data.UserFitnessCenter;

public class FitnessCenterUtil {

    // constant
    public static final String DB_TARGET_USER_NODE = "user";
    public static final String DB_TARGET_FITNESS_CENTER_NODE = "fitnessCenter";

    public static void showFitnessCenterInfo(TextView name, TextView address) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference(DB_TARGET_USER_NODE);

        Query query = db
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(DB_TARGET_FITNESS_CENTER_NODE);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public static  void getAllFitnessCenterLocation() {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference(DB_TARGET_FITNESS_CENTER_NODE);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot search : snapshot.getChildren()) {

                    String key = search.getKey();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public interface OnSuccessListener {
        void setWidget(UserFitnessCenter userFitnessCenter);
    }
}
