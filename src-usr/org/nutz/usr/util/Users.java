package org.nutz.usr.util;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.usr.UserField;

/**
 * 提供用户操作的帮助函数
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class Users {
    
    /**
     * 对 UserField 配置行进行解析，本函数并不考虑空行的情况
     * <p>
     * 一个 UserField 配置行，可以将类似下面的结构
     * 
     * <pre>
     * address.company    : .*
     * other.astrological : enum:astro.:1-12
     * </pre>
     * 
     * 即，第一个冒号作为分割，左边是字段全名，右边是配置信息
     * 
     * @param line
     *            配置行
     * @return UserField 对象
     */
    public static UserField parseUserField(String line) {
        UserField uf = new UserField();
        int pos = line.indexOf(':');
        if (pos == -1)
            throw Lang.makeThrow("Invalid UserField configuration line: %s", line);

        uf.setName(Strings.trim(line.substring(0, pos)));
        uf.setRestrain(Strings.trim(line.substring(pos + 1)));

        return uf;
    }

}
