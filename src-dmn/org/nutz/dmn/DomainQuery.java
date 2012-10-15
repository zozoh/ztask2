package org.nutz.dmn;

import org.nutz.web.query.WebQuery;

/**
 * 本查询对象， keyword 本身就相对于名称
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class DomainQuery extends WebQuery {

    public static DomainQuery NEW(String str) {
        DomainQuery q = new DomainQuery();
        q.setKeyword(str);
        return q;
    }

    @Override
    protected String getDefaultSortBy() {
        return "name";
    }

    @Override
    protected void parseKeyword() {}

}
