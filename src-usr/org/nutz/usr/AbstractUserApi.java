package org.nutz.usr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.Each;
import org.nutz.lang.Strings;

public abstract class AbstractUserApi implements UserApi {

    @Inject("java:$conf.get('usr-nm')")
    protected String _regex_name;

    @Inject("java:$conf.get('usr-pwd')")
    protected String _regex_pwd;

    @Inject("java:$conf.get('rgx.email')")
    protected String _regex_email;

    @Inject("java:$conf.get('usr-major-email')")
    protected String _usr_major_email;

    protected abstract User do_create(String loginName);

    @Override
    public User create(String loginName, String pwd, String email) {
        // 检查值
        if (Strings.isBlank(loginName) || !loginName.matches(_regex_name))
            throw UErr.INVALID_NAME(loginName);

        if (Strings.isBlank(pwd) || !pwd.matches(_regex_pwd))
            throw UErr.INVALID_PASSWORD();

        if (Strings.isBlank(email) || !email.matches(_regex_email))
            throw UErr.INVALID_EMAIL(email);

        // 确保 loginName 不存在
        if (count(loginName) > 0)
            throw UErr.EXISTS(loginName);

        // 确保 email 不存在
        if (count(email) > 0)
            throw UErr.EMAIL_USED(email);

        // 首先创建一条记录
        User u = do_create(loginName);

        // 保存 Email 信息
        setValue(u, _usr_major_email, email);

        // 设置主邮箱
        switchMajorEmail(u, _usr_major_email);

        // 修改密码
        changePassword(u, null, pwd);

        // 搞定定 ^_^
        return u;
    }

    @Override
    public User check(String loginName) {
        User u = fetch(loginName);
        if (null == u)
            throw UErr.NO_EXISTS(loginName);
        return u;
    }

    @Override
    public User checkValues(User u) {
        if (null == u.getValues()) {
            User dbUser = this.fetch(u.getLoginName());
            if (null != dbUser)
                u.setValues(dbUser.getValues());
            if (null == u.getValues())
                u.setValues(new HashMap<String, Object>());
        }
        return u;
    }

    @Override
    public long count(String keyword) {
        return count(UserQuery.NEW(keyword));
    }

    @Override
    public List<User> query(UserQuery q) {
        final List<User> list = new LinkedList<User>();
        each(q, new Each<User>() {
            public void invoke(int index, User u, int length) {
                list.add(u);
            }
        });
        return list;
    }

    @Override
    public List<User> query(String keyword) {
        return query(UserQuery.NEW(keyword));
    }

    @Override
    public void each(String keyword, Each<User> callback) {
        each(UserQuery.NEW(keyword), callback);
    }
}
