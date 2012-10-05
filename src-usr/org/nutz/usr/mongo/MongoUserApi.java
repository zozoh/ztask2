package org.nutz.usr.mongo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Each;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.MCur;
import org.nutz.mongo.util.Moo;
import org.nutz.usr.UErr;
import org.nutz.usr.User;
import org.nutz.usr.UserField;
import org.nutz.usr.UserQuery;
import org.nutz.usr.UserApi;
import org.nutz.usr.util.Users;

@IocBean(name = "userApi")
public class MongoUserApi implements UserApi {

    private static final Log log = Logs.get();

    @Inject("java:$conf.dao($connector, 'db-name')")
    private MongoDao dao;

    @Inject("java:$conf.get('usr-nm')")
    private String _regex_name;

    @Inject("java:$conf.get('usr-pwd')")
    private String _regex_pwd;

    @Inject("java:$conf.get('rgx.email')")
    private String _regex_email;

    @Inject("java:$conf.get('usr-fields')")
    String _usr_fields;

    @Inject("java:$conf.get('usr-major-email')")
    private String _usr_major_email;

    @Override
    public void init() {
        dao.create(getUserType(), false);
        if (log.isInfoEnabled())
            log.info("mongo.usrs collections checked");
    }

    /**
     * 缓存从配置文件读出的结果
     */
    private UserField[] fields;

    public void set_usr_fields(String str) {
        this._usr_fields = str;
        // 进行解析
        String[] lines = Strings.splitIgnoreBlank(str, "\n");
        fields = new UserField[lines.length];
        for (int i = 0; i < lines.length; i++) {
            fields[i] = Users.parseUserField(lines[i]);
        }
    }

    @Override
    public Class<? extends User> getUserType() {
        return MongoUser.class;
    }

    @Override
    public User fetch(String loginName) {
        return dao.findOne(this.getUserType(), Q(loginName));
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
            User dbUser = dao.findOne(MongoUser.class, Q(u.getLoginName()));
            if (null != dbUser)
                u.setValues(dbUser.getValues());
            if (null == u.getValues())
                u.setValues(new HashMap<String, Object>());
        }
        return u;
    }

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
        if (dao.count(MongoUser.class, Q(loginName)) > 0)
            throw UErr.EXISTS(loginName);

        // 确保 email 不存在
        if (dao.count(MongoUser.class, Moo.NEW("email", email)) > 0)
            throw UErr.EMAIL_USED(email);

        // 首先创建一条记录
        MongoUser u = Mirror.me(MongoUser.class).born();
        u.setLoginName(loginName);
        dao.save(u);

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
    public boolean remove(String loginName) {
        return dao.remove(getUserType(), Q(loginName)).getN() > 0;
    }

    @Override
    public long count() {
        return dao.count(getUserType(), null);
    }

    @Override
    public long count(UserQuery q) {
        return dao.count(getUserType(), Q(q));
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
    public void each(UserQuery q, Each<User> callback) {
        dao.each(callback, getUserType(), Q(q), C(q));
    }

    @Override
    public void switchMajorEmail(User u, String fnm) {
        String email = checkValues(u).getString(fnm);
        if (Strings.isBlank(email) || !email.matches(_regex_email))
            throw UErr.INVALID_EMAIL(fnm + ":" + email);
        u.setEmail("?" + email);
        dao.update(MongoUser.class, Q(u.getLoginName()), Moo.SET("email", u.getEmail()));
    }

    @Override
    public void changePassword(User u, String pwdOld, String pwdNew) {
        // 检查新密码是否符合要求
        if (Strings.isBlank(pwdNew) || !pwdNew.matches(_regex_pwd))
            throw UErr.INVALID_PASSWORD();

        // 检查旧密码
        if (pwdOld != null) {
            if (dao.count(MongoUser.class, Q(u.getLoginName()).eq("password", pwdOld)) <= 0) {
                throw UErr.INVALID_PASSWORD();
            }
        }

        // 执行更新
        dao.update(MongoUser.class, Q(u.getLoginName()), Moo.SET("password", pwdNew));
    }

    @Override
    public void setValue(User u, String fnm, Object val) {
        String sVal = null == val ? null : val.toString();
        u.setValue(fnm, sVal);
        dao.updateBy(User.CO_NM,
                     Moo.NEW(User.COF_LNM, u.getLoginName()),
                     Moo.SET(User.COF_VALS + "." + fnm, sVal));
    }

    @Override
    public UserField[] getFields() {
        return fields;
    }

    /**
     * @param q
     *            查询对象
     * @return 根据查询对象生成的游标配置信息
     */
    private MCur C(UserQuery q) {
        MCur mc = q.isASC() ? MCur.ASC(q.getSortBy()) : MCur.DESC(q.getSortBy());
        if (q.limit() > 0)
            mc.limit(q.limit());
        if (q.skip() > 0)
            mc.skip(q.skip());
        return mc;
    }

    /**
     * @param q
     *            查询对象
     * @return 根据查询对象生成的 MongoDB 查询条件
     */
    private Moo Q(UserQuery q) {
        if (null == q)
            return null;

        Moo oo = Moo.NEW();
        if (!Strings.isBlank(q.qName())) {
            oo.match("loginName", q.qName());
        }
        if (!Strings.isBlank(q.qEmail())) {
            oo.eq("email", q.qEmail());
        }
        if (q.qValues() != null && q.qValues().length > 0) {
            Moo[] subs = new Moo[q.qValues().length];
            int i = 0;
            for (String[] ss : q.qValues()) {
                subs[i++] = Moo.NEW(User.COF_VALS + "." + ss[0], ss[1]);
            }
            oo.or(subs);
        }
        return oo;
    }

    /**
     * 一个快捷的方法，根据用户登录名创建查询对象
     * 
     * @param loginName
     *            用户登录名
     * @return MongoDB 的查询对象
     */
    private Moo Q(String loginName) {
        return Moo.NEW("loginName", loginName);
    }
}
