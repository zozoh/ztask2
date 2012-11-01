(function($, ui, $z) {
    ui('ztask.dmn.mana', {
        on_init: function() {
            var html = '<div class="dmn-mana">';
            html += '</div>';
            this.selection.html(html);
        }
    });
})(window.jQuery, window.NutzUI, window.NutzUtil);