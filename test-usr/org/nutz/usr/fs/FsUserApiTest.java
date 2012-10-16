package org.nutz.usr.fs;

import org.nutz.lang.Lang;
import org.nutz.usr.UserApiTest;
import org.nutz.web.WebConfig;

public class FsUserApiTest extends UserApiTest {

    @Override
    protected void setupConf(WebConfig conf) {
        conf.set("usr-info-home", "~/tmp/nutz/modules/usr");
    }

    @Override
    protected String[] ioc_anno_packages() {
        return Lang.array("org.nutz.usr.fs");
    }

}
