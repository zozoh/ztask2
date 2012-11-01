package org.nutz.dmn.mongo;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    @CoField("admins")
    private List<String> admins;

    @CoField("members")
    private List<String> members;

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

    @Override
    public List<String> getAdmins() {
        return admins;
    }

    @Override
    public void setAdmins(List<String> admins) {
        this.admins = admins;
        if (null != admins && null != members)
            for (String adm : admins)
                members.remove(adm);
    }

    @Override
    public void addAdmin(String loginName) {
        if (null == admins)
            admins = new LinkedList<String>();
        this.removeMember(loginName);
        if (!ownerName.equals(loginName) && !admins.contains(loginName))
            admins.add(loginName);
    }

    @Override
    public void removeAdmin(String loginName) {
        if (null != admins && admins.remove(loginName)) {
            this.addMemeber(loginName);
        }
    }

    @Override
    public boolean isAdmin(String loginName) {
        return ownerName.equals(loginName) || isAdminOnly(loginName);
    }

    @Override
    public boolean isAdminOnly(String loginName) {
        return null != admins && admins.contains(loginName);
    }

    @Override
    public List<String> getMembers() {
        return members;
    }

    @Override
    public void setMembers(List<String> members) {
        this.members = members;
        if (null != members && null != admins)
            for (String mm : members)
                admins.remove(mm);
    }

    @Override
    public void addMemeber(String loginName) {
        if (null == members)
            members = new LinkedList<String>();
        this.removeAdmin(loginName);
        if (!ownerName.equals(loginName) && !members.contains(loginName))
            members.add(loginName);
    }

    @Override
    public void removeMember(String loginName) {
        if (null != members)
            members.remove(loginName);
    }

    @Override
    public boolean isMember(String loginName) {
        return isAdmin(loginName) || isMemberOnly(loginName);
    }

    @Override
    public boolean isMemberOnly(String loginName) {
        return null != members && members.contains(loginName);
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
