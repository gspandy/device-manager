var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				managercode : {
					required : true
				},
				password : {
					required : true
				},
				name : {
					required : true
				}
			},
			messages : {
				managercode : {
					required : "账号必填 "
				},
				password : {
					required : "密码必填"
				},
				name : {
					required : "姓名必填"
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

function validateIdCard(idCard){
	 var flag = false;
	 //15位和18位身份证号码的正则表达式
	 var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

	 //如果通过该验证，说明身份证格式正确，但准确性还需计算
	 if(regIdCard.test(idCard)){
	  if(idCard.length==18){
	   var idCardWi=new Array( 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ); //将前17位加权因子保存在数组里
	   var idCardY=new Array( 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
	   var idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
	   for(var i=0;i<17;i++){
	    idCardWiSum+=idCard.substring(i,i+1)*idCardWi[i];
	   }

	   var idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
	   var idCardLast=idCard.substring(17);//得到最后一位身份证号码

	   //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
	   if(idCardMod==2){
	    if(idCardLast=="X"||idCardLast=="x"){
	     //alert("恭喜通过验证啦！");
	    	flag = true;
	    }else{
	     /*alert("身份证号码错误！");
	     return;*/
	    	flag = false;
	    }
	   }else{
	    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
	    if(idCardLast==idCardY[idCardMod]){
	     //alert("恭喜通过验证啦！");
	    	flag = true;
	    }else{
	     /*alert("身份证号码错误！");
	     return;*/
	    	flag = false
	    }
	   }
	  } 
	 }else{
		 /*alert("身份证格式不正确!");
		 return;*/
		 flag = false;
	 }
	 return flag;
}


$(function() {
	// 保存按钮	
	$("#saveManager").click(function() {
		var idcard = $("#managercode").val();	
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		if(!validateIdCard(idcard)){
			alert("身份证格式不正确！");
			return;
		}		
		var msg = JSON.stringify($("#myform").serializeJson());				
		$.post("../../DeviceManagerServlet?method=saveManager", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset();
				window.location.href="../Manager/index.html";
			}else if(result == 'existed'){
				alert("账号已存在,请重新输入!");
				return;
			}
		});
	});
	
	$("#updateManager").click(function() {
		var idcard = $("#managercode").val();
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		if(!validateIdCard(idcard)){
			alert("身份证格式不正确！");
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());
		$.post("../../DeviceManagerServlet?method=updateManager", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset();
				window.location.href="../Manager/index.html";
			}
		});
	});
	

});