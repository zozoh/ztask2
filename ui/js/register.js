$(document).ready(function(){
	$("form").password({
		msg : function(key) {
			var attName = "msg-" + key.replace(/[.]/,"-");
			return $(document.body).attr(attName);
		},
		on_ready : function() {
			$("form .regi-submit").addClass("regi-submit-on");
		},
		on_unmatch : function() {
			$("form .regi-submit").removeClass("regi-submit-on");	
		}
	});

	$(".regi-submit").click(function(){
		// 表单数据不正确，无视
		if(!$(this).hasClass("regi-submit-on"))
			return;

		// 去掉重复输入的密码
		$(this).parents("form").find(".pwd-re").val("-you-should-not-know-it-");

		// 加密
		var pwd = $(this).parents("form").find("input[name=pwd]");
		var pwd_encrypt_method = pwd.attr("method");
		if(pwd_encrypt_method)
			pwd.val(CryptoJS[pwd_encrypt_method](pwd.val()));

		$(this).parents("form")[0].submit();
	});
});