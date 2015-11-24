
$(document).ready(function() {
	var user_obj = $.cookie('user');
    var user = JSON.parse(user_obj); //字符串转化成JSON数据
    
	var userid = user.userid;
	var usercode = user.usercode;
	var username = user.username;
	$("#username").html(username);
	getSecurity(userid);
});


//查询所有菜单
function getSecurity(userid) {
	$.ajax({
		type : "post",
		dataType : "json",
		cache:false,
		url : "../UserInfoServlet?method=getSecurity&userid="+userid+"&date="+new Date(),
		success : function(data) {
			var html = createTree(data);
			$("#sidebar_menu").append(html);
			binkTreeClick();
		}
	});
};

/**
 * 创建权限树
 * @param data
 */
function createTree(data){
	var rootNode = getRootNode(data);
	var html="";
	for(var i=0;i<rootNode.length;i++){
		var root = rootNode[i];
		html += "<li class='sub-menu'>" +
						"<a href='javascript:;' class=''> "+
							"<i class='"+root.icon+"'></i> "+
							"<span>"+root.resourcename+"</span> "+
							"<span class='arrow'></span>"+
						"</a>";
		
		html += "<ul class='sub'> ";
		$.each(data,function(i,obj){
			if(obj.pid==root.resouceid){
				html += "<li><a href='javascript:void(0);' onclick='menuClick(\""+obj.url+"\",\""+obj.resouceid+"\",\""+obj.resourcename+"\")'>"+obj.resourcename+"</a></li>";
			}
		});
		html += "</ul>";
		html += "</li>";
	};
	return html;
}


/**
 * 获取根节点
 * @param data
 * @returns {Array}
 */
function getRootNode(data){
	var rootnode = new Array(); //根节点数组
	$.each(data,function(i,obj){
		if(obj.pid=="-1"||obj.pid=="0"){
			rootnode.push(obj);
		}
	});
	return rootnode;
}


/**
 * 绑定树事件
 */
function binkTreeClick(){
	jQuery('#sidebar .sub-menu > a').click(function () {
        var last = jQuery('.sub-menu.open', $('#sidebar'));
        last.removeClass("open");
        jQuery('.arrow', last).removeClass("open");
        jQuery('.sub', last).slideUp(200);
        var sub = jQuery(this).next();
        if (sub.is(":visible")) {
            jQuery('.arrow', jQuery(this)).removeClass("open");
            jQuery(this).parent().removeClass("open");
            sub.slideUp(200);
        } else {
            jQuery('.arrow', jQuery(this)).addClass("open");
            jQuery(this).parent().addClass("open");
            sub.slideDown(200);
        }
        var o = ($(this).offset());
        diff = 200 - o.top;
        if(diff>0)
            $(".sidebar-scroll").scrollTo("-="+Math.abs(diff),500);
        else
            $(".sidebar-scroll").scrollTo("+="+Math.abs(diff),500);
    });
}


/**
 * 超连接事件
 * @param link
 * @param resouceid
 * @param name
 */
function menuClick(link,resouceid,name){
	var imageMenuData = [ [ {
		text : "关闭当前",
		func : function() {
			// 1.移除
			var li = $(this);
			var id = li.find("a").eq(0).attr("name");
			var style = li.find("a").eq(0).attr("class");
			li.remove();
			$("#content").find("[id='" + id + "']").remove();
			// 2.如果关闭的是当前显示的则让上一个标签显示
			if (style == "current") {
				var last_li = $("#tabs li:last");
				var id = last_li.find("a").attr("name");
				$("#tabs").find("[name='" + id + "']").removeClass("disabled"); // 添加选中状态的样式
				$("#tabs").find("[name='" + id + "']").addClass("current"); // 添加选中状态的样式
				$("#content #" + id).fadeIn(); // 找到新创建的标签页，并显示
			}
		}
	}, {
		text : "关闭其它",
		func : function() {
			var cuurent_id = $(this).find("a").attr("name");
			$("#tabs li").each(function() {
				li = $(this);
				var li_id = li.attr("id");
				var id = li.find("a").attr("name");
				if (cuurent_id != id && "li_home" != li_id) {
					li.remove();
					$("#content #" + id).remove();
				}
			});
			var last_li = $("#tabs li:last");
			var id = last_li.find("a").attr("name");
			$("#tabs").find("[name='" + id + "']").removeClass("disabled"); // 添加选中状态的样式
			$("#tabs").find("[name='" + id + "']").addClass("current"); // 添加选中状态的样式
			$("#content #" + id).fadeIn();
		}
	}, {
		text : "全部关闭",
		func : function() {
			$("#tabs li").each(function() {
				li = $(this);
				var li_id = li.attr("id");
				if ("li_home" != li_id) {
					var id = li.find("a").attr("name");
					li.remove();
					$("#content #" + id).remove();
				}
			});
			var last_li = $("#tabs li:last");
			var id = last_li.find("a").attr("name");
			$("#tabs").find("[name='" + id + "']").removeClass("disabled"); // 添加选中状态的样式
			$("#tabs").find("[name='" + id + "']").addClass("current"); // 添加选中状态的样式
			$("#content #" + id).fadeIn();
		}
	} ], [ {
		text : "取消",
		func : function() {
		}
	} ] ];
	
	//1.查找是否有当前选项卡
	var flag=false;
	$("#tabs li").each(function(index){
		var a = $(this).find("a").eq(0);
		if(a.attr("name")==resouceid){
			flag = true;
		}
		a.removeClass("current");
		a.addClass("disabled");
     }); 
	
	//2.如果有 则选中、没有则创建
	if(flag==true){
		$("#content").find("div").hide(); 
		$("#tabs").find("[name='"+resouceid+"']").removeClass("disabled");	//添加选中状态的样式
		$("#tabs").find("[name='"+resouceid+"']").addClass("current");		//添加选中状态的样式
		$("#content").find("[id='"+resouceid+"']").fadeIn(); 
		return;
	}else{
		var url=link;
		var li = $("<li></li>");
		li.smartMenu(imageMenuData); //添加右键菜单
		var close_div = $('<div></div>');
		close_div.html("x");
		//绑定关闭按钮事件
		close_div.bind("click",function(){
			//1.移除
			var id = $(this).parent().find("a").eq(0).attr("name");
			var style = $(this).parent().find("a").eq(0).attr("class");
			$(this).parent().remove();
			$("#content").find("[id='"+id+"']").remove();
			
			//2.如果关闭的是当前显示的则让上一个标签显示
			if(style=="current"){
				var last_li = $("#tabs li:last");
				var id = last_li.find("a").attr("name");
				$("#tabs").find("[name='"+id+"']").removeClass("disabled");	//添加选中状态的样式
				$("#tabs").find("[name='"+id+"']").addClass("current");		//添加选中状态的样式
				$("#content #"+id).fadeIn(); 								//找到新创建的标签页，并显示
			}
		});
		
		var a = $("<a></a>");
		a.attr('href','javascript:void(0)');    
		a.attr('name',resouceid);    
		a.html(name);
		a.appendTo(li);
		close_div.appendTo(li);
		c_html="<div id='"+resouceid+"' style='height: 100%;'> <iframe  src='"+url+"' style='width: 100%; height: 100%;'' frameborder='no'></iframe></div>";
		$("#tabs").append(li);
		$("#content").append(c_html);
		
		$("#tabs").find("[name='"+resouceid+"']").removeClass("disabled");	//添加选中状态的样式
		$("#tabs").find("[name='"+resouceid+"']").addClass("current");		//添加选中状态的样式
		$("#content").find("div").hide(); 									//隐藏所有内容窗口
		$("#content #"+resouceid).fadeIn(); 								//找到新创建的标签页，并显示
	}
	
	$('#tabs a').die().live("click",function(e) {
		e.preventDefault();
		var id=$(this).attr("name");
		var style = $("#tabs").find("[name='"+id+"']").attr("class");
		if (style == "current") { //detection for current tab
			return;
		} else {
			//重置所有状态
			$("#tabs li").each(function(index){
				var a = $(this).find("a").eq(0);
				a.removeClass("current");
				a.addClass("disabled");
		     }); 
			$("#tabs").find("[name='"+id+"']").removeClass("disabled");	//移除未选中状态的样式
			$("#tabs").find("[name='"+id+"']").addClass("current");		//添加选中状态的样式
			
			$("#content").find("div").hide(); 
			$("#content").find("[id='"+id+"']").fadeIn(); 
		}
	}); 
}

function loginout(){
	$.ajax({
		type : "post",
		dataType : "json",
		cache:false,
		url : "../UserInfoServlet?method=logout"+"&date="+new Date(),
		success : function(data) {
			$.cookie("user", "", { expires: -1, path: '/' }); 
			window.location.href="DeviceManage/Public/login.html";    	
		}
	});
}