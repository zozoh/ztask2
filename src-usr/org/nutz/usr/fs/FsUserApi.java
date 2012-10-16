package org.nutz.usr.fs;

import java.io.File;
import java.util.regex.Pattern;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.usr.AbstractUserApi;
import org.nutz.usr.UErr;
import org.nutz.usr.User;
import org.nutz.usr.UserQuery;

@IocBean(name = "userApi")
public class FsUserApi extends AbstractUserApi {

    @Inject("java:$conf.get('usr-info-home')")
    private String home;

    @Override
    public void init() {
        Files.createDirIfNoExists(home);
    }

    @Override
    public Class<? extends User> getUserType() {
        return FsUser.class;
    }

    @Override
    public User fetch(String loginName) {
        return _u(_uf(loginName));
    }

    @Override
    protected User do_create(String loginName) {
        File f = _uf(loginName);
        Files.createFileIfNoExists(f);
        FsUser u = new FsUser();
        u.setLoginName(loginName);
        _wu(f, u);
        return u;
    }

    @Override
    public boolean remove(String loginName) {
        File f = _uf(loginName);
        return Files.deleteFile(f);
    }

    @Override
    public void clear() {
        Files.clearDir(Files.findFile(home));
    }

    @Override
    public long count() {
        File hm = _home();
        return hm.list().length;
    }

    @Override
    public long count(UserQuery q) {
        return query(q).size();
    }

    @Override
    public void each(final UserQuery q, Each<User> callback) {
        File hm = _home();
        if (null != callback) {
            // 看看是要用正则还是名字匹配
            Pattern regex = null;
            String name = null;
            try {
                regex = Pattern.compile(q.qName());
            }
            catch (Exception e1) {
                name = q.qName();
            }

            // 开始查找 ...s
            File[] fs = hm.listFiles();
            try {
                for (int i = 0; i < fs.length; i++) {
                    User u = _u(fs[i]);
                    // 正则...
                    if (null != regex && !regex.matcher(u.getLoginName()).find())
                        continue;

                    // 名字
                    if (null != name && !u.getLoginName().contains(name))
                        continue;

                    // 进行 Email 的筛选
                    if (!Strings.isBlank(q.qEmail())) {
                        if (Strings.isBlank(u.getEmail())
                            || !q.qEmail().equalsIgnoreCase(u.getEmail()))
                            continue;
                    }
                    // 进行 value 筛选
                    if (q.qValues() != null && q.qValues().length > 0) {
                        boolean matched = true;
                        for (String[] ss : q.qValues()) {
                            String fnm = ss[0];
                            Object val = u.getValue(fnm);
                            if (null == val || !val.toString().equalsIgnoreCase(ss[1])) {
                                matched = false;
                                break;
                            }
                        }
                        if (!matched)
                            continue;
                    }
                    // 调用回调
                    try {
                        callback.invoke(i, u, fs.length);
                    }
                    catch (ContinueLoop e) {}
                }
            }
            catch (ExitLoop e) {}
            catch (LoopException e) {
                throw Lang.wrapThrow(e);
            }
        }
    }

    @Override
    public void switchMajorEmail(User u, String fnm) {
        u = check(u.getLoginName());
        String email = checkValues(u).getString(fnm);
        if (Strings.isBlank(email) || !email.matches(_regex_email))
            throw UErr.INVALID_EMAIL(fnm + ":" + email);
        u.setEmail("?" + email);
        _wu(u);
    }

    @Override
    public void changePassword(User u, String pwdOld, String pwdNew) {
        // 检查新密码是否符合要求
        if (Strings.isBlank(pwdNew) || !pwdNew.matches(_regex_pwd))
            throw UErr.INVALID_PASSWORD();

        // 检查旧密码
        if (pwdOld != null) {
            if (!pwdOld.equals(u.getPassword())) {
                throw UErr.INVALID_PASSWORD();
            }
        }

        // 执行更新
        u = check(u.getLoginName());
        u.setPassword(pwdNew);
        _wu(u);
    }

    @Override
    public void setValue(User u, String fnm, Object val) {
        u = check(u.getLoginName());
        u.setValue(fnm, val);
        _wu(u);
    }

    private void _wu(User u) {
        if (null != u) {
            _wu(_uf(u.getLoginName()), u);
        }
    }

    private void _wu(File f, User u) {
        if (null != u) {
            Json.toJsonFile(f, u, JsonFormat.nice().setQuoteName(true));
        }
    }

    private User _u(File f) {
        if (null != f && f.exists())
            return Json.fromJson(FsUser.class, Streams.buffr(Streams.fileInr(f)));
        return null;
    }

    private File _uf(String loginName) {
        File hm = _home();
        File f = Files.getFile(hm, loginName);
        return f;
    }

    private File _home() {
        return Files.findFile(home);
    }
}
