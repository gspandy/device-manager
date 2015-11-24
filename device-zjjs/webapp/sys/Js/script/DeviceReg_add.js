var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			/*errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				model : {
					required : true
				},
				address : {
					required : true
				}
			},*/
			messages : {
				model : {
					required : "设备型号必填 "
				},
				address : {
					required : "设备放置地址必填"
				}
			}
			/*,
			highlight : function(element) {
				$(element).closest('.control-group').addClass('error');
			},

			success : function(label) {
				label.closest('.control-group').removeClass('error');
				label.remove();
			},

			errorPlacement : function(error, element) {
				element.parent('div').append(error);
			}*/
		});
		 $("#myform").validate();
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

function check(){
	var flag = true;
	if($('#devicetypeid').val()=="-1"){
		alert("请选择设备类型!");
		flag = false;
		return flag;
	}
	if($('#organizationid').val()=="-1"){
		alert("请选择组织机构!");
		flag = false;
		return flag;
	}
	if($('#usefor').val()=="-1"||$('#usefor').val()==null||$('#usefor').val()==""){
		alert("请选择业务类型!");
		flag = false;
		return flag;
	}
	if($('#admdivcode').val()=="-1"){
		alert("请选择区划!");
		flag = false;
		return flag;
	}
	//验证IP地址^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$
	var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	if(!reg.test($("#ip").val()))
	{
		alert("IP地址不合法!");
		flag = false;
		return flag;
	}
	return flag;
}

$(function() {
	// 保存按钮
	var form = document.getElementById('myform');
	$("#saveReg").click(function() {
		if(!check()){return;}
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();		
		if (!flag) {
			return;
		}else{
			var str = "";
			var usefor = $("#usefor").val();
			for (var i = 0; i < usefor.length; i++) {
				if(i!=usefor.length-1){
					str += usefor[i]+",";
				}else{
					str += usefor[i];
				}
		        
		    }
			$("#useforstr").attr("value",str);
			form.submit() ;
		}
		/*var msg = JSON.stringify($("#myform").serializeJson());
		
		$.post("../../DeviceRegistServlet?method=saveDeviceReg", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../DeviceReg/index.html";
			}
		});*/
	});
	$("#updateReg").click(function() {
		if(!check()){return;}
		MyValidator.init();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}else{
			var str = "";
			var usefor = $("#usefor").val();
			for (var i = 0; i < usefor.length; i++) {
				if(i!=usefor.length-1){
					str += usefor[i]+",";
				}else{
					str += usefor[i];
				}
		        
		    }
			$("#useforstr").attr("value",str);
			form.submit() ;
		}
		/*var msg = JSON.stringify($("#myform").serializeJson());
		
		$.post("../../DeviceRegistServlet?method=updateDeviceReg", {
			msg : msg
		}, function(result) {
			if (result == 'true') {
				alert("保存成功!");
				$('#myform')[0].reset()
				window.location.href="../DeviceReg/index.html";
			}
		});*/
	});

});