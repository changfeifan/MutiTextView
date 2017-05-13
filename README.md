
## MutiTextView for Android
this is a Textview for solve the problem which is the normal textView can not enter two styles words.

So I write this one.
## Examples
![MacDown logo](https://github.com/changfeifan/MutiTextView/blob/master/demoPng.png)

## Download

Gradle:

First write at the **build.gradle** (Project).


```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

then write at the **build.gradle** (app or which projcect you want to use).

```
dependencies {
	compile 'com.github.changfeifan:MutiTextView:1.0.0'
}

```
## How to use

write this in your layout.

```
<com.changfeifan.mutitextview.MutiTextView
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     app:leftText="DemoLeft"
     app:leftTextColor="#000000"
     app:leftTextSize="12dp"
     app:rightText="DemoRight"
     app:rightTextColor="#00FF00"
     app:rightTextSize="30dp" />
```


## Contact

**wechat:** changfeifan002

**Email:** 283410998@qq.com  /	 Changfeifan1993@gmail.com

