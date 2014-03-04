
package com.rperryng.tutorialview;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author rperry
 *
 */
/**
 * @author rperry
 *
 */
public class TutorialView extends DialogFragment {

    private static final String packageName = TutorialView.class.getPackage().toString() + ".";

    private static final String TITLE = packageName + "TITLE";
    private static final String MESSAGE = packageName + "MESSAGE";
    private static final String BUTTON_TEXT = packageName + "BUTTON_TEXT";
    private static final String BACK_TOAST_TEXT = packageName + "BACK_TOAST_TEXT";
    private static final String DIMENSIONS = packageName + "DIMENSIONS";
    private static final String COORDINATES = packageName + "COORDINATES";
    private static final String RADIUS = packageName + "RADIUS";
    private static final String CIRCLE_MARGIN = packageName + "CIRCLE_MARGIN";
    private static final String TEXT_OFFSET = packageName + "TEXT_OFFSET";
    private static final String BUTTON_GRAVITY = packageName + "BUTTON_GRAVITY";
    private static final String ALPHA = packageName + "ALPHA";
    private static final String ACTIVITY_DIM_LEVEL = packageName + "ACTIVITY_DIM_LEVEL";
    private static final String COLOR_THEME = packageName + "COLOR_THEME";
    private static final String COLOR_THEME_FADED = packageName + "COLOR_THEME_FADED";
    private static final String SET_CIRCLE_CLICKABLE = packageName + "SET_CIRCLE_CLICKABLE";
    private static final String SHOW_DELAYED_MILLIS = packageName + "SHOW_DELAYED_MILLIS";

    private static final String TARGET_REFERENCE_ID = packageName + "TARGET_REFERENCE_ID";

    public static final String TAG = TutorialView.class.getSimpleName();

    public static class Builder {

        private ArrayList<OnTutorialDismissedListener> mListeners;
        private Bundle bundle;
        private final Resources resources;

        private static final int DEFAULT_ALPHA = 80;
        private static final int DEFAULT_BUTTON_GRAVITY = Gravity.BOTTOM | Gravity.RIGHT;

        public Builder(FragmentActivity activity) {
            bundle = new Bundle();
            resources = activity.getResources();

            bundle.putString(TITLE, resources.getString(R.string.tutview__default_title));
            bundle.putString(MESSAGE, resources.getString(R.string.tutview__default_message));
            bundle.putString(BUTTON_TEXT, resources.getString(R.string.tutview__OK));
            bundle.putString(BACK_TOAST_TEXT, resources.getString(R.string.tutview__toast_text));
            bundle.putInt(CIRCLE_MARGIN, resources.getDimensionPixelSize(R.dimen.tutview__circle_margin));
            bundle.putInt(BUTTON_GRAVITY, DEFAULT_BUTTON_GRAVITY);
            bundle.putInt(ALPHA, DEFAULT_ALPHA);
            bundle.putInt(ACTIVITY_DIM_LEVEL, 0);
            bundle.putInt(COLOR_THEME, resources.getColor(R.color.tutview__holo_blue));
            bundle.putInt(TEXT_OFFSET,
                    resources.getDimensionPixelSize(R.dimen.tutview__text_container_margin_offset));
        }

        public Builder setTitle(String title) {
            bundle.putString(TITLE, title);
            return this;
        }

        public Builder setTitle(int stringId) {
            bundle.putString(TITLE, resources.getString(stringId));
            return this;
        }

        public Builder setMessage(String message) {
            bundle.putString(MESSAGE, message);
            return this;
        }

        public Builder setMessage(int stringId) {
            bundle.putString(MESSAGE, resources.getString(stringId));
            return this;
        }

        public Builder setButtonText(String buttonText) {
            bundle.putString(BUTTON_TEXT, buttonText);
            return this;
        }

        public Builder setButtonText(int stringId) {
            bundle.putString(BUTTON_TEXT, resources.getString(stringId));
            return this;
        }

        /**
         * @param toastText The text to be displayed when the user hits the back
         *            button
         * @return
         */
        public Builder setBackToastText(String toastText) {
            bundle.putString(BACK_TOAST_TEXT, toastText);
            return this;
        }

        /**
         * @param toastText The text to be displayed when the user hits the back
         *            button
         * @return
         */
        public Builder setBackToastText(int stringId) {
            bundle.putString(BACK_TOAST_TEXT, resources.getString(stringId));
            return this;
        }

        /**
         * @param alpha The alpha value (0 - 255) to set the faded portion of
         *            the tutorial
         * @return
         */
        public Builder setAlpha(int alpha) {
            bundle.putInt(ALPHA, alpha);
            return this;
        }

        /**
         * @param gravity The gravity value for the button, in case it is
         *            overlapping your desired target
         * @return
         */
        public Builder setButtonGravity(int gravity) {
            bundle.putInt(BUTTON_GRAVITY, gravity);
            return this;
        }

        /**
         * @param dimLevel Dim the background activity by an alpha value (0 -
         *            255). Makes the overlay easier to see. Especially helpful
         *            if your activity is composed of mainly white/light
         *            colours.
         * @return
         */
        public Builder setActivityDim(int dimLevel) {
            bundle.putInt(ACTIVITY_DIM_LEVEL, dimLevel);
            return this;
        }

        /**
         * @param colorTheme Set the colour theme for the overlay. The button
         *            will remain coloured to default. To override button
         *            colours, hardcode the colours in
         *            TutorialView/res/drawable/btn_real_*.xml
         * @return
         */
        public Builder setColorTheme(int colorTheme) {
            bundle.putInt(COLOR_THEME, colorTheme);
            return this;
        }

        /**
         * @param colorThemeFaded Sets the colour theme for the faded portion of
         *            the gradient. There is no need to call this unless you
         *            explicitly want the colour theme of the faded portion to
         *            be completely different than that set in setColourTheme();
         * @return
         */
        public Builder setColorThemeFaded(int colorThemeFaded) {
            bundle.putInt(COLOR_THEME_FADED,
                    Utils.applyAlpha(bundle.getInt(ALPHA), colorThemeFaded));
            return this;
        }

        /**
         * @param target The view for the tutorial to show.  this view must have a valid reference ID
         * @return
         */
        public Builder setTarget(View target) {
            if (target.getId() == View.NO_ID) {
                throw new IllegalArgumentException("Must send view with a valid reference ID");
            }
            bundle.putInt(TARGET_REFERENCE_ID, target.getId());
            return this;
        }

        /**
         * @param targetId the Referene ID for the view that you would like to target
         * @return
         */
        public Builder setTarget(int targetId) {
            bundle.putInt(TARGET_REFERENCE_ID, targetId);
            return this;
        }

        // not implemented yet
        public Builder setClickable(boolean clickable) {
            bundle.putBoolean(SET_CIRCLE_CLICKABLE, clickable);
            return this;
        }

        /**
         * @param millis Set the delay before this tutorial should reveal itself
         * @return
         */
        public Builder setDelay(long millis) {
            bundle.putLong(SHOW_DELAYED_MILLIS, millis);
            return this;
        }

        /**
         * @param listener Set a listener for the Tutorial to call when it is dismissed or cancelled
         * @return
         */
        public Builder setOnTutorialDismissedListener(OnTutorialDismissedListener listener) {
            if (mListeners == null) {
                mListeners = new ArrayList<OnTutorialDismissedListener>();
            }
            mListeners.add(listener);
            return this;
        }

        /**
         * @return The new TutorialView object
         */
        public TutorialView build() {

            if (!bundle.containsKey(COLOR_THEME_FADED)) {
                bundle.putInt(
                        COLOR_THEME_FADED, Utils.applyAlpha(bundle.getInt(ALPHA),
                                bundle.getInt(COLOR_THEME)));
            }

            TutorialView frag = new TutorialView();
            frag.setArguments(bundle);

            if (mListeners != null) {
                frag.mListeners = this.mListeners;
            }
            return frag;
        }
    }

    private static final int BACK_BUTTON_RESET_DELAY = 1000 * 2; /* 2 seconds */

    private boolean doubleBackToExit;
    private boolean dismissed;

    private FrameLayout parentView;

    private TextView textViewTitle;
    private TextView textViewMessage;
    private Button buttonDismiss;

    private ArrayList<OnTutorialDismissedListener> mListeners;
    
    public interface OnTutorialDismissedListener {
        public void onTutorialDismissed(boolean backExited);
    }

    public void setOnTutorialDismissedListener(OnTutorialDismissedListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<OnTutorialDismissedListener>();
        }
        mListeners.add(listener);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        setupContentView();

        setupDialog(dialog);
        // setupClick(dialog.getWindow().getDecorView());

        textViewTitle = (TextView) parentView.findViewById(R.id.tutorial_title);
        textViewMessage = (TextView) parentView.findViewById(R.id.tutorial_message);
        buttonDismiss = (Button) parentView.findViewById(R.id.tutorial_button);
        buttonDismiss.invalidate();

        setupText();
        setupButton(dialog);

        return dialog;
    }

    private void setupContentView() {
        Bundle bundle = getArguments();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        parentView = (FrameLayout) inflater.inflate(R.layout.tutview__container, null, false);

        DrawingView drawingView = new DrawingView(getActivity(), bundle.getIntArray(COORDINATES),
                bundle.getInt(COLOR_THEME), bundle.getInt(COLOR_THEME_FADED),
                bundle.getInt(ACTIVITY_DIM_LEVEL), bundle.getInt(RADIUS));

        parentView.addView(drawingView);
        parentView.addView(inflater.inflate(R.layout.tutview__ui_items, null, false));
    }

    private void setupDialog(Dialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(parentView);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        dialog.takeKeyEvents(true);
        dialog.setOnKeyListener(onKeyListener);
        dialog.setCancelable(false);
    }

    private void setupButton(Dialog dialog) {
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorialView.this.dismiss();
            }
        });

        ((FrameLayout.LayoutParams) buttonDismiss.getLayoutParams()).gravity = getArguments()
                .getInt(BUTTON_GRAVITY);
    }

    /**
     * Positions the text accordingly dependant on where and how big the target is
     */
    private void setupText() {
        Bundle bundle = getArguments();
        int[] coordinates = bundle.getIntArray(COORDINATES);
        int[] dimensions = bundle.getIntArray(DIMENSIONS);
        int textViewOffset = bundle.getInt(TEXT_OFFSET);

        textViewTitle.setText(bundle.getString(TITLE));
        textViewMessage.setText(bundle.getString(MESSAGE));
        buttonDismiss.setText(bundle.getString(BUTTON_TEXT));

        textViewTitle.setTextColor(bundle.getInt(COLOR_THEME));

        RelativeLayout textContainer = (RelativeLayout) parentView
                .findViewById(R.id.tutorial_text_container);
        FrameLayout.LayoutParams containerParams = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        containerParams.width = LayoutParams.MATCH_PARENT;
        containerParams.height = LayoutParams.WRAP_CONTENT;
        containerParams.leftMargin = (int) getActivity().getResources().getDimensionPixelSize(
                R.dimen.tutview__activity_margin);

        if (coordinates[1] < (dimensions[1] / 2)) {
            containerParams.gravity = Gravity.TOP;
            containerParams.topMargin = coordinates[1] + bundle.getInt(RADIUS) + textViewOffset;
        } else {
            containerParams.gravity = Gravity.BOTTOM;
            containerParams.bottomMargin = (dimensions[1] - coordinates[1]) + bundle.getInt(RADIUS)
                    + textViewOffset;
        }
        textContainer.setLayoutParams(containerParams);
    }

    private OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP
                    && !event.isCanceled()) {

                if (doubleBackToExit) {
                    onHide(true);
                    TutorialView.this.dismiss();
                    return true;
                } else {
                    doubleBackToExit = true;
                }

                Toast.makeText(getActivity(), getArguments().getString(BACK_TOAST_TEXT),
                        Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExit = false;
                    }
                }, BACK_BUTTON_RESET_DELAY);
            }
            return true;
        }
    };

    /**
     * Call this method when you want the tutorial to be shown.  Do not call the other inherited show() methods from Fragment
     * 
     * @param activity The FragmentActivity for this tutorial to be displayed in
     */
    public void showTutorial(FragmentActivity activity) {
        Bundle bundle = getArguments();
        if (bundle.containsKey(SHOW_DELAYED_MILLIS)) {
            this.showTutorialDelayed(activity, bundle.getLong(SHOW_DELAYED_MILLIS));
        } else {
            this.showTutorialReal(activity);
        }
    }

    private void showTutorialReal(final FragmentActivity activity) {
        activity.getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getArguments();
                if (bundle.containsKey(TARGET_REFERENCE_ID)) {
                    grabDimensions(activity);
                }
                TutorialView.this.show(activity.getSupportFragmentManager(), "some_tag");
            }
        });
    }

    private void showTutorialDelayed(final FragmentActivity activity, long delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TutorialView.this.showTutorialReal(activity);
            }
        }, delayMillis);
    }

    private void grabDimensions(final FragmentActivity activity) {
        Bundle bundle = getArguments();

        View view = activity.findViewById(bundle.getInt(TARGET_REFERENCE_ID));
        int[] coordinates = new int[2];
        int[] dimensions = new int[2];

        view.getLocationOnScreen(coordinates);

        dimensions[0] = view.getRootView().getWidth();
        dimensions[1] = view.getRootView().getHeight();

        coordinates[0] = coordinates[0] + ((view.getRight() - view.getLeft()) / 2);
        // coordinates[1] = coordinates[1] + ((view.getBottom() -
        // view.getTop()) / 2);

        if (!bundle.containsKey(RADIUS)) {
            int radius;
            radius = (view.getRight() - view.getLeft());
            if ((view.getBottom() - view.getTop()) > radius) {
                radius = (view.getBottom() - view.getTop());
            }
            radius = (radius / 2) + bundle.getInt(CIRCLE_MARGIN);
            bundle.putInt(RADIUS, radius);
        }

        bundle.putIntArray(DIMENSIONS, dimensions);
        bundle.putIntArray(COORDINATES, coordinates);
    }

    private void onHide(boolean backExited) {
        if (mListeners != null && !dismissed) {
            dismissed = true;
            for (OnTutorialDismissedListener listener : mListeners) {
                listener.onTutorialDismissed(backExited);
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        onHide(false);
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        onHide(false);
        super.onCancel(dialog);
    }
}
