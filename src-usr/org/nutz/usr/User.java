package org.nutz.usr;

import java.util.Map;

public interface User {

    /*
     * 下面是数据库表名和字段名的常量
     */

    public static final String CO_NM = "usr";
    public static final String COF_LNM = "nm";
    public static final String COF_VALS = "vals";

    /**
     * @return 用户的登录名
     */
    String getLoginName();

    /**
     * @return 用户的主邮箱
     */
    String getEmail();

    /**
     * 设置用户的主邮箱，本函数不会做持久化
     * 
     * @param email
     *            用户的主邮箱
     */
    void setEmail(String email);

    /**
     * @return 用户的密码
     */
    String getPassword();

    /**
     * @return 用户字段所有的字段填充表
     */
    Map<String, Object> getValues();

    /**
     * 仅仅为当前用户的内存对象设置 values，本函数不会做持久化
     * 
     * @param values
     *            用户字段值表
     */
    void setValues(Map<String, Object> values);

    /**
     * 根据字段名，自动创建子 Map
     * 
     * @param fnm
     *            字段名，用 . 来分隔路径
     * @param val
     *            字段值
     */
    void setValue(String fnm, Object val);

    /**
     * 根据字段名，获得值
     * 
     * @param fnm
     *            字段名，用 . 来分隔路径
     * @return 字段值
     */
    Object getValue(String fnm);

    int getInt(String fnm);

    long getLong(String fnm);

    String getString(String fnm);

}
