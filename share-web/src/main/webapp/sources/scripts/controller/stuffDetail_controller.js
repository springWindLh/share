angular.module("shareApp")
.controller("stuffDetail_controller",function($rootScope,$scope,$location,$stateParams,$http,$timeout,stuffService,alertService,commentService,replyService,$modal,messageService){
	stuffService.get({id:$stateParams.id},function(response){
		$scope.stuff=response.json;
		
		window._bd_share_config = {
				"common" : {
					"bdDesc":$scope.stuff.description,
					"bdText" : "《校园资源分享平台》"+$scope.stuff.title,
					"bdMini" : "2",
					"bdMiniList" : false,
					"bdStyle" : "1",
					"bdSize" : "24",
					"bdPic":$scope.stuff.attachments.length>0?$rootScope.baseAttachmentImgUrl+$scope.stuff.attachments[0].path:""
				},
				
				"slide" : {
					"type" : "slide",
					"bdImg" : "5",
					"bdPos" : "right",
					"bdTop" : "100"
				},
				"image" : {
					"viewList" : [ "qzone", "tsina", "tqq", "renren", "weixin" ],
					"viewText" : "分享到：",
					"viewSize" : "16"
				},
				"selectShare" : {
					"bdContainerClass" : null,
					"bdSelectMiniList" : [ "qzone", "tsina", "tqq", "renren", "weixin" ]
				}
			};
			with (document)
				0[(getElementsByTagName('head')[0] || body)
						.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
						+ ~(-new Date() / 36e5)];
	});
	
	$scope.getComments=function(page){
		commentService.query(
		{
			stuffId:$stateParams.id,
			_count:10,
			_page:page
		},
		function(response){
			$scope.comments=response.comments;
			$scope.pagesize=response._pageSize;
		});
	};
	$scope.getComments(1);
	$scope.$on("page",function(event,data){
		$scope.getComments(data);
	});
	$scope.topage=function(page){
		alert(page);
	};
	$scope.save_comment=function(newComment,stuff){
		newComment.stuff=stuff;
		commentService.add(newComment,function(response){
			if(response.success){
				newComment.content="";
				$scope.comments.push(response.json);
			}
		});
	};
	
	$scope.save_reply=function(newReply,comment){
		newReply.stuff=$scope.stuff;
		newReply.comment=comment;
		replyService.add(newReply,function(response){
			if(response.success){
				comment.replies.push(response.json);
				newReply.content="";
			}
		});
	};
	
	$scope.messageModal=function(){
		if(!$rootScope.checkLogin()){
			return;
		}
		$scope.acceptName=$scope.stuff.user.name;
		 var myOtherModal = $modal({
			 scope: $scope, 
			 template: 'sources/template/send_message_modal.html',
	         show : true
			 });
		  $scope.hideModal = function() {
		    myOtherModal.$promise.then(myOtherModal.hide);
		  };
		  $scope.newMessage={};
		  $scope.sendMessage=function(){
			  $scope.newMessage.accepter=$scope.stuff.user;
			  messageService.add($scope.newMessage,function(response){
				  if(response.success){
					  $rootScope.sendMsg($scope.newMessage.accepter.id);
					  alertService.show("信息发送成功！","success");
					  $scope.hideModal();
				  }else{
					  alertService.show("信息发送失败！","danger");
				  }
			  });
		  };
	};
	$scope.deleteComment=function(commentId,index){
		commentService['delete']({id:commentId},function(response){
			if(response.success){
				$scope.comments.splice(index,1);
			}
		});
	};
	$scope.deleteReply=function(comment,replyId,index){
		replyService['delete']({id:replyId},function(response){
			if(response.success){
				comment.replies.splice(index,1);
			}
		});
	};
	
});