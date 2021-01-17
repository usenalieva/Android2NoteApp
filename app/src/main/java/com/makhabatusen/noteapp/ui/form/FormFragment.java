package com.makhabatusen.noteapp.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.models.Note;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormFragment extends Fragment {
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.et_text);
        view.findViewById(R.id.btn_save).setOnClickListener(v -> {
            save();
        });
    }

    private void save() {
        String text = editText.getText().toString().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm yyyy/MM/dd", Locale.ROOT);
        String date = dateFormat.format(System.currentTimeMillis());

        Note note = new Note(text,date);
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        getParentFragmentManager().setFragmentResult("rk_form", bundle);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }
}