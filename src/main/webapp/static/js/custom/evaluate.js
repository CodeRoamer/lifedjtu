
$('document').ready(function(){
	$('.eva-button').click(function(event){
		var self = $(this);
		
		// progress bar
		var barWidth = 0;
		var bar = $('.eva-bar');
		
		//alert message
		var alert = $('.result-alert');
		alert.children().hide();

		//prevent submit the form
		event.preventDefault();
		//prepare the data to post, in order to login
		var data = {};
	
		
		function makeAlert(type, message){
			alert.children().hide();
			if(type==-1){
				alert.children('.alert-danger').show();
				alert.children('.alert-danger').text(message);
			}else if(type==0){
				alert.children('.alert-success').show();
				alert.children('.alert-success').text(message);
			}else if(type==1){
				alert.children('.alert-info').show();
				alert.children('.alert-info').text(message);
			}
		}
		
		//get user input, studentId and password
		data.studentId = $('input[name="studentId"]').val();
		data.password = $('input[name="password"]').val();
		
		//change the button state
		self.text("登录中...");
		self.attr("disabled","true");
		
		//enlarge the width of progress bar
		bar.width((barWidth+=5)+"%");
		
		$.ajax({
			 type: "POST",
			 url: "signin.action",
			 data: data,
			 success: function(data){
				 var sessionId = data.sessionId;
				 if(sessionId==null||sessionId==undefined){
					 bar.width("100%");
					 makeAlert(-1,"亲，登陆失败了~~这可能是由于破译验证码次数超限或密码错误所致，请再试一次");
					 self.text("一键评估");
					 self.removeAttr("disabled");
					 bar.width("0%");
				 }else{
					 bar.width((barWidth+=5)+"%");
					 self.text("获取列表...");
					 var table = $('.eva-table');
					 table.empty();
					 table.append("<tr><th>课程名</th><th>评估状态</th><tr>");
					 $.ajax({
						 type: "POST",
						 url: "evaList.action",
						 data: {"sessionId": sessionId},
						 success:function(data){
							 bar.width((barWidth+=5)+"%");
							 var entries = data.evaList.entries;
							 
							 if(entries.length==0){
								 bar.width("100%");
								 makeAlert(0,"所有课程已经全部评估完毕！");
								 bar.width("0%");
								 self.text("一键评估");
								 self.removeAttr("disabled");
							 }
							 
							 $.each(entries, function(index, entry){
								 var entryDom = '<tr>\
									 			<td><span class="lead">'+entry.courseName+'</span></td>\
									 			<td><span class="label label-warning item'+index+'">未评估</span></td>\
									 			</tr>';
								 table.append(entryDom);
							 });
							 
							 
							 var handleSum = entries.length;
							 var handledNum = 0;
							 var segmentWidth = (100-barWidth)/handleSum;

							 
							 $.each(entries, function(index, entry){

								 (function(index, entry){
									 var data = {
										"sessionId" : sessionId,
										"courseHref" : entry.courseHref
									 };
									 
									 $('.item'+index).text("正在评估...");
									 $('.item'+index).removeClass("label-warning");
									 $('.item'+index).addClass("label-info");
									 
									 var times = 4;
									 
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
													 bar.width((barWidth+=segmentWidth)+"%");
													 if(++handledNum==handleSum){
														 self.text("一键评估");
														 self.removeAttr("disabled");
														 makeAlert(1,"评估完成，如有失败，请重试一至三次");
														 bar.width("0%");
													 }
													 
												 }else{
													 if(times>0)
														 evaluate();
													 else{
														 $('.item'+index).text("评估失败");
														 bar.width((barWidth+=segmentWidth)+"%");
														 if(++handledNum==handleSum){
															 self.text("一键评估");
															 self.removeAttr("disabled");
															 makeAlert(1,"评估完成，如有失败，请重试一至三次");
															 bar.width("0%");
														 }
													 }
												 }
											 },
											 error:function(){
												 makeAlert(-1,"Wrong!服务器连接丢失，请重新评测");
											 }
										 });
									 }
									 									 
									 evaluate();
									 
								 })(index, entry);
							 });
							 
							 
						 },
						 error:function(){
							 makeAlert(-1,"获取列表失败~！检查网络连接...");
							 self.text("一键评估");
							 self.removeAttr("disabled");
						 }
					 });
					 
				 }
			 },
			 error: function(xhr, textStatus, error){
				 alert("评估失败！"+error);
				 self.text("一键评估");
				 self.removeAttr("disabled");
			 },
            timeout:5000
		});
	});
});