angular.module("shareApp")
.controller("menu_controller",function($rootScope,$scope,$http,$timeout,$location,userService,alertService,$modal){
$scope.saveUser=function(newUser){
		var tel_exp=new RegExp('1[0-9]{10}$');
		if(!tel_exp.test(newUser.phone)){
			alertService.show("请填写正确的手机号！","danger","#register_alert_div");
			return;
		};

		userService.add(newUser,function(response){
			if(response.success){				
				alertService.show("注册成功！赶快去登录吧！","success","#register_alert_div");
				$scope.newUser={};
				$scope.newUser.sex="男";
				$timeout(function(){
					$("#register_div").modal('hide');
					$("#login_div").modal();
				},2000);	
			}else{
				alertService.show(response.message,"danger","#register_alert_div");
			}
		});
	};
	$scope.login=function(){
		$http.post("user/login.json",$scope.user).
		success(function(response){
			if(response.success){			
				location.reload();
			}else if(angular.isDefined(response.message)){
				alertService.show(response.message,"danger","#login_alert_div");
			}
			else{
				alertService.show("用户名或密码错误！","danger","#login_alert_div");
			}
		});
	};
	$rootScope.exit=function(){
		$http.post("user/loginOut.json").success(function(response){
			if(response.success){			
				location.reload();
		}
		});
	};
	$scope.showFindBackPwdModal=function(){
		$scope.loading=false;
		$("#login_div").hide();
		 var myOtherModal = $modal({
			 scope: $scope, 
			 template: 'sources/template/find_back_pwd_modal.html',
	         show : true
			 });
		  $scope.hideModal = function() {
		    myOtherModal.$promise.then(myOtherModal.hide);
		  };
		  $scope.user={};
	$scope.findBackPwd=function(){
		$scope.loading=true;
		$http.post('user/find_back_password.json',$scope.user).success(function(response){
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
	};
	
	if($location.url()==""){
		window.location.hash="main";
	}



	$rootScope.selectPlaceModal=function(reloadVisble){
		$scope.currentProvince="";
		$scope.currentSchooltype="";
		$scope.provinceVisble=true;
		 $scope.schoolVisble=false;
		 $scope.reloadVisble=reloadVisble;
		 // var myOtherModal = $modal({
			//  scope: $scope,
			//  template: 'sources/template/select_school_modal.html',
	      //    show : true
			//  });
		 //  $scope.hideModal = function() {
		 //    myOtherModal.$promise.then(myOtherModal.hide);
		 //  };
		$('#placeModal').modal('show');


		function getProvinces(){
			$http.get('school/list/provinces').success(
				function(response){
					$scope.provinces=response.json.provinces;
				});
		}
		getProvinces();
		function getSchools(province,schooltype){
			$http.get('school/list/schools',{params:{
				province:province,
				schooltype:""
			}}).success(
				function(response){
					$scope.schools=response.json.schools;
				});
		}
		  $scope.findSchools=function(province,schooltype){
			  getSchools(province,schooltype);
			  $scope.currentProvince=province;
			  $scope.schoolVisble=true;
			  $scope.provinceVisble=false;
			  $scope.searchVisble=true;
		  };
		  $scope.back=function(){
			  $scope.schoolVisble=false;
			  $scope.provinceVisble=true;
			  $scope.searchVisble=false;
			};

		$scope.currentSchooltype="";
		$scope.setSchooltype=function(type){
			$scope.currentSchooltype=type;
		};
	};

	$scope.closePlaceModal = function () {
		$('#placeModal').modal('hide')
	};

	$scope.updateSchoolInfo=function(){
		if(angular.isUndefined($rootScope.schoolid)){
			$rootScope.schoolname=$rootScope.currentSchoolname;
			$rootScope.schoolid=$rootScope.currentSchoolid;
		}
		$http.post('school/update/'+$rootScope.schoolid).success(function(response){
			if(response.success){
				localStorage.schoolname=$rootScope.schoolname;
				localStorage.schoolid=$rootScope.schoolid;
				$scope.hideModal();
			}
		});
	};
	
	$scope.selectSchool=function(schoolname,schoolid){
		$rootScope.schoolname=schoolname;
		$rootScope.schoolid=schoolid;
	};
	
	$scope.updateSchoolInfoAndReload=function(){
		if(angular.isDefined($scope.schoolid)){
			$rootScope.currentSchoolname=$scope.schoolname;
			$rootScope.currentSchoolid=$scope.schoolid;
		}
		$http.post('school/update/'+$rootScope.currentSchoolid).success(function(response){
			if(response.success){
				localStorage.schoolname=$rootScope.currentSchoolname;
				localStorage.schoolid=$rootScope.currentSchoolid;
				location.reload();
			}
		});
	};
	
	if(!window.localStorage){
		 alert('sorry,your browser doest not support Html5');
		}
	if(angular.isUndefined(localStorage.schoolid)){
		//$rootScope.selectPlaceModal();
	}else{
		if(!$rootScope.currentUserId>0){
			$rootScope.currentSchoolname=localStorage.schoolname;
			$rootScope.currentSchoolid=localStorage.schoolid;
		}
		$scope.updateSchoolInfo();
	}
	
	$scope.seeAllSchoolInfo=function(){
		$http.get('school/seeAll').success(function(response){
			if(response.success){
				localStorage.clear();
				location.reload();
			}
		});
	};
});