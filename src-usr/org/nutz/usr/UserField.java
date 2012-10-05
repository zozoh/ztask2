package org.nutz.usr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

/**
 * 描述了一个用户字段
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class UserField {

    /**
     * 用户字段的名称，字段的名称格式必须为:
     * 
     * <pre>
     * [组名].[字段名]
     * 比如
     * g.name    # 表示组名为 g， 字段名为 name
     * </pre>
     * 
     */
    private String name;

    private String groupName;

    private String fieldName;

    /**
     * 用户字段的限制条件。
     * 
     * <pre>
     * 默认的是一个正则式，用来约束字段值
     * 
     * 如果以 enum: 开头，则为枚举字段
     * enum:abc.:0-9 # 值为 0-9，文字为 abc.0,abc.1 ... abc.9
     * enum:A-Z      # 值为 A-Z，文字也为 A-Z
     * enum:X,Y,Z    # 值和文字都为, X,Y,Z
     * enum:abc.:M,N # 值为 M,N, 文字为 abc.M, abc.N
     * 
     * 如果为 date，则实际上需要一个日期字符串
     * 9-21 或者 09-21 都是合法的
     * </pre>
     */
    private String restrain;

    public String getGroupName() {
        return groupName;
    }

    public String getFeildName() {
        return fieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (Strings.isBlank(name))
            name = null;
        // 检查一下名称是否符合格式规定
        Matcher m = Pattern.compile("^([a-zA-Z]+)([.])([a-zA-Z0-9]+)$").matcher(name);
        if (m.find()) {
            this.name = name;
            this.groupName = m.group(1);
            this.fieldName = m.group(3);
        }
        // 如果格式不对，零容忍
        else {
            throw Lang.makeThrow("Wrong name '%s' for UserField", name);
        }
    }

    public String getRestrain() {
        return restrain;
    }

    public void setRestrain(String restrain) {
        this.restrain = restrain;
    }

}
