var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			/*errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			
			rules : {
				usercode : {
					required : true
				},
				password : {
					required : true
				},
				username : {
					required : true
				},
				dept : {
					required : true
				},
				sex : {
					required : true
				},
				isenabled : {
					required : true
				}
			},*/
			messages : {
				usercode : {
					required : "账号必填 "
				},
				password : {
					required : "密码必填"
				},
				username : {
					required : "姓名必填"
				},
				dept : {
					required : "部门必填"
				},
				sex : {
					required : "性别必填" 
				},
				isenabled : {
					required : "性别必填"
				},
				code : {
					required : "用户编码必填"
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
				element.append(error);
			}
		});
		//$("#myform").validate();
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

function check(data){
	var flag = data.error;
	if(flag){
		window.parent.location.href="../Public/login.html"
	}
}

var reg=/^[a-z0-9](\w|\.|-)*@([a-z0-9]+-?[a-z0-9]+\.){1,3}[a-z]{2,4}$/i;

$(function() {
	// 保存按钮
	$("#saveUser").click(function() {
		MyValidator.init();
		var email = $("#email").val().replace(/^\s+|\s+$/g,"").toLowerCase();//去除前后空格并转小写
		if(email==""||email==null){
			
		}else{
			if(email.match(reg)==null) {
			    alert("请输入有效的邮箱地址");
			    return;
		    }
		}
		
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../UserInfoServlet?method=saveUser", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				window.location.href="../User/index.html";
			}else if(result == 'existed'){
				alert("登录名已存在，请重新输入");
				return;
			}else if(result == "existed code"){
				alert("用户编码已存在，请重新输入");
				return;
			}
		});
	});
	$("#updateUser").click(function() {
		MyValidator.init();
		var email = $("#email").val().replace(/^\s+|\s+$/g,"").toLowerCase();//去除前后空格并转小写
		if(email==""||email==null){
			
		}else{
			if(email.match(reg)==null) {
			    alert("请输入有效的邮箱地址");
			    return;
		    }
		}
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../UserInfoServlet?method=updateUser", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				window.location.href="../User/index.html";
			}else if(result == 'existed'){
				alert("登录名已存在，请重新输入");
				return;
			}else if(result == "existed code"){
				alert("用户编码已存在，请重新输入");
				return;
			}
		});
	});
	

});