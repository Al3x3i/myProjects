<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="imgagePath" value="${pageContext.request.contextPath}/resources/login-page/img" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Log in with your account</title>


<link href="${contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/login-page/css/login-css.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<style>
.special_page>.content .background {
	background: url(${imgagePath}/header.png);
}

.box .header.grey {
	background: url(${imgagePath}/bg-header.png);
}

.box .header.grey h3 {
	background: url(${imgagePath}/divider-header.png) top left no-repeat
		!important;
}

.box .actions {
	background: url(${imgagePath}/bg-action.png) repeat-x #f7f7f7;
}

.special_page .wrapper .shadow {
	background: url(${imgagePath}/box-shadow.png);
}
</style>

<body class=special_page>
	<div class=top>
		<div class=gradient></div>
	</div>
	<div class="content">
		<h1>h'Brouwershuis | Login</h1>
		<div class="background"></div>
		<div class="wrapper">
			<div class="box">
				<div class="header grey">
					<img src="${imgagePath}/lock.png" width="16" height="16">
					<h3>Login</h3>
				</div>
				<form method="POST" action="${contextPath}/login" id="login-form">
					<div class="content no-padding with-actions grey">
						<div class="section _100  ${error != null ? 'has-error error-msg' : ''}" style="display: none">
							<span class="control-label"> ${error} </span>
						</div>
						<div class="section _100">
							<label> Username </label>
							<div class="form-group">
								<input name="username" class="form-control"> <span class="help-inline control-label" style="display: none;">Invalid username</span>
							</div>
						</div>
						<div class="section _100">
							<label> Password </label>
							<div class="form-group ">
								<input name="password" type="password" class="form-control"> <span class="help-inline control-label" style="display: none;">Invalid password</span>
							</div>
						</div>
					</div>
					<div class="actions">
						<div class="actions-left"></div>
						<div class="actions-right">
							<input type="submit" value="Login" onClick="">
						</div>
					</div>
				</form>
			</div>
			<div class="shadow"></div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/jquery/js/jquery.js"></script>

	<script>
 		$(document).ready(function() {

			var login = $("[name^=username]");
			var psw = $("[name^=password]");

			login.val("aukje2017");
			psw.val("aukje2017");

			$("#login-form").submit();  

		}) 

		$("#login-form").submit(function(e) {

			var login = $("[name^=username]");
			var psw = $("[name^=password]");

			var isValid = true;

			if (login.val() == "") {
				login.parent().addClass("has-error");
				login.parent().children().eq(1).show();
				isValid = false;
			} else {
				login.parent().removeClass("has-error");
				login.parent().children().eq(1).hide()
			}
			if (psw.val() == "") {
				psw.parent().addClass("has-error");
				psw.parent().children().eq(1).show()
				isValid = false;
			} else {
				psw.parent().removeClass("has-error");
				psw.parent().children().eq(1).hide()
			}
		});
	</script>
	<!--[if lt IE 7 ]><script defer src="//ajax.googleapis.com/ajax/libs/chrome-frame/1.0.3/CFInstall.min.js"></script> <script defer>window.attachEvent("onload",function(){CFInstall.check({mode:"overlay"})});</script><![endif]-->

</body>

</html>
