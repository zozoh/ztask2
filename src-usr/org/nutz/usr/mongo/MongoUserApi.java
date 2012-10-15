package org.nutz.usr.mongo;

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
import org.nutz.usr.AbstractUserApi;
import org.nutz.usr.UErr;
import org.nutz.usr.User;
import org.nutz.usr.UserQuery;

@IocBean(name = "userApi")
public class MongoUserApi extends AbstractUserApi {

    private static final Log log = Logs.get();

    @Inject("java:$conf.dao($connector, 'db-name')")
    private MongoDao dao;

    @Override
    public void init() {
        dao.create(getUserType(), false);
        if (log.isInfoEnabled())
            log.info("mongo.usr collections checked");
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
    protected User do_create(String loginName) {
        MongoUser u = Mirror.me(MongoUser.class).born();
        u.setLoginName(loginName);
        dao.save(u);
        return u;
    }

    @Override
    public boolean remove(String loginName) {
        return dao.remove(getUserType(), Q(loginName)).getN() > 0;
    }

    @Override
    public void clear() {
        dao.create(getUserType(), true);
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
        // 删除
        if (null == val) {
            dao.updateBy(User.CO_NM,
                         Moo.NEW(User.COF_LNM, u.getLoginName()),
                         Moo.NEW().unset(User.COF_VALS + "." + fnm));
        }
        // 更新
        else {
            String sVal = val.toString();
            u.setValue(fnm, sVal);
            dao.updateBy(User.CO_NM,
                         Moo.NEW(User.COF_LNM, u.getLoginName()),
                         Moo.SET(User.COF_VALS + "." + fnm, sVal));
        }
        // 更新内存对象
        u.setValue(fnm, val);
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
            for (String[] ss : q.qValues()) {
                oo.eq(User.COF_VALS + "." + ss[0], ss[1]);
            }
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
