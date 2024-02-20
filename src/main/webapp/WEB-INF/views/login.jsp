<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
		<!-- BEGIN LOGO -->
		<div class="login">
			<div class="logo">

			</div>
			<!-- END LOGO -->
			<!-- BEGIN LOGIN -->
			<div class="content">
				<!-- BEGIN LOGIN FORM -->

				<form class="login-form" action="${_ctx}/security/login.hs" method="post">
					<div style="text-align:center">
						<img style="width: 75%;height: auto;"
							src="${_ctx}/resources/logintemp/Content/themes/assets/img/logoVK.png" alt="logo" />
					</div>
					<h3 class="form-title" style="margin-top: 50px">
						<spring:message code='login.title' />
						<div id = "str-message"></div>
					</h3>
					<div class="alert alert-danger display-hide">
						<button class="close" data-close="alert"></button>
						<span style="color: #b94a48">Your login data is incorrect.</span>
					</div>
					<div class="form-group">
						<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
						<label class="control-label visible-ie8 visible-ie9">Username</label>
						<div class="input-icon">
							<i class="fa fa-user"></i>
							<input class="form-control placeholder-no-fix" type="text" autocomplete="off"
								placeholder="<spring:message code='login.form.identity'/>" name="loginId" id="loginId"
								required />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label visible-ie8 visible-ie9">Password</label>
						<div class="input-icon">
							<i class="fa fa-lock"></i>
							<input class="form-control placeholder-no-fix" type="password" autocomplete="off"
								placeholder="<spring:message code='login.form.password'/>" name="loginPw" id="loginPw"
								required />
						</div>
					</div>
					<div class="form-actions">

						<button type="submit" class="btn blue pull-right" name="loginbtn1" id="loginbtn1">
							<spring:message code='login.form.login' /> <i class="m-icon-swapright m-icon-white"></i>
						</button>

					</div>
					<div>
						<p class="fa fa-hand-o-right"> </p> <a href="#" id="findPassword" style="color:white;font-size:17px">
							<spring:message code='login.misspass' />
						</a>
						<div class="modal fade" id="findPasswordModal" tabindex="-1" role="dialog" aria-labelledby="findPasswordModalLabel"
							 aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="findPasswordModalLabel">
											<spring:message code='login.form.change.password' />
										</h5>
									</div>
									<div class="modal-body">
										<div class="login-content fs-13px">
											<div class="form-floating mb-20px">
												<input type="text" class="form-control fs-13px h-45px" id="emp_id"
												placeholder="<spring:message code='login.find.password.empid' />" maxlength="32">
												<label for="emp_id" class="d-flex align-items-center py-0">
													<spring:message code='login.find.password.empid' />
												</label>
											</div>
											<div class="form-floating mb-20px">
												<input type="text" class="form-control fs-13px h-45px" id="emp_name"
												placeholder="<spring:message code='login.find.password.empname' />" maxlength="100">
												<label for="emp_name" class="d-flex align-items-center py-0">
													<spring:message code='login.find.password.empname' />
												</label>
											</div>
											<div class="form-floating mb-20px">
												<input type="email" class="form-control fs-13px h-45px" id="emp_email"
												placeholder="<spring:message code='login.find.password.email' />" maxlength="100">
												<label for="emp_email" class="d-flex align-items-center py-0">
													<spring:message code='login.find.password.email' />
												</label>
											</div>
											<div class="result_password" class="form-floating mb-20px">
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" id="find_pw_btn" class="btn btn-primary">
											<spring:message code='button.find.title' />
										</button>
										<button type="button" id="find_pw_cancle_btn" class="btn btn-secondary"><spring:message code='button.cancle.title' />
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="form-group m-b-20">

						<select name="lang" style="margin-top: 15px;">
							<option value="vt">
								<spring:message code='lang.select.lang.vt' />
							</option>
							<option value="ko">
								<spring:message code='lang.select.lang.ko' />
							</option>
							<option value="en">
								<spring:message code='lang.select.lang.en' />
							</option>

						</select>
					</div>
				</form>
				
				<!-- END LOGIN FORM -->
				<!-- BEGIN FORGOT PASSWORD FORM -->
				<!-- END FORGOT PASSWORD FORM -->
			</div>
			<!-- END LOGIN -->
			<!-- BEGIN COPYRIGHT -->
			<div class="copyright">
				2022 &copy; Copyright IT Vietko soft.
			</div>
			<!-- END COPYRIGHT -->
			<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
			<!-- BEGIN CORE PLUGINS -->
			<!--[if lt IE 9]>
    <script src="~${_ctx}/resources/logintemp/Content/themes/assets/plugins/excanvas.min.js"></script>
    <script src="~${_ctx}/resources/logintemp/Content/themes/assets/plugins/respond.min.js"></script>
    <![endif]-->
			<script src="${_ctx}/resources/logintemp/Content/themes/assets/plugins/jquery-1.10.2.min.js"
				type="text/javascript"></script>



			<script src="${_ctx}/resources/logintemp/Content/themes/assets/plugins/bootstrap/js/bootstrap.min.js"
				type="text/javascript"></script>


			<!-- END CORE PLUGINS -->
			<!-- BEGIN PAGE LEVEL PLUGINS -->
			<script
				src="${_ctx}/resources/logintemp/Content/themes/assets/plugins/jquery-validation/dist/jquery.validate.min.js"
				type="text/javascript"></script>
			<script
				src="${_ctx}/resources/logintemp/Content/themes/assets/plugins/backstretch/jquery.backstretch.min.js"
				type="text/javascript"></script>
			<script type="text/javascript"
				src="${_ctx}/resources/logintemp/Content/themes/assets/plugins/select2/select2.min.js"></script>
			<!-- END PAGE LEVEL PLUGINS -->
			<!-- BEGIN PAGE LEVEL SCRIPTS -->
			<script src="${_ctx}/resources/logintemp/Content/themes/assets/scripts/core/app.js"
				type="text/javascript"></script>
			<script src="${_ctx}/resources/logintemp/Content/themes/assets/scripts/custom/login-soft.js"
				type="text/javascript"></script>

			<!-- END PAGE LEVEL SCRIPTS -->
			<script>
				$(function () {
					// 초기 접속시 정의된 언어 선택되도록.
					$("select[name=lang]").val("${_lang}");

					// 언어 변경시 선택된 언어로 변경.
					$("select[name=lang]").change(function () {
						location.href = location.pathname + "?lang=" + $(this).val();
					});

					$("#emp_id").val("");
					$("#emp_email").val("");

					$("#findPassword").click(function(){
						$('#findPasswordModal').modal({
							show: 'true'
						});
					});

					$("#find_pw_btn").click(function(){
						$.post("/editPassword.hs", {
							'empId' : $("#emp_id").val(),
							'empName' : $("#emp_name").val(),
							'mail' : $("#emp_email").val()
						}, function( data ) {
							if(data != "F"){
								$(".result_password").html("<spring:message code='login.find.password.message' /> : "+data);
							} else {
								$(".result_password").html("<font color='red'><spring:message code='login.find.password.notfind.message' /></font>");
							}
						}, 'text');
					});

					$("#find_pw_cancle_btn").click(function(){
						$('#findPasswordModal').modal('hide');
						$("#emp_id").val("");
						$("#emp_email").val("");
					});

					App.init();
					Login.init();

					var username = $('[name="username"]').val();
					if (username == '')
						$('[name="username"]').focus();
					else
						$('[name="password"]').focus();

					let loginStr = window.location.search;
					if(loginStr === '?login=error'){
						<%--$("#str-message").html("<spring:message code='login.form.thatbai' />")--%>
						<%--toastr['error']("<spring:message code='login.form.thatbai' />");--%>
						alert("<spring:message code='login.form.thatbai' />");
					}
					if(loginStr === '?login=session'){
						<%--$("#str-message").html("<spring:message code='login.form.hoanthanh' />")--%>
						<%--toastr['error']("<spring:message code='login.form.hoanthanh' />");--%>
						alert("<spring:message code='login.form.hoanthanh' />");
					}

				});
			</script>
			<!-- END JAVASCRIPTS -->
		</div>
