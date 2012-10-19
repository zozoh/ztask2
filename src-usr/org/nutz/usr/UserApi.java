package org.nutz.usr;

import java.util.List;

import org.nutz.lang.Each;
import org.nutz.web.Initializable;

public interface UserApi extends Initializable {

    /**
     * @return 当前用户类的类型
     */
    Class<? extends User> getUserType();

    /**
     * 获取一个用户
     * 
     * @param loginName
     *            用户登录名
     * @return 用户对象，null 表示不存在
     */
    User fetch(String loginName);

    /**
     * 获取一个用户，如果用户不存在，将抛出:
     * 
     * <pre>
     * e.u.no_exists
     * </pre>
     * 
     * @param loginName
     *            用户登录名
     * @return 用户对象，本函数不会返回 null
     */
    User check(String loginName);

    /**
     * 确保当前 User 对象的 values 对象被加载了<br>
     * 如果传入 User 对象已经有了 values 对象，本函数将跳过执行
     * 
     * @param u
     *            用户对象
     * @return 传入的用户对象以便链式赋值
     */
    User checkValues(User u);

    /**
     * 创建一个新用户
     * <p>
     * 本函数可能产生如下的错误:
     * 
     * <pre>
     * e.u.email_used
     * e.u.invalid_name
     * e.u.invalid_password
     * e.u.exists
     * </pre>
     * 
     * @param loginName
     *            登录名
     * @param pwd
     *            密码
     * @param email
     *            主邮箱
     * @return 新创建的用户对象，绝对不会返回 null
     */
    User create(String loginName, String pwd, String email);

    /**
     * 彻底从数据库中将某用户删除
     * 
     * @param loginName
     *            用户登录名
     * @return 是否移除成功，false 表示用户原先就不存在
     */
    boolean remove(String loginName);

    /**
     * @return 用户的总数
     */
    long count();

    /**
     * @param q
     *            用户查询对象
     * @return 符合条件的用户的总数
     */
    long count(UserQuery q);

    /**
     * @param keyword
     *            用户查询关键字
     * @return 符合条件的用户的总数
     */
    long count(String keyword);

    /**
     * 查询一组用户
     * 
     * @param q
     *            用户查询对象
     * @return 符合条件的用户列表
     */
    List<User> query(UserQuery q);

    /**
     * 查询一组用户
     * 
     * @param keyword
     *            查询关键字
     * @return 符合条件的用户列表
     * @see UserQuery
     */
    List<User> query(String keyword);

    /**
     * 迭代一组用户
     * 
     * @param q
     *            用户查询对象
     * @param callback
     *            每个用户的特殊处理
     */
    void each(UserQuery q, Each<User> callback);

    /**
     * 迭代一组用户
     * 
     * @param keyword
     *            用户查询关键字
     * @param callback
     *            每个用户的特殊处理
     */
    void each(String keyword, Each<User> callback);

    /**
     * 将某个 Email 字段变成账户的主 Email。
     * <p>
     * 账户的主 Email 将作为该账户的 Email 通知接收账户。这里有一个规定:
     * 
     * <pre>
     * 如果主 Email 为 abc@xyz.com
     * 首次切换时， 用户的主 Email 字段值将为 "?abc@xyz.com" 表示这个邮箱没有被验证。
     * 如果验证完毕，该字段的值将变成 "abc@xyz.com"
     * </pre>
     * 
     * @param u
     *            用户
     * @param fnm
     *            Email 字段名
     */
    void switchMajorEmail(User u, String fnm);

    /**
     * 修改一个用户的密码
     * 
     * @param u
     *            用户对象
     * @param pwdOld
     *            旧密码，可以为 null，如果为 null 不检查旧密码
     * @param pwdNew
     *            新密码，不可以为 null
     */
    void changePassword(User u, String pwdOld, String pwdNew);

    /**
     * 更新用户某一个字段的值
     * 
     * @param u
     *            用户对象
     * @param fnm
     *            用户字段名称
     * @param val
     *            字段值，如果值为 null，则表示删除
     */
    void setValue(User u, String fnm, Object val);

}
