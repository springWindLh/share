var shareApp_admin = angular.module("shareApp_admin", ['mgcrea.ngStrap']);
shareApp_admin
.controller("menu_controller",function($rootScope,$scope,$http,$timeout,$location,alertService){

	$scope.login=function(){
		$http.post("../admin_login.json",$scope.user).
		success(function(response){
			if(response.success){			
				window.location.href="../admin/#dashboard";
			}else{
				alertService.show("用户名或密码错误！","danger","#login_alert_div");
			}
		});
	};
	
	
	$scope.findBackPwd=function(){
		$scope.loading=true;
		$http.post('../user/find_back_password.json',$scope.user).success(function(response){
			if(response.success){
				$scope.hideModal();
				$("#login_div").show();
				alertService.show('找回密码邮件已发送，请注意查收！','success','#login_alert_div');
			}else{
				alertService.show('用户名或邮箱验证失败！','danger','#findBackPwd_div');
				$scope.loading=false;
			}
		});
	};
});