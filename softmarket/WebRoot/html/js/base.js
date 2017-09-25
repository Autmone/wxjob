	var page_size=10;
	
	$(function() {
		var soft_username = '';
		var soft_usernames = document.cookie;
		soft_usernames = soft_usernames.split(";");
		for(var i=0;i<soft_usernames.length;i++) {
			if(soft_usernames[i].split("=")[0].trim()=='soft_username') {
				soft_username = soft_usernames[i].split("=")[1];
				break;
			}
		}
		if(soft_username == '' || soft_username == undefined) {
			$("#usercontent").html("<a href='' data-am-offcanvas=\"{target: '#doc-oc-demo2', effect: 'push'}\">登 录</a>");
		} else {
			$("#usercontent").text(soft_username + '，你好');
		}
		
	});
	
	//日期转换
	function changeTime(time){
	
		//检查月、日、年、分、秒，前面加0
		function check(i){
	        if(i < 10){
	            i = "0" + i;
	        }
	        return i;
	    }
	
	    //转换时间
	    var timeS = parseInt(time),
	        date = new Date(timeS),
	        year = date.getFullYear(),
	        Month = check(date.getMonth() + 1),
	        day = check(date.getDate()),
	// 	        hours = check(date.getHours()),
	// 	        minutes = check(date.getMinutes()),
	// 	        seconds = check(date.getSeconds()),
	        getDate = year + "-" + Month + "-" + day;
	
	    return getDate;
	}
	
	function getSoftInfoList(curr,searchKey) {
		$.ajax({
			url: "/softmarket/api/information/getInfoList?page="+curr+'&pageSize='+page_size + "&infoTitle="+searchKey,
			type: 'GET',
			dataType: 'json',
			success:function(rs){
		        var html = '';
	            if(rs.code == "0"){
	            	if(rs.data.list.length > 0) {
	            		$.each(rs.data.list, function(index, item){
	            			html += '<div class="am-panel am-panel-default"> <div class="am-panel-hd" onClick="checkIsPassword(' + item.infoId + ')"> <h4 class="am-panel-title" data-am-collapse="{parent: \'#accordion\'}"> ' + item.infoTitle + ' </h4> '+changeTime(item.addTime)+' | 点击：'+item.checkNum+' | 下载：'+item.downloadNum+' </div> <div id="do-not-say-' + index + '" class="am-panel-collapse am-collapse am-in"> <div class="am-panel-bd"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + item.infoContent + ' </div> </div> </div>';
	            		});
	            	} else {
	            		html='<tr><td colspan="10">暂无记录</td></tr>';
	            	}
	            	renderPage(rs.data.totalCount,curr,searchKey);   //laypage分页插件调用 
	            }else{
	    			html='<tr><td colspan="10">暂无记录</td></tr>';
	    			tipTopShow(rs.msg);
	            }
	    		$("#part_list").html(html);
	        }
		})
	}
	
	
	// 分页
	function renderPage(total,curr,searchKey){
	    var pages = Math.ceil(total/page_size); //得到总页数
	    //显示分页
	    laypage({
	        cont: 'p_page', //容器。值支持id名、原生dom对象，jquery对象。
	        pages: pages, //通过后台拿到的总页数
	        skip: true, //是否开启跳页
	        skin: '#5877a4', // 红色: #AF0000
	        groups: 5, //连续显示分页数
	        curr: curr || 1, //当前页
	        jump: function(obj, first){ //触发分页后的回调
	            if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
	            	getSoftInfoList(obj.curr,searchKey);
	            }
	        }
	    });
	}
	
	function checkIsPassword(infoId) {
		$.ajax({
			url: "/softmarket/api/information/getInforDetail?infoId="+infoId,
			type: 'GET',
			dataType: 'json',
			success:function(rs){
				if(rs.code == "0"){
	            	if(rs.data != null) {
	            		if(rs.data.isPassword == 0) {
	            			window.open(rs.data.infoUrl, rs.data.infoTitle);
	            		} else {
	            			showPasswordCheck(rs.data.infoTitle,rs.data.infoNum,infoId);
	            		}
	            	} else {
	            		alert("暂无记录");
	            	}
	            } else{
	            	alert("暂无记录");
	            }
	        }
		})
	}
	
	function showPasswordCheck(showTitle, infoNum, infoId) {
		$("#showTitle").text(showTitle); 
		$("#showContent").html("请输入访问密码:<br /><span style='font-size:12px;'>不知道密码？可使用微信扫码关注以下公众号，回复" + infoNum +"，进行密码查询.</span>"
		+ "<img src='../images/qrcode.jpg' style='width:90%;'></div>" 
		+ "<input type='text' class='am-modal-prompt-input'>");
		$('#my-prompt').modal({
			relatedTarget : this,
			onConfirm : function(e) {
				$.ajax({
					url: "/softmarket/api/information/checkPassword?infoPassword="+e.data + "&infoId="+infoId,
					type: 'GET',
					dataType: 'json',
					success:function(rs){
						if(rs.code == "0"){
			            	window.open(rs.data.infoUrl, rs.data.infoTitle);
			            } else{
			            	$('#my-alert').modal({
			            	
			            	});
			            }
			        }
				})
			},
			onCancel : function(e) {
				
			}
		});
	}
	

	$("#login").click(function() {
		var username = $("#username").val();
		var password = $("#password").val();
		
		if(username == '' || password == '') {
        	$('#null-alert').modal({
        	});
        	return ;
		}
		
		$.ajax({
			url: "/softmarket/api/user/login?username="+username + "&password="+password,
			type: 'POST',
			dataType: 'json',
			success:function(rs){
				if(rs.code == "0"){
					document.cookie = "soft_username="+rs.data.username+";path=/";
					$("#usercontent").text(rs.data.username + '，你好');							
					$("#doc-oc-demo2").offCanvas('close');
	            } else{
	            	$("#my-alert_content").text(rs.msg);
	            	$('#my-alert').modal({
	            	});
	            }
	        }
		});
	});

	$("#register").click(function() {
		var username = $("#username").val();
		var password = $("#password").val();
		
		if(username == '' || password == '') {
        	$('#null-alert').modal({
        	});
			return ;
		}
		
		$.ajax({
			url: "/softmarket/api/user/addUser?username="+username + "&password="+password,
			type: 'POST',
			dataType: 'json',
			success:function(rs){
				if(rs.code == "0"){
					document.cookie = "soft_username="+rs.data.username+";path=/";
					$("#usercontent").text(rs.data.username + '，你好');
					$("#doc-oc-demo2").offCanvas('close');
	            } else{
	            	$("#my-alert_content").text(rs.msg);
	            	$('#my-alert').modal({
	            	
	            	});
	            }
	        }
		});
	});
