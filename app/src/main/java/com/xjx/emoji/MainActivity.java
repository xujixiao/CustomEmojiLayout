package com.xjx.emoji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xjx.emojilibrary.EmotionLayout;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    public StringBuffer emoji=new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_emoji);
        EmotionLayout emojiLayout = (EmotionLayout) findViewById(R.id.emoji);
        emojiLayout.setOnItemClickListener(new EmotionLayout.ICallBackItem() {
            @Override
            public void onItemClick(String emojiText) {
                MainActivity.this.emoji.append(emojiText);
                mTextView.setText(emoji.toString());
            }
        });
    }
}
