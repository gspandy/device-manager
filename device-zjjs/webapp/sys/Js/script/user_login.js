var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-signin').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : true,
			rules : {
				usercode : {
					required : true
				},
				password : {
					required : true
				}
			},
			messages : {
				usercode : {
					required : "账号必填 "
				},
				password : {
					required : "密码必填"
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
				element.parent('div').parent('div').append(error);
			}
		});

		$('.form-signin input').keypress(function(e) {
			if (e.which == 13) {
				if ($('.form-signin').validate().form()) {
					$('.form-signin').submit();
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

$(document).keydown(function(e){
	var e = event || window.event || arguments.callee.caller.arguments[0];                    
     if(e && e.keyCode==13){ // enter 键
         //要做的事情
    	 $("#login").focus();
    	 $("#login").click();
    	 //dologin();
    }
});

function dologin(){
	MyValidator.init();
	var flag = $('.form-signin').validate().form();
	if (!flag) {
		return;
	}
	//var msg = JSON.stringify($("#myform").serializeJson());
	var usercode = $("#usercode").val();
	var userpwd = $("#password").val();
	var admdivcode = $("#admdivcode").val();
	var year = $("#year").val();
	var chk = $("#rem").get(0).checked;
	$.ajax({
        type: "get",
        dataType: "json",
        url: "../../UserInfoServlet?method=checkUser&round="+Math.round(),
        data: {"code":usercode,"pwd":userpwd,"admdivcode":admdivcode,"year":year},	
        async: false,
        cache:false,
        success: function(data){
        	if(data==null){
        		alert("账号或密码有误！");
          		return;
        	}else{
        		var userid = data.id;
            	var usercode = data.usercode;
            	var username = data.username;
            	var admdivcode = data.admdivcode;
            	var user = new Object();
            	user.userid=userid;
            	user.usercode=usercode;
            	user.username=username;
            	user.admdivcode = admdivcode;
            	var user_str = JSON.stringify(user); //JSON 数据转化成字符串
            	$.cookie("user", user_str,{path:"/"});           	          	
            	if(chk){
            		$.cookie("remember", true, {expires:30,path:"/"});
            		$.cookie("usercode", usercode, {expires:30,path:"/"});
            		$.cookie("year", year, {expires:30,path:"/"});
            		$.cookie("admdivcode", admdivcode, {expires:30,path:"/"});
            	}else{
            		$.cookie("usercode", "", {path:"/"});
            		$.cookie("remember", false,{path:"/"});
            	}
            	window.location.href="../index.html?round="+Math.random();   
        	}        	 	
        } , error: function(XMLHttpRequest, textStatus, errorThrown){
        	 if(XMLHttpRequest.responseText=="unabled"){
        		alert("该账号已停用！");
          		return;
        	}else if(XMLHttpRequest.responseText=="error"){
        		alert("无匹配的用户，请检查！");
          		return;
        	}
        }
  })
}

function loadYear(){//加载年度
	var year = $.cookie('year');
	$.ajax({
		type : "get",
		url : "../../UserInfoServlet?method=loadBusiYear",
		//data:{"typeid":typeid},
		dataType : "json",
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$.each(data, function(i) {
				if(data[i]==year){
					document.getElementById("year").options.add(new Option(
							data[i],data[i], true, true));
				}else{
					document.getElementById("year").options.add(new Option(
							data[i],data[i], false, false));

				}
			});
		},
		error : function() {
			alert("请求出错");
		}
	});
}

function loadAdm(){//加载区划	
	var admdivcode = $.cookie('admdivcode');
	$.ajax({
		type : "get",
		url : "../../AdmdivServlet?method=getAllAdm",
		//data:{"typeid":typeid},
		dataType : "json",
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			$.each(data, function(i) {
				if(data[i].code==admdivcode){
					document.getElementById("admdivcode").options.add(new Option(
							data[i].code+" "+data[i].name,data[i].code, true, true));
				}else{
					document.getElementById("admdivcode").options.add(new Option(
							data[i].code+" "+data[i].name,data[i].code , false, false));

				}
			});
		},
		error : function() {
			alert("请求出错");
		}
	});
}

$(document).ready(function() {  
	var chk = $.cookie('remember');
	if(chk=="true"){
		$("#rem").attr("checked",true);
		$("#usercode").attr("value",$.cookie('usercode'));
	}else{
		$("#rem").attr("checked",false);
	}
	 //loadYear();
	 //loadAdm();
});