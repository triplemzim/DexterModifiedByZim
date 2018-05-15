DexterModifiedByZim [![Build Status](https://travis-ci.org/Karumi/Dexter.svg?branch=master)](https://travis-ci.org/Karumi/Dexter) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.karumi/dexter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.karumi/dexter) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Dexter-green.svg?style=true)](https://android-arsenal.com/details/1/2804)
======


DexterModifiedByZim is an Android library that simplifies the process of requesting permissions at runtime and some modification like showing a hint dialog before taking permission allowing the user the need of taking permission.

Android Marshmallow includes a new functionality to let users grant or deny permissions when running an app instead of granting them all when installing it. This approach gives the user more control over applications but requires developers to add lots of code to support it.

The official API is heavily coupled with the ``Activity`` class.
DexterModifiedByZim frees your permission code from your activities and lets you write that logic anywhere you want.


Screenshots
-----------

![Demo screenshot][1]


Usage
-----

### Dependency

Include this to your root build.gradle at the end of repositories

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```


Include the library in your ``build.gradle``

```groovy
dependencies{
    implementation 'com.github.triplemzim:DexterModifiedByZim:1.0'
}
```

Please visit the this link [Dexter](https://github.com/Karumi/Dexter) for basic usage. Here is the modification made to that library

To start using the library you just need to call `Dexter` with a valid `Activity`:

```java
public MyActivity extends Activity {
	@Override public void onCreate() {
		super.onCreate();
		Dexter.withActivity(activity)
			.withPermission(permission)
			.withListener(listener)
			.check();
	}
}
```

### Alert Dialog 
For showing an Alert Dialog before showing the permission taking pop up:

```java
Dexter.withActivity(this)
	.withPermission(Manifest.permission.CAMERA)
	.withListener(permissionListener)
	.withAlertDialog("Title", "Message", this)
	.check();
```

![Demo screenshot][6]


### FullScreen Dialog 
For showing an FullScreen Dialog with default background before showing the permission taking pop up:

```java
Dexter.withActivity(this)
	.withPermission(Manifest.permission.CAMERA)
	.withListener(permissionListener)
	.withFullScreenDialog("Title", "Message", this)
	.check();
```

![Demo screenshot][7]


### FullScreen Dialog with Background image
Pass the image resource id for showing an FullScreen Dialog with custom background before showing the permission taking pop up:

```java
Dexter.withActivity(this)
	.withPermission(Manifest.permission.CAMERA)
	.withListener(permissionListener)
	.withFullScreenDialog("Title", "Message", this)
	.withFullScreenDialogBackground(R.drawable.background)
	.check();
```

or pass the colors for text, button backgroun and button text if you want:

```java
Dexter.withActivity(this)
	.withPermission(Manifest.permission.CAMERA)
	.withListener(permissionListener)
	.withFullScreenDialog("Title", "Message", this)
	.withFullScreenDialogBackground(R.drawable.background,Color.GREEN, Color.LTGRAY, Color.BLACK)
	.check();
```

![Demo screenshot][8]

### FullScreen Dialog with custom layout

Pass the layout id if you want to show custom layout before taking permission. 
**IMPORTANT** You **must** have atleast one Textview with id-> **tvTitle**, one Textview with id-> **tvMessage** and a Button with id-> **btOK**
After pressing the Ok button the permission request will be sent to system.

```java
Dexter.withActivity(this)
	.withPermission(Manifest.permission.CAMERA)
	.withListener(permissionListener)
    .withFullScreenDialogAndLayout("Title", "Message", activity, R.layout.custom_background)
    .check();
```


Sample Layout Code:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/background2"
    android:gravity="center"
    >

    <ImageView
        android:id="@+id/backgroundImage"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:adjustViewBounds="true"
        android:scaleType="centerCrop" />

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#80000000" />


    <TextView
        android:id="@+id/tvTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="64dp"
        android:layout_marginTop="64dp"
        android:fontFamily="sans-serif-light"
        android:layout_alignParentTop="true"
        android:gravity="center"
        android:paddingBottom="8dp"
        android:text="We need your permission"
        android:textColor="#ffffff"
        android:textSize="18sp"
        android:textStyle="bold" />


    <Button
        android:id="@+id/btOK"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_gravity="bottom"
        android:layout_margin="16dp"
        android:fontFamily="sans-serif-light"
        android:gravity="center"
        android:padding="16dp"
        android:text="@android:string/ok"
        android:textColor="#333333"
        android:textSize="16sp" />

    <TextView
        android:id="@+id/tvMessage"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@id/btOK"
        android:layout_centerInParent="true"
        android:layout_marginBottom="32dp"
        android:fontFamily="sans-serif-light"
        android:gravity="center"
        android:paddingLeft="16dp"
        android:paddingRight="16dp"
        android:text="Hi Zim! We need your humble permission to run this feature in our very beautiul app. :)"
        android:textColor="#ffffff"
        android:textSize="14sp" />
</RelativeLayout>
```





**IMPORTANT**: Remember to follow the [Google design guidelines][2] to make your application as user-friendly as possible.


Caveats
-------
* For permissions that did not exist before API Level 16, you should check the OS version and use *ContextCompat.checkSelfPermission*. See [You Cannot Hold Non-Existent Permissions](https://commonsware.com/blog/2015/11/09/you-cannot-hold-nonexistent-permissions.html).
* Don't call Dexter from an Activity with the flag `noHistory` enabled. When a permission is requested, Dexter creates its own Activity internally and pushes it into the stack causing the original Activity to be dismissed.
* Permissions `SYSTEM_ALERT_WINDOW` and `WRITE_SETTINGS` are considered special by Android. Dexter doesn't handle those and you'll need to request them in the old fashioned way.

Do you want to contribute?
--------------------------

Feel free to add any useful feature to the library, we will be glad to improve it with your help.

Keep in mind that your PRs **must** be validated by Travis-CI. Please, run a local build with ``./gradlew checkstyle build test`` before submiting your code.


Libraries used in this project
------------------------------

* [Butterknife][3]
* [JUnit][4]
* [Mockito][5]

License
-------

    Copyright 2015 Karumi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: ./art/sample.gif
[2]: http://www.google.es/design/spec/patterns/permissions.html
[3]: https://github.com/JakeWharton/butterknife
[4]: https://github.com/junit-team/junit
[5]: https://github.com/mockito/mockito
[6]: ./art/AlertDialog.gif
[7]: ./art/DefaultFullscreenDialog.gif
[8]: ./art/DialogwithBackground.gif
