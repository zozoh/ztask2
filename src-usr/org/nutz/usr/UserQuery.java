package org.nutz.usr;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.web.query.WebQuery;

/**
 * 封装了一个用户的查询字符串
 * <p>
 * 查询字符串的用法为:
 * 
 * <pre>
 * abc           # 名字包含 abc 的用户，支持正则表达式
 * abc@xyz.com   # 查询用户的主邮箱
 * x.y=abc       # 查询用户的某个属性字段
 * </pre>
 * 
 * 上述条件可以混用，只要半角逗号分隔就好。但是用户名和主 email 之后最后一个会生效
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class UserQuery extends WebQuery {

    public static UserQuery NEW(String str) {
        UserQuery q = new UserQuery();
        q.setKeyword(str);
        return q;
    }

    static final Pattern REGEX_EMAIL = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+[.][a-z]+$");
    static final Pattern REGEX_VALUE = Pattern.compile("^([a-z. \t]+)(=)(.+)$");

    @Override
    protected String getDefaultSortBy() {
        return "loginName";
    }

    @Override
    protected void parseKeyword() {
        if (Strings.isBlank(keyword))
            return;

        // 开始解析
        String[] ss = Strings.splitIgnoreBlank(keyword, ",");
        List<String[]> vals = new LinkedList<String[]>();
        Matcher m;
        for (String s : ss) {
            m = REGEX_EMAIL.matcher(s);
            if (m.find()) {
                qEmail = s;
                continue;
            }
            m = REGEX_VALUE.matcher(s);
            if (m.find()) {
                vals.add(Lang.array(Strings.trim(m.group(1)), Strings.trim(m.group(3))));
                continue;
            }
            qName = s;
        }
        if (!vals.isEmpty()) {
            qValues = vals.toArray(new String[vals.size()][]);
        }
    }

    private String qName;

    private String qEmail;

    private String[][] qValues;

    public String qName() {
        return qName;
    }

    public String qEmail() {
        return qEmail;
    }

    public String[][] qValues() {
        return qValues;
    }
}
