package com.skymanlab.weighttraining.management.project.fragment.More.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.user.data.User;
import com.skymanlab.weighttraining.management.user.data.UserTraining;

import java.util.HashMap;

public class ThreeMajorMeasurementsDialog extends DialogFragment implements FragmentSectionInitializable {

    // instance variable
    private TextView register;
    private EditText squat;
    private EditText deadlift;
    private EditText benchPress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog_three_major_measurements, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectWidget();

        initWidget();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void connectWidget() {

        // [ TextView | register ]
        this.register = (TextView) getView().findViewById(R.id.custom_dialog_three_major_measurements_button_register);

        // [ EditText | squat ]
        this.squat = (EditText) getView().findViewById(R.id.custom_dialog_three_major_measurements_squat);

        // [ EditText | deadlift ]
        this.deadlift = (EditText) getView().findViewById(R.id.custom_dialog_three_major_measurements_deadlift);

        // [ EditText | benchPress ]
        this.benchPress = (EditText) getView().findViewById(R.id.custom_dialog_three_major_measurements_bench_press);
    }

    @Override
    public void initWidget() {

        // click listener
        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!squat.getText().toString().equals("")
                                && !deadlift.getText().toString().equals("")
                                && !benchPress.getText().toString().equals("")) {

                            int squatValue = Integer.parseInt(squat.getText().toString());
                            int deadliftValue = Integer.parseInt(deadlift.getText().toString());
                            int benchPressValue = Integer.parseInt(benchPress.getText().toString());

                            HashMap<String, Object> saveData = new HashMap<>();
                            saveData.put(UserTraining.SQUAT, squatValue);
                            saveData.put(UserTraining.DEADLIFT, deadliftValue);
                            saveData.put(UserTraining.BENCH_PRESS, benchPressValue);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(User.TRAINING)
                                    .setValue(
                                            saveData,
                                            new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                                    if (error != null) {

                                                        return;
                                                    }

                                                    Bundle args = new Bundle();
                                                    args.putInt(UserTraining.SQUAT, squatValue);
                                                    args.putInt(UserTraining.DEADLIFT, deadliftValue);
                                                    args.putInt(UserTraining.BENCH_PRESS, benchPressValue);

                                                    getActivity().getSupportFragmentManager().setFragmentResult(User.TRAINING, args);

                                                    getActivity().getSupportFragmentManager().popBackStack();
                                                }
                                            }
                                    );

                        } else {
                            Snackbar.make(getView(), R.string.custom_dialog_three_major_measurements_snack_no_input, Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }
        );

    }

}
