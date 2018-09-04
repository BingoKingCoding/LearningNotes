package com.great.adou.app.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bingo.lib.dialog.impl.DialogBase;
import com.great.adou.R;

/**
 * <加载框>
 * Created by WangWB on 2018/09/04  16:34.
 */
public class ProgressDialog extends DialogBase {

    public ProgressDialog(Activity activity) {
        super(activity);
        init();
    }

    private TextView tv_content;
    private ProgressWheel progress_wheel;

    private void init() {
        setContentView(R.layout.dialog_progress);
        tv_content = findViewById(R.id.tv_content);
        progress_wheel = findViewById(R.id.progress_wheel);
    }

    public ProgressDialog setTextContent(String text) {
        if (TextUtils.isEmpty(text)) {
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText(text);
        }
        return this;
    }

}
