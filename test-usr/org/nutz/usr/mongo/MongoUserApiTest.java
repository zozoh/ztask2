package org.nutz.usr.mongo;

import org.nutz.lang.Lang;
import org.nutz.usr.UserApiTest;

public class MongoUserApiTest extends UserApiTest {

    @Override
    protected String[] ioc_anno_packages() {
        return Lang.array("org.nutz.usr.mongo");
    }

}
