function check(data) {
		var flag = data.error;
		if (flag) {
			window.parent.location.href = "../Public/login.html"
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
	
	function reset(id) {//单个重置
		var arr = new Array();
		arr.push(id);
		var ids = JSON.stringify(arr);
		
		if (confirm("确定要重置密码吗？")) {
			$.post("../../UserInfoServlet?method=resetPwd", {
				ids : ids
			}, function(result) {
				if (result == 'true') {
					alert("重置成功!");
					window.location.href = "resetpwd.html";
				} else if (result == 'illegal') {
					alert("有非法用户!");
					return;
				} else if(result == 'none'){
					alert("请选择用户!");
					return;
				}
			});
		}
	}

	function batchReset(){//批量重置
		var arr = new Array();		
		var b = $("#tbd").get(0);
		for(var i=0;i<b.rows.length;i++){
			var ckb = b.rows[i].cells[0].getElementsByTagName("input")[0];//得到复选框
			if(ckb.checked == true){
				//var id = Number(b.rows[i].cells[1].innerText);
				var id = Number(ckb.value);
				arr.push(id);
			}
		}
		var ids = JSON.stringify(arr);
		if(arr.length==0){
			alert("请勾选要重置的记录！");
			return;
		}else{
			if (confirm("确定要重置密码吗？")) {
				$.post("../../UserInfoServlet?method=resetPwd", {
					ids : ids
				}, function(result) {
					if (result == 'true') {
						alert("重置成功!");
						window.location.href = "resetpwd.html";
					} else if (result == 'illegal') {
						alert("有非法用户!");
						return;
					} else if(result == 'none'){
						alert("请选择用户!");
						return;
					}
				});
			}
		}
	}
		
	function chk(){
		var el = document.getElementsByTagName('input'); 
		var len = el.length; 
		for(var i=0; i<len; i++) { 
		if((el[i].type=="checkbox") && $('#chb').get(0).checked==true) { 
			el[i].checked = true; 
		}else if((el[i].type=="checkbox") && $('#chb').get(0).checked==false){
			el[i].checked = false; 
		  }
		}
	}
	
	var pager = null;//分页使用的对象
	
	//查询
	function likeSearch(page){
		var p = $('#param').val();
			$("#tbd").empty();
			var pvalue = $('#pvalue').val();
			$.ajax({
				type : "get",
				url : "../../UserInfoServlet?method=getUserByParam",
				data : {"type":p,"value":pvalue,"pageIndex":page},
				dataType : "json",
				cache:false,
				contentType : "application/json;charset=utf-8",
				success : function(data) {				
					$.each(data, function(i) {						
						var sex = data[i].sex;
						var s = "男";
						if (sex == 0) {
							s = "女";
						}
						$("#tbd").append(
								"<tr>"+
								 	"<td><input type='checkbox' class='checkboxes' value="+ data[i].id +"></td>"+
								 	"<td style='text-align: center;'>" + data[i].usercode + "</td>"+ 
									"<td style='text-align: center;'>" + data[i].username + "</td>"+
									"<td style='text-align: center;'>" + s + "</td>"+
									"<td style='text-align: center;'>" + data[i].tel + "</td>"+
									"<td style='text-align: center;'>" + data[i].officetel + "</td>"+
									"<td style='text-align: center;'>" + data[i].email + "</td>"+
									"<td style='text-align: center;'>" +									 
										 "<a class='btn btn-success btn-mini' href='javascript:reset("+data[i].id+")'> "+
										 "<i class='icon-pencil '></i>密码重置 </a>"+"&nbsp;&nbsp;"+
									"</td></tr> ");
					});
					//加载分页标签
					if(data.length>0){
						pager = data[0].pager;
						document.getElementById("totalRecord").innerText = pager.totalCount;
						document.getElementById("currentPage").innerText = pager.pageIndex;
						document.getElementById("totalPage").innerText = pager.totalPages;
					}				
				},
				error : function() {
					alert("请求出错");
				}
			})
	}
	
	function home(){//点击首页
		var page = 1;
		likeSearch(page);
	}
	
	function previous(){//上一页
		var page = pager.pageIndex -1;
		if(page<1){
			return;
		}else{
			likeSearch(page);
		}
	}
	
	function next(){//下一页
		if(pager.pageIndex==pager.totalPages){
			return;
		}else{
			likeSearch(pager.pageIndex+1);
		}
		
	}
	
	function last(){//末页
		if(pager.pageIndex==pager.totalPages){
			return;
		}else{
			likeSearch(pager.totalPages);
		}		
	}
	
	$(function() {
		$('#batchReset').click(function() {
			batchReset();
		});
		$('#btnSearch').click(function() {
			likeSearch(1);
		});
	});
	
	$(document).ready(function() {
		likeSearch(1);
	});