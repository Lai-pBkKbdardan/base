package com.dream.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dream.base.R;

/**
 * Creater : WangKH
 * Date : 2016/6/7.
 * Desc :
 */
public class CommonTitleView extends LinearLayout {

    private String TAG = "CommonTitleView";

    private Context context;

    /**
     * 根布局
     */
    private LinearLayout rootView;

    /**
     * 左布局容器
     */
    private RelativeLayout leftContainer;

    /**
     * 右布局容器
     */
    private RelativeLayout rightContainer;

    /**
     * 左图标
     */
    private ImageView leftIcon;

    /**
     * 右图标
     */
    private ImageView rightIcon;

    /**
     * 左文字
     */
    private TextView leftText;

    /**
     * 右文字
     */
    private TextView rightText;

    /**
     * 标题
     */
    private TextView title;

    public CommonTitleView(Context context) {
        this(context, null);
    }

    public CommonTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.app_common_title, this, true);

        findViews();

        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleView);
        int titleRes = a.getResourceId(R.styleable.CommonTitleView_ct_title, 0);
        if (titleRes != 0) {
            title.setText(titleRes);
        }
        int titleWordColor = a.getColor(R.styleable.CommonTitleView_ct_title_color, 0);
        if (titleWordColor != 0) {
            title.setTextColor(titleWordColor);
        }

        int bgColor = a.getColor(R.styleable.CommonTitleView_ct_background, 0);
        if (bgColor != 0) {
            rootView.setBackgroundColor(bgColor);
        }

        int tvLeftColor = a.getColor(R.styleable.CommonTitleView_ct_left_color, 0);
        if (tvLeftColor != 0) {
            leftText.setTextColor(tvLeftColor);
        }

        int tvRightColor = a.getColor(R.styleable.CommonTitleView_ct_right_color, 0);
        if (tvRightColor != 0) {
            rightText.setTextColor(tvRightColor);
        }

        int leftTextRes = a.getResourceId(R.styleable.CommonTitleView_ct_left_text, 0);
        int leftIconRes = a.getResourceId(R.styleable.CommonTitleView_ct_left_icon, 0);
        if (leftIconRes == 0 && leftTextRes == 0) {
            leftContainer.setVisibility(View.INVISIBLE);
        } else if (leftTextRes != 0 && leftIconRes == 0) {
            leftContainer.setVisibility(View.VISIBLE);
            leftText.setVisibility(View.VISIBLE);
            leftText.setText(leftTextRes);
            leftIcon.setVisibility(View.INVISIBLE);
        } else if (leftTextRes == 0 && leftIconRes != 0) {
            leftContainer.setVisibility(View.VISIBLE);
            leftText.setVisibility(View.INVISIBLE);
            leftIcon.setImageResource(leftIconRes);
            leftIcon.setVisibility(View.VISIBLE);
        } else {
            //图片和文字都不为空  显示文字
            leftContainer.setVisibility(View.VISIBLE);
            leftText.setVisibility(View.VISIBLE);
            leftText.setText(leftTextRes);
            leftIcon.setVisibility(View.INVISIBLE);
        }

        int rightTextRes = a.getResourceId(R.styleable.CommonTitleView_ct_right_text, 0);
        int rightIconRes = a.getResourceId(R.styleable.CommonTitleView_ct_right_icon, 0);
        if (rightTextRes == 0 && rightIconRes == 0) {
            rightContainer.setVisibility(View.INVISIBLE);
        } else if (rightTextRes != 0 && rightIconRes == 0) {
            rightContainer.setVisibility(View.VISIBLE);
            rightText.setVisibility(View.VISIBLE);
            rightText.setText(rightTextRes);
            rightIcon.setVisibility(View.INVISIBLE);
        } else if (rightTextRes == 0 && rightIconRes != 0) {
            rightContainer.setVisibility(View.VISIBLE);
            rightText.setVisibility(View.INVISIBLE);
            rightIcon.setVisibility(View.VISIBLE);
            rightIcon.setImageResource(rightIconRes);
        } else {
            //图片和文字都不为空  显示文字
            rightContainer.setVisibility(View.VISIBLE);
            rightText.setVisibility(View.VISIBLE);
            rightText.setText(rightTextRes);
            rightIcon.setVisibility(View.INVISIBLE);
        }
    }

    private void findViews() {

        rootView = (LinearLayout) findViewById(R.id.root_view);
        leftContainer = (RelativeLayout) findViewById(R.id.ll_left_container);
        rightContainer = (RelativeLayout) findViewById(R.id.ll_right_container);
        leftIcon = (ImageView) findViewById(R.id.iv_left_icon);
        rightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        leftText = (TextView) findViewById(R.id.tv_left_text);
        rightText = (TextView) findViewById(R.id.tv_right_text);
        title = (TextView) findViewById(R.id.tv_title);

    }

    public void setTextColor(int colorRes) {
        title.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        if (leftText.getVisibility() == View.VISIBLE) {
            leftText.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        }
        if (rightText.getVisibility() == View.VISIBLE) {
            rightText.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        }
    }

    public void setTitle(String content) {
        title.setText(content);
    }

    public void setTitle(int resId) {
        title.setText(resId);
    }

    public void setLeftString(int stringRes) {
        setLeftString(stringRes, null);
    }

    public void setLeftIcon(int imgRes) {
        setLeftIcon(imgRes, null);
    }

    public void setLeftString(int stringRes, final OnTitleClickListener onLiftClick) {
        leftText.setText(stringRes);
        leftContainer.setVisibility(View.VISIBLE);
        setOnLiftClick(onLiftClick);
    }

    public void setLeftIcon(int imgRes, final OnTitleClickListener onLiftClick) {
        leftIcon.setImageResource(imgRes);
        leftContainer.setVisibility(View.VISIBLE);
        setOnLiftClick(onLiftClick);
    }

    public void setRightIcon(int imgRes) {
        setRightIcon(imgRes, null);
    }

    public void setRightIcon(int imgRes, final OnTitleClickListener onRightListener) {
        rightIcon.setImageResource(imgRes);
        rightContainer.setVisibility(View.VISIBLE);

        setOnRightClick(onRightListener);
    }

    public void setRightString(int stringRes) {
    }

    public void setRightString(int stringRes, final OnTitleClickListener onRightListener) {
        rightText.setText(stringRes);
        rightContainer.setVisibility(View.VISIBLE);

        setOnRightClick(onRightListener);
    }

    /**
     * 添加左点击事件
     *
     * @param onLiftClick
     */
    public void setOnLiftClick(final OnTitleClickListener onLiftClick) {

        if (onLiftClick != null) {
            leftContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLiftClick.onClick(view);
                }
            });
        }
    }

    public void setLeftGone() {
        leftContainer.setVisibility(View.INVISIBLE);
    }

    /**
     * 添加右点击事件
     *
     * @param onRightClick
     */
    public void setOnRightClick(final OnTitleClickListener onRightClick) {

        if (onRightClick != null) {
            rightContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRightClick.onClick(view);
                }
            });
        }
    }


    public interface OnTitleClickListener {
        public void onClick(View view);
    }

    public void reverseString() {
        StringBuilder builder = new StringBuilder("123456");
        StringBuilder reverse = builder.reverse();
        Log.i(TAG, "reverse  :  " + reverse.toString());
    }

}
