package com.skymanlab.weighttraining.management.project.fragment.More;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.HashMap;

public class FitnessCenterUtil {

    // constant
    private static final String CLASS_NAME = FitnessCenterUtil.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    public static final String DB_TARGET_USER_NODE = "user";
    public static final String DB_TARGET_FITNESS_CENTER_NODE = "fitnessCenter";


    public static void getMyFitnessCenterData(OnDataProcessingListener listener) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference(FirebaseConstants.DATABASE_FIRST_NODE_USER);

        Query query = db
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(User.FITNESS_CENTER);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserFitnessCenter userFitnessCenter = snapshot.getValue(UserFitnessCenter.class);

                listener.showData(userFitnessCenter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void saveContentOfMyFitnessCenterData(String uid, UserFitnessCenter userFitnessCenter) {

        // 저장할 데이터
        HashMap<String, Object> saveData = new HashMap<>();
        saveData.put(UserFitnessCenter.MEMBER_NUMBER, userFitnessCenter.getMemberNumber());
        saveData.put(UserFitnessCenter.FIRST_ADDRESS, userFitnessCenter.getFirstAddress());
        saveData.put(UserFitnessCenter.SECOND_ADDRESS, userFitnessCenter.getSecondAddress());
        saveData.put(UserFitnessCenter.THIRD_ADDRESS, userFitnessCenter.getThirdAddress());
        saveData.put(UserFitnessCenter.CONTRACT_DATE, userFitnessCenter.getContractDate());
        saveData.put(UserFitnessCenter.EXPIRY_DATE, userFitnessCenter.getExpiryDate());
//        saveData.put(UserFitnessCenter.IS_DISCLOSED, userFitnessCenter.isDisclosed());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                .child(uid)
                .child(User.FITNESS_CENTER)
                .setValue(saveData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


    }


    public static void getAllFitnessCenterLocation() {

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

    public interface OnDataProcessingListener {
        void showData(UserFitnessCenter userFitnessCenter);
    }
}
