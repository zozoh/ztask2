package org.nutz.dmn.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.nutz.dmn.Domain;
import org.nutz.dmn.DomainApi;
import org.nutz.lang.Lang;
import org.nutz.unit.IocCase;
import org.nutz.usr.User;
import org.nutz.usr.UserApi;

public class MongoDomainApiTest extends IocCase {

    @Override
    protected String[] ioc_anno_packages() {
        return Lang.array("org.nutz.usr.mongo", "org.nutz.dmn.mongo");
    }

    private DomainApi domains;

    private UserApi usrs;

    private User ow;

    @Override
    protected void onBefore() {
        // 获得用户服务类
        domains = ioc.get(DomainApi.class);
        usrs = ioc.get(UserApi.class);
        // 清除所有域数据
        domains.init();
        domains.clear();
        // 清除所有用户数据
        usrs.init();
        usrs.clear();

        // 准备一个用户数据
        ow = usrs.create("zozoh", "123456", "zozohtnt@gmail.com");
    }

    @Test
    public void test_simple_create_fetch_remove() {
        Domain dmn = domains.create("nutz", ow);
        assertNotNull(dmn);

        dmn = domains.fetch("nutz");
        assertEquals(ow.getLoginName(), dmn.getOwnerName());

        domains.remove("nutz");
        dmn = domains.fetch("nutz");
        assertNull(dmn);
        assertEquals(0, domains.count());
    }

}
