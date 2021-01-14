package com.makhabatusen.noteapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.OnItemClickListener;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.models.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //placing the adapter in this method so it won't be recreated each time
        adapter = new NoteAdapter();

        ArrayList<Note> list = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ROOT);
        String date = dateFormat.format(System.currentTimeMillis());

        for (int i = 1; i < 11  ; i++) {
            list.add(new Note("Task # " + i, date));
        }
        adapter.addList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        view.findViewById(R.id.fab_add).setOnClickListener(v -> {
            openForm();
        });
        setFragmentListener();
        initList();
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onCLick(int pos) {
                Note note = adapter.getItem(pos);
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int pos) {
                deleteItem(pos);
            }

            private void deleteItem(int pos) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete Element");
                alert.setMessage("Are you sure you want to delete the note?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.deleteItem(pos);
                    }
                });
                alert.setNegativeButton("NO", (dialogInterface, i) -> {
                });
                alert.create().show();
            }
        });

    }



    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_form",
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    // Log.e("Home", "note =  " + result.getSerializable("note"));
                    Note note = (Note) result.getSerializable("note");
                    adapter.addItem(note);
                });
    }

    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);

    }
}