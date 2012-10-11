(function($, ui, $z){
/*
 * 横向标签布局控件
 * 扩展点:
 *   > arena : 右侧主绘制区域
 */
ui("ztask.usr", {
	extend     : "tabs.h",
	dft_option : {
		data : function(callback){
			return {
				"::u.me.profile" : function(bind){
					ui("usr.profile").bind(bind, "arena");
				},
				"::u.me.avata"   : function(bind){
					ui("avatas").bind(bind, "arena", {
						sizes: ["128x128", "64x64", "32x32"],
						cate : "u",
						nm   : $z.ui.myName()
					});
				},
				"::u.me.chgpwd"  : function(bind){
					ui("usr.pwd").bind(bind, "arena", {
						encrypt : $(document.body).attr("nutz-usr-pwd-method")
					});
				},
				"::u.me.messages": function(bind){
					$z.log.d($(this).text());
				},
				"::u.me.projs"   : function(bind){
					$z.log.d($(this).text());
				}
			};
		}
	},
	on_init : function(){
		// 执行父类的初始化方法
		this.super().on_init.call(this);
		// 为顶级元素增加自己特殊的选择器名
		this.selection.children(".tabs-h").addClass("ztask-usr");  
	}
});
})(window.jQuery, window.NutzUI, window.NutzUtil);
