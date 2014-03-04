
package com.rperryng.sampletesting;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rperryng.tutorialview.TutorialView;
import com.rperryng.tutorialview.TutorialView.OnTutorialDismissedListener;
import com.rperryng.tutorialview.TutorialViewHandler;

public class MainActivity extends FragmentActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        
        button3.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        

//        TutorialView.Builder builder1 = new TutorialView.Builder(MainActivity.this);
//        builder1.setTarget((View) button1).setColorTheme(Color.RED).setClickable(true)
//                .setBackToastText(R.string.default_toast_text).build()
//                .showTutorial(MainActivity.this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TutorialViewHandler tutorialHandler = new TutorialViewHandler(MainActivity.this);
                
                TutorialView.Builder tutorialOne = new TutorialView.Builder(MainActivity.this);
                tutorialOne.setActivityDim(150)
                        .setTarget((View) button1)
                        .setTitle("hey")
                        .setMessage("message")
                        .setBackToastText("toastTest")
                        .setOnTutorialDismissedListener(new OnTutorialDismissedListener() {
                            @Override
                            public void onTutorialDismissed(boolean backExited) {
                                Toast.makeText(MainActivity.this, "Toast", Toast.LENGTH_SHORT).show();
                            }
                        });
                tutorialHandler.add(tutorialOne.build());
                
                TutorialView.Builder tutorialTwo = new TutorialView.Builder(MainActivity.this);
                tutorialTwo.setActivityDim(150)
                        .setTarget((View) button2)
                        .setDelay(1000)
                        .setTitle(R.string.tutview__default_title)
                        .setMessage(R.string.tutview__default_message)
                        .setBackToastText(R.string.tutview__toast_text)
                        .setButtonGravity(Gravity.BOTTOM | Gravity.LEFT)
                        .setOnTutorialDismissedListener(new OnTutorialDismissedListener() {
                            @Override
                            public void onTutorialDismissed(boolean backExited) {
                                Toast.makeText(MainActivity.this, R.string.tutview__toast_text, Toast.LENGTH_LONG).show();
                            }
                        });
                tutorialHandler.add(tutorialTwo.build());
                
                tutorialHandler.showAllInOrder();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorialView.Builder builder1 = new TutorialView.Builder(MainActivity.this);
                builder1.setTarget((View) button1).setClickable(true).setActivityDim(25)
                        .setColorTheme(Color.YELLOW).setClickable(true)
                        .setActivityDim(150)
                        .setBackToastText(R.string.tutview__toast_text).build()
                        .showTutorial(MainActivity.this);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorialViewHandler handler = new TutorialViewHandler(MainActivity.this);

                TutorialView.Builder builder1 = new TutorialView.Builder(MainActivity.this);
                builder1.setTarget((View) button1).setColorTheme(Color.RED).setClickable(true).setActivityDim(150);
                handler.add(builder1.build());

                TutorialView.Builder builder2 = new TutorialView.Builder(MainActivity.this);
                builder2.setTarget((View) button2).setActivityDim(150).setColorTheme(Color.MAGENTA).setTitle("Some title")
                        .setMessage("lorem ipsum I don't remmeber the rest")
                        .setButtonGravity(Gravity.BOTTOM | Gravity.LEFT);
                TutorialView build2 = builder2.build();
                build2.setOnTutorialDismissedListener(new OnTutorialDismissedListener() {
                    @Override
                    public void onTutorialDismissed(boolean backExited) {
                        Toast.makeText(MainActivity.this, "Success! New Record!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                handler.add(build2);

                TutorialView.Builder builder3 = new TutorialView.Builder(MainActivity.this);
                builder3.setTarget((View) button3).setActivityDim(150).setColorTheme(Color.GREEN);
                handler.add(builder3.build());

                TutorialView.Builder builder4 = new TutorialView.Builder(MainActivity.this);
                builder4.setTarget((View) button1).setTitle("This is a repeat")
                        .setMessage("Analyzing number one again");
                TutorialView build4 = builder4.build();
                build4.setOnTutorialDismissedListener(new OnTutorialDismissedListener() {
                    @Override
                    public void onTutorialDismissed(boolean backExited) {
                        Toast.makeText(MainActivity.this, "Reenable this tutorial in settings",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                handler.add(build4);

                handler.showAllInOrder();
                
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
