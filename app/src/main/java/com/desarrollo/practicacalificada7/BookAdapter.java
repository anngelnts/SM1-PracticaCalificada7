package com.desarrollo.practicacalificada7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.desarrollo.practicacalificada7.Models.BookModel;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolderBooks> {

    private ArrayList<BookModel> listBooks;

    public BookAdapter(ArrayList<BookModel> listBooks) {
        this.listBooks = listBooks;
    }

    @NonNull
    @Override
    public ViewHolderBooks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolderBooks(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBooks holder, int position) {
        holder.assignData(listBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    static class ViewHolderBooks extends RecyclerView.ViewHolder {

        ImageView item_thumbnail;
        TextView item_title;
        TextView item_publisher;
        TextView item_authors;

        ViewHolderBooks(@NonNull View itemView) {
            super(itemView);
            item_thumbnail = itemView.findViewById(R.id.item_thumbnail);
            item_title = itemView.findViewById(R.id.item_title);
            item_publisher = itemView.findViewById(R.id.item_publisher);
            item_authors = itemView.findViewById(R.id.item_authors);
        }

        void assignData(BookModel book) {
            item_title.setText(book.getTitle());
            item_publisher.setText(book.getPublisher());
            item_authors.setText(book.getAuthors());
            Glide.with(item_thumbnail.getContext()).load(book.getThumbnail()).into(item_thumbnail);
        }
    }
}
