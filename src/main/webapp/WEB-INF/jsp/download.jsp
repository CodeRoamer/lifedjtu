<%@ page language="java" import="java.util.*,java.net.URL"
	contentType="text/html;charset=UTF8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>人在交大</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="/static/css/bootstrap.css" rel="stylesheet">

<link href="/static/css/bootstrap-responsive.css" rel="stylesheet">
<link href="/static/css/index.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="/static/js/html5shiv.js"></script>
    <![endif]-->

<style>
.header-adr {
    
    width: 96px;
    width: 0.96rem;
    height: 63px;
    height: 0.63rem;
    background: url("/static/ico/tab-adr.png") no-repeat 0 0;
    background-size: 100%;
}

.header-adr-on, .header-adr:hover {
    background-image: url("/static/ico/tab-adr-on.png");
    background-size: 100%;
}

.header-iph {
	
	width: 83px;
	width: 0.83rem;
	height: 63px;
	height: 0.63rem;
	background: url("/static/ico/tab-iph.png") no-repeat 0 0;
	background-size: 100%;
}

.header-iph-on,.header-iph:hover {
	background-image: url("/static/ico/tab-iph-on.png");
	background-size: 100%;
}
</style>

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="/static/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="/static/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="/static/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="/static/ico/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="/static/ico/favicon.png">
</head>

<body>

	<div class="container">

		<div class="masthead">
			<h3 class="muted">人在交大</h3>
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<ul class="nav">
							<li><a href="./index.action">主页</a></li>
							<li class="active"><a>下载</a></li>
							<li><a href="./evaluate.action">服务</a></li>
							<li><a href="#">关于</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- /.navbar -->
		</div>

		<!-- Jumbotron -->
		<div class="jumbotron">
			<h1>Life in DJTU</h1>
			<p class="lead">吃在交大，生活在交大，玩在交大，受罪也在交大~这生活，也是生活！</p>
			<a class="btn btn-large btn-inverse" href="#">Android</a> 
			<a class="btn btn-large btn-success" href="#">IOS</a>
			
		</div>


		<div class="footer">
			<p>&copy; CodeHolic Team 2013</p>
		</div>

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="/static/js/jquery.js"></script>
	<script src="/static/js/bootstrap-transition.js"></script>
	<script src="/static/js/bootstrap-alert.js"></script>
	<script src="/static/js/bootstrap-modal.js"></script>
	<script src="/static/js/bootstrap-dropdown.js"></script>
	<script src="/static/js/bootstrap-scrollspy.js"></script>
	<script src="/static/js/bootstrap-tab.js"></script>
	<script src="/static/js/bootstrap-tooltip.js"></script>
	<script src="/static/js/bootstrap-popover.js"></script>
	<script src="/static/js/bootstrap-button.js"></script>
	<script src="/static/js/bootstrap-collapse.js"></script>
	<script src="/static/js/bootstrap-carousel.js"></script>
	<script src="/static/js/bootstrap-typeahead.js"></script>

</body>
</html>
