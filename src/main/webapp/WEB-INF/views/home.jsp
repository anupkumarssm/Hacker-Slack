<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
	<style>
.notification {
	padding: 20px 26px;
	position: relative;
	display: inline-block;
	border-radius: 2px;
}

.notification .badge {
	position: absolute;
	top: 8px;
	right: -10px;
	padding: 5px 15px;
	border-radius: 50%;
	background-color: red;
	color: white;
}
</style>
	<div class="card">
		<div class="card-header">
			<div class="row">
				<div class="col-12 col-sm-8 col-md-8 col-lg-8">
					<h4>Home</h4>
				</div>
				<sec:authorize access="hasAnyRole('HACKER')">
					<div class="col-12 col-sm-4 col-md-4 col-lg-4">
						<button type="button" class="btn btn-primary form-control"
							data-toggle="modal" data-target="#createGroupModal">
							<i class="fa fa-users" aria-hidden="true">&nbsp;Create Group</i>
						</button>
					</div>
				</sec:authorize>
			</div>
		</div>
		<c:if test="${empty getGroupList}">
			<h4 align="center">No Groups</h4>
		</c:if>
		<div class="card-body" style="height: 500px; overflow-y: scroll">
			<ul class="list-group list-group-flush" style="cursor: pointer">
				<c:forEach items="${getGroupList}" var="getGroups">
					<li id="activeLi${getGroups.id}"
						class="list-group-item getusername activeLi"
						data-grpId="${getGroups.id}"
						data-grpName="${getGroups.group_name}"
						onclick="getGroupId('${getGroups.id}','${getGroups.group_name}')"><div
							class="paragraphs">
							<div class="row">
								<div class="span4">
									<div class="content-heading">
										<i class="fas fa-users">&nbsp;&nbsp;<b>${getGroups.group_name}</b><a
											href="#" class="notification"><span class="badge"
												id="messageCountValue${getGroups.id}"></span></a></i><br>
										<p style="font-family: 'Courier New'">
											<b>Create By :</b> ${getGroups.fullname} <br> <b>
												Date :</b> ${getGroups.timestamp}
										</p>
									</div>
								</div>
							</div>
						</div></li>
				</c:forEach>

			</ul>
		</div>
	</div>

	<!-- The Modal -->
	<div class="modal" id="createGroupModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header bg-primary">
					<h4 class="modal-title text-white">Create Group</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action="createGroup" method="post" class="needs-validation"
						id="createGroupForm" novalidate>
						<div class="form-group textstyle">
							<label for="username">Group Name:</label> <input type="text"
								class="form-control" placeholder="Enter Group name"
								maxlength="20" name="groupName" autocomplete="off" required="">
						</div>
						<div class="form-group textstyle">
							<label for="username">Select Users:</label> <select
								style="width: 100%;" class="form-control select-multiple-users"
								name="mobile" multiple="multiple">
								<c:forEach items="${getAllContacts}" var="contacts">
									<option value="${contacts.mobile}">${contacts.fullname}</option>
								</c:forEach>
							</select>
						</div>
						<div align="center">
							<input type="submit"
								class="btn btn-sm btn-primary form-control col-sm-6"
								value="Create">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<link
		href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
		rel="stylesheet" />
	<script
		src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('.select-multiple-users').select2();
							$(".activeLi")
									.click(
											function() {
												$(".activeLi").removeClass(
														"active");
												$(this).addClass("active");
												var grpId = $(this).attr(
														"data-grpId");
												var grpName = $(this).attr(
														"data-grpName");
												window.location.href = 'group-messages?group-id='
														+ grpId
														+ "&group-name="
														+ grpName;
											});

							$('#createGroupForm')
									.bootstrapValidator(
											{
												fields : {
													groupName : {
														validators : {
															notEmpty : {
																message : '<div class="text-center text-danger">Group Name cannot be empty</div>'
															},
														}
													},
													mobile : {
														validators : {
															notEmpty : {
																message : '<div class="text-center text-danger">Users cannot be empty</div>'
															},
														}
													}
												}
											});

							window
									.setInterval(
											function() {
												$
														.get(
																"countUnreadGroupMsg")
														.done(
																function(data) {
																	$
																			.each(
																					data,
																					function(
																							index,
																							itemData) {
																						var isActive = $(
																								"#activeLi"
																										+ itemData.group_id)
																								.hasClass(
																										'active');
																						if (isActive == false) {
																							$(
																									"#messageCountValue"
																											+ itemData.group_id)
																									.html(
																											itemData.count);
																						}

																					});
																});
											}, 2000);
						});
	</script>
</body>
</html>