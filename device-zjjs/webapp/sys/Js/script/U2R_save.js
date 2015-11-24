var MyValidator = function() {
	var handleSubmit = function() {
		$('.form-horizontal').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			rules : {
				/*code : {
					required : true
				},
				name : {
					required : true
				}*/
			},
			messages : {
				/*code : {
					required : "组织机构编号必填 "
				},
				name : {
					required : "组织机构名称必填"
				}*/
			},

			highlight : function(element) {
				$(element).closest('.form-group').addClass('has-error');
			},

			success : function(label) {
				label.closest('.form-group').removeClass('has-error');
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
	function checkUser(){
		var usertree = $.fn.zTree.getZTreeObj("usertree");	
		var nodes = usertree.getNodes();
		var snodes = usertree.getCheckedNodes();
		var arr = new Array();
		if(snodes.length==0&&nodes.length>0){//存在节点，但没有选中节点
			alert("请选择一个用户!");
			return;
		}else if(snodes.length==0&&nodes.length==0){//没有任何节点也没有选中的节点
			return;
		}/*else if(snodes.length>1){//选中了多个节点
			alert("用户不允许多选!");
			return;
		}else{//选中了一个用户
			return snodes[0].id;  
		}*/
		var cnode = usertree.getCheckedNodes();
		if(cnode.length>0){
			for(var i=0;i<cnode.length;i++){
				arr.push(cnode[i].id);
			}			 
		}
		return JSON.stringify(arr);
	}

	function checkRole(){
		var roletree = $.fn.zTree.getZTreeObj("roletree");
		var cnode = roletree.getCheckedNodes();
		if(cnode.length==0){
			alert("请选择一个角色!");
		}else if(cnode.length==1){
			return cnode[0].id;
		}
	}
	
	// 保存按钮
	$("#save").click(function() {
		/*MyValidator.init();
		var pid = $("#parentid").val();
		var flag = $('.form-horizontal').validate().form();
		if (!flag) {
			return;
		}
		var msg = JSON.stringify($("#myform").serializeJson());*/
		//var id = $("#id").val();

			var ids = checkUser();
			var roleid = checkRole();
			$.post("../../ResourceAuthorityServlet?method=saveUsersRole", {
				ids : ids,
				roleid : roleid
			}, function(result) {
				if (result == 'true') {
					alert("保存成功!");
					
					window.location.href="../User2Role/index.html";
				}else if(result == 'existed'){
					/*alert("组织机构编码已存在，请重新输入!");
					return;*/
				}
			});		
	});	
	
});

