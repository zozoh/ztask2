package org.nutz.ztask.web.module.avata;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.nutz.img.Images;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

/**
 * 提供形象管理的数据操作方法
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
@IocBean
@At("/avata")
public class ZTaskAvataModule {

    @Inject("java:$conf.get('sys-avata-home')")
    private String avata_home;

    /**
     * 读取一个头像信息
     * <p>
     * 接受的 URL 参数类型类似 "/u/zozoh/128x128"
     * 
     * @param cate
     *            分类字符串，现在只支持 "u" 和 "p"
     * @param nm
     *            形象数据文件的名称
     * @param sz
     *            要将原图片处理成什么样的尺寸
     */
    @At("/?/?/?")
    @Ok("raw:image/png")
    @Fail("void")
    public InputStream do_read(String cate, String nm, String sz) {
        File f = Files.findFile(String.format("%s/%s/%s/%s.png", avata_home, cate, nm, sz));
        // 获取默认对应尺寸图片
        if (null == f) {
            f = Files.findFile(String.format("avata/%s_%s.png", cate, sz));
            // 获取最终默认图片
            if (null == f) {
                f = Files.findFile("avata/default.png");
            }
        }
        return Streams.fileIn(f);
    }

    /**
     * 上传形象数据
     * 
     * <pre>
     * 接受如下 HEADER:
     * 
     * xhr_FILE_NAME : 文件本地名称
     * xhr_cate      : 分类字符串，现在只支持 "u" 和 "p"
     * xhr_nm        : 要写入的数据文件的名称
     * xhr_szs       : 多组尺寸，比如 128x128,64x64,32x32
     * </pre>
     * 
     * @throws IOException
     */
    @At("/upload")
    @Ok("ajax")
    public void do_upload(HttpServletRequest req) throws IOException {
        // TODO 首先验证权限
        // 如果是 u，那么看看是不是当前操作的账户
        // 如果是 p，那么看看项目是否存在，以及当前用户是不是这个项目的 owner

        // 处理 HEADER
        String cate = req.getHeader("xhr_cate");
        String nm = req.getHeader("xhr_nm");
        String szs = req.getHeader("xhr_szs");

        // 开始处理，首先确保目录存在
        File home = Files.createDirIfNoExists(avata_home + "/" + cate);

        // 分析尺寸
        String[] ss = Strings.splitIgnoreBlank(szs, ",");
        int[][] whs = new int[ss.length][];
        int max = 0;
        int maxW = 0;
        int maxH = 0;
        for (int i = 0; i < ss.length; i++) {
            String[] wh = Strings.splitIgnoreBlank(ss[i], "[xX]");
            int w = Integer.parseInt(wh[0]);
            int h = Integer.parseInt(wh[1]);
            whs[i] = new int[2];
            whs[i][0] = w;
            whs[i][1] = h;
            if (w * h > max) {
                max = w * h;
                maxW = w;
                maxH = h;
            }
        }

        // 读取图片数据
        BufferedImage im = ImageIO.read(Streams.buff(req.getInputStream()));

        // 首先将图片处理的小一点
        if (im.getWidth() * im.getHeight() > max) {
            im = Images.clipScale(im, maxW, maxH);
        }

        // 开始保存图片
        for (int i = 0; i < whs.length; i++) {
            int w = whs[i][0];
            int h = whs[i][1];
            File f = Files.createFileIfNoExists(String.format("%s/%s/%dx%d.png",
                                                              home.getAbsolutePath(),
                                                              nm,
                                                              w,
                                                              h));
            // 直接保存
            if (im.getWidth() == w) {
                Images.write(im, f);
            }
            // 缩小后保存
            else {
                BufferedImage im2 = Images.clipScale(im, w, h);
                Images.write(im2, f);
            }
        }

        // 搞定 ^_^
    }
}
