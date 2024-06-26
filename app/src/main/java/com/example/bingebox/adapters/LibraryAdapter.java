package com.example.bingebox.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bingebox.R;
import com.example.bingebox.RVInterface;
import com.example.bingebox.database.Entity_Movie;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {
    private List<Entity_Movie> items;
    private RVInterface rvInterface;

    public LibraryAdapter(List<Entity_Movie> items, RVInterface rvInterface) {
        this.items = items;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_item_movie, parent, false);
        return new LibraryViewHolder(view, rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Entity_Movie> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    public Entity_Movie getItemAt(int position) {
        return items.get(position);
    }

    static class LibraryViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public LibraryViewHolder(@NonNull View itemView, RVInterface rvInterface) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movieTitle);
            imageView = itemView.findViewById(R.id.movieImage);

            itemView.setOnClickListener(v -> {
                if (rvInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        rvInterface.onItemClick(pos);
                    }
                }
            });
        }

        public void bind(Entity_Movie movie) {
            titleTextView.setText(movie.getTitle());
            Glide.with(itemView.getContext())
                    .load(movie.getImgUrl())
                    .into(imageView);
        }
    }
}