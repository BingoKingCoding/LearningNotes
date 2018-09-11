package com.great.adou.app.utils.select;

import android.view.View;

/**
 * <view的选择管理器>
 * Created by wwb on 2018/1/17 14:18.
 */

public class SelectViewManager<T extends View> extends SelectManager<T>
{
    private boolean mClickAble = true;

    @Override
    protected void initItem(int index, T item)
    {
        item.setOnClickListener(mOnClickListener);
        notifyNormal(index, item);
        super.initItem(index, item);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener()
    {

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View v)
        {
            if (mClickAble)
            {
                performClick((T) v);
            }
        }
    };

    public boolean isClickAble()
    {
        return mClickAble;
    }

    public void setClickAble(boolean clickAble)
    {
        mClickAble = clickAble;
    }

    @Override
    protected void notifyNormal(int index, T item)
    {
        item.setSelected(false);
        super.notifyNormal(index, item);
    }

    @Override
    protected void notifySelected(int index, T item)
    {
        item.setSelected(true);
        super.notifySelected(index, item);
    }
}
