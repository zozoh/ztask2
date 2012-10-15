package org.nutz.usr.fs;

import org.nutz.lang.Lang;
import org.nutz.usr.UserApiTest;

public class FsUserApiTest extends UserApiTest {

    @Override
    protected String[] ioc_anno_packages() {
        return Lang.array("org.nutz.usr.fs");
    }

}
