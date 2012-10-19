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

    @Test
    public void test_simple_create_fetch_remove() {
        Domain dmn = Idmn.create("nutz", ow);
        assertNotNull(dmn);

        dmn = Idmn.fetch("nutz");
        assertEquals(ow.getLoginName(), dmn.getOwnerName());

        Idmn.remove("nutz");
        dmn = Idmn.fetch("nutz");
        assertNull(dmn);
        assertEquals(0, Idmn.count());
    }

    private DomainApi Idmn;

    private UserApi Iusr;

    private User ow;

    @Override
    protected void onBefore() {
        // 获得用户服务类
        Idmn = ioc.get(DomainApi.class);
        Iusr = ioc.get(UserApi.class);
        // 清除所有域数据
        Idmn.init();
        Idmn.clear();
        // 清除所有用户数据
        Iusr.init();
        Iusr.clear();

        // 准备一个用户数据
        ow = Iusr.create("zozoh", "123456", "zozohtnt@gmail.com");
    }
}
