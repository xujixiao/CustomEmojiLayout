# CustomEmojiLayout
### 库的使用方法
#### 在layout的布局中设置
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/ll_content"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.xjx.emoji.MainActivity">

    <com.xjx.emojilibrary.EmotionLayout
        android:id="@+id/emoji"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <TextView
        android:layout_width="wrap_content"
        android:id="@+id/tv_emoji"
        android:layout_height="wrap_content" />

    </LinearLayout>
#### 代码中使用
     EmotionLayout emojiLayout = (EmotionLayout) findViewById(R.id.emoji);
        emojiLayout.setOnItemClickListener(new EmotionLayout.ICallBackItem() {
            @Override
            public void onItemClick(String emojiText) {
                MainActivity.this.emoji.append(emojiText);
                mTextView.setText(emoji.toString());
            }
        });


