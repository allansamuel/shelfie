package com.shelfie.ui.main.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.Category;
import com.shelfie.model.ChildProfile;
import com.shelfie.ui.fragments.CategoryBooksFragment;
import com.shelfie.ui.fragments.ProfileAvatarFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FragmentManager fragmentManager = ((AppCompatActivity)holder.context)
                .getSupportFragmentManager();

        Category category = categories.get(position);

        // Delete old fragment
//        int containerId = holder.mediaContainer.getId();// Get container id
//        Fragment oldFragment = fragmentManager.findFragmentById(containerId);
//        if(oldFragment != null) {
//            fragmentManager.beginTransaction().remove(oldFragment).commit();
//        }
//
//        int newContainerId = View.generateViewId();// Generate unique container id
//        holder.mediaContainer.setId(newContainerId);// Set container id
//
//        // Add new fragment
//        Fragment categoryBooksFragment = CategoryBooksFragment.newInstance(category);
//        fragmentManager.beginTransaction().replace(newContainerId, categoryBooksFragment).commit();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        FrameLayout mediaContainer;
        RecyclerView recyclerView;
        Context context;
        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            recyclerView = view.findViewById(R.id.rv_category_books);
//            mediaContainer = view.findViewById(R.id.fl_category_books_container);
        }
    }
}
