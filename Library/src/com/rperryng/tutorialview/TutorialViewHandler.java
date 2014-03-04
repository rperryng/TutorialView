package com.rperryng.tutorialview;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.rperryng.tutorialview.TutorialView.OnTutorialDismissedListener;

public class TutorialViewHandler {
//    private static final String TAG = TutorialViewHandler.class.getSimpleName();

    List<TutorialView> tutorialViews;
    WeakReference<FragmentActivity> activity;
    WeakReference<ViewGroup> parent;

    public TutorialViewHandler(FragmentActivity activity) {
        this.activity = new WeakReference<FragmentActivity>(activity);
        parent = new WeakReference<ViewGroup>((ViewGroup) activity.getWindow().getDecorView());
        tutorialViews = new ArrayList<TutorialView>();
    }

    public void add(TutorialView tutorial) {
        tutorialViews.add(tutorial);
    }
    
    public void remove(TutorialView tutorial) {
        tutorialViews.remove(tutorial);
    }

    public void showAllInOrder() {

        for (int i = 0; i < tutorialViews.size(); i++) {
            final int current = i;
            final TutorialView tutorial = tutorialViews.get(i);

            if (i == 0) {
                tutorial.showTutorial(activity.get());
            } 
            
            if (i != tutorialViews.size() - 1) {
                tutorial.setOnTutorialDismissedListener(new OnTutorialDismissedListener() {
                    @Override
                    public void onTutorialDismissed(boolean forceBreak) {
                        FragmentActivity activity = TutorialViewHandler.this.activity.get();
                        if (!forceBreak && activity != null) {
                            tutorialViews.get(current + 1).showTutorial(activity);
                        }
                    }
                });
            }
        }
    }
    
    public void showAtIndex(int i) {
        FragmentActivity activity = TutorialViewHandler.this.activity.get();
        if (activity != null) {
            tutorialViews.get(i).showTutorial(activity);
        }
    }
}
