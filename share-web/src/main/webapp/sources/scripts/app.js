var shareApp = angular.module("shareApp", [ 'mgcrea.ngStrap', 'ngAnimate','ui.router','ngResource','angularFileUpload','ngTable' ]);
shareApp.run(['$rootScope','$state','$http','$stateParams','msgCountService',function($rootScope,$state,$http,$stateParams,msgCountService){
	$rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.currentUserId=$("#currentUserId").val();
    $rootScope.currentSchoolname=$("#currentSchoolname").val();
    $rootScope.currentSchoolid=$("#currentSchoolid").val();
	$rootScope.baseUrl=$("#baseUrl").val();
	$rootScope.baseHeadImgUrl=$rootScope.baseUrl+"sources/img/head/";
	$rootScope.baseAttachmentImgUrl=$rootScope.baseUrl+"sources/img/attachment/";
	$rootScope.baseSystemImgUrl=$rootScope.baseUrl+"sources/img/system/";
	if($rootScope.currentUserId>0){
	//websocket for msg
	var msgWs=new WebSocket("ws://localhost:8080/share/Msg/point/"+$rootScope.currentUserId+"/msg.html");
	msgWs.onopen=function(){
		msgWs.onmessage=function(e){
			if(e.data=="#come"){
				getMsgCount();
			}
		};
	};
		$rootScope.sendMsg=function(targetId){
			msgWs.send("#send"+targetId);
		};
	};
	
	$rootScope.checkLogin=function(){
		if(!$rootScope.currentUserId>0){
			$("#login_div").modal({show:true});
			return false;
		}else{
			return true;
		}
	};
	$rootScope.showLoginModal=function(){
		$("#login_div").modal({show:true});
	};
	var getMsgCount=function(){
		msgCountService.get({
			id:$rootScope.currentUserId
		},function(response){
			if(response.success){
				$rootScope.MsgCount=response.json;
				$rootScope.msgCount=response.json.count;
			}
		});
	};
	getMsgCount();
}]);



shareApp.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider
	.state('share', {
		url : '/:uri',
		templateUrl : function($stateParams) {
			return $stateParams.uri+'.html'; 
		},
		controllerProvider : function($stateParams) {
			return $stateParams.uri + '_controller';
		}
	}).state('stuff_list', {
		url:'/stuff_list/:free',
		views:{
			'@':{
			templateUrl:'stuffList.html',
			controller:'stuffList_controller'
			}
		}
	}).state('stuff', {
		url:'/stuff/:id',
		templateUrl:'stuffDetail.html',
		controller:'stuffDetail_controller'
	}).state('exchangeStuff', {
		url:'/exchangeStuff/:id',
		templateUrl:'exchangeStuffDetail.html',
		controller:'exchangeStuffDetail_controller'
	}).state('user', {
		url:'/user/:id',
		templateUrl:'userDetail.html',
		controller:'userDetail_controller'
	});
});

