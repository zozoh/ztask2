package org.nutz.ztask.web;

import org.nutz.mongo.MongoConnector;
import org.nutz.mongo.MongoDao;
import org.nutz.web.WebConfig;

/**
 * 封装对于配置文件的读取
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZTaskConfig extends WebConfig {

    public ZTaskConfig(String path) {
        super(path);
    }

    /**
     * 获取 Mongo 数据库操作对象
     * 
     * @param conn
     *            连接对象
     * @param dbKey
     *            数据库名称在配置文件中的 KEY
     * @return MongoDao 对象
     */
    public MongoDao dao(MongoConnector conn, String dbKey) {
        return conn.getDao(get(dbKey) + JUNIT_DB_SUFFIX);
    }

    /**
     * @return 邀请码模式是否打开
     */
    public boolean isRegcodeOn() {
        return this.getBoolean("sys-regcode", false);
    }

}
