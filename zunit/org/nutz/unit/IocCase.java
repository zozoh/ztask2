package org.nutz.unit;

import org.junit.After;
import org.junit.Before;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.AnnotationIocLoader;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.lang.Lang;
import org.nutz.web.WebConfig;

/**
 * 本基类假设:
 * <ul>
 * <li>测试需要 MongoDB
 * <li>测试有一个 Ioc 容器
 * </ul>
 * 对于 Ioc 容器的配置，它还需要如下细节:
 * 
 * <pre>
 * Ioc 容器默认的包扫描路径是 "org.nutz"
 * CLASSPATH 必须存在一个 ioc 的文件夹，这里面放置了所有 JSON 配置文件，
 * 
 *     如果想改变，请重载默认构造函数来设置 
 *         "ioc_json_path"
 *         "ioc_anno_packages"
 *     这两个成员变量
 *     
 * 容器中必须存在一个 $connector 对象，类型为 org.nutz.mongo.MongoConnector
 *     
 * 容器中，必须存在一个 $conf 对象，类型为 org.nutz.web.WebConfig，这个对象必须有一个签名为:
 *      public MongoDao dao(MongoConnector conn, String dbKey)
 * 的方法以便本类获取一个 MongoDao
 * 
 * 同事， $conf 对象中，必须包括 "db-name" 的项，制定一个数据库名称
 * </pre>
 * 
 * 子类可以通过重载 onBefore 和 onAfter 来设定自定义的测试用例初始化和注销操作
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class IocCase {

    protected WebConfig conf;

    protected Ioc ioc;

    protected String[] ioc_json_path;

    protected String[] ioc_anno_packages;

    public IocCase() {
        ioc_json_path = Lang.array("ioc");
        ioc_anno_packages = ioc_anno_packages();
    }

    protected abstract String[] ioc_anno_packages();

    @Before
    public void _before() {
        // 修改测试用例后缀
        WebConfig.JUNIT_DB_SUFFIX = "_unit";

        // 构建 Ioc 容器
        JsonLoader jsonL = new JsonLoader(ioc_json_path);
        AnnotationIocLoader annoL = new AnnotationIocLoader(ioc_anno_packages);
        ComboIocLoader loader = new ComboIocLoader(jsonL, annoL);
        ioc = new NutIoc(loader);

        // 获取 Dao 对象
        conf = ioc.get(WebConfig.class, "conf");
        setupConf(conf);

        // 调用子类用例初始化函数
        onBefore();
    }

    /**
     * 这个方法可以被子类用来修改容器中的 conf 对象的值
     * 
     * @param conf
     *            容器中的 $conf
     */
    protected void setupConf(WebConfig conf) {}

    @After
    public void _after() {
        // 调用子类用例注销函数
        onAfter();

        // 注销容器
        ioc.depose();
    }

    protected void onBefore() {}

    protected void onAfter() {}
}
