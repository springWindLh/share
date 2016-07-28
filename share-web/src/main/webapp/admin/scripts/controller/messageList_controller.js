angular.module("shareApp_admin")
.controller("messageList_controller",function($rootScope,$scope,$http,messageService,alertService,$modal,msgCountService){
	if($rootScope.msgCount>0){
		$rootScope.MsgCount.count=0;
		msgCountService.update($rootScope.MsgCount,function(response){
			if(response.success){
				$rootScope.msgCount=0;
			}
		});
	}
	messageService.query(function(response){
		$scope.acceptMsgs=[];
		$scope.sendMsgs=[];
		angular.forEach(response.messages,function(message){
			if(message.accepter.id==$rootScope.currentUserId){
				$scope.acceptMsgs.push(message);
			}
			if(message.sender.id==$rootScope.currentUserId){
				$scope.sendMsgs.push(message);
			}
		});
		
		$scope.messageModal=function(acceptName,accepter){
			 var myOtherModal = $modal({
				 scope: $scope, 
				 template: 'sources/template/reply_message_modal.html',
		         show : true
				 });
			  $scope.hideModal = function() {
			    myOtherModal.$promise.then(myOtherModal.hide);
			  };
			  $scope.acceptName=acceptName;
			  $scope.newMessage={};
			  $scope.replyMessage=function(){
				  $scope.newMessage.accepter=accepter;
				  messageService.add($scope.newMessage,function(response){
					  if(response.success){
						  alertService.show("信息发送成功！","success");
						  $rootScope.sendMsg(accepter.id);
						  $scope.sendMsgs.unshift(response.json);
						  $scope.hideModal();
					  }else{
						  alertService.show("信息发送失败！","danger");
					  }
				  });
			  };
		};
		
		$scope.deleteMessage=function(id,index,tag){
			messageService['delete']({id:id},function(response){
				if(tag=="send"){
					$scope.sendMsgs.splice(index, 1);
				}
				if(tag=="accept"){
					$scope.acceptMsgs.splice(index, 1);
				}
			});
		};
	});
});