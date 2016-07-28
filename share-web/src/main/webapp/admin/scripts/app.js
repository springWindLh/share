var shareApp_admin = angular.module("shareApp_admin", [ 'mgcrea.ngStrap', 'ngAnimate','ui.router','ngResource','angularFileUpload','ngTable','pascalprecht.translate' ]);

shareApp_admin.run(['$rootScope','$state','$http','$stateParams','msgCountService',function($rootScope,$state,$http,$stateParams,msgCountService){
	$rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.currentUserId=$("#currentUserId").val();
    $rootScope.currentAdminId=$("#currentAdminId").val();
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


shareApp_admin.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider
	.state('share_admin', {
		url : '/:uri',
		templateUrl : function($stateParams) {
			return 'admin/'+$stateParams.uri+'.html'; 
		},
		controllerProvider : function($stateParams) {
			return $stateParams.uri + '_controller';
		}
	});
});

