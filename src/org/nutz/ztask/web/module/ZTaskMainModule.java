package org.nutz.ztask.web.module;

import org.nutz.mvc.View;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.mvc.view.JspView;
import org.nutz.web.ajax.AjaxViewMaker;
import org.nutz.ztask.util.ZTasks;
import org.nutz.ztask.web.ZTaskSetup;

/**
 * 系统主模块，定义了 Nutz.Mvc 的配置信息
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
@Modules(packages = "org.nutz.web.module", scanPackage = true)
@Localization(value = "msg", defaultLocalizationKey = "zh_CN")
@Fail("jsp:jsp.error")
@SetupBy(ZTaskSetup.class)
@IocBy(args = {"*org.nutz.ioc.loader.json.JsonLoader",
               "ioc",
               "*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
               "org.nutz.usr.mongo",
               "org.nutz.dmn.mongo",
               "org.nutz.web.module",
               "org.nutz.ztask.web.module",
               "org.nutz.ztask.impl"}, type = ComboIocProvider.class)
@Views({AjaxViewMaker.class})
@ChainBy(args = "ztask.chain.js")
@Chain("ztask")
public class ZTaskMainModule {

    /**
     * 访问主页的时候，根据是否登录，转到不同的页面
     */
    @At("/")
    public View show_index_page() {
        if (ZTasks.i_am_logined()) {
            return new JspView("jsp.main");
        }
        return new JspView("jsp.entry");
    }

}
