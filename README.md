# MultiImageSelector
Image selector for Android which supports single and multiple choices.
This library developed by [lovetuzitong](https://github.com/lovetuzitong) originally but in Chinese language and poor English. I continue the development to improve this great library.

## Screenshots
![Example1](art/example_1.png) ![Select1](art/select_1.png) ![Select2](art/select_2.png) ![Select3](art/select_3.png)

## Features
* Multiple and single choices
* Take photo
* Limit selecting photos

## Quick Start
* Add module `multi-image-selector` as your dependence.
* Declare permission `android.permission.READ_EXTERNAL_STORAGE` in your `AndroidManifest.xml`.
* Declare `MultiImageSelectorActivity` in your `AndroidManifest.xml`.

```xml
<activity
    android:configChanges="orientation|screenSize"
    android:name="me.nereo.multi_image_selector.MultiImageSelectorActivity" />
```

* Call image selector activity in your code, eg.
```java
Intent intent = new Intent(context, MultiImageSelectorActivity.class);

// Whether show camera
intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

// Max select image amount
intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);

// Select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);

// Whether log (true by default)
intent.putExtra(MultiImageSelectorActivity.EXTRA_LOG, false);

startActivityForResult(intent, REQUEST_IMAGE);
```

* Receive result in your `onActivityResult` method. eg.
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == REQUEST_IMAGE){
        if(resultCode == RESULT_OK){
	          // Get the list of selected images paths
            List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            // Do your logic...
        }
        else {
          // Result has canceled
        }
    }
}
```

## Extend MultiImageSelectorActivity
```java
public class MySelector extends MultiImageSelectorActivity {
    @Override
    public void onCameraShot(File imageFile) {
        super.onCameraShot(imageFile);
    }

    @Override
    public void onImageSelected(String path) {
        super.onImageSelected(path);
    }

    @Override
    public void onImageUnselected(String path) {
        super.onImageUnselected(path);
    }

    @Override
    public void onSingleImageSelected(String path) {
        super.onSingleImageSelected(path);
    }
}
```

*Note:* When you extend `MultiImageSelectorActivity` you must declare your activity in the `AndroidManifest.xml` and step 3 of quick start can be ignored.

## Changelog
* September 23, 2015
  1. Improve the sample application

* September 22, 2015
  1. Whether log or not
  2. Minor improvements

* May 5, 2015
  1. Fixed: Can't display some images. (Reported by [sd6352051](https://github.com/sd6352051), [larry](https://github.com/18611480882))
  2. Fixed: `ListPopupWindow` can not fill parent
  3. Added: Add checked mask.

* April 9, 2015
  1. Fixed: When set `EXTRA_SHOW_CAMERA` to `true`, the first grid item `onclick` event were messed.
  2. Added: Support initial selected image list.

* April 4, 2015
  1. Fixed: Crack when rotate device. (Reported by [@Leminity](https://github.com/Leminity))
  2. Fixed: `PopupListView` position error. (Reported by [@Slock](https://github.com/Slock))
  3. Changed: Demo application shortcut.

## Thanks
* [square/picasso](https://github.com/square/picasso) A powerful image downloading and caching library for Android

## License
>The MIT License (MIT)

>Copyright (c) 2015 Nereo

>Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

>The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
