package com.makhabatusen.noteapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.R;

import pl.droidsonroids.gif.GifImageView;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] titles = {"Telegram", "Fast", "Free", "Powerful", "Secure", "Cloud-Based"};
    private String[] descriptions = {
            "The world's fastest messaging app. It is free and secure.",
            "Telegram delivers messages faster than any other application.",
            "Telegram is free forever. No ads. No subscription fees.",
            "Telegram has no limits on the size of your media and chats.",
            "Telegram keeps your messages safe from hacker attacks.",
            "Telegram lets you access your messages from multiple devices."
    };

    private int[] icons = {
            R.drawable.telegram,
            R.drawable.fast,
            R.drawable.free,
            R.drawable.powerful,
            R.drawable.secure,
            R.drawable.cloud};
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
        private GifImageView gifImages;
        private Button btnStart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_title);
            textDesc = itemView.findViewById(R.id.tv_desc);
            gifImages = itemView.findViewById(R.id.gif_board_pic);
            btnStart = itemView.findViewById(R.id.btn_start);

            btnStart.setOnClickListener(v -> {
                boardFragmentListener.skip();
            });

        }

        public void bind(int position) {
            textTitle.setText(titles[position]);
            textDesc.setText(descriptions[position]);
            gifImages.setImageResource(icons[position]);
            startBtn(position);

        }

        private void startBtn(int position) {
            if (position == (titles.length - 1))
                btnStart.setVisibility(View.VISIBLE);
            else btnStart.setVisibility(View.GONE);
        }
    }
    public  interface BoardFragmentListener {
        void skip();
    }
}
