package pvtitov.anothertranslator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Павел on 04.02.2018.
 */

public class DeleteDialog extends DialogFragment {

    public static final String DELETE_TAG = "delete_tag";

    public interface DeleteDialogListener {
        void onClickPositive(DialogFragment dialogFragment, String word);
    }

    DeleteDialogListener mListener;

    private String mWord;

    void setWord(String word){
        mWord = word;
    }

    // Получим ссылку на DeleteDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DeleteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " - must implement DeleteDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_dialog)
                .setMessage(mWord)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    mListener.onClickPositive(DeleteDialog.this, mWord);
                })
                .setNegativeButton(R.string.cancel, null);

        return builder.create();
    }
}
