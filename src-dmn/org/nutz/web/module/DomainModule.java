package org.nutz.web.module;

import java.util.List;

import org.nutz.dmn.DErr;
import org.nutz.dmn.Domain;
import org.nutz.dmn.DomainApi;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Scope;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.usr.User;
import org.nutz.web.Webs;
import org.nutz.web.ajax.AjaxCheckSession;

/**
 * 提供了对域的配置和操作的操作接口
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
@Filters(@By(type = AjaxCheckSession.class, args = {Webs.ME}))
@IocBean
@At("/d")
@Ok("ajax")
@Fail("ajax")
public class DomainModule {

    @Inject("refer:domainApi")
    private DomainApi Idmn;

    @At("/del")
    public boolean do_remove(@Param("nm") String name,
                             @Attr(scope = Scope.SESSION, value = Webs.ME) User me) {
        Domain dmn = Idmn.fetch(name);
        if (null == dmn)
            return false;
        if (!dmn.getOwnerName().equals(me.getLoginName()))
            throw DErr.NOT_OWNER(dmn.getName(), me.getLoginName());
        return Idmn.remove(dmn.getName());
    }

    @At("/query")
    public List<Domain> query(@Param("kwd") String keyword) {
        return Idmn.query(keyword);
    }

    @At("/check")
    public boolean do_check_name_exists(@Param("nm") String name) {
        return Idmn.exists(name);
    }

    @At("/new")
    public Domain do_create(@Param("nm") String name,
                            @Attr(scope = Scope.SESSION, value = Webs.ME) User me) {
        return Idmn.create(name, me);
    }

}
