var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				oldpwd : {
					required : true
				},
				newpwd : {
					required : true
				},
				newpwd2 : {
					required : true
				}
			},
			messages : {
				oldpwd : {
					required : "原密码必填 "
				},
				newpwd : {
					required : "新密码必填"
				},
				newpwd2 : {
					required : "请再次输入新密码"
				}
			},

			highlight : function(element) {
				$(element).closest('.control-group').addClass('error');
			},

			success : function(label) {
				label.closest('.control-group').removeClass('error');
				label.remove();
			},

			errorPlacement : function(error, element) {
				element.parent('div').append(error);
			}
		});

		$('.form-horizontal input').keypress(function(e) {
			if (e.which == 13) {
				if ($('.form-horizontal').validate().form()) {
					$('.form-horizontal').submit();
				}
				return false;
			}
		});
	};
	return {
		init : function() {
			handleSubmit();
		}
	};

}();

$(function() {
	// 保存按钮
	$("#updateManager").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		if($('#newpwd').val()!=$('#newpwd2').val()){
			alert("两次输入的新密码不一致！");
			$("#newpwd").val("");
			$("#newpwd2").val("");
			return;
		}
		$.post("../../DeviceManagerServlet?method=updatePwd", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("密码修改成功!");
				window.location.href="../Manager/index.html";
			}else if(result == 'uncorrectpwd'){
				alert("原密码输入不正确！");
				return;
			}
		});
	});
	
});