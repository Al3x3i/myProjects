
<style>
.special_page {
	background-color: #fafafa;
	position: relative;
}

.special_page>.top {
	height: 359px;
	width: 100%;
	position: absolute;
	background: #608dbe;
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
	border-bottom: 1px solid #0d3259;
	box-shadow: 0 2px 5px rgba(17, 16, 13, 0.5);
}

.special_page>.top .gradient {
	position: absolute;
	width: 100%;
	top: 360px;
	height: 270px;
	background: #e3e3e3;
	background: -moz-linear-gradient(top, #e3e3e3 0, #fafafa 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #e3e3e3),
		color-stop(100%, #fafafa));
	background: -webkit-linear-gradient(top, #e3e3e3 0, #fafafa 100%);
	background: -o-linear-gradient(top, #e3e3e3 0, #fafafa 100%);
	background: -ms-linear-gradient(top, #e3e3e3 0, #fafafa 100%);
	background: linear-gradient(top, #e3e3e3 0, #fafafa 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#e3e3e3',
		endColorstr='#fafafa', GradientType=0);
}

.special_page>.top .white {
	position: absolute;
	height: 1px;
	width: 787px;
	top: 358px;
	margin: 0 auto;
	left: 0;
	right: 0;
	/*   background: url(../img/special-page/white-line.png); */
	z-index: 99;
}

.special_page>.top .shadow {
	z-index: 80;
	/* background: url(../img/special-page/white-line-shadow.png); */
	height: 25px;
	width: 460px;
	position: absolute;
	top: 360px;
	margin: 0 auto;
	left: 0;
	right: 0;
	z-index: 99;
}

.error_nr h1 {
	margin-top: 50px;
	color: #fff;
	font-size: 200px;
	text-shadow: 0 1px 0 #ccc, 0 2px 0 #c9c9c9, 0 3px 0 #bbb, 0 4px 0
		#b9b9b9, 0 5px 0 #aaa, 0 6px 1px rgba(0, 0, 0, 0.1), 0 0 5px
		rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.3), 0 3px 5px
		rgba(0, 0, 0, 0.2), 0 5px 10px rgba(0, 0, 0, 0.25), 0 10px 10px
		rgba(0, 0, 0, 0.2), 0 20px 20px rgba(0, 0, 0, 0.15);
	position: absolute;
	top: 60px;
	text-align: center;
	width: 100%;
}

.error_text {
	position: absolute;
	top: 380px;
	width: 100%;
	text-align: center;
}

.error_text a.button {
	display: inline-block;
}
</style>


<div class=special_page>
	<div class=top>
		<div class=gradient></div>
		<div class=white></div>
		<div class=shadow></div>
	</div>
	<div class=content>
		<div class=error_nr>
			<h1>404</h1>
		</div>
		<div class=error_text>
			<!-- <a href="dashboard.html" class=button> &laquo; Back to dashboard </a> -->
		</div>
	</div>
	<!--[if lt IE 7 ]><script defer src="//ajax.googleapis.com/ajax/libs/chrome-frame/1.0.3/CFInstall.min.js"></script> <script defer>window.attachEvent("onload",function(){CFInstall.check({mode:"overlay"})});</script><![endif]-->
</div>
