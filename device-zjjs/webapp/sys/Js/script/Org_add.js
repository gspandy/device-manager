var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				code : {
					required : true
				},
				name : {
					required : true
				}
			},
			messages : {
				code : {
					required : "组织机构编号必填 "
				},
				name : {
					required : "组织机构名称必填"
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
	$("#saveOrg").click(function() {
		MyValidator.init();
		var pid = $("#parentid").val();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		var id = $("#id").val();
		if(id==""||id==null){
			$.post("../../OrganizationServlet?method=saveOrg", {
				msg : msg
			}, function(result) {
				if (result == 'true') {
					alert("保存成功!");
					$('#myform')[0].reset()
					window.location.href="../Organization/index.html";
				}else if(result == 'existed'){
					alert("组织机构编码已存在，请重新输入!");
					return;
				}
			});
		}else{
			$.post("../../OrganizationServlet?method=updateOrg", {
				msg : msg
			}, function(result) {
				if (result == 'true') {
					alert("保存成功!");
					$('#myform')[0].reset()
					window.location.href="../Organization/index.html";
				}
			});
		}
		
	});	
	
});