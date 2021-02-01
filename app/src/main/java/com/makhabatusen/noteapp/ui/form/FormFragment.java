package com.makhabatusen.noteapp.ui.form;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makhabatusen.noteapp.App;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.models.Note;
import com.makhabatusen.noteapp.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormFragment extends Fragment {
    private EditText editText;
    public static final String REQUEST_KEY_FF = "rk_form";
    public static final String KEY_NOTE_FF = "note";
    private Note note;
    private final FirebaseFirestore FS_DB = FirebaseFirestore.getInstance();

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

        getParentFragmentManager().setFragmentResultListener(HomeFragment.REQUEST_KEY_HF,
                getViewLifecycleOwner(), (requestKey, result) -> {
                note = (Note) result.getSerializable(HomeFragment.KEY_NOTE_HF);
                editText.setText(note.getTitle());
                });
    }




    private void save() {
        String text = editText.getText().toString().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm yyyy/MM/dd", Locale.ROOT);
        String date = dateFormat.format(System.currentTimeMillis());

        if (note == null) {
            note = new Note(text, date);
            addToFireStore();

        } else{
            note.setTitle(text);
            editAtFireStore();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_NOTE_FF, note);
        getParentFragmentManager().setFragmentResult(REQUEST_KEY_FF, bundle);
        close();
    }

    private void addToFireStore() {

        FS_DB.collection("notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) { // Adding to DataBase
                        note.setId(documentReference.getId());
                        App.getAppDataBase().noteDao().insert(note);
                        Log.e("ololo", "Note added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ololo", "Error adding note", e);
                    }
                });
    }

    private void editAtFireStore() {


        FS_DB.collection("notes").document(note.getId())
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("ololo", "Note updated with ID: " + note.getId());
                        App.getAppDataBase().noteDao().upDateItem(note);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ololo", "Error updating note", e);

                    }
                });

    }


    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }
}