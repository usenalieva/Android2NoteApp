package com.makhabatusen.noteapp.ui.auth;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.makhabatusen.noteapp.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class PhoneFragment extends Fragment {
    private View viewSmsRequest;
    private View viewPinCode;
    private EditText editPhone;
    private PinView pinView;
    private TextView tvTimer;
    private TextView tvCheckNum;
    private Button btnNext;
    private CountDownTimer timer;
    private CountryCodePicker ccp;
    private String verificationID;
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
        initViews(view);
        showSmsRequestView();
        setListeners(view);
        setCallBacks();
    }

    private void initViews(@NonNull View view) {
        editPhone = view.findViewById(R.id.et_phone);
        pinView = view.findViewById(R.id.pin_view);
        viewSmsRequest = view.findViewById(R.id.view_sms_request);
        viewPinCode = view.findViewById(R.id.view_pin_code);
        tvTimer = view.findViewById(R.id.tv_timer);
        ccp = view.findViewById(R.id.ccp);
        tvCheckNum = view.findViewById(R.id.tv_check_num);
        tvCheckNum.setVisibility(View.GONE);
    }

    private void setListeners(@NonNull View view) {
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> requestSms());

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Log.e("ololo", "handleOnBackPressed" );
                        close();
                    }
                });

        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> confirm());

    }

    private void confirm() {
        String code = Objects.requireNonNull(pinView.getText()).toString();
        if (code.length() == 6 && TextUtils.isDigitsOnly(code)) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
            signIn(credential);

        }
    }

    private void showSmsRequestView() {
        viewSmsRequest.setVisibility(View.VISIBLE);
        viewPinCode.setVisibility(View.GONE);
    }

    private void showPinCodeView() {
        viewSmsRequest.setVisibility(View.GONE);
        viewPinCode.setVisibility(View.VISIBLE);
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
                Log.e("TAG", "onVerificationCompleted: " + phoneAuthCredential.getSmsCode());
                signIn(phoneAuthCredential);
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationID = s;
                showPinCodeView();
                Log.e("ololo", "onCodeSent" + s);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    close();
                } else {
                    Toast.makeText(requireContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }

    private void requestSms() {
        String countryCode = ccp.getSelectedCountryCode();
        String phone = "+" + countryCode + editPhone.getText().toString();
        Log.e("ololo", "requestSms" + phone);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phone)                    // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)  // Timeout and unit
                        .setActivity(requireActivity())           // Activity (for callback binding)
                        .setCallbacks(callbacks)               // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        tvCheckNum.setVisibility(View.GONE);
    }


}