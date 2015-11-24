var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				typecode : {
					required : true
				},
				typename : {
					required : true
				}
			},
			messages : {
				typecode : {
					required : "类别编号必填 "
				},
				typename : {
					required : "类别名称必填"
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
	$("#saveDeviceType").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../DeviceTypeServlet?method=saveDeviceType", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../DeviceType/index.html";
			}else if(result == 'existed'){
				alert("编码已存在,请重新输入!");
				return;
			}
		});
	});
	$("#updateDeviceType").click(function() {
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../DeviceTypeServlet?method=updateDeviceType", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../DeviceType/index.html";
			}
		});
	});
	

});