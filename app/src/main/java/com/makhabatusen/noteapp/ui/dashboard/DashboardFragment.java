package com.makhabatusen.noteapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makhabatusen.noteapp.OnItemClickListener;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.models.Note;
import com.makhabatusen.noteapp.ui.home.NoteAdapter;

import java.util.List;

public class DashboardFragment extends Fragment {

    private NoteAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        initList();
        loadData();
    }

    private void loadData() {
        FirebaseFirestore.getInstance().collection("notes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Note> list = task.getResult().toObjects(Note.class);
                        if (task.isSuccessful()) {

//                            for (DocumentSnapshot snapshot : task.getResult()
//                            ) {
//                                String docID = snapshot.getId();
//                                Note note = snapshot.toObject(Note.class);
//                                note.setId(docID);
//                                list.add(note);
//                            }

                            adapter.addList(list);

                        }
                    }
                });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onCLick(int pos, Note note) {
                
            }

            @Override
            public void onLongClick(int pos, Note note) {

            }
        });
    }
}