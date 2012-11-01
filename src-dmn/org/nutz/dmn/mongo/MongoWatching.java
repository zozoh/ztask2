package org.nutz.dmn.mongo;

import java.util.Date;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoIndexes;

@Co("dmn_usr")
@CoIndexes("!:+domainName,+userName")
public class MongoWatching {

    @CoField("dmn")
    private String domainName;

    @CoField("usr")
    private String userName;

    @CoField("ct")
    private Date createTime;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
