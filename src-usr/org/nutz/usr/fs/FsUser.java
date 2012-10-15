package org.nutz.usr.fs;

import java.util.HashMap;
import java.util.Map;

import org.nutz.lang.Strings;
import org.nutz.usr.User;

public class FsUser implements User, Comparable<User> {

    private String loginName;

    private String email;

    private String password;

    private Map<String, Object> values;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int compareTo(User u) {
        return loginName.compareTo(u.getLoginName());
    }

}
