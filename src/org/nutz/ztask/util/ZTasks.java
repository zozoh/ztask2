package org.nutz.ztask.util;

import org.nutz.mvc.impl.NutMessageMap;
import org.nutz.usr.User;
import org.nutz.web.god.GodKey;

public abstract class ZTasks {

    /**
     * 后门的密码
     */
    public static GodKey GOD_KEY = new GodKey("~/.ztask_god_key");

    /**
     * 保存当前线程的多国语言设定
     */
    private static ThreadLocal<NutMessageMap> _msgs_ = new ThreadLocal<NutMessageMap>();

    /**
     * 设置当前线程的多国语言设定
     * 
     * @param msgs
     *            多国语言设定
     */
    public static void setMsgs(NutMessageMap msgs) {
        _msgs_.set(msgs);
    }

    /**
     * 获取当前线程的多国语言设定
     * 
     * @return 当前线程的多国语言设定
     */
    public static NutMessageMap getMsgs() {
        return _msgs_.get();
    }

    /**
     * 清除当前线程的多国语言设定
     */
    public static void clearMsgs() {
        _msgs_.set(null);
    }

    /**
     * 保存当前线程的操作用户
     */
    private static ThreadLocal<User> _me_ = new ThreadLocal<User>();

    /**
     * 设置当前线程操作的用户
     * 
     * @param me
     *            当前操作的用户
     */
    public static void setME(User me) {
        _me_.set(me);
    }

    /**
     * 获取当前线程操作的用户
     * 
     * @return 当前操作的用户
     */
    public static User getME() {
        return _me_.get();
    }

    /**
     * 获取当前线程操作的用户名
     * 
     * @return 当前操作的用户名
     */
    public static String getMyName() {
        User me = _me_.get();
        if (null == me)
            return null;
        return me.getLoginName();
    }

    /**
     * 判断当前线程操作的用户是否已经登录了
     * 
     * @return true 已经登录了，false 没有登录
     */
    public static boolean i_am_logined() {
        return null != _me_.get();
    }

    /**
     * 清除当前线程操作的用户
     */
    public static void clearME() {
        _me_.set(null);
    }

}
