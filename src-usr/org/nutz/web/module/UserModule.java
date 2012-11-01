package org.nutz.web.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.Scope;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.usr.UErr;
import org.nutz.usr.User;
import org.nutz.usr.UserApi;
import org.nutz.web.Webs;
import org.nutz.web.ajax.AjaxCheckSession;

/**
 * 提供了用户登陆，界面等诸多操作 URL
 * <p>
 * 本模块监听的 URL 前缀为 "/u"，并且依赖 Ioc 容器中的下述对象
 * <ul>
 * <li>$conf.get("usr-type")
 * <li>$conf.get("usr-url-after-reg")
 * <li>$conf.get("usr-url-after-login")
 * <li>$conf.get("usr-url-after-logout")
 * <li>$userService
 * </ul>
 * 本模块需要外接 JSP 模板的支持:
 * <ul>
 * <li>WEB-INF/usr/register.jsp
 * <li>WEB-INF/web/jsp_error.jsp
 * </ul>
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
@Filters(@By(type=AjaxCheckSession.class))
@IocBean
@At("/u")
@Ok("ajax")
@Fail("ajax")
public class UserModule {

    @Inject("refer:userApi")
    private UserApi Iusr;

    @Inject("java:$conf.get('usr-url-after-reg')")
    private String url_after_reg;

    @Inject("java:$conf.get('usr-url-after-login')")
    private String url_after_login;

    @Inject("java:$conf.get('usr-url-after-logout')")
    private String url_after_logout;

    /**
     * 获取用户数据对象
     * 
     * @param loginName
     *            用户登录名，如果为 "me" 表示当前会话用户
     * @return 用户数据对象
     */
    @At("/get/?")
    public User get_usr(String loginName, @Attr(scope = Scope.SESSION, value = Webs.ME) User u) {
        if (Strings.isBlank(loginName) || "me".equalsIgnoreCase(loginName))
            return Iusr.check(u.getLoginName());
        return Iusr.check(loginName);
    }

    /**
     * 设置用户档案某一字段的值
     * 
     * @param loginName
     *            用户登录名，如果为 "me" 表示当前会话用户
     * @param fnm
     *            字段名
     * @param val
     *            字段值
     * @param u
     *            被操作用户
     */
    @At("/do/set/?")
    public void do_set_value(String loginName,
                             @Param("fnm") String fnm,
                             @Param("val") String val,
                             @Attr(scope = Scope.SESSION, value = Webs.ME) User u) {
        if (!Strings.isBlank(loginName) && !"me".equalsIgnoreCase(loginName)) {
            u = Iusr.check(loginName);
        }
        Iusr.setValue(u, fnm, val);
    }

    /**
     * 修改当前用户的登录密码
     * 
     * @param old
     *            旧密码
     * @param pwd
     *            新密码
     * @param u
     *            被操作用户
     */
    @At("/do/chgpwd")
    public void do_change_password(@Param("old") String old,
                                   @Param("pwd") String pwd,
                                   @Attr(scope = Scope.SESSION, value = Webs.ME) User u) {
        Iusr.changePassword(u, old, pwd);
    }

    /**
     * 执行用户登陆，并根据配置信息将客户端重定向到相应地址
     * <p>
     * 本函数可能产生如下的错误:
     * 
     * <pre>
     * e.u.invalid_login
     * </pre>
     * 
     * @param loginName
     *            用户登录名
     * @param pwd
     *            登录的密码
     * @return 根据配置生成的重定向视图
     */
    @Filters
    @At("/do/login")
    @Fail("jsp:web.jsp_error")
    public View do_login(@Param("nm") String loginName, @Param("pwd") String pwd, HttpSession sess) {
        if (Strings.isBlank(loginName) || Strings.isBlank(pwd)) {
            throw UErr.INVALID_LOGIN();
        }
        // 看看用户的密码是否匹配
        User u = Iusr.fetch(loginName);
        if (null == u)
            throw UErr.INVALID_NAME(loginName);
        if (!pwd.equals(u.getPassword()))
            throw UErr.INVALID_PASSWORD();

        // 记录到会话中
        sess.setAttribute(Webs.ME, u);

        // 登录成功，返回重定向
        return new ServerRedirectView(url_after_login);
    }

    /**
     * 执行用户的注册，并根据配置信息将客户端重定向到相应的地址
     * 
     * @return 根据配置生成的重定向视图
     */
    @Filters
    @At("/do/reg")
    @Fail("jsp:web.jsp_error")
    public View do_reg(@Param("lnm") String loginName,
                       @Param("pwd") String pwd,
                       @Param("email") String email) {
        // 创建用户
        Iusr.create(loginName, pwd, email);

        // 注册成功，返回重定向
        return new ServerRedirectView(url_after_reg);
    }

    /**
     * 注销当前的登录
     * 
     * @param sess
     *            会话对象
     * @return 根据配置生成的重定向视图
     */
    @At("/do/logout")
    @Fail("jsp:web.jsp_error")
    public View do_logout(HttpSession sess) {
        // 移除掉 Session 对象
        sess.removeAttribute(Webs.ME);
        // 成功，返回重定向
        return new ServerRedirectView(url_after_logout);
    }

    @Filters
    @At("/register")
    @Ok("jsp:usr.register")
    @Fail("jsp:web.jsp_error")
    public void show_register() {}
}
