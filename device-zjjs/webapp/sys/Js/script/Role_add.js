var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				rolecode : {
					required : true
				},
				rolename : {
					required : true
				}
			},
			messages : {
				rolecode : {
					required : "角色编号必填 "
				},
				rolename : {
					required : "角色名称必填"
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
	$("#saveRole").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../SysRoleServlet?method=saveRole", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../Role/index.html";
			}else if(result == 'existed'){
				alert("角色编码已存在,请重新输入!");
				return;
			}
		});
	});
	
	$("#updateRole").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../SysRoleServlet?method=updateRole", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../Role/index.html";
			}
		});
	});
	

});