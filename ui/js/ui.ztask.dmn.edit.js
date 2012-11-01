(function($, ui, $z) {
    ui('ztask.dmn.edit', {
        extend: 'tabs.h',
        dft_option: {
            data: function(callback) {
                return {
                    '::dmn.avata': function(bind) {
                        ui('avatas').bind(bind, 'arena', {
                            sizes: ['128x128', '64x64', '32x32'],
                            cate: 'p',
                            nm: bind.proj.name
                        });
                    },
                    '::dmn.name': function(bind) {
                        bind.jq('.tabs-h-arena').text($(this).text());
                    },
                    '::dmn.general': function(bind) {
                        bind.jq('.tabs-h-arena').text($(this).text());
                    },
                    '::dmn.pvg': function(bind) {
                        bind.jq('.tabs-h-arena').text($(this).text());
                    },
                    '::dmn.member': function(bind) {
                        bind.jq('.tabs-h-arena').text($(this).text());
                    },
                    '::dmn.message': function(bind) {
                        bind.jq('.tabs-h-arena').text($(this).text());
                    },
                    '::dmn.stack': function(bind) {
                        bind.jq('.tabs-h-arena').text($(this).text());
                    },
                    '::dmn.advance': function(bind) {
                        bind.jq('.tabs-h-arena').text($(this).text());
                    }
                };
            }
        },
        on_init: function() {
            // 执行父类的初始化方法
            this.super().on_init.call(this);
            // 为顶级元素增加自己特殊的选择器名
            this.selection.children('.tabs-h').addClass('ztask-proj');
        }
    });
})(window.jQuery, window.NutzUI, window.NutzUtil);