(function($, ui, $z) {
    /*
     * 根 UI
     * 扩展点:
     *   > arena : 下部主绘制区域
     */
    ui("zTaskMain", {
        keepDom: true,
        extend: "",
        on_ready: function() {
            this.jq(".sky-info-me").click();
        },
        on_resize: function(w, h) {
            var skyH = $("#sky").height();
            $("#arena").css("height", h - skyH);
            var infoW = $("#skyInfo").attr("css-width") * 1;
            $("#skyInfo").css("width", infoW);
            $("#skyMenu").css("width", w - infoW);
        },
        gasket: function(nm) {
            return nm == "arena" ? $("#arena") : null;
        },
        events: {
            // 当点击账户设置的时候，绑定用户设置子 UI
            "click:.sky-info-me": function() {
                var myBind = $z.ui.getBind(this);
                // 绑定子 UI
                ui("ztask.usr").bind(myBind, "arena");
            }
        }
    });

    // zTask 的主函数
    $(document).ready(function() {
        $z.keyboard.watch();
        var bind = ui("zTaskMain").bind("body").resize();
    });

})(window.jQuery, window.NutzUI, window.NutzUtil);