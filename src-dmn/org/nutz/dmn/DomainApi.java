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
     * 清除所有域数据
     */
    void clear();

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
     * 更新域的某个字段的值
     * 
     * @param d
     *            域对象
     * @param fnm
     *            用户字段名称
     * @param val
     *            字段值，如果值为 null，则表示删除
     */
    void setValue(Domain u, String fnm, Object val);

}
