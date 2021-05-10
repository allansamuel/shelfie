package com.shelfie.ui.main.home;

import android.app.Activity;
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
    private Activity activity;

    public CategoryBooksAdapter(Activity activity, ArrayList<Category> categories) {
        this.categories = categories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_books_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FragmentManager fragmentManager = ((AppCompatActivity)holder.context)
                .getSupportFragmentManager();
        Category category = categories.get(position);
        holder.frameLayout.setId(View.generateViewId());
        fragmentManager.beginTransaction().replace(
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
        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            frameLayout = view.findViewById(R.id.fl_category_books_container);
        }
    }
}
