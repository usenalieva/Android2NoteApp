package com.makhabatusen.noteapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.models.Note;
import com.makhabatusen.noteapp.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> list;
  private OnItemClickListener listener;


    public NoteAdapter() {
        list = new ArrayList<>();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addList(List<Note> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addNewList(List<Note> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public  void deleteItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void addItem(Note note) {
        // adds to the bottom (by default)
        // list.add(note);

        // to top
        list.add(0, note);

        //notifyItemInserted(list.size() - 1);
       notifyItemInserted(list.indexOf(note));
    }


    public Note getItem(int position) {
        return list.get(position);
    }

    public void editItem(int position, Note note) {
        list.set(position, note);
        notifyItemChanged(position);
    }

    public void sortByTitle(List<Note> list) {

        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private  TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(v -> {
                listener.onCLick(getAdapterPosition(), getItem(getAdapterPosition()));

            });

            itemView.setOnLongClickListener(v -> {
                listener.onLongClick(getAdapterPosition(), getItem(getAdapterPosition()));
                return true;
            });
        }


        public void bind(Note note) {
            tvTitle.setText(note.getTitle());
            tvDate.setText(note.getCreatedAt());

        }
    }
}
