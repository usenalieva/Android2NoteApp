package com.makhabatusen.noteapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.makhabatusen.noteapp.Prefs;
import com.makhabatusen.noteapp.R;

public class BoardFragment extends Fragment {
    public BoardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        BoardAdapter adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);

        // setting  TabLayout indicators
        TabLayout tabLayout = view.findViewById(R.id.tl_indicators);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
        }).attach();
        adapter.setBoardFragmentListener(() -> {
            new Prefs(requireContext()).saveBoardStatus();
            close();
        });

        view.findViewById(R.id.btn_skip).setOnClickListener(v -> {
            new Prefs(requireContext()).saveBoardStatus();
            close();
        });

        // closing the BoardFragment
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
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