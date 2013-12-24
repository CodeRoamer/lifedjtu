
$('document').ready(function(){
	$('.eva-button').click(function(event){
		var self = $(this);
		
		event.preventDefault();
		var data = {};
	
		data.studentId = $('input[name="studentId"]').val();
		data.password = $('input[name="password"]').val();
		
		self.text("登录中...");
		self.attr("disabled","true");
		
		$.ajax({
			 type: "POST",
			 url: "signin.action",
			 data: data,
			 success: function(data){
				 var sessionId = data.sessionId;
				 if(sessionId==null||sessionId==undefined){
					 alert("亲，登陆失败了~~密码是不是错啦？");
					 self.text("一键评估");
					 self.removeAttr("disabled");
				 }else{
					 self.text("获取列表...");
					 var table = $('.eva-table');
					 table.empty();
					 table.append("<tr><th>课程名</th><th>评估状态</th><tr>");
					 $.ajax({
						 type: "POST",
						 url: "evaList.action",
						 data: {"sessionId": sessionId},
						 success:function(data){
							 var entries = data.evaList.entries;
							 $.each(entries, function(index, entry){
								 var entryDom = '<tr>\
									 			<td><span class="lead">'+entry.courseName+'</span></td>\
									 			<td><span class="label label-warning item'+index+'">未评估</span></td>\
									 			</tr>';
								 table.append(entryDom);
							 });
							 
							 $.each(entries, function(index, entry){

								 (function(index, entry){
									 var data = {
										"sessionId" : sessionId,
										"courseHref" : entry.courseHref
									 };
									 
									 $('.item'+index).text("正在评估...");
									 $('.item'+index).removeClass("label-warning");
									 $('.item'+index).addClass("label-info");
									 
									 var times = 3;
									 
									 function evaluate(){
										 times--;
										 $.ajax({
											 type: "POST",
											 url: "evaluateCourse.action",
											 data: data,
											 success:function(data){
												 if(data.status){
													 $('.item'+index).removeClass("label-info");
													 $('.item'+index).addClass("label-success");
													 $('.item'+index).text("已评估");
												 }else{
													 if(times>0)
														 evaluate();
													 else{
														 $('.item'+index).text("评估失败");
													 }
												 }
											 },
											 error:function(){
												 alert("Wrong!服务器连接丢失，请重新评测");
											 }
										 });
									 }
									 									 
									 evaluate();
									 
								 })(index, entry);
							 });
							 
							 self.text("一键评估");
							 self.removeAttr("disabled");
						 },
						 error:function(){
							 
						 }
					 });
					 
				 }
			 },
			 error: function(xhr, textStatus, error){
				 alert("评估失败！"+error);
				 $(this).text("一键评估");
				 $(this).removeAttr("disabled");
			 }
		});
	});
});