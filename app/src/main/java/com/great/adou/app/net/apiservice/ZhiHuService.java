package com.great.adou.app.net.apiservice;


import com.great.adou.app.net.bean.CommentBean;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.DetailExtraBean;
import com.great.adou.app.net.bean.HotListBean;
import com.great.adou.app.net.bean.SectionChildListBean;
import com.great.adou.app.net.bean.SectionListBean;
import com.great.adou.app.net.bean.ThemeChildListBean;
import com.great.adou.app.net.bean.ThemeListBean;
import com.great.adou.app.net.bean.ZhihuDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/3 09:32.
 */

public interface ZhiHuService
{
    /**
     * 最新日报
     */
    @GET("news/latest")
    Observable<DailyListBean> requestDailyList();
    /**
     * 主题日报
     */
    @GET("themes")
    Observable<ThemeListBean> requestThemeList();

    /**
     * 主题日报详情
     */
    @GET("theme/{id}")
    Observable<ThemeChildListBean> requestThemeChildList(@Path("id") int id);

    /**
     * 专栏日报
     */
    @GET("sections")
    Observable<SectionListBean> requestSectionList();

    /**
     * 专栏日报详情
     */
    @GET("section/{id}")
    Observable<SectionChildListBean> requestSectionChildList(@Path("id") int id);

    /**
     * 热门日报
     */
    @GET("news/hot")
    Observable<HotListBean> requestHotList();

    /**
     * 日报详情
     */
    @GET("news/{id}")
    Observable<ZhihuDetailBean> requestDetailInfo(@Path("id") int id);

    /**
     * 日报的额外信息
     */
    @GET("story-extra/{id}")
    Observable<DetailExtraBean> requestDetailExtraInfo(@Path("id") int id);

    /**
     * 日报的长评论
     */
    @GET("story/{id}/long-comments")
    Observable<CommentBean> requestLongCommentInfo(@Path("id") int id);

    /**
     * 日报的短评论
     */
    @GET("story/{id}/short-comments")
    Observable<CommentBean> requestShortCommentInfo(@Path("id") int id);
}
