<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="baseURL" value="${pageContext.request.localName}" />


<c:set var="main_imagePath" value="${pageContext.request.contextPath}/resources/main-page/img" />

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">


<!-- Bootstrap Core CSS -->
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">

<%-- <!-- JQuery - UI CSS -->
<link href="${pageContext.request.contextPath}/resources/jquery/css/jquery-ui.css" rel="stylesheet">
 --%>

<!-- Bootstrap3 Date Picker CSS -->
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-datepicker3.min.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="${pageContext.request.contextPath}/resources/css/plugins/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="${pageContext.request.contextPath}/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
@media print {
	body {-webkit-print-color-adjust: exact;}
}

html, body {
	margin: 0px;
	padding: 0px;
	height: 100%;
	background-color: #fafafa;
}

#page-wrapper {
	height: 100%;
	min-width: 900px;
	position: relative;
}

#page_content {
	height: auto;
	min-height: 100%;
	position: absolute;
	width: 100%;
}

#header-main {
	border-top: 1px solid #7a9cc0;
	background: #366fac;
	background: -moz-linear-gradient(top, rgba(54, 111, 172, 1) 0,
		rgba(24, 75, 130, 1) 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(54,
		111, 172, 1)), color-stop(100%, rgba(24, 75, 130, 1)));
	background: -webkit-linear-gradient(top, rgba(54, 111, 172, 1) 0,
		rgba(24, 75, 130, 1) 100%);
	background: -o-linear-gradient(top, rgba(54, 111, 172, 1) 0,
		rgba(24, 75, 130, 1) 100%);
	background: -ms-linear-gradient(top, rgba(54, 111, 172, 1) 0,
		rgba(24, 75, 130, 1) 100%);
	background: linear-gradient(top, rgba(54, 111, 172, 1) 0,
		rgba(24, 75, 130, 1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#366fac',
		endColorstr='#184b82', GradientType=0);
}

.container_12 {
	width: 92%;
	margin-left: 4%;
	margin-right: 4%;
}

#header-main .container_12 {
	/* width: 92%; */
	padding-top: 30px;
}

#header-main .container_12 ul {
	/* width: 600px; */
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
}

header {
	position: absolute;
	z-index: 10;
	width: 100%;
	top: 0;
}

header a, header a:visited, header a:focus {
	color: #4a4a4a;
	text-decoration: none;
	outline: 0;
}

#nav_main li {
	/* display: inline-block;
	margin-right: 2px; */
	float: left;
	margin-right: 10px;
}

#nav_main>li>a img {
	display: block;
	margin: 0 auto;
}

nav ul, nav ol {
	list-style: none;
	list-style-image: none;
	margin: 0;
	padding: 0;
}

#nav_main .current .menu_item {
	background: url(${main_imagePath}/nav/bg-active.png) no-repeat;
}

#nav_main .menu_item {
	display: table-cell;
	height: 61px;
	width: 120px;
	background: url(${main_imagePath}/nav/bg-normal.png) no-repeat;
	line-height: 26px;
	text-align: center;
	vertical-align: bottom;
}

#nav_main a {
	text-decoration: none;
}

#nav_main .on_hover>.menu_item:hover {
	background: url(${main_imagePath}/nav/bg-hover.png) no-repeat;
}

#nav_main>li>a:hover img, #nav_main>li.current>a img {
	opacity: 1;
}

#nav_main .menu_item img {
	display: block;
	margin: 0 auto;
	opacity: .58;
	filter: alpha(opacity = 58);
}

#nav_sub {
	height: 37px;
	border-bottom: 1px solid #b2b2b2;
	background: #e8e8e8;
	background: -moz-linear-gradient(top, #e8e8e8 0, #d4d4d4 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #e8e8e8),
		color-stop(100%, #d4d4d4));
	background: -webkit-linear-gradient(top, #e8e8e8 0, #d4d4d4 100%);
	background: -o-linear-gradient(top, #e8e8e8 0, #d4d4d4 100%);
	background: -ms-linear-gradient(top, #e8e8e8 0, #d4d4d4 100%);
	background: linear-gradient(top, #e8e8e8 0, #d4d4d4 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#e8e8e8',
		endColorstr='#d4d4d4', GradientType=0);
}

#nav_main li ul {
	width: 100%;
	position: absolute;
	line-height: 38px;
	left: 0;
	display: none;
	display: block;
}

#nav_main li ul li {
	display: inline-block;
	padding: 0 30px;
	float: left;
}

#nav_main li ul li:first-child {
	padding-left: 5px;
}

#nav_main li .sub-menu {
	display: none;
	padding-left: 4%;
}

#nav_main li .sub-menu.current {
	display: block;
}

#nav_main li .sub-menu .current a {
	color: #205a94;
}

.panel-heading {
	background: #78b1ed;
	background: -moz-linear-gradient(top, rgba(120, 177, 237, 1) 0,
		rgba(65, 123, 181, 1) 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(120,
		177, 237, 1)), color-stop(100%, rgba(65, 123, 181, 1)));
	background: -webkit-linear-gradient(top, rgba(120, 177, 237, 1) 0,
		rgba(65, 123, 181, 1) 100%);
	background: -o-linear-gradient(top, rgba(120, 177, 237, 1) 0,
		rgba(65, 123, 181, 1) 100%);
	background: -ms-linear-gradient(top, rgba(120, 177, 237, 1) 0,
		rgba(65, 123, 181, 1) 100%);
	background: linear-gradient(top, rgba(120, 177, 237, 1) 0,
		rgba(65, 123, 181, 1) 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#78b1ed',
		endColorstr='#417bb5', GradientType=0);
	height: 34px;
	line-height: 34px;
	border: 1px solid #2b5177;
	border-radius: 3px 3px 0 0;
	border-bottom: 0;
	box-shadow: inset 0 1px 0 0 rgba(255, 255, 255, 0.5);
}

.panel_content {
	border-radius: 0;
	border-bottom: 0;
	padding: 0 10px;
	border: 1px solid #c8c8c8;
	border-radius: 0 0 3px 3px;
	border-top: 1px solid #2b5177;
	background: #fff;
}

#content-wrapper {
	width: 100%;
	height: 100%;
	/* z-index: 50; */
	position: relative;
	padding-top: 130px;
	background: #fafafa;
}

.toolbutton {
	position: absolute;
	right: 0;
	margin-right: 20px;
	margin-top: 30px;
}
</style>

</head>

<body>
	<div id="page-wrapper">
		<header>
			<div class="toolbutton">
				<div class="btn-group">

					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<form id="logoutForm" method="POST" action="${pageContext.request.contextPath}/login?logout">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
						<h4 style="color: #5bc0de; font-weight: bold">
							Welcome ${pageContext.request.userPrincipal.name} |
							<a style="color: #cdcdcd;" onclick="document.forms['logoutForm'].submit()">
								<i class="fa fa-sign-out fa-fw"></i>Logout
							</a>
						</h4>
					</c:if>
				</div>
			</div>
			<nav id="header-main">
				<div class="container_12">
					<ul id="nav_main">
						<li id="rooster" class="on_hover current">
							<a href="#" class="menu_item" onclick="menuButtonClick('rooster')">
								<img src="${main_imagePath}/nav/computer-imac.png" width="25" height="25" alt=""> Rooster
							</a>
							<ul class="sub-menu">
								<li id="roosterdienst">
									<a href="#" onClick="subMenuButtonClick('roosterdienst')">Rooster</a>
								</li>
								<li id="slaapdienst">
									<a href="#" onClick="subMenuButtonClick('slaapdienst')">Slaap dienst</a>
								</li>
								<li id="schakeldienst">
									<a href="#" onClick="subMenuButtonClick('schakeldienst')">Schakel dienst</a>
								</li>
								<li id="vvvdienst">
									<a href="#" onClick="subMenuButtonClick('vvvdienst')">VVV dienst</a>
								</li>
							</ul>
						</li> 
						<li id="working_hours" class="on_hover">
							<a href="#" class="menu_item" onclick="menuButtonClick('working_hours')">
								<img src="${main_imagePath}/nav/blocks---images.png" width="25" height="25" alt=""> Vacantie Uren
							</a>
						</li>
						
						<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
							<li id="employees" class="on_hover">
								<a href="#" class="menu_item" onclick="menuButtonClick('employees')">
									<img src="${main_imagePath}/nav/user-comment.png" width="25" height="25" alt=""> Medewerkers
								</a>
							</li>
						</sec:authorize>
						<%-- <li class="on_hover">
							<a href="#" class="menu_item">
								<img src="${main_imagePath}/nav/robot.png" width="25" height="25" alt=""> Settings
							</a>
						</li> --%>
					</ul>
				</div>
				<div id="nav_sub"></div>
			</nav>
			<div id="header-sub"></div>
		</header>

		<div id="content-wrapper">
			<div id="page_content" class="container-fluid"></div>
		</div>
		<div class="footer"></div>
		<!-- /#page-wrapper -->

	</div>
	<!-- jQuery -->
	<script src="${pageContext.request.contextPath}/resources/jquery/js/jquery.js"></script>
	<!--END -->

	<!-- Bootstrap Core JavaScript -->
	<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
	<!--END -->

	<!-- Bootstrap Datepicker JavaScript -->
	<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-datepicker.min.js"></script>
	<!--END -->

	<!-- Date time helper JavaScript -->
	<script src="${pageContext.request.contextPath}/resources/js/moment.js"></script>
	<!--END -->

	<!-- Table time input filter -->
	<script src="${pageContext.request.contextPath}/resources/js/inputTimeHandler.js"></script>

	<!-- jQuery time picker -->
	<script src="${pageContext.request.contextPath}/resources/js/auto_format_timepicker.js"></script>

	<!--If I move below code to the page then it is deprecated by JSON  -->
	<!-- DataTable Script, See employees page Core JavaScript -->
	<script src="${pageContext.request.contextPath}/resources/jquery/js/jquery.dataTables.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/bootstrap/js/dataTables.bootstrap.js"></script>


	<script src="${pageContext.request.contextPath}/resources/js/declareWorkHours.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/workSchedule.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/agendaView.js"></script>
	

</body>

<script>
	$(document).ready(function() {
		//menuButtonClick("employees");
		//menuButtonClick("working_hours");
		menuButtonClick("rooster");
		subMenuButtonClick("vvvdienst")
	})

	function menuButtonClick(viewName) {
		var requestString = "";
		switch (viewName) {
		case "employees":
			requestString = "${pageContext.request.contextPath}/employees";
			break;
		case "working_hours":
			requestString = "${pageContext.request.contextPath}/working_hours";
			break;
		case "rooster":
			break;
		default:
			//requestString = "${pageContext.request.contextPath}/"; break;
		}

		var activeTag = $("#nav_main>.current");
		var activeTagText = "";

		if (activeTag.length != 0) {
			activeTagText = activeTag.first().text().trim();
		}

		var selectedTag = $("#nav_main #" + viewName);

		if (viewName != activeTagText) {
			activeTag.first().removeClass("current");
			selectedTag.addClass("current");

			var subActiveBar = activeTag.find("ul");
			var subSelectedBar = selectedTag.find("ul");

			if (subActiveBar.length != 0) {
				subActiveBar.first().removeClass("current");
			}

			if (subSelectedBar.length != 0) {
				subSelectedBar.first().addClass("current");
			}
		}

		//load content of slected menu item
		if (requestString != "") {
			$.get(requestString, function(data) {
				$("#page_content").html(data);
			});
		}
	}

	function subMenuButtonClick(subViewName) {
		var requestString = "";

		switch (subViewName) {
		case "roosterdienst":
			requestString = "${pageContext.request.contextPath}/schedules/subview/roosterdienst";
			break;
		case "slaapdienst":
			requestString = "${pageContext.request.contextPath}/schedules/subview/slaapdienst";
			break;
		case "vvvdienst":
			requestString = "${pageContext.request.contextPath}/schedules/subview/vvvdienst";
			break;
		default:
			//requestString = "${pageContext.request.contextPath}/"; break;
		}

		var activeSubTag = $("#nav_main ul.current>li.current");
		var activeSubTagId = "";

		if (activeSubTag.length != 0) {
			activeSubTagId = activeSubTag.first().attr("id");
		}

		var selectedSubTag = $("#nav_main #" + subViewName);

		if (activeSubTagId != subViewName) {
			activeSubTag.first().removeClass("current");
			selectedSubTag.addClass("current");
		}

		if (requestString != "") {
			$.get(requestString, function(data, v, s, g) {
				$("#page_content").html(data);
			});
		}
	}
</script>


</html>
