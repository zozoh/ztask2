(function($, ui, $z) {
    // 主控件
    ui('zTaskMain', {
        keepDom: true,
        on_ready: function() {
            this.jq('.sky-info-dmn').click();
        },
        on_resize: function(w, h) {
            var skyH = $('#sky').height();
            $('#arena').css('height', h - skyH);
            var infoW = $('#skyInfo').attr('css-width') * 1;
            $('#skyInfo').css('width', infoW);
            $('#skyMenu').css('width', w - infoW);
        },
        gasket: function(nm) {
            return nm == 'arena' ? $('#arena') : null;
        },
        events: {
            '.sky-info-dmn': function() {
                ui('ztask.dmn.mana').bind($z.ui.getBind(this), 'arena');
            },
            '.sky-info-me': function() {
                ui('ztask.usr').bind($z.ui.getBind(this), 'arena');
            }
        }
    });

    // zTask 的主函数
    $(document).ready(function() {
        $z.keyboard.watch();
        var bind = ui('zTaskMain').bind('body').resize();
    });

})(window.jQuery, window.NutzUI, window.NutzUtil);