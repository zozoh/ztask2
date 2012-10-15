package org.nutz.dmn;

import java.util.Date;
import java.util.Map;

public interface Domain {

    /*
     * 下面是数据库表名和字段名的常量
     */

    public static final String CO_NM = "dmn";
    public static final String COF_DNM = "nm";
    public static final String COF_VALS = "vals";

    /**
     * @return 域名
     */
    String getName();

    /**
     * @return 域的所有者名称
     */
    String getOwnerName();

    /**
     * 重新设置域的所有者名称
     * 
     * @param ownerName
     *            域所有者名称
     */
    void setOwnerName(String ownerName);

    /**
     * @return 域创建时间
     */
    Date getCreateTime();

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
