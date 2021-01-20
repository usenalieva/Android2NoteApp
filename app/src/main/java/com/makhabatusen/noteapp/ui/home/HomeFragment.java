package com.makhabatusen.noteapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.App;
import com.makhabatusen.noteapp.OnItemClickListener;
import com.makhabatusen.noteapp.Prefs;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.models.Note;
import com.makhabatusen.noteapp.ui.form.FormFragment;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private Note note;
    private boolean toAddNote;
    int position;
    public static String REQUEST_KEY_HF = "rk_home";
    public static String KEY_NOTE_HF = "rk_home";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //placing the adapter in this method so it won't be recreated each time
        adapter = new NoteAdapter();

       setHasOptionsMenu(true);
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.prefs_clear_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_menu_clear_prefs)
            new Prefs(requireContext()).clearPrefs();
        openBoard();
        return super.onOptionsItemSelected(item);
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
            toAddNote = true;
            openForm();
        });
        setFragmentListener();
        initList();

    }

    private void loadData() {
        List<Note> list = App.getAppDataBase().noteDao().getAll();
        adapter.addList(list);
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onCLick(int pos, Note note) {
                position = pos;
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_NOTE_HF,note);
                getParentFragmentManager().setFragmentResult(REQUEST_KEY_HF,bundle);
                openForm();
                toAddNote = false;


                //   Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int pos, Note note) {
                deleteItem(pos, note);
            }

            private void deleteItem(int pos, Note note) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete Element");
                alert.setMessage("Are you sure you want to delete the note?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.deleteItem(pos);
                        App.getAppDataBase().noteDao().delete(note);

                    }
                });
                alert.setNegativeButton("NO", null);
                alert.create().show();
            }
        });

    }


    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(FormFragment.REQUEST_KEY_FF,
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    // Log.e("Home", "note =  " + result.getSerializable("note"));
                    note = (Note) result.getSerializable(FormFragment.KEY_NOTE_FF);
                    if (toAddNote)
                    adapter.addItem(note);
                    else adapter.editItem(position,note);
                });
    }

    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }
    private void openBoard() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.boardFragment);
    }
}