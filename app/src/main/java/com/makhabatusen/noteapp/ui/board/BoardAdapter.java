package com.makhabatusen.noteapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.makhabatusen.noteapp.R;

import pl.droidsonroids.gif.GifImageView;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] titles = {"Note 27", "Simple",  "Powerful", "Secure", "Cloud-Based"};
    private String[] descriptions = {
            "The world's best notepad app. It is free and secure.",
            "Note 27  is a simple and awesome notepad app.",
            "Note 27 has no limits on the size of your notes.",
            "Note 27 keeps your notes safe from hacker attacks.",
            "Note 27 lets you access your notes from multiple devices."
    };

    private int[] icons = {
            R.raw.a_notepad,
            R.raw.a_simple,
            R.raw.a_infinity,
            R.raw.a_security_scan,
            R.raw.a_cloud};
    private BoardFragmentListener boardFragmentListener;


    public BoardAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setBoardFragmentListener(BoardFragmentListener boardFragmentListener) {
        this.boardFragmentListener = boardFragmentListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textDesc;

      //  private GifImageView gifImages;
        private LottieAnimationView lottieAnimationView;

        private Button btnStart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_title);
            textDesc = itemView.findViewById(R.id.tv_desc);
           // gifImages = itemView.findViewById(R.id.gif_board_pic);
            lottieAnimationView = itemView.findViewById(R.id.av_lottie);
            btnStart = itemView.findViewById(R.id.btn_start);

            btnStart.setOnClickListener(v -> {
                boardFragmentListener.skip();
            });

        }

        public void bind(int position) {
            textTitle.setText(titles[position]);
            textDesc.setText(descriptions[position]);
           // gifImages.setImageResource(icons[position]);
            lottieAnimationView.setAnimation(icons[position]);
            startBtn(position);

        }

        private void startBtn(int position) {
            if (position == (titles.length - 1))
                btnStart.setVisibility(View.VISIBLE);
            else btnStart.setVisibility(View.GONE);
        }
    }

    public interface BoardFragmentListener {
        void skip();
    }
}
