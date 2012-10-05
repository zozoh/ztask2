package org.nutz.ztask.impl.mongo;

import org.nutz.mongo.MongoDao;

public abstract class AbstractMongoApi {

    protected MongoDao dao;

    public MongoDao getDao() {
        return dao;
    }

    public void setDao(MongoDao dao) {
        this.dao = dao;
    }

}
