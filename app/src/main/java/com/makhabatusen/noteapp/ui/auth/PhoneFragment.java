package com.makhabatusen.noteapp.ui.auth;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.makhabatusen.noteapp.MainActivity;
import com.makhabatusen.noteapp.R;


public class PhoneFragment extends Fragment {
    private View viewSmsRequest;
    private View viewPinCode;
    private EditText editPhone;
    private PinView pinView;
    private TextView tvTimer;
    private TextView tvCheckNum;
    private Button btnNext;
    private CountDownTimer timer;
    NavController navController;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.et_phone);
        pinView = view.findViewById(R.id.pin_view);
        viewSmsRequest = view.findViewById(R.id.view_sms_request);
        viewPinCode = view.findViewById(R.id.view_pin_code);
        tvTimer = view.findViewById(R.id.tv_timer);
        tvCheckNum = view.findViewById(R.id.tv_check_num);
        tvCheckNum.setVisibility(View.GONE);
        showSmsRequestView();
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> requestSms());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> confirm());
        setCallBacks();
    }

    private void confirm() {

    }

    private void showSmsRequestView() {
        viewSmsRequest.setVisibility(View.VISIBLE);
        viewPinCode.setVisibility(View.GONE);
    }

    private void showPinCodeView() {
        viewSmsRequest.setVisibility(View.GONE);
        viewPinCode.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
        startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                String text = String.valueOf(l / 1000);
                tvTimer.setText("00:" + text);
                if (text.length() < 2) tvTimer.setText("00:0" + text);
            }

            @Override
            public void onFinish() {
                showSmsRequestView();
                btnNext.setVisibility(View.VISIBLE);
                tvCheckNum.setVisibility(View.VISIBLE);

            }
        };
        timer.start();
    }

    private void setCallBacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                pinView.setText(phoneAuthCredential.getSmsCode());

                Log.e("TAG", "onVerificationCompleted");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationCompleted" + e.getMessage());
            }

        };
    }

    private void requestSms() {
//        String phone = editPhone.getText().toString();
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
//                        .setPhoneNumber(phone)                    // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS)  // Timeout and unit
//                        .setActivity(requireActivity())           // Activity (for callback binding)
//                        .setCallbacks(callbacks)               // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
        tvCheckNum.setVisibility(View.GONE);
        showPinCodeView();
    }


}