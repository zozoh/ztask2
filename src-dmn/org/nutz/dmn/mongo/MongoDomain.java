package org.nutz.dmn.mongo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.nutz.dmn.Domain;
import org.nutz.lang.Strings;
import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;

@Co(Domain.CO_NM)
public class MongoDomain implements Domain {

    @CoField(Domain.COF_DNM)
    private String name;

    @CoField(Domain.COF_VALS)
    private Map<String, Object> values;

    @CoField("ct")
    private Date createTime;

    @CoField("ow")
    private String ownerName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(String fnm, Object val) {
        if (null == values)
            values = new HashMap<String, Object>();

        String[] nms = Strings.splitIgnoreBlank(fnm, "[.]");
        int last = nms.length - 1;
        Map<String, Object> map = values;
        for (int i = 0; i < last; i++) {
            Map<String, Object> sub = (Map<String, Object>) map.get(nms[i]);
            if (null == sub) {
                sub = new HashMap<String, Object>();
                map.put(nms[i], sub);
            }
            map = sub;
        }
        // 删除
        if (null == val) {
            map.remove(nms[last]);
        }
        // 修改
        else {
            map.put(nms[last], val);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getValue(String fnm) {
        if (null == values || Strings.isBlank(fnm))
            return null;
        String[] nms = Strings.splitIgnoreBlank(fnm, "[.]");
        int last = nms.length - 1;
        Map<String, Object> map = values;
        for (int i = 0; i < last; i++) {
            map = (Map<String, Object>) map.get(nms[i]);
            if (null == map)
                return null;
        }
        Object obj = map.get(nms[last]);
        return obj;
    }

    @Override
    public int getInt(String fnm) {
        Object obj = getValue(fnm);
        if (null == obj)
            return -1;
        if (obj instanceof Number)
            return ((Number) obj).intValue();
        return Integer.parseInt(obj.toString());
    }

    @Override
    public long getLong(String fnm) {
        Object obj = getValue(fnm);
        if (null == obj)
            return -1;
        if (obj instanceof Number)
            return ((Number) obj).longValue();
        return Long.parseLong(obj.toString());
    }

    @Override
    public String getString(String fnm) {
        Object obj = getValue(fnm);
        return obj == null ? null : obj.toString();
    }
}
