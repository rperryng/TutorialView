# TutorialView

TutorialView is a simple library designed to help you create a simple JellyBean styled walkthrough for your Android app.  It uses Android's DialogFragment class to create a simple view to highlight the best, or perhaps less intuitive parts of your app.

## Setup

Download the project, import it into Eclipse.  Right click your project in the package explorer, hit properties, navigate to the Android tab, and import the TutorialView class in the Library pane.

If you haven't already, import android's [support v4 library](http://developer.android.com/tools/support-library/index.html), and make sure you're using a [FragmentActivity](http://developer.android.com/reference/android/support/v4/app/FragmentActivity.html) rather than an `Activity` (in whichever activities you'd like to display the tutorial of course).

## Usage


#### Single tutorial

Create a TutorialView by using the Tutorial View's Builder class.

```java
// Instantiate a new tutorial view builder
TutorialView.Builder someTutorial = new TutorialView.Builder(someFragmentActivity);

// Customize the tutorial
someTutorial.setTarget((View) someViewWithAnId).setTitle("This is a Title").setMessage(R.string.someStringId).setButtonText("OK").setColorTheme(Color.RED);

// Either create a TutorialView instance or call `showTutorial()` after calling `build()` on the builder instance

someTutorial.build().showTutorial(someFragmentActivity);
```

#### Queueing multiple tutorials

If you'd like multiple tutorials to show one after the other, make use of the `TutorialViewHandler` class as shown.

```java      
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
```

## Legal Stuff

```
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org>
```