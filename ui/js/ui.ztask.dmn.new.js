(function($, ui, $z) {
    ui('ztask.dmn.new', {
        events : {
            'focus:.dmn-new-input' : function() {
                $z.ui.getBind(this).jq('.dmn-new-do').addClass('dmn-new-do-off');
            },
            'change:.dmn-new-input' : function() {
                var myBind = $z.ui.getBind(this);
                var str = $.trim($(this).val());
                // 打开按钮
                if(str) {
                    myBind.jq('.dmn-new-do').removeClass('dmn-new-do-off');
                }
            }
        },
        on_init: function() {
            var html = '<div class="dmn-new">';
            html += '<div class="dmn-new-title">' + $z.ui.msg('dmn.new') + '</div>';
            html += '<div class="dmn-new-tip">' + $z.ui.msg('dmn.new.tip') + '</div>';
            html += '<div class="dmn-new-form">';
            html += '    <input class="dmn-new-input">';
            html += '    <div class="dmn-new-check" style="display:none;">' + $z.ui.msg('dmn.new.checking') + '</div>';
            html += '</div>'
            html += '<div class="dmn-new-do dmn-new-do-off btn">' + $z.ui.msg('dmn.new.do') + '</div>';
            html += '</div>';
            this.selection.html(html);
        }
    });
})(window.jQuery, window.NutzUI, window.NutzUtil);