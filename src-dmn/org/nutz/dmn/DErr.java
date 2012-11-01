package org.nutz.dmn;

import static org.nutz.web.Webs.Err.create;

import org.nutz.web.WebException;

/**
 * 封装所有关于 DOMAIN 的错误
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class DErr {

    public static WebException NO_EXISTS(String dnm) {
        return create("e.d.no_exists").reason(dnm);
    }

    public static WebException EXISTS(String dnm) {
        return create("e.d.exists").reason(dnm);
    }

    public static WebException INVALID_NAME(String name) {
        return create("e.d.invalid_name").reason(name);
    }

    public static WebException NOT_ADMIN(String dnm, String unm) {
        return create("e.d.not.admin").reasonf("u(%s) @ dmn(%s)", dnm, unm);
    }

    public static WebException NOT_OWNER(String dnm, String unm) {
        return create("e.d.not.owner").reasonf("u(%s) @ dmn(%s)", dnm, unm);
    }

}
