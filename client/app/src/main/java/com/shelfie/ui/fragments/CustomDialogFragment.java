package com.shelfie.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.shelfie.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    public static final int WARNING_DIALOG = 0;
    public static final int CONFIRMATION_DIALOG = 1;
    private String message;
    private int dialogType;
    private DialogInterface.OnClickListener positiveBtnListener;

    public void buildDialog(String message) {
        buildDialog(message, WARNING_DIALOG, null);
    }

    public void buildDialog(String message, int dialogType, DialogInterface.OnClickListener positiveBtnListener)  {
        this.message = message;
        this.dialogType = dialogType;
        this.positiveBtnListener = positiveBtnListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);

        if(dialogType == WARNING_DIALOG) {
            builder.setPositiveButton(R.string.ok, positiveBtnListener);
        } else if(dialogType == CONFIRMATION_DIALOG) {
            builder.setPositiveButton(R.string.confirm, positiveBtnListener);
            builder.setNegativeButton(R.string.cancel, null);
        }
        return builder.create();
    }
}
