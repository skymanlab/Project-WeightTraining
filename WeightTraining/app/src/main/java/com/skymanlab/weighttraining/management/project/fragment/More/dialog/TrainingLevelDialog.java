package com.skymanlab.weighttraining.management.project.fragment.More.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class TrainingLevelDialog extends DialogFragment implements FragmentSectionInitializable {

    // instance variable
    private ImageView cancel;
    private TextView register;
    private Spinner trainingLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog_training_level, container, false);
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

        // [ ImageView | cancel ]
        this.cancel = (ImageView) getView().findViewById(R.id.custom_dialog_trainingLevel_button_cancel);

        // [ TextView | register ]
        this.register = (TextView) getView().findViewById(R.id.custom_dialog_trainingLevel_button_register);

        // [ Spinner | trainingLevel ]
        this.trainingLevel = (Spinner) getView().findViewById(R.id.custom_dialog_trainingLevel_level);
    }

    @Override
    public void initWidget() {

        // spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.trainingLevel, android.R.layout.simple_spinner_dropdown_item);
        trainingLevel.setAdapter(adapter);

        // click listener
        this.cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        // click listener
        this.register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!trainingLevel.getSelectedItem().toString().equals("")) {

                            String level = trainingLevel.getSelectedItem().toString();

                            HashMap<String, Object> saveData = new HashMap<>();
                            saveData.put(UserTraining.LEVEL, level);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child(FirebaseConstants.DATABASE_FIRST_NODE_USER)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(User.TRAINING)
                                    .updateChildren(
                                            saveData,
                                            new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                                    if (error != null) {

                                                        return;
                                                    }

                                                    Bundle args = new Bundle();
                                                    args.putString(UserTraining.LEVEL, level);

                                                    getActivity().getSupportFragmentManager().setFragmentResult(UserTraining.LEVEL, args);

                                                    dismiss();
                                                }
                                            }
                                    );

                        } else {
                            Snackbar.make(getView(), R.string.custom_dialog_trainingLevel_snack_noSelect, Snackbar.LENGTH_SHORT).show();
                        }


                    }
                }
        );

    }
}
