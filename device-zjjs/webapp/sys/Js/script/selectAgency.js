//单位树
	var setting = {  
	   		view: {
	   			  dblClickExpand: false,
	   			  expandSpeed: 10, //设置树展开的动画速度
	   			  selectedMulti: false,
	   			  fontCss: getFontCss
	   		},
	   		//获取json数据
	        async : {  
	            enable : true, 
	            url : "../../OrganizationServlet?method=getAllAgency" // Ajax 获取数据的 URL 地址  
	            //autoParam : [ "id", "name" ] //ajax提交的时候，传的是id值
	        },  
	        data:{ // 必须使用data  
			 	key : {
			 		name : "nodename"
	        	}, 
	            simpleData : {  
	                enable : true,  
	                idKey : "guid", // id编号命名   
	                pIdKey : "parentguid", // 父id编号命名    
	                rootPId : 0
	            }
	        
	        },
	        edit : {
	        	drag : {
	        		autoExpandTrigger:true,
	        		autoOpenTime:500
	        	}
	        },
	        // 回调函数  
	        callback : {  
	            onClick : function(event, treeId, treeNode, clickFlag) {  //点击节点后绑定值到form
	                if(true) {
	                    //alert(" 节点id是：" + treeNode.id + ", 节点文本是：" + treeNode.name);
	                }  
	            },  
	            //捕获异步加载出现异常错误的事件回调函数 和 成功的回调函数  
	            onAsyncSuccess : function(event, treeId, treeNode, msg){
	            	//$.fn.zTree.getZTreeObj("tree").expandAll(true); 
	            },
	            onDblClick :function(event,treeId,treeNode) {
		        	selectAgency();
		        }
	        }
	       
	    }; 
	
	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#FF6666", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	
	function searchAgn(){
		var p = $("#txtOrgname").val();
		
		 var setting = {  
			   		view: {
			   			  dblClickExpand: false,
			   			  expandSpeed: 10, //设置树展开的动画速度
			   			  selectedMulti: false,
			   			  fontCss: getFontCss
			   		},
			   		//获取json数据
			        async : {  
			            enable : true, 
			            url : "../../OrganizationServlet?method=searchAgn", 
			            otherParam : ["para", p] //para参数
			        },  
			        data:{ // 必须使用data  
					 	key : {
					 		name : "nodename"
			        	}, 
			            simpleData : {  
			                enable : true,  
			                idKey : "guid", // id编号命名   
			                pIdKey : "parentguid", // 父id编号命名    
			                rootPId : 0
			            }
			        
			        },
			        edit : {
			        	drag : {
			        		autoExpandTrigger:true,
			        		autoOpenTime:500
			        	}
			        },
			        // 回调函数  
			        callback : {  
			            onClick : function(event, treeId, treeNode, clickFlag) {  //点击节点后绑定值到form
			            	
			            },  
			            //捕获异步加载出现异常错误的事件回调函数 和 成功的回调函数  
			            onAsyncSuccess : function(event, treeId, treeNode, msg){ 
			            	//$.fn.zTree.getZTreeObj("tree").expandAll(true); 
			            },
			            onDblClick :function(event,treeId,treeNode) {
				        	selectAgency();
				        }
			        }  
			    };
		 
		 $.fn.zTree.init($("#tree"), setting);
	}
	
	function selectAgency(){//选择单位点击确定按钮时、或双击树节点时调用
		var tree = $.fn.zTree.getZTreeObj("tree");
		var snodes = tree.getSelectedNodes();
		var code = "";
		var text = "";
		
		if(snodes.length==0&&nodes.length>0){//存在节点，但没有选中节点
			alert("请选择一个执收单位!");
			return;
		}else if(snodes.length==0&&nodes.length==0){//没有任何节点也没有选中的节点
			 
		}else if(snodes.length>1){//选中了多个节点
			alert("执收单位不允许多选!");
			return;
		}else{
			code = snodes[0].agencycode; 
			text = snodes[0].agencycode+" "+snodes[0].agencyname;
			guid = snodes[0].guid;
			$('#selectAgency').modal('hide');
			if(busi=="devcard"){//设备卡片模块判断
				$("#agencycode").empty();
				document.getElementById("agencycode").options.add(new Option("全部","-1", false, false));
				document.getElementById("agencycode").options.add(new Option(text,code, true, true));
				getAllDevice();
			}else if(busi=="adduser"||busi=="edituser"){//用户管理添加、修改模块判断
				$("#dept").empty();
				document.getElementById("dept").options.add(new Option("全部","-1", false, false));
				document.getElementById("dept").options.add(new Option(text,guid, true, true));
			}else if(busi=="managerAdd"||busi=="managerEdit"){//设备管理员添加、修改模块判断
				$("#agencyid").empty();
				document.getElementById("agencyid").options.add(new Option("全部","-1", false, false));
				document.getElementById("agencyid").options.add(new Option(text,guid, true, true));
			}else if(busi=="devAdd"||busi=="devEdit"){//设备注册管理添加、修改模块判断
				$("#agencycode").empty();
				document.getElementById("agencycode").options.add(new Option("全部","-1", false, false));
				document.getElementById("agencycode").options.add(new Option(text,code, true, true));
			}else if(busi=="user2role"){//用户对角色模块判断
				$("#organizationid").empty();
				document.getElementById("organizationid").options.add(new Option("全部","0", false, false));
				document.getElementById("organizationid").options.add(new Option(text,guid, true, true));
			}else if(busi=="billdestory"){//专用票据核销
				$("#select_dw").empty();
				document.getElementById("select_dw").options.add(new Option("全部","", false, false));
				document.getElementById("select_dw").options.add(new Option(text,code, true, true));
				loadTable(1);
			}else if(busi=="devBillStore"){//设备库存查询
				$("#select_dw").empty();
				document.getElementById("select_dw").options.add(new Option("全部","", false, false));
				document.getElementById("select_dw").options.add(new Option(text,code, true, true));
				loadTable(1);
			}else if(busi=="billStore"){//票据库存查询
				$("#orgid").empty();
				document.getElementById("orgid").options.add(new Option("全部","", false, false));
				document.getElementById("orgid").options.add(new Option(text,code, true, true));
				loadTable(1);
			}
		}		
	}