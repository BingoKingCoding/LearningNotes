package com.great.adou.app.event;

import org.greenrobot.eventbus.EventBus;

/**
 * <将EventBus封装一层.>
 * Created by WangWB on 2018/09/11  14:53.
 */
public class EventBusUtil {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }

    // 其他
}
