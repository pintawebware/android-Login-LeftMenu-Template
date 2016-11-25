package com.pinta.login_signup_gps.template.utils;

import android.content.Context;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class DialogUtils {

    private static Toast mToast;

    /**
     * Method to show Toast-message with given text, duration short.
     *
     * @param context context of the app.
     * @param message text need to be shown in Toast-message.
     */
    public static void show(Context context, String message) {
        if (message == null) {
            return;
        }
        if (mToast == null && context != null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (mToast != null) {
            mToast.setText(message);
            mToast.show();
        }
    }

    /**
     * Method to show input dialog.
     *
     * @param context                 context of the app.
     * @param title                   text in title, null - create dialog without title
     * @param positiveOnClickListener initialize listener to get choosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createInputDialog(Context context,
                                                           int title,
                                                           String content,
                                                           int inputType,
                                                           boolean cancelable,
                                                           CharSequence hint,
                                                           CharSequence prefill,
                                                           MaterialDialog.InputCallback positiveOnClickListener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(inputType)
                .cancelable(cancelable)
                .input(hint, prefill, positiveOnClickListener)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel);
    }

    /**
     * Method to show basic dialog.
     *
     * @param context                 context of the app.
     * @param title                   text in title, null - create dialog without title
     * @param positiveOnClickListener initialize listener to get choosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createBasicDialog(Context context,
                                                           int title,
                                                           String content,
                                                           boolean cancelable,
                                                           MaterialDialog.SingleButtonCallback positiveOnClickListener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .cancelable(cancelable)
                .onPositive(positiveOnClickListener)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no);
    }
}