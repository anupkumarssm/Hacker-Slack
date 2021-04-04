<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="card border-secondary">
		<div class="card-header" id="groupName">${groupName}</div>
		<div class="card-body1" style="height: 400px;">
			<div class="col-12 px-0">
				<div class="px-4 py-5 chat-box bg-white" id="chat-box">
					<div id="mainMessageBox"></div> 
				</div>
			</div>
		</div>
		<div class="card-footer">
			<div class="row">
			<div class="col-12 col-sm-2 col-md-2 col-lg-2 my-1" align="center">
					<button class="form-control" style="color: green;"> <i class="fa fa-paperclip" aria-hidden="true"></i>
					</button>
				</div>
				<div class="col-12 col-sm-7 col-md-7 col-lg-7 my-1">
					<input class="form-control" type="hidden" value="${groupId}"
						id="groupId"> <input class="form-control" type="text"
						maxlength="50" placeholder="Message" id="textMessage">
				</div>
				<div class="col-12 col-sm-3 col-md-3 col-lg-3 my-1" align="center">
					<button class="form-control" style="color: green;"
						id="sendBtn"> <i class="fa fa-paper-plane" aria-hidden="true"></i>
					</button>
				</div>
			</div>
		</div>
	</div> 
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var element = document.getElementById("chat-box");
							element.scrollTop = element.scrollHeight;
							setInterval(function(){ 
								var groupId = $("#groupId").val();
								$.get( "groupMessageListener", {groupId:groupId } )
								  .done(function( data) {
									  if(data.length != 0){
										  $.each(data, function (index, itemData) {
										  var reciverBoxDiv = $(' <div class="media w-50 ml-auto mb-3"> <div class="media-body"> <div class="bg-primary rounded py-2 px-3 mb-2"> <p class="text-small mb-0 text-white">'+itemData.text_message+'</p> </div> <p class="small text-muted">Username : '+itemData.fullname+', Date :'+itemData.timestamp+'</p> </div> </div>');
										  $('#mainMessageBox').append(reciverBoxDiv); 
										  });
										  var element = document.getElementById("chat-box");
											element.scrollTop = element.scrollHeight;
									  }
								  }); 
							
							}, 1000);
							$(document)
									.on(
											"click",
											"#sendBtn",
											function() {
												var currentdate = new Date(); 
												var month = (currentdate.getMonth()+1);
												if(month < 10){
													month = "0"+month;
												}  
											    var datetime = currentdate.getFullYear() + "-"   + month  + "-" + currentdate.getDate() + " "
											                + currentdate.getHours() + ":"  
											                + currentdate.getMinutes() + ":" 
											                + currentdate.getSeconds();
											                
												var textMessage = $(
														"#textMessage").val();
												if (textMessage != '') {
													var element = document
															.getElementById("chat-box");
													element.scrollTop = element.scrollHeight;
													var groupId = $("#groupId").val();
													
													$.get( "addGroupMessage", { textMessage:textMessage, groupId:groupId } )
													  .done(function( data ) {
														  var mySecondDiv = $('<div class="media w-50 mb-3"> <i class="fas fa-user">(Me)</i> <div class="media-body ml-3"> <div class="bg-light rounded py-2 px-3 mb-2"> <p class="text-small mb-0 text-muted">'
																	+ textMessage
																	+ '</p> </div> <p class="small text-muted">Date : '+datetime+'</p> </div></div>');
															$('#mainMessageBox').append(
																	mySecondDiv);
															 var element = document.getElementById("chat-box");
																element.scrollTop = element.scrollHeight;
													  }); 
													$("#textMessage").val('')
												} else {
													alert("Please enter a message");
												}
											});
							getGroupMessageFunc('${groupId}');
						});
		function getGroupId(id, name) {
			$("#groupName").html(name);
			$("#groupId").val(id);
			 getGroupMessageFunc(id)
		}
		
		function getGroupMessageFunc(groupId){
			var username = '${username}'; 
			$.get( "getGroupMessageData", { groupId:groupId} )
			  .done(function( data ) {
				  $('#mainMessageBox').empty();
				  $.each(data, function (index, itemData) {
					  if(username == itemData.from_username){
						  var mySecondDiv = $('<div class="media w-50 mb-3"> <i class="fas fa-user">(Me)</i> <div class="media-body ml-3"> <div class="bg-light rounded py-2 px-3 mb-2"> <p class="text-small mb-0 text-muted">'
									+ itemData.text_message
									+ '</p> </div> <p class="small text-muted">Date :'+itemData.timestamp+'</p> </div></div>');
							$('#mainMessageBox').append(
									mySecondDiv); 
					  }else{
						  var reciverBoxDiv = $(' <div class="media w-50 ml-auto mb-3"> <div class="media-body"> <div class="bg-primary rounded py-2 px-3 mb-2"> <p class="text-small mb-0 text-white">'+itemData.text_message+'</p> </div> <p class="small text-muted">Username : '+itemData.fullname+', Date :'+itemData.timestamp+'</p> </div> </div>');
						  $('#mainMessageBox').append(reciverBoxDiv); 
					  }
					   $("#updateDate"+itemData.id).html("Date : "+itemData.timestamp);
					});
				  var element = document.getElementById("chat-box");
					element.scrollTop = element.scrollHeight;
			  }); 
			
		}
	</script>
</body>
</html>