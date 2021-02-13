package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FitnessCenterRegisterSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // instance variable
    private String fitnessCenterName;
    private String fitnessCenterAddress;

    // instance variable
    private TextView name;
    private TextView address;
    private LinearLayout contractDateWrapper;
    private TextView contractDate;
    private LinearLayout expiryDateWrapper;
    private TextView expiryDate;

    // constructor
    public FitnessCenterRegisterSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setFitnessCenterName(String fitnessCenterName) {
        this.fitnessCenterName = fitnessCenterName;
    }

    public void setFitnessCenterAddress(String fitnessCenterAddress) {
        this.fitnessCenterAddress = fitnessCenterAddress;
    }

    @Override
    public void connectWidget() {

        // [ TextView | name ]
        this.name = (TextView) getView().findViewById(R.id.f_fitness_center_register_name);

        // [ TextView | address ]
        this.address = (TextView) getView().findViewById(R.id.f_fitness_center_register_address);

        // [LinearLayout] [contractDateWrapper] widget connect
        this.contractDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitness_center_register_contract_date_wrapper);

        // [TextView] [contractDate] widget connect
        this.contractDate = (TextView) getView().findViewById(R.id.f_fitness_center_register_contract_date);

        // [LinearLayout] [expiryDateWrapper] widget connect
        this.expiryDateWrapper = (LinearLayout) getView().findViewById(R.id.f_fitness_center_register_expiry_date_wrapper);

        // [TextView] [expiryDate] widget connect
        this.expiryDate = (TextView) getView().findViewById(R.id.f_fitness_center_register_expiry_date);

    }

    @Override
    public void initWidget() {

        // [ TextView | name ] text
        this.name.setText(fitnessCenterName);

        // [ TextView | address ] text
        this.address.setText(fitnessCenterAddress);

        // [LinearLayout] [contractDateWrapper] click listener
        this.contractDateWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 날짜 가져오기
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // [DatePickerDialog] [dialog]
                DatePickerDialog dialog = new DatePickerDialog(getFragment().getContext(), createOnDateSetListener(contractDate), year, month, dayOfMonth);
                dialog.show();
            }
        });


        // [LinearLayout] [expiryDateWrapper] click listener
        this.expiryDateWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 날짜 가져오기
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // [DatePickerDialog] [dialog]
                DatePickerDialog dialog = new DatePickerDialog(getFragment().getContext(), createOnDateSetListener(expiryDate), year, month, dayOfMonth);
                dialog.show();
            }
        });

    }


    public FragmentTopBarManager.EndButtonListener newInstanceOfEndButtonListener() {
        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getFragment().getContext());
                builder.setTitle(R.string.f_fitness_center_register_alert_register_title)
                        .setMessage(R.string.f_fitness_center_register_alert_register_message)
                        .setPositiveButton(R.string.f_fitness_center_register_alert_register_bt_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.f_fitness_center_register_alert_register_bt_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                return builder.create();
            }
        };
    }


    private DatePickerDialog.OnDateSetListener createOnDateSetListener(TextView date) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // Date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY년 MM월 dd일");

                // Date format setting
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                // TextView 의 setting / text / background
                date.setText(simpleDateFormat.format(calendar.getTime()));
                date.setBackgroundResource(R.color.colorBackgroundWhite);
            }
        };
    }

}
