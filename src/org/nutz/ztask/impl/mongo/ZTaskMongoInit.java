package org.nutz.ztask.impl.mongo;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.web.Initializable;

public class ZTaskMongoInit extends AbstractMongoApi implements Initializable {

    private static final Log log = Logs.get();

    @Override
    public void init() {

        if (log.isInfoEnabled())
            log.info("mongo.task collections checked");
    }

}
