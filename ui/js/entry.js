(function($) {
    $(document).ready(function() {
        $('.entry-btn').click(function() {
            var jForm = $(this).parents('form');
            var jPwd = jForm.find('input[type=password]');
            // 加密
            var pwd = jPwd.val();
            var pwd_encrypt_method = jPwd.attr('method');
            if (pwd_encrypt_method) {
                jPwd.val(CryptoJS[pwd_encrypt_method](jPwd.val()));
            }
            // 提交
            jForm[0].submit();
        });
    });
})(window.jQuery);
