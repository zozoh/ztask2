package org.nutz.dmn;

import java.util.List;

import org.nutz.lang.Each;
import org.nutz.usr.User;
import org.nutz.web.Initializable;

public interface DomainApi extends Initializable {

    /**
     * @return 当前域类的类型
     */
    Class<? extends Domain> getDomainType();

    /**
     * 获取一个域
     * 
     * @param name
     *            域名
     * @return 域对象，null 表示不存在
     */
    Domain fetch(String name);

    /**
     * 判断一个域是否存在
     * 
     * @param name
     *            域名
     * @return 是否存在
     */
    boolean exists(String name);

    /**
     * 获取一个用户，如果用户不存在，将抛出:
     * 
     * <pre>
     * e.d.no_exists
     * </pre>
     * 
     * @param name
     *            域名
     * @return 域对象，本函数不会返回 null
     */
    Domain check(String name);

    /**
     * 确保当前域对象的 values 对象被加载了<br>
     * 如果传入域对象已经有了 values 对象，本函数将跳过执行
     * 
     * @param d
     *            域对象
     * @return 传入的域对象以便链式赋值
     */
    Domain checkValues(Domain d);

    /**
     * 创建一个新用户
     * <p>
     * 本函数可能产生如下的错误:
     * 
     * <pre>
     * e.d.exists
     * </pre>
     * 
     * @param dmn
     *            域名
     * @param owner
     *            项目所有者
     * @return 新创建的域对象，绝对不会返回 null
     */
    Domain create(String name, User owner);

    /**
     * 彻底从数据库中将某域删除
     * 
     * @param name
     *            域名
     * @return 是否移除成功，false 表示域原先就不存在
     */
    boolean remove(String name);

    /**
     * @return 域的总数
     */
    long count();

    /**
     * @param q
     *            域查询对象
     * @return 符合条件的域的总数
     */
    long count(DomainQuery q);

    /**
     * @param keyword
     *            域查询关键字
     * @return 符合条件的域的总数
     */
    long count(String keyword);

    /**
     * @param q
     *            域查询对象
     * @return 符合条件的域列表
     */
    List<Domain> query(DomainQuery q);

    /**
     * @param keyword
     *            域查询关键字
     * @return 符合条件的域列表
     */
    List<Domain> query(String keyword);

    /**
     * 迭代一组用户
     * 
     * @param q
     *            域查询对象
     * @param callback
     *            每个域的特殊处理
     */
    void each(DomainQuery q, Each<Domain> callback);

    /**
     * 迭代一组用户
     * 
     * @param keyword
     *            域查询关键字
     * @param callback
     *            每个域的特殊处理
     */
    void each(String keyword, Each<Domain> callback);

    /**
     * 统计域有多少关注者
     * 
     * @param dnm
     *            域名
     * @return 关注者数量
     */
    int countWatchers(String dnm);

    /**
     * 查询域的关注者列表
     * 
     * @param dnm
     *            域名
     * @param keyword
     *            查询关键字，过滤关注者名称的一部分
     * @param pn
     *            第几页，小于等于 0 表示不分页
     * @param pgsz
     *            每页大小，最小不能小于 1
     * @return 关注者列表
     */
    List<String> queryWatcher(String dnm, String keyword, int pn, int pgsz);

    /**
     * 为域增加一个关注者
     * 
     * @param d
     *            域对象
     * @param loginName
     *            用户登录名
     */
    void addWatcher(Domain d, String loginName);

    /**
     * 为域移除一个关注者
     * 
     * @param d
     *            域对象
     * @param loginName
     *            用户登录名
     */
    void removeWatcher(Domain d, String loginName);

    /**
     * @param dnm
     *            域名
     * @param loginName
     *            用户登录名
     * @return 该用户是否关注域
     */
    boolean isWatch(String dnm, String loginName);

    /**
     * 增加域管理员（非持久化操作）
     * 
     * @param dnm
     *            域名
     * @param loginName
     *            用户登录名
     */
    Domain addAdmin(String dnm, String loginName);

    /**
     * 删除一个域管理员（非持久化操作）<br>
     * 这个管理员将降级成为成员
     * 
     * @param dnm
     *            域名
     * @param loginName
     *            用户登录名
     */
    Domain removeAdmin(String dnm, String loginName);

    /**
     * @param dnm
     *            域名
     * @param loginName
     *            用户登录名
     * @return 是否是管理员
     */
    boolean isAdmin(String dnm, String loginName);

    /**
     * 增加域成员（非持久化操作）
     * 
     * @param dnm
     *            域名
     * @param loginName
     *            用户登录名
     */
    Domain addMemeber(String dnm, String loginName);

    /**
     * 删除一个域成员（非持久化操作）
     * 
     * @param dnm
     *            域名
     * @param loginName
     *            用户登录名
     */
    Domain removeMember(String dnm, String loginName);

    /**
     * 保存一个域对象全部的信息
     * 
     * @param d
     *            域对象
     */
    void save(Domain d);

    /**
     * 更新域的某个字段的值
     * 
     * @param d
     *            域对象
     * @param fnm
     *            用户字段名称
     * @param val
     *            字段值，如果值为 null，则表示删除
     */
    void setValue(Domain d, String fnm, Object val);

}
