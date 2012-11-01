package org.nutz.dmn.mongo;

import static org.junit.Assert.*;

import java.util.List;

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
    public void test_add_remove_watcher() {
        Domain d = Idmn.create("nutz", ow);

        assertEquals(0, Idmn.countWatchers("nutz"));

        Idmn.addWatcher(d, "BBB");
        assertEquals(1, Idmn.countWatchers("nutz"));
        assertFalse(Idmn.isWatch("nutz", "AAA"));
        assertTrue(Idmn.isWatch("nutz", "BBB"));

        Idmn.addWatcher(d, "AAA");
        assertEquals(2, Idmn.countWatchers("nutz"));
        assertTrue(Idmn.isWatch("nutz", "AAA"));
        assertTrue(Idmn.isWatch("nutz", "BBB"));

        Idmn.addWatcher(d, "ACC");
        Idmn.addWatcher(d, "ADD");
        Idmn.addWatcher(d, "BCC");

        assertEquals(5, Idmn.countWatchers("nutz"));

        List<String> ws = Idmn.queryWatcher("nutz", null, 0, 0);
        assertEquals(5, ws.size());
        assertEquals("AAA", ws.get(0));
        assertEquals("ACC", ws.get(1));
        assertEquals("ADD", ws.get(2));
        assertEquals("BBB", ws.get(3));
        assertEquals("BCC", ws.get(4));

        ws = Idmn.queryWatcher("nutz", "A", 0, 0);
        assertEquals(3, ws.size());
        assertEquals("AAA", ws.get(0));
        assertEquals("ACC", ws.get(1));
        assertEquals("ADD", ws.get(2));

        ws = Idmn.queryWatcher("nutz", "A", 1, 2);
        assertEquals(2, ws.size());
        assertEquals("AAA", ws.get(0));
        assertEquals("ACC", ws.get(1));

        ws = Idmn.queryWatcher("nutz", "A", 2, 2);
        assertEquals(1, ws.size());
        assertEquals("ADD", ws.get(0));

        assertFalse(Idmn.isWatch("nutz", "XYZ"));

        Idmn.removeWatcher(d, "ACC");
        Idmn.removeWatcher(d, "ADD");
        Idmn.removeWatcher(d, "BCC");

        assertEquals(2, Idmn.countWatchers("nutz"));
        assertTrue(Idmn.isWatch("nutz", "AAA"));
        assertTrue(Idmn.isWatch("nutz", "BBB"));
        assertFalse(Idmn.isWatch("nutz", "ACC"));
        assertFalse(Idmn.isWatch("nutz", "ADD"));
        assertFalse(Idmn.isWatch("nutz", "BCC"));

        ws = Idmn.queryWatcher("nutz", null, 0, 0);
        assertEquals(2, ws.size());
        assertEquals("AAA", ws.get(0));
        assertEquals("BBB", ws.get(1));
    }

    @Test
    public void test_add_remove_member() {
        Idmn.create("nutz", ow);
        Idmn.addMember("nutz", ow.getLoginName());

        Domain d = Idmn.check("nutz");
        assertEquals(0, d.getMembers().size());

        Idmn.addMember("nutz", "AAA");
        Idmn.addMember("nutz", "BBB");

        d = Idmn.check("nutz");
        assertEquals(2, d.getMembers().size());
        assertEquals("AAA", d.getMembers().get(0));
        assertEquals("BBB", d.getMembers().get(1));

        Idmn.addMember("nutz", "AAA");
        Idmn.addMember("nutz", "BBB");

        d = Idmn.check("nutz");
        assertEquals(2, d.getMembers().size());
        assertEquals("AAA", d.getMembers().get(0));
        assertEquals("BBB", d.getMembers().get(1));

        Idmn.removeMember("nutz", "BBB");

        d = Idmn.check("nutz");
        assertEquals(1, d.getMembers().size());
        assertEquals("AAA", d.getMembers().get(0));
        assertTrue(d.isMember("AAA"));
        assertFalse(d.isMember("BBB"));

        Idmn.removeMember("nutz", "AAA");

        d = Idmn.check("nutz");
        assertEquals(0, d.getMembers().size());
        assertFalse(d.isMember("AAA"));
        assertFalse(d.isMember("BBB"));

    }

    @Test
    public void test_add_remove_admin() {
        Idmn.create("nutz", ow);
        Idmn.addAdmin("nutz", ow.getLoginName());

        Domain d = Idmn.check("nutz");
        assertEquals(0, d.getAdmins().size());

        Idmn.addAdmin("nutz", "AAA");
        Idmn.addAdmin("nutz", "BBB");

        d = Idmn.check("nutz");
        assertEquals(2, d.getAdmins().size());
        assertEquals("AAA", d.getAdmins().get(0));
        assertEquals("BBB", d.getAdmins().get(1));

        Idmn.addAdmin("nutz", "AAA");
        Idmn.addAdmin("nutz", "BBB");

        d = Idmn.check("nutz");
        assertEquals(2, d.getAdmins().size());
        assertEquals("AAA", d.getAdmins().get(0));
        assertEquals("BBB", d.getAdmins().get(1));

        Idmn.removeAdmin("nutz", "AAA");

        d = Idmn.check("nutz");
        assertEquals(1, d.getAdmins().size());
        assertEquals("AAA", d.getMembers().get(0));
        assertEquals("BBB", d.getAdmins().get(0));

        Idmn.removeAdmin("nutz", "BBB");

        d = Idmn.check("nutz");
        assertEquals(0, d.getAdmins().size());
        assertEquals("AAA", d.getMembers().get(0));
        assertEquals("BBB", d.getMembers().get(1));

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
