package com.makhabatusen.noteapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.makhabatusen.noteapp.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fab_add).setOnClickListener(v -> {
            openForm();
        });
        setFragmentListener();
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_form",
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    Log.e("Home", "text =  " + result.getString("text"));
                });
    }

    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);

    }
}