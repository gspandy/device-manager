var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				resourcecode : {
					required : true
				},
				resourcename : {
					required : true
				}
			},
			messages : {
				resourcecode : {
					required : "资源编号必填 "
				},
				resourcename : {
					required : "资源名称必填"
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
	$("#saveRes").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		var id = $("#id").val();
		if(msg.menutype==""||msg.menutype==null){
			msg.menutype = 0;
		}
		if(id==""||id==null){
			$.post("../../SysResourceServlet?method=saveResource", {
				msg : msg
			}, function(result) {
				if (result == 'true') {
					alert("保存成功!");
					$('#myform')[0].reset();
					window.location.href="../Resource/index2.html";
				}else if(result == 'existed'){
					alert("资源编码已存在,请重新输入!");
					return;
				}
			});
		}else{
			$.post("../../SysResourceServlet?method=updateResource", {
				msg : msg
			}, function(result) {
				if (result == 'true') {
					alert("保存成功!");
					$('#myform')[0].reset();
					window.location.href="../Resource/index2.html";
				}else if(result == 'existed'){
					//alert("角色编码已存在,请重新输入!");
					return;
				}
			});
		}
		
	});
	
});