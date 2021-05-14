package com.shelfie.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shelfie.R;
import com.shelfie.model.Category;
import com.shelfie.ui.fragments.CategoryBooksFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryBooksAdapter extends RecyclerView.Adapter<CategoryBooksAdapter.ViewHolder> {

    private ArrayList<Category> categories;

    public CategoryBooksAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_holder_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FragmentManager fragmentManager = ((AppCompatActivity)holder.context)
                .getSupportFragmentManager();
        Category category = categories.get(position);
        holder.frameLayout.setId(View.generateViewId());
        holder.frameLayout.setLayoutParams(
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT)
        );
        fragmentManager.beginTransaction().add(
                holder.frameLayout.getId(),
                CategoryBooksFragment.newInstance(category))
                .commit();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        FrameLayout frameLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            frameLayout = itemView.findViewById(R.id.fragment_holder_container);
        }
    }
}
