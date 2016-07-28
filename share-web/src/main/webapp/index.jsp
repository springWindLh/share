<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html ng-app="shareApp">
<base href="<%=basePath%>">
<head lang="en">
<meta charset="UTF-8">
<meta name="keywords" content="校园资源,二手交易,资源分享,平台,share,school,campus,sources" />
<meta name="description" content="校园资源分享,校园二手交易,资源,二手,share,school,campus,sources" />
<meta >
<title>校园资源分享平台</title>


<link rel="stylesheet" href="sources/css/angular-motion.css" type="text/css">
<link rel="stylesheet" href="sources/css/bootstrap.css" type="text/css">
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

</head>
<body>
	<div id="alert_div"></div>
	<input type="hidden" id="currentUserId" value="${sessionScope.user.id}">
	<input type="hidden" id="currentSchoolname" value="${sessionScope.school.schoolname}">
	<input type="hidden" id="currentSchoolid" value="${sessionScope.school.schoolid}">
	<input type="hidden" id="baseUrl" value="<%=basePath%>">
	<div class="maxWidth170 menu_div">
		<div ng-class="{schoolname:true,center:currentSchoolname.length>6}">
			<a ng-show="currentUserId==null||currentUserId==''" ng-click="selectPlaceModal(true)" href=""
				style="color: #F0EBEB; font-weight: bold; font-size: 16px; text-shadow: 2px -2px 4px #F5AD81;"><span><i
					class="fa fa-map-marker margin-right10"></i>
					<span ng-if="currentSchoolname==''">选择学校</span>
					<span ng-if="currentSchoolname!=''">{{currentSchoolname}}</span>
					</span></a>
			<span ng-show="currentUserId>0"
				style="color: #F0EBEB; font-weight: bold; font-size: 16px; text-shadow: 2px -2px 4px #F5AD81;"><i
					class="fa fa-map-marker margin-right10"></i>{{currentSchoolname}}</span>
		</div>
		<div class="top_menu">
			<a ui-sref="share({uri:'main'})"
				ng-class="{selected:$state.params.uri!=='shareStuff'&&$state.params.uri!=='stuffSquare'}"><span><i
					class="fa fa-home fa-lg margin10"></i>首页</span></a> <a
				ui-sref="share({uri:'stuffSquare'})"
				ng-class="{selected:$state.params.uri=='stuffSquare'}"><span><i
					class="fa fa-fire fa-lg margin10"></i>资源广场</span></a>
			<div ng-show="currentUserId!=null&&currentUserId!=''">
				<a ui-sref="share({uri:'shareStuff'})"
					ng-class="{selected:$state.params.uri=='shareStuff'}"><span><i
						class="fa fa-share fa-lg margin10"></i>分享资源</span></a>
			</div>
		</div>
		<div class="bottom_menu">
			<div ng-show="currentUserId!=null&&currentUserId!=''">
				<a ui-sref="share({uri:'userCenter'})"><span
					class="fa fa-user fa-lg margin10"></span>个人中心</a> <a
					ui-sref="user({id:currentUserId})"><span
					class="fa fa-share-alt fa-lg margin10"></span>分享之路</a> <a
					ui-sref="share({uri:'myStuffList'})"><span
					class="fa fa-flag fa-lg margin10"></span>资源管理</a> <a
					ui-sref="share({uri:'messageList'})"><span
					class="fa fa-envelope fa-lg margin10"></span>留言信息<span
					ng-show="msgCount>0" class="badge margin-left10">{{msgCount}}</span></a>
				<a href="" ng-click="exit()"><span
					class="fa fa-power-off fa-lg margin10"></span>退出</a>
			</div>
			<div ng-show="currentUserId==null||currentUserId==''">
				<a href="" data-toggle="modal" data-target="#login_div"><span
					class="fa fa-sign-in fa-lg margin10"></span>登录</a> <a href=""
					data-toggle="modal" data-target="#register_div"><span
					class="fa fa-sign-in fa-lg margin10"></span>注册</a>
			</div>

		</div>
	</div>

	<div class="col-sm-12 heightFull paddingNone"
		style="margin-left: 170px;" ui-view></div>

	<div ng-controller="menu_controller">
		<div class="modal fade" id="login_div" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog width400">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<span class="fa fa-signin fa-lg margin10"></span>登录
						</h4>
					</div>
					<form name="loginForm" ng-submit="login()">
						<div id="login_alert_div"></div>
						<div class="modal-body">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<span class="fa fa-envelope"></span>
									</div>
									<input type="text" class="form-control" placeholder="用户名/Email"
										ng-model="user.name" required="required" maxlength="45">
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<span class="fa fa-lock fa-lg"></span>
									</div>
									<input type="password" name="password" class="form-control"
										ng-model="user.password" placeholder="密码" required="required"
										maxlength="45" />
								</div>
							</div>

							<a href="" ng-click="showFindBackPwdModal();" class="pull-right">忘记密码</a>
							<br class="clearfix">

						</div>
						<div class="modal-footer">
							<button type="button" class="button button-pill"
								data-dismiss="modal">取消</button>
							<button type="submit" class="button button-pill button-primary">登录</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div class="modal fade" id="register_div" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog width400">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<span class="fa fa-signin fa-lg margin10"></span>注册
						</h4>
					</div>
					<form name="saveUserForm" ng-submit="saveUser(newUser)">
						<div id="register_alert_div"></div>
						<div class="modal-body">
							<div class="form-group">
								<span class="size20" ng-if="schoolid==undefined"><span
									class="fa fa-map-marker margin10"></span>{{currentSchoolname}}</span>
									<span class="size20" ng-if="schoolid!=undefined"><span
									class="fa fa-map-marker margin10"></span>{{schoolname}}</span>
								<span class="pull-right size12 pointer"
									ng-click="selectPlaceModal(false)"><i class="fa fa-university"></i>更换学校</span>
							</div>
							<div class="form-group">
								<label class="checkbox-inline"><input type="radio"
									ng-init="newUser.sex='男'" ng-model="newUser.sex" value="男">&nbsp;男</label>
								<label class="checkbox-inline"><input type="radio"
									ng-model="newUser.sex" value="女">&nbsp;女</label>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<span class="fa fa-user"></span>
									</div>
									<input type="text" class="form-control" placeholder="用户名"
										ng-model="newUser.name" required="required" maxlength="45">
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<span class="fa fa-envelope"></span>
									</div>
									<input type="email" class="form-control"
										ng-model="newUser.email" placeholder="e-mail"
										required="required" />
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<span class="fa fa-mobile-phone fa-lg"></span>
									</div>
									<input type="tel" class="form-control" ng-model="newUser.phone"
										placeholder="手机号" required="required" maxlength="11" />
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<span class="fa fa-lock fa-lg"></span>
									</div>
									<input type="password" class="form-control"
										ng-model="newUser.password" placeholder="密码"
										required="required" maxlength="45" />
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="button button-pill"
								data-dismiss="modal">取消</button>
							<button type="submit" ng-disabled="schoolid==undefined" class="button button-pill button-action">注册</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="sources/scripts/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="sources/scripts/js/angular.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-file-upload-shim.js"></script>
<script type="text/javascript" src="sources/scripts/js/angular-strap.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-strap.tpl.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-animate.min.js"></script>
<script type="text/javascript"
		src="sources/scripts/js/angular-sanitize.min.js"></script>
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
<script type="text/javascript" src="sources/scripts/app.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/menu_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/main_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/userCenter_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/shareStuff_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/stuffList_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/myStuffList_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/stuffDetail_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/userDetail_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/messageList_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/stuffSquare_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/exchangeStuffList_controller.js"></script>
<script type="text/javascript"
		src="sources/scripts/controller/exchangeStuffDetail_controller.js"></script>
<script type="text/javascript" src="sources/scripts/services/service.js"></script>
<script type="text/javascript"
		src="sources/scripts/filters/common-filters.js"></script>
<script type="text/javascript"
		src="sources/scripts/directives/page-navigation/page-navigation.js"></script>
<script type="text/javascript"
		src="sources/scripts/directives/image-show/image-show.js"></script>
</html>