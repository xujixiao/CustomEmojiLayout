package com.xjx.emojilibrary;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.xjx.emojilibrary.adapter.EmotionGridViewAdapter;
import com.xjx.emojilibrary.adapter.EmotionPagerAdapter;
import com.xjx.emojilibrary.utils.DisplayUtils;
import com.xjx.emojilibrary.view.EmotionIndicatorView;
import com.xjx.emojilibrary.utils.EmojiParseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zejian
 * Time  16/1/5 下午4:32
 * Email shinezejian@163.com
 * Description:可替换的模板表情，gridview实现
 */
public class EmotionLayout extends LinearLayout {
    private EmotionPagerAdapter mEmotionPagerAdapter;
    private ViewPager mViewPager;
    private EmotionIndicatorView mEmojiIndicatorView;//表情面板对应的点列表
    private ICallBackItem mOnItemClickListener;

    public interface ICallBackItem {
        void onItemClick(String emojiText);
    }

    public void setOnItemClickListener(ICallBackItem itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    /**
     * 设置监听事件
     *
     * @param itemClick
     */
    public void setItemClick(ICallBackItem itemClick) {
        this.mOnItemClickListener = itemClick;
    }

    public EmotionLayout(Context context) {
        super(context);
        init();
    }

    public EmotionLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public EmotionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.fragment_complate_emotion, null);
        initView(rootView);
        initListener();
        addView(rootView);
    }


    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        mViewPager = (ViewPager) rootView.findViewById(R.id.vp_complate_emotion_layout);
        mEmojiIndicatorView = (EmotionIndicatorView) rootView.findViewById(R.id.ll_point_group);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> emojiList = EmojiParseUtils.parseEmoji(getContext());
                if (emojiList != null && emojiList.size() > 0) {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initEmotion(emojiList);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mEmojiIndicatorView.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化表情面板
     * 思路：获取表情的总数，按每行存放7个表情，动态计算出每个表情所占的宽度大小（包含间距），
     * 而每个表情的高与宽应该是相等的，这里我们约定只存放3行
     * 每个面板最多存放7*3=21个表情，再减去一个删除键，即每个面板包含20个表情
     * 根据表情总数，循环创建多个容量为20的List，存放表情，对于大小不满20进行特殊
     * 处理即可。
     *
     * @param emojiList
     */
    private void initEmotion(List<String> emojiList) {
        // 获取屏幕宽度
        int screenWidth = DisplayUtils.getScreenWidthPixels((Activity) getContext());
        // item的间距
        int spacing = DisplayUtils.dp2px(getContext(), 12);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 8) / 7;
        //动态计算gridview的总高度
        int gvHeight = itemWidth * 3 + spacing * 3;

        List<GridView> emotionViews = new ArrayList<>();
        List<String> emotionNames = new ArrayList<>();
        // 遍历所有的表情的key
        for (String emojiName : emojiList) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<>();
            }
        }

        // 判断最后是否有不足20个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
            emotionViews.add(gv);
        }

        //初始化指示器
        mEmojiIndicatorView.initIndicator(emotionViews.size());
        // 将多个GridView添加显示到ViewPager中
        mEmotionPagerAdapter = new EmotionPagerAdapter(emotionViews);
        mViewPager.setAdapter(mEmotionPagerAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        mViewPager.setLayoutParams(params);
    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gridView = new GridView(getContext());
        //设置点击背景透明
        gridView.setSelector(android.R.color.white);
        //设置7列
        gridView.setNumColumns(8);
        gridView.setPadding(padding, padding, padding, padding);
        gridView.setHorizontalSpacing(padding);
        gridView.setVerticalSpacing(padding);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gridView.setLayoutParams(params);
        // 给GridView设置表情图片
        final EmotionGridViewAdapter adapter = new EmotionGridViewAdapter(getContext(), emotionNames, itemWidth);
        gridView.setAdapter(adapter);
        //设置全局点击事件
        if (mOnItemClickListener != null) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String emoji = adapter.getItem(i).toString();
                    mOnItemClickListener.onItemClick(emoji);
                }
            });
        }
        return gridView;
    }


}
