package org.nutz.usr;

import static org.nutz.web.Webs.Err.create;

import org.nutz.web.WebException;

/**
 * 用户相关的错误的帮助生成类
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class UErr {

    public static WebException REGCODE_EXPIRED(String code) {
        return create("e.u.regcode_expired").reason(code);
    }

    public static WebException REGCODE_HOLD(String code) {
        return create("e.u.regcode_hold").reason(code);
    }

    public static WebException REGCODE_INVALID(String code) {
        return create("e.u.regcode_invalid").reason(code);
    }

    public static WebException INVALID_PASSWORD() {
        return create("e.u.invalid_password");
    }

    public static WebException INVALID_EMAIL(String email) {
        return create("e.u.invalid_email").reason(email);
    }

    public static WebException EMAIL_USED(String email) {
        return create("e.u.email_used").reason(email);
    }

    public static WebException INVALID_NAME(String name) {
        return create("e.u.invalid_name").reason(name);
    }

    public static WebException INVALID_LOGIN() {
        return create("e.u.invalid_login");
    }

    public static WebException NO_EXISTS(String name) {
        return create("e.u.no_exists").reason(name);
    }

    public static WebException EXISTS(String name) {
        return create("e.u.exists").reason(name);
    }

}
