package com.shelfie.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.Quest;

public class QuestPreviewFragment extends Fragment {

    private static final String ARG_QUEST = "QUEST_DATA";

    private Quest quest;
    private ImageView imgQuestIcon;
    private TextView tvQuestTitle;
    private TextView tvQuestDescription;
    private TextView tvQuestReward;

    public QuestPreviewFragment() {
    }

    public static QuestPreviewFragment newInstance(Quest quest) {
        QuestPreviewFragment fragment = new QuestPreviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUEST, quest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quest = (Quest) getArguments().getSerializable(ARG_QUEST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        View view = getView();
        tvQuestTitle = view.findViewById(R.id.tv_quest_title);
        tvQuestDescription = view.findViewById(R.id.tv_quest_description);
        tvQuestReward = view.findViewById(R.id.tv_quest_reward);

        if(quest != null) {
            tvQuestTitle.setText(quest.getQuestTitle());
            tvQuestDescription.setText(quest.getQuestDescription());
            tvQuestReward.setText(String.valueOf(quest.getCoinsReward()));
        }

    }
}