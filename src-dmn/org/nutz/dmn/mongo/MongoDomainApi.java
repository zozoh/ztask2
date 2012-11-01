package org.nutz.dmn.mongo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.nutz.dmn.DErr;
import org.nutz.dmn.Domain;
import org.nutz.dmn.DomainApi;
import org.nutz.dmn.DomainQuery;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Each;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.MCur;
import org.nutz.mongo.util.Moo;
import org.nutz.usr.User;

@IocBean(name = "domainApi")
public class MongoDomainApi implements DomainApi {

    private static final Log log = Logs.get();

    @Inject("java:$conf.dao($connector, 'db-name')")
    private MongoDao dao;

    @Inject("java:$conf.get('dmn-nm')")
    private String _regex_name;

    @Override
    public void init() {
        dao.create(MongoDomain.class, false);
        dao.create(MongoWatching.class, false);
        if (log.isInfoEnabled())
            log.info("mongo.dmn collections checked");
    }

    @Override
    public Class<? extends Domain> getDomainType() {
        return MongoDomain.class;
    }

    @Override
    public Domain fetch(String name) {
        return dao.findOne(getDomainType(), Q(name));
    }

    @Override
    public boolean exists(String name) {
        return dao.count(getDomainType(), Q(name)) > 0;
    }

    @Override
    public Domain check(String name) {
        Domain d = fetch(name);
        if (null == d)
            throw DErr.NO_EXISTS(name);
        return d;
    }

    @Override
    public Domain checkValues(Domain d) {
        if (null == d.getValues()) {
            Domain dbDmn = dao.findOne(MongoDomain.class, Q(d.getName()));
            if (null != dbDmn)
                d.setValues(dbDmn.getValues());
            if (null == d.getValues())
                d.setValues(new HashMap<String, Object>());
        }
        return d;
    }

    @Override
    public Domain create(String name, User owner) {
        // 检查一下名称
        if (null == name || !name.matches(_regex_name)) {
            throw DErr.INVALID_NAME(name);
        }

        // 首先检查一下这个域是否存在
        if (exists(name))
            throw DErr.EXISTS(name);

        // 开始创建域对象
        MongoDomain d = new MongoDomain();
        d.setCreateTime(Times.now());
        d.setName(name);
        d.setOwnerName(owner.getLoginName());

        // 插入数据库
        dao.save(d);

        // 返回
        return d;
    }

    @Override
    public long countWatchers(String dnm) {
        return dao.count(MongoWatching.class, Moo.NEW("domainName", dnm));
    }

    @Override
    public List<String> queryWatcher(String dnm, String keyword, int pn, int pgsz) {
        final List<String> list = new LinkedList<String>();
        // 计算游标
        MCur mc = MCur.ASC("userName");
        if (pn > 0) {
            mc.skip((pn - 1) * Math.max(1, pgsz)).limit(pgsz);
        }
        // 计算查询条件
        Moo q = Moo.NEW("domainName", dnm);
        if (!Strings.isBlank(keyword)) {
            q.contains("userName", keyword);
        }
        // 查询
        dao.each(new Each<MongoWatching>() {
            public void invoke(int index, MongoWatching mw, int length) {
                list.add(mw.getUserName());
            }
        }, MongoWatching.class, q, mc);
        return list;
    }

    @Override
    public void addWatcher(Domain d, String loginName) {
        if (!isWatch(d.getName(), loginName)) {
            MongoWatching mw = new MongoWatching();
            mw.setCreateTime(Times.now());
            mw.setDomainName(d.getName());
            mw.setUserName(loginName);
            dao.save(mw);
        }
    }

    @Override
    public long removeWatcher(Domain d, String loginName) {
        return dao.remove(MongoWatching.class,
                          Moo.NEW("domainName", d.getName()).append("userName", loginName)).getN();
    }

    @Override
    public boolean isWatch(String dnm, String loginName) {
        return dao.count(MongoWatching.class,
                         Moo.NEW("domainName", dnm).append("userName", loginName)) > 0;
    }

    @Override
    public Domain addAdmin(String dnm, String loginName) {
        Domain d = check(dnm);
        d.addAdmin(loginName);
        save(d);
        return d;
    }

    @Override
    public Domain removeAdmin(String dnm, String loginName) {
        Domain d = check(dnm);
        d.removeAdmin(loginName);
        save(d);
        return d;
    }

    @Override
    public Domain addMember(String dnm, String loginName) {
        Domain d = check(dnm);
        d.addMember(loginName);
        save(d);
        return d;
    }

    @Override
    public Domain removeMember(String dnm, String loginName) {
        Domain d = check(dnm);
        d.removeMember(loginName);
        save(d);
        return d;
    }

    @Override
    public void save(Domain d) {
        dao.save(d);
    }

    @Override
    public boolean remove(String name) {
        return dao.remove(getDomainType(), Q(name)).getN() > 0;
    }

    @Override
    public void clear() {
        dao.create(getDomainType(), true);
        dao.create(MongoWatching.class, true);
    }

    @Override
    public long count() {
        return dao.count(getDomainType(), null);
    }

    @Override
    public long count(DomainQuery q) {
        return dao.count(getDomainType(), Q(q));
    }

    @Override
    public long count(String keyword) {
        return count(DomainQuery.NEW(keyword));
    }

    @Override
    public List<Domain> query(String keyword) {
        return query(DomainQuery.NEW(keyword));
    }

    @Override
    public List<Domain> query(DomainQuery q) {
        final List<Domain> list = new LinkedList<Domain>();
        each(q, new Each<Domain>() {
            public void invoke(int index, Domain dmn, int length) {
                list.add(dmn);
            }
        });
        return list;
    }

    @Override
    public void each(String keyword, Each<Domain> callback) {
        each(DomainQuery.NEW(keyword), callback);
    }

    @Override
    public void each(DomainQuery q, Each<Domain> callback) {
        dao.each(callback, getDomainType(), Q(q), C(q));
    }

    @Override
    public void setValue(Domain d, String fnm, Object val) {
        // 删除
        if (null == val) {
            dao.updateBy(Domain.CO_NM,
                         Moo.NEW(Domain.COF_DNM, d.getName()),
                         Moo.NEW().unset(Domain.COF_VALS + "." + fnm));
        }
        // 更新
        else {
            String sVal = val.toString();
            d.setValue(fnm, sVal);
            dao.updateBy(Domain.CO_NM,
                         Moo.NEW(Domain.COF_DNM, d.getName()),
                         Moo.SET(Domain.COF_VALS + "." + fnm, sVal));
        }
        // 更新内存对象
        d.setValue(fnm, val);
    }

    /**
     * @param q
     *            查询对象
     * @return 根据查询对象生成的游标配置信息
     */
    private MCur C(DomainQuery q) {
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
    private Moo Q(DomainQuery q) {
        if (null == q)
            return null;

        Moo oo = Moo.NEW();
        if (!Strings.isBlank(q.getKeyword())) {
            oo.match("name", q.getKeyword());
        }
        return oo;
    }

    /**
     * 一个快捷的方法，根据域名创建查询对象
     * 
     * @param name
     *            域名
     * @return MongoDB 的查询对象
     */
    private Moo Q(String name) {
        return Moo.NEW("name", name);
    }

}
