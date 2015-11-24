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
	function checkRole(){
		var roletree = $.fn.zTree.getZTreeObj("roletree");	
		var nodes = roletree.getNodes();
		var snodes = roletree.getSelectedNodes();
		if(snodes.length==0&&nodes.length>0){//存在节点，但没有选中节点
			alert("请选择一个角色!");
			return;
		}else if(snodes.length==0&&nodes.length==0){//没有任何节点也没有选中的节点
			 
		}else if(snodes.length>1){//选中了多个节点
			alert("角色不允许多选!");
			return;
		}else{
			return snodes[0].id;  
		}
	}

	function checkResource(){
		var arr = new Array();
		var roletree = $.fn.zTree.getZTreeObj("restree");
		var cnode = roletree.getCheckedNodes();
		if(cnode.length>0){
			for(var i=0;i<cnode.length;i++){
				arr.push(cnode[i].id);
			}			 
		}
		return JSON.stringify(arr);
	}
	
	// 保存按钮
	$("#save").click(function() {
			var roleid = checkRole();
			var ids = checkResource();
			$.post("../../ResourceAuthorityServlet?method=saveRoleResource", {
				roleid : roleid,
				ids : ids
			}, function(result) {
				if (result == 'true') {
					alert("保存成功!");					
					window.location.href="../Role2Res/index.html";
				}else if(result == 'existed'){
				}
			});		
	});	
	
});

