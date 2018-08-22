<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ page session="false"%>


<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Modern Modular Insurance Calculator</title>

<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />

<style>
.insurance-module:hover {
	background-color: #ffc96e;
}

.insurance-module {
	background: #e8e6e3;
}
</style>

</head>

<body>

	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="">
				<img id="logo" src="<c:url value="/resources/img/shield.png" />" alt="" height="42" width="42"> Modular Insurance Calculator
			</a>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">

				</ul>
			</div>
		</div>
	</nav>

	<!-- Page Content -->
	<div class="container">

		<!-- Page Heading/Breadcrumbs -->
		<h2 class="text-center mt-4 mb-3" style="text-align: center;">Online Insurance</h2>


		<!-- Portfolio Item Row -->
		<div class="row">
			<div class="col-md-12">
				<img class="img-fluid rounded mx-auto d-block" style="max-width: 500px;" src="<c:url value="/resources/img/welcome_insurance.jpg" />" alt="">
			</div>

		</div>
		<!-- /.row -->

		<!-- Related Projects Row -->
		<h3 class="my-4">Related Projects</h3>

		<div class="row">

			<div class="col-md-3 col-sm-6 mb-4" onClick="loadSubview('Bike')">

				
					<div class="border insurance-module" style="text-align: center; border-radius: 10px;">
						<img style="padding: 10px; height: 100px;" class="img-fluid" src="<c:url value="/resources/img/bike.png" />" alt="">

					</div>
				
				<p style="text-align: center;">Bike</p>

			</div>

			<div class="col-md-3 col-sm-6 mb-4" onClick="loadSubview('Jewerly')">

				<div class="border insurance-module" style="text-align: center; border-radius: 10px;">
					<img style="padding: 10px; height: 100px;" class="img-fluid" src="<c:url value="/resources/img/jewerly.png" />" alt="">
				</div>

				<p style="text-align: center;">Jewerly</p>

			</div>

			<div class="col-md-3 col-sm-6 mb-4" onClick="loadSubview('Electronics')">

				<div class="border insurance-module" style="text-align: center; border-radius: 10px;">
					<img style="padding: 10px; height: 100px;" class="img-fluid" src="<c:url value="/resources/img/electronics.png" />" alt="">
				</div>

				<p style="text-align: center;">Electronics</p>

			</div>
			<div class="col-md-3 col-sm-6 mb-4" onClick="loadSubview('SportsEquipment')">

				<div class="border insurance-module" style="text-align: center; border-radius: 10px;">
					<img style="padding: 10px; height: 100px;" class="img-fluid" src="<c:url value="/resources/img/sports.png" />" alt="">
				</div>

				<p style="text-align: center;">Sports Equipment</p>
			</div>
		</div>
		<div class="row" id="modules-content" style="margin-bottom: 50px;"></div>
		<!-- /.row -->

	</div>
	<!-- /.container -->

	<!-- Footer -->
	<footer class="py-2 bg-dark fixed-bottom">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Your Website 2018</p>
		</div>
		<!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="<c:url value="/resources/jquery/jquery.min.js" />"></script>
	<script>
		function loadSubview(subviewName) {
			$("#modules-content").load("subviews?name=" + subviewName, function(){
				window.scrollTo(0,document.body.scrollHeight);
			});
		}
	</script>

</body>

</html>
