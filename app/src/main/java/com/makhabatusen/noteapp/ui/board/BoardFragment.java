package com.makhabatusen.noteapp.ui.board;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makhabatusen.noteapp.OnItemClickListener;
import com.makhabatusen.noteapp.Prefs;
import com.makhabatusen.noteapp.R;

public class BoardFragment extends Fragment {
    public BoardFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        BoardAdapter adapter =  new BoardAdapter();
        viewPager.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onCLick(int pos) { close();
            }

            @Override
            public void onLongClick(int pos) {
            }
        });

        view.findViewById(R.id.btn_skip).setOnClickListener(v->{
            new Prefs(requireContext()).saveBoardStatus();
            close();
        });

    }
    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardStatus();
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();

    }



}