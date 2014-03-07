<%@ page language="java" import="java.util.*,java.net.URL" contentType="text/html;charset=UTF8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>人在交大</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="./static/css/bootstrap.css" rel="stylesheet">
   
    <link href="./static/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="./static/css/index.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="./static/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="./static/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="./static/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="./static/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="./static/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="./static/ico/favicon.png">
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
                <li><a href="./download.action">下载</a></li>
                <li class="active"><a href="#">服务</a></li>
                <li><a href="#">关于</a></li>
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>
     

      <!-- Example row of columns -->
      <div class="row-fluid">
        <div class="span2"></div>
        <div class="span8">
        	<h1>交大课程一键评估</h1>
			<form action="" method="post">
				<input name="studentId" type="text" placeholder="输入学号">
				<input name="password" type="password" placeholder="输入密码">
				<button class="btn btn-primary eva-button">一键评估</button>
			</form>
        </div>
        <div class="span2"></div>
      </div>

	<div class="row-fluid">
		<div class="span2"></div>
		<div class="span8">
			<div class="progress progress-striped active">
    			<div class="bar eva-bar" style="width: 0%;"></div>
    		</div>
		</div>
		<div class="span2"></div>
	</div>
	
	<div class="row-fluid">
		<div class="span2"></div>
		<div class="span8 result-alert">
			<div class="alert alert-danger" style="display:none;">
    			<button type="button" class="close" data-dismiss="alert">&times;</button>
    		</div>
    		<div class="alert alert-success" style="display:none;">
    			<button type="button" class="close" data-dismiss="alert">&times;</button>
    		</div>
    		<div class="alert alert-info" style="display:none;">
    			<button type="button" class="close" data-dismiss="alert">&times;</button>
    		</div>
		</div>
		<div class="span2"></div>
	</div>

	<div class="row-fluid">
        <div class="span2"></div>
        <div class="span8">
			<div class="panel panel-danger">
				<div class="panel-heading">
					<h3>待评估课程</h3>
				</div>
				<div class="panel-body">
					<table class="table eva-table">
						
						
					</table>
				</div>
			</div>
        </div>
        <div class="span2"></div>
      </div>

      <hr>

    <div class="footer">
        <p>&copy; CodeHolic Team 2013</p>
      </div>


    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./static/js/jquery.js"></script>
    <script src="./static/js/bootstrap-transition.js"></script>
    <script src="./static/js/bootstrap-alert.js"></script>
    <script src="./static/js/bootstrap-modal.js"></script>
    <script src="./static/js/bootstrap-dropdown.js"></script>
    <script src="./static/js/bootstrap-scrollspy.js"></script>
    <script src="./static/js/bootstrap-tab.js"></script>
    <script src="./static/js/bootstrap-tooltip.js"></script>
    <script src="./static/js/bootstrap-popover.js"></script>
    <script src="./static/js/bootstrap-button.js"></script>
    <script src="./static/js/bootstrap-collapse.js"></script>
    <script src="./static/js/bootstrap-carousel.js"></script>
    <script src="./static/js/bootstrap-typeahead.js"></script>
    <script src="./static/js/custom/evaluate.js"></script>
  </body>
</html>
