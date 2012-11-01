package org.nutz.dmn;

import java.util.Date;
import java.util.List;
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
     * @return 域中的管理员，管理员，一定包括 owner
     */
    List<String> getAdmins();

    /**
     * 重设域管理员（非持久化操作）
     * <p>
     * 所有的管理员如果是域的成员，那么会从成员列表中移除 <br>
     * 如果是owner，则无视
     * 
     * @param admins
     *            域中的管理员登录名列表
     */
    void setAdmins(List<String> admins);

    /**
     * 增加域管理员（非持久化操作）
     * 
     * @param loginName
     *            用户登录名
     */
    void addAdmin(String loginName);

    /**
     * 删除一个域管理员（非持久化操作）<br>
     * 这个管理员将降级成为成员
     * 
     * @param loginName
     *            用户登录名
     */
    void removeAdmin(String loginName);

    /**
     * @param loginName
     *            用户登录名
     * @return 是否是管理员（owner 一定是管理员）
     */
    boolean isAdmin(String loginName);

    /**
     * @param loginName
     *            用户登录名
     * @return 是否仅是管理员
     */
    boolean isAdminOnly(String loginName);

    /**
     * @return 域中的成员
     */
    List<String> getMembers();

    /**
     * 重设域成员（非持久化操作）
     * 
     * @param members
     *            域中的成员
     */
    void setMembers(List<String> members);

    /**
     * 增加域成员（非持久化操作）
     * 
     * @param loginName
     *            用户登录名
     */
    void addMemeber(String loginName);

    /**
     * 删除一个域成员（非持久化操作）
     * 
     * @param loginName
     *            用户登录名
     */
    void removeMember(String loginName);

    /**
     * @param loginName
     *            用户登录名
     * @return 用户是否是成员，admin 一定是成员
     */
    boolean isMember(String loginName);

    /**
     * @param loginName
     *            用户登录名
     * @return 用户是否仅是成员
     */
    boolean isMemberOnly(String loginName);

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
