package com.shelfie.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shelfie.R;
import com.shelfie.model.InteractiveBook;
import com.shelfie.ui.fragments.BookThumbnailFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>  {

    private ArrayList<InteractiveBook> interactiveBooks;

    public BookAdapter(ArrayList<InteractiveBook> interactiveBooks) {
        this.interactiveBooks = interactiveBooks;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_holder_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        FragmentManager fragmentManager = ((AppCompatActivity)holder.context)
                .getSupportFragmentManager();
        InteractiveBook interactiveBook = interactiveBooks.get(position);
        holder.frameLayout.setId(View.generateViewId());
        fragmentManager.beginTransaction().replace(
                holder.frameLayout.getId(),
                BookThumbnailFragment.newInstance(interactiveBook))
                .commit();
    }

    @Override
    public int getItemCount() {
        return interactiveBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private FrameLayout frameLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            frameLayout = itemView.findViewById(R.id.fragment_holder_container);
        }
    }
}