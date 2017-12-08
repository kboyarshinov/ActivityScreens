# [DEPRECATED] ActivityScreens

## Deprecation note

Library was never finished and released and there are few alternatives exist: [Dart & Henson](https://github.com/f2prateek/dart) or [IntentBuilder](https://github.com/emilsjolander/IntentBuilder).

## Description

Android library that simplifies passing arguments between activities using generated classes aka `Screens`.
Read the motivation about it in [blog post](http://kboyarshinov.com/android/my-journey-in-developing-android-library/).

Example:

```java
@ActivityScreen
public class SampleActivity extends Activity {

    @ActivityArg
    long id;
    @ActivityArg
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // read fields annotated with @ActivityArg
        SampleActivityScreen.inject(this);
    }
}
```

This example generates helper class `SampleActivityScreen`:

```java
public final class SampleActivityScreen {
    public final long id;

    public final String title;

    public SampleActivityScreen(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void open(Activity activity) {
        Intent intent = toIntent(activity);
        activity.startActivity(intent);
    }

    public void openForResult(Activity activity, int requestCode) {
        Intent intent = toIntent(activity);
        activity.startActivityForResult(intent, requestCode);
    }

    public Intent toIntent(Activity activity) {
        Intent intent = new Intent(activity, SampleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        return intent;
    }

    public static void inject(SampleActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("SampleActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        checkArguments(bundle);
        activity.id = bundle.getLong("id");
        activity.title = bundle.getString("title");
    }

    private static void checkArguments(Bundle bundle) {
        if (!bundle.containsKey("id")) {
            throw new IllegalStateException("Required argument id with key 'id' is not set");
        }
        if (!bundle.containsKey("title")) {
            throw new IllegalStateException("Required argument title with key 'title' is not set");
        }
    }
}
```

And it can be easily used:

```java
    new SampleActivityScreen(3, "Sample title").open(activity);
```

## LICENSE

Copyright 2015 Kirill Boyarshinov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
