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
	<div class="card">
		<div class="card-header">
			<div class="row">
			<div class="col-12 col-sm-9 col-md-9 col-lg-9">
					<h4>Contacts</h4>
				</div>
			<div class="col-12 col-sm-2 col-md-2 col-lg-2">
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#addContactModal"><i class="fa fa-user-plus" aria-hidden="true">&nbsp;Add Contact</i></button>
				</div>
			</div>
		</div>
		<div class="card-body" style="height: 500px; overflow-y: scroll">
			<!-- Button to Open the Modal -->
			<ul class="list-group list-group-flush">
				<c:forEach items="${getAllContacts}" var="contacts">
					<li class="list-group-item"><div class="paragraphs">
							<div class="row">
								<div class="span4">
									<div class="content-heading">
										<h5><i class="fa fa-user-plus" aria-hidden="true">&nbsp;${contacts.fullname}</i></h5>
									</div>
									<p style="clear: both; width: 1200px;">${contacts.mobile}</p>
								</div>
							</div>
						</div></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!-- The Modal -->
	<div class="modal" id="addContactModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header bg-primary">
					<h4 class="modal-title text-white">Add Contact</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action="addContacts" method="post" class="needs-validation" id="contactForm"
						novalidate>
						<div class="form-group textstyle">
							<label for="username">Full Name:</label> <input type="text"
								class="form-control" placeholder="Enter First name" maxlength="20"
								name="fullname" autocomplete="off" required="">
						</div>
						<div class="form-group textstyle">
							<label for="username">(Username) Mobile:</label> <input type="text"
								class="form-control mobile" placeholder="Enter Mobile"
								name="mobile" autocomplete="off" required="">
						</div>
						<div align="center">
							<input type="submit"
								class="btn btn-sm btn-primary form-control col-sm-6" value="Save">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div> 
	<script type="text/javascript">
		$(document).ready(function() {
			$('.mobile').inputmask('999-999-9999');
			$('#contactForm')
			.bootstrapValidator(
					{
						fields : {
							fullname : {
								validators : {
									notEmpty : {
										message : '<div class="text-center text-danger">Full Name cannot be empty</div>'
									},
								}
							},
							mobile : {
								validators : {
									notEmpty : {
										message : '<div class="text-center text-danger">Mobile cannot be empty</div>'
									},
								}
							}
						}
					});
		});
	</script>
</body>
</html>