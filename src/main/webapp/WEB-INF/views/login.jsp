<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta name="viewport" content="width=device-width">
</head>
<body>
	<div class="row" style="margin-top: 100px;">
				<div class="col-12 col-sm-3 col-md-3 col-lg-3"></div>
				<div class="col-12 col-sm-6 col-md-6 col-lg-6">
					<div class="card">
						<div class="card-body">
						<h4 align="center"><b>Hacker Slack</b></h4><hr>
							<ul class="nav nav-pills textstyle">
								<li class="nav-item"><a
									class="nav-link active button bg-dark" data-toggle="pill"
									href="#signin">Sign in</a></li>
								<li class="nav-item"><a class="nav-link button bg-dark"
									data-toggle="pill" href="#signup">Sign up</a></li>
							</ul>
							<hr>
							<!-- Tab panes -->
							<div class="tab-content textstyle">
								<div class="tab-pane container active" id="signin">
									<form action="/login" method="post" class="needs-validation"
										id="signinForm" novalidate>
										<div class="form-group">
											<label for="username" class=""><b>Username
													(Mobile)</b></label> <input type="text" id="loginusername"
												name="username" class="form-control mobile"
												placeholder="Enter Username (Mobile)" autocomplete="off"
												required="">
										</div>
										<div class="form-group">
											<input type="hidden" name="password" class="form-control"
												id="loginpassword">
										</div>
										<div align="center">
											<input type="submit"
												class="btn btn-sm btn-info form-control col-sm-6"
												value="Sign in">
										</div>
									</form>
								</div>
								<div class="tab-pane container fade" id="signup">
									<form action="/signup" method="post" class="needs-validation"
										id="singupForm" novalidate>
										<div class="form-group textstyle">
											<label for="username" class=""><b>Full
													Name:</b></label> <input type="text" class="form-control"
												placeholder="Enter First name" maxlength="15"
												name="fullname" autocomplete="off" required="required">
										</div>
										<div class="form-group textstyle">
											<label for="username" class=""><b>Username
													(Mobile) :</b></label> <input type="text" class="form-control mobile"
												placeholder="Enter Username (Mobile)" name="mobile"
												autocomplete="off" required="required">
										</div>
										<div class="form-group textstyle">
										<label for="username" class=""><b>Role :</b></label><br>
											<input type="radio"  name="userrole" value="ROLE_USER" required="required">
											<label  class="">User</label><br>
											
											 <input type="radio"
												id="female" name="userrole" value="ROLE_HACKER" required="required"> <label
												 class="">Hacker</label><br>
										</div>

										<div align="center">
											<input type="submit"
												class="btn btn-sm btn-info form-control col-sm-6"
												value="Sign up">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-12 col-sm-3 col-md-3 col-lg-3"></div>
			</div>
	<link rel="stylesheet" href="resources/css/bootstrapValidator.min.css" />
	<script type="text/javascript"
		src="resources/js/bootstrapValidator.min.js"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('.mobile').inputmask('999-999-9999');
							$("#loginusername").keyup(function() {
								$("#loginpassword").val($(this).val());
							});

							$('#signinForm')
									.bootstrapValidator(
											{
												fields : {
													username : {
														validators : {
															notEmpty : {
																message : '<div class="text-center text-danger">Mobile cannot be empty</div>'
															},
														}
													}
												}
											});

							$('#singupForm')
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
													},
													userrole : {
														validators : {
															notEmpty : {
																message : '<div class="text-center text-danger">Role cannot be empty</div>'
															},
														}
													}
												}
											});
						});
	</script>
</body>
</html>