package org.nutz.usr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.nutz.unit.IocCase;

public abstract class UserApiTest extends IocCase {

    protected UserApi usrs;

    @Override
    protected void onBefore() {
        usrs = ioc.get(UserApi.class);
        usrs.init();
        usrs.clear();
    }

    @Test
    public void test_remove_inner_doc() {
        User u = usrs.create("zozoh", "123456", "zozohtnt@gmail.com");
        usrs.setValue(u, "abc", "xyz");

        // 判断
        assertEquals("xyz", usrs.fetch("zozoh").getValue("abc"));

        // 修改
        usrs.setValue(u, "abc", "ttt");
        assertEquals("ttt", usrs.fetch("zozoh").getValue("abc"));

        // 删除
        usrs.setValue(u, "abc", null);
        u = usrs.fetch("zozoh");
        assertNull(u.getValue("abc"));
        assertFalse(u.getValues().containsKey("abc"));
    }

    @Test
    public void test_simple_query() {
        usrs.create("aabb", "123456", "aabb@xyz.com");
        usrs.create("aacc", "123456", "aacc@xyz.com");
        usrs.create("bbcc", "123456", "bbcc@xyz.com");
        usrs.create("dddd", "123456", "dddd@xyz.com");

        /*
         * 测试计算
         */
        assertEquals(2, usrs.count(UserQuery.NEW("bb")));
        assertEquals(1, usrs.count(UserQuery.NEW("^bb")));

        /*
         * 测试查询
         */
        UserQuery q = UserQuery.NEW("^(aa|bb)");
        q.asc().setSortBy("loginName");
        List<User> list = usrs.query(q);
        assertEquals(3, list.size());
        assertEquals("aabb", list.get(0).getLoginName());
        assertEquals("aacc", list.get(1).getLoginName());
        assertEquals("bbcc", list.get(2).getLoginName());

        /*
         * 设点值再查查
         */
        User u = usrs.fetch("dddd");
        usrs.setValue(u, "x", 50);
        usrs.setValue(u, "y", 80);

        u = usrs.fetch("aacc");
        usrs.setValue(u, "x", 50);
        usrs.setValue(u, "g.age", 33);

        q = UserQuery.NEW("x=50");
        q.asc().setSortBy("loginName");
        list = usrs.query(q);
        assertEquals(2, list.size());
        assertEquals("aacc", list.get(0).getLoginName());
        assertEquals("dddd", list.get(1).getLoginName());

        q = UserQuery.NEW("g.age=33");
        list = usrs.query(q);
        assertEquals(1, list.size());
        assertEquals("aacc", list.get(0).getLoginName());

        q = UserQuery.NEW("x=50,g.age=33");
        q.asc().setSortBy("loginName");
        list = usrs.query(q);
        assertEquals(1, list.size());
        assertEquals("aacc", list.get(0).getLoginName());
    }

    @Test
    public void test_simple_create_fetch_remove() {
        User u = usrs.create("zozoh", "123456", "zozohtnt@gmail.com");
        assertNotNull(u);

        u = usrs.fetch("zozoh");
        assertEquals("123456", u.getPassword());
        assertEquals("?zozohtnt@gmail.com", u.getEmail());
        assertEquals(1, usrs.count());

        usrs.remove("zozoh");
        u = usrs.fetch("zozoh");
        assertNull(u);
        assertEquals(0, usrs.count());
    }

}
