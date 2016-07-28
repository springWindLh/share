<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html ng-app="shareApp_admin">
<base href="<%=basePath%>">
<head>
<title>校园资源分享平台</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="sources/scripts/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="sources/scripts/js/angular.min.js"></script>

<!-- css -->
<link rel="stylesheet" href="admin/css/bootstrap.min.css" />
<link rel="stylesheet" href="sources/css/bootstrap.css" />
<link rel="stylesheet" href="admin/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="admin/css/fullcalendar.css" />
<link rel="stylesheet" href="admin/css/unicorn.main.css" />
<link rel="stylesheet" href="admin/css/unicorn.grey.css"
	class="skin-color" />
<link rel="stylesheet" href="sources/css/angular-motion.css"
	type="text/css">
<link rel="stylesheet" href="sources/css/main.css" type="text/css">
<link rel="stylesheet" href="sources/css/buttons.css" type="text/css">
<link rel="stylesheet" href="sources/css/jqpagination.css"
	type="text/css">
<link rel="stylesheet" href="sources/css/font-awesome.css"
	type="text/css">
<link rel="stylesheet" href="sources/css/jquery.fancybox.css"
	type="text/css">
<link rel="stylesheet" href="sources/css/jquery.fancybox-thumbs.css"
	type="text/css">
<link rel="stylesheet" href="sources/css/ng-table.css" type="text/css">
<link rel="stylesheet" href="sources/css/base.css" type="text/css">
<body ng-controller="main_controller">
	<input type="hidden" id="currentUserId" value="${sessionScope.user.id}">
	<input type="hidden" id="currentAdminId"
		value="${sessionScope.admin.id}">
	<input type="hidden" id="baseUrl" value="<%=basePath%>">
	<a href="" ng-click="exit()"
		style="cursor: pointer; color: white; float: right; margin: 20px;"><i
		class="fa fa-power-off"></i> <span class="text">退出</span></a>
	<div style="color: white; width: 300px; margin: 0 auto; padding: 10px;">
		<h3>校园资源分享平台</h3>
	</div>

	<h2 style="color: white; margin-top: -30px;">
		<i class="fa fa-wrench fa-2x"></i>后台管理
	</h2>
	<div id="sidebar">
		<a href="#" class="visible-phone"><i class="icon icon-home"></i>仪表盘</a>
		<ul>
			<li ng-class="{active:$state.params.uri=='dashboard'}"><a
				ui-sref="share_admin({uri:'dashboard'})"><i
					class="icon icon-home"></i> <span>仪表盘</span></a></li>
			<li class="submenu"
				ng-class="{active:$state.params.uri=='userList'||$state.params.uri=='stuffList'||$state.params.uri=='typeList'}"><a href="#"><i
					class="icon icon-th-list"></i> <span>数据管理</span> <span
					class="label">3</span></a>
				<ul>
					<li><a ui-sref="share_admin({uri:'userList'})">用户管理</a></li>
					<li><a ui-sref="share_admin({uri:'stuffList'})">资源管理</a></li>
					<li><a ui-sref="share_admin({uri:'typeList'})">资源类型管理</a></li>
				</ul></li>
			<li class="submenu"
				ng-class="{active:$state.params.uri=='schoolInit'}"><a href="#"><i
					class="icon icon-th-list"></i> <span>数据初始化管理</span> <span
					class="label">3</span></a>
				<ul>
					<li><a ui-sref="share_admin({uri:'schoolInit'})">学校信息导入</a></li>
					<li><a ui-sref="share_admin({uri:''})">***1</a></li>
					<li><a ui-sref="share_admin({uri:'typeList'})">***2</a></li>
				</ul></li>
		</ul>

	</div>

	<div id="style-switcher">
		<i class="icon-arrow-left icon-white"></i> <span>Style:</span> <a
			id="#grey"
			style="background-color: #555555; border-color: #aaaaaa; cursor: pointer;"></a>
		<a id="#blue" style="background-color: #2D2F57; cursor: pointer;"></a>
		<a id="#red" style="background-color: #673232; cursor: pointer;"></a>
	</div>

	<div id="content" class="heightFull">
		<div ui-view></div>
	</div>

</body>
<script src="admin/scripts/js/excanvas.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-file-upload-shim.js"></script>
<script type="text/javascript" src="sources/scripts/js/angular-strap.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-strap.tpl.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-animate.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-sanitize.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-translate.js"></script>
<script type="text/javascript" src="sources/scripts/js/bootstrap.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-ui-router.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-file-upload.js"></script>
<script type="text/javascript" src="sources/scripts/js/buttons.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/jquery.jqpagination.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-resource.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/jquery.fancybox-thumbs.js"></script>
<script type="text/javascript" src="sources/scripts/js/ng-table.js"></script>
<script type="text/javascript" src="admin/scripts/app.js"></script>
<script src="admin/scripts/js/jquery.ui.custom.js"></script>
<script src="admin/scripts/js/bootstrap.min.js"></script>
<script src="admin/scripts/js/jquery.flot.min.js"></script>
<script src="admin/scripts/js/jquery.flot.resize.min.js"></script>
<script src="admin/scripts/js/jquery.peity.min.js"></script>
<script src="admin/scripts/js/fullcalendar.min.js"></script>
<script src="admin/scripts/js/unicorn.js"></script>
<script src="admin/scripts/js/unicorn.dashboard.js"></script>
<script src="admin/scripts/js/ichart.1.2.min.js"></script>

<!-- controller -->
<script type="text/javascript"
		src="admin/scripts/controller/userList_controller.js"></script>
<script type="text/javascript"
		src="admin/scripts/controller/typeList_controller.js"></script>
<script type="text/javascript"
		src="admin/scripts/controller/stuffList_controller.js"></script>
<script type="text/javascript"
		src="admin/scripts/controller/messageList_controller.js"></script>
<script type="text/javascript"
		src="admin/scripts/controller/exchangeStuffList_controller.js"></script>
<script type="text/javascript"
		src="admin/scripts/controller/dashboard_controller.js"></script>
<script type="text/javascript"
		src="admin/scripts/controller/main_controller.js"></script>
<script type="text/javascript"
		src="admin/scripts/controller/schoolInit_controller.js"></script>
<script type="text/javascript" src="admin/scripts/services/service.js"></script>
<script type="text/javascript"
		src="admin/scripts/filters/common-filters.js"></script>
<script type="text/javascript"
		src="admin/scripts/directives/page-navigation/page-navigation.js"></script>
<script type="text/javascript"
		src="admin/scripts/directives/image-show/image-show.js"></script>
</html>