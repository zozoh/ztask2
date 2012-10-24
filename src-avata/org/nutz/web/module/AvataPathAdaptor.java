package org.nutz.web.module;

public interface AvataPathAdaptor {

    /**
     * 获取图片路径
     * 
     * @param cate
     *            分类字符串，现在只支持 "u" 和 "p"
     * @param nm
     *            形象数据文件的名称
     * @param sz
     *            要将原图片处理成什么样的尺寸，比如 64x64
     * @return 图片相对 home 的路径，比如 "u/zozoh/64x64"
     */
    String getPath(String cate, String nm, String sz);

}
