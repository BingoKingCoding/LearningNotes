package com.great.adou.module.main.home;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.great.adou.R;
import com.great.adou.app.net.bean.HomeListBean;
import com.great.adou.app.utils.GlideUtils;

import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/13  16:05.
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeAdapter(List<HomeListBean> data) {
        super(data);
        addItemType(HomeListBean.TITLE, R.layout.item_home_title);
        addItemType(HomeListBean.DAILY, R.layout.item_home_daily);
        addItemType(HomeListBean.HOT, R.layout.item_home_hot);
        addItemType(HomeListBean.THEME, R.layout.item_home_theme);
        addItemType(HomeListBean.SECTION, R.layout.item_home_section);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeListBean item) {
        switch (helper.getItemViewType())
        {
            case HomeListBean.TITLE:
                helper.setText(R.id.tv_title, item.getTitle());
                break;
            case HomeListBean.DAILY:
                helper.setText(R.id.tv_daily_dec, item.getDailyList().getTitle());
                GlideUtils.getInstance().loadImage(2, item.getDailyList().getImages().get(0), helper.getView(R.id.iv_daily));
                helper.getView(R.id.ll_section).setOnClickListener(v -> onItemClickListener.onItemClickListener(item.getDailyList().getId(), helper.getView(R.id.iv_daily)));
                break;
            case HomeListBean.HOT:
                helper.setText(R.id.tv_three_one_one_title, item.getHotList().get(0).getTitle());
                helper.setText(R.id.tv_three_one_two_title, item.getHotList().get(1).getTitle());
                helper.setText(R.id.tv_three_one_three_title, item.getHotList().get(2).getTitle());
                GlideUtils.getInstance().loadImage(3, item.getHotList().get(0).getThumbnail(), helper.getView(R.id.iv_three_one_one));
                GlideUtils.getInstance().loadImage(3, item.getHotList().get(1).getThumbnail(), helper.getView(R.id.iv_three_one_two));
                GlideUtils.getInstance().loadImage(3, item.getHotList().get(2).getThumbnail(), helper.getView(R.id.iv_three_one_three));
                onItemClick(helper, R.id.iv_three_one_one, item.getHotList().get(0).getNews_id());
                onItemClick(helper, R.id.iv_three_one_two, item.getHotList().get(1).getNews_id());
                onItemClick(helper, R.id.iv_three_one_three, item.getHotList().get(2).getNews_id());
                break;
            case HomeListBean.THEME:
                helper.setText(R.id.tv_two_one_one_title, item.getThemeList().get(0).getDescription());
                helper.setText(R.id.tv_two_one_two_title, item.getThemeList().get(1).getDescription());
                GlideUtils.getInstance().loadImage(3, item.getThemeList().get(0).getThumbnail(), helper.getView(R.id.iv_two_one_one));
                GlideUtils.getInstance().loadImage(3, item.getThemeList().get(1).getThumbnail(), helper.getView(R.id.iv_two_one_two));
                OnItemThemeClick(helper, R.id.iv_two_one_one, item.getThemeList().get(0).getId());
                OnItemThemeClick(helper, R.id.iv_two_one_two, item.getThemeList().get(1).getId());
                break;
            case HomeListBean.SECTION:
                helper.setText(R.id.tv_one_photo_title, item.getSectionList().get(0).getName());
                helper.setText(R.id.tv_two_one_one_title, item.getSectionList().get(1).getName());
                helper.setText(R.id.tv_two_one_two_title, item.getSectionList().get(2).getName());
                GlideUtils.getInstance().loadImage(3, item.getSectionList().get(0).getThumbnail(), helper.getView(R.id.iv_one_photo));
                GlideUtils.getInstance().loadImage(3, item.getSectionList().get(1).getThumbnail(), helper.getView(R.id.iv_two_one_one));
                GlideUtils.getInstance().loadImage(3, item.getSectionList().get(2).getThumbnail(), helper.getView(R.id.iv_two_one_two));
                OnItemSectionClick(helper, R.id.iv_one_photo, item.getSectionList().get(0).getId());
                OnItemSectionClick(helper, R.id.iv_two_one_one, item.getSectionList().get(1).getId());
                OnItemSectionClick(helper, R.id.iv_two_one_two, item.getSectionList().get(2).getId());
                break;
        }
    }

    /**
     * 抽取成一个方法不然每一个都要重新写setOnClickListener(new View.OnClickListener()
     *
     */
    private void onItemClick(BaseViewHolder helper, int id, final int urlId)
    {
        helper.getView(id).setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(urlId, v);
            }
        });
    }

    private void OnItemThemeClick(BaseViewHolder helper, int id, final int urlId)
    {
        helper.getView(id).setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.OnItemThemeClickListener(urlId, v);
            }
        });
    }

    private void OnItemSectionClick(BaseViewHolder helper, int id, final int urlId)
    {
        helper.getView(id).setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.OnItemSectionClickListener(urlId, v);
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClickListener(int id, View view);

        void OnItemThemeClickListener(int id, View view);

        void OnItemSectionClickListener(int id, View view);
    }




}
