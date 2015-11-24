var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				busiCode : {
					required : true
				},
				busiName : {
					required : true
				}
			},
			messages : {
				busiCode : {
					required : "业务编号必填 "
				},
				busiName : {
					required : "业务名称必填"
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
	$("#saveBusiType").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../BusiTypeServlet?method=saveBusiType", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../BusiType/index.html";
			}else if(result == 'existed'){
				alert("编码已存在,请重新输入!");
				return;
			}
		});
	});
	
	$("#updateBusiType").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../BusiTypeServlet?method=updateBusiType", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../BusiType/index.html";
			}
		});
	});
	

});