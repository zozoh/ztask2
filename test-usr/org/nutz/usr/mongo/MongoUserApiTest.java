package org.nutz.usr.mongo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.nutz.unit.MongoIocCase;
import org.nutz.usr.User;
import org.nutz.usr.UserQuery;
import org.nutz.usr.UserApi;

public class MongoUserApiTest extends MongoIocCase {

    private UserApi usrs;

    @Override
    protected void onBefore() {
        // 获得用户服务类
        usrs = ioc.get(UserApi.class);
        // 清除所有用户数据，得到一个干净的集合
        dao.create(usrs.getUserType(), true);
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

        q = UserQuery.NEW("y=80,g.age=33");
        q.asc().setSortBy("loginName");
        list = usrs.query(q);
        assertEquals(2, list.size());
        assertEquals("aacc", list.get(0).getLoginName());
        assertEquals("dddd", list.get(1).getLoginName());
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
