(function($, ui, $z) {
    ui('ztask.dmn.mana', {
        dft_option : {
            'className' : 'dmn-mana',
            'cols' : [300, '*', '*'],
            'rows' : ['*']
        },
        extend : 'cells',
        on_init: function() {
            this.super().on_init.call(this);
        },
        on_ready : function() {
            ui('ztask.dmn.new').bind(this, 'g0_0');
        }
    });
})(window.jQuery, window.NutzUI, window.NutzUtil);