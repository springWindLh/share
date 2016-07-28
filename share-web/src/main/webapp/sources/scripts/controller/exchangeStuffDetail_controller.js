angular.module("shareApp")
.controller("exchangeStuffDetail_controller",function($rootScope,$scope,$stateParams,$http,$timeout,exchangeStuffService,alertService,$modal,$upload,messageService){
	exchangeStuffService.get({id:$stateParams.id},function(response){
		$scope.exchangeStuff=response.json;
	});
	$http.get('exchangeStuff/listReplys/'+$stateParams.id+'.json')
	.success(function(response){
		$scope.exchangeStuffReplys=response.exchangeStuffRelys;
		if($rootScope.currentUserId>0){
		exchangeStuffService.query({
			_count:999,
			_operator:'=',
			_query:'user.id,'+$rootScope.currentUserId
		},function(response){
			$scope.userExchangeStuffs=response.exchangeStuffs;
			for(var i=0;i<response.exchangeStuffs.length;i++){
			 angular.forEach($scope.exchangeStuffReplys,function(es){
				 if($scope.userExchangeStuffs[i].id==es.id){
					 $scope.userExchangeStuffs.splice(i,1);
				 }
			 });
			}
		});
		}
	});
	
	
	$scope.messageModal=function(){
		if(!$rootScope.checkLogin()){
			return;
		}
		$scope.acceptName=$scope.exchangeStuff.user.name;
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
			  $scope.newMessage.accepter=$scope.exchangeStuff.user;
			  messageService.add($scope.newMessage,function(response){
				  if(response.success){
					  alertService.show("信息发送成功！","success");
					  $scope.hideModal();
				  }else{
					  alertService.show("信息发送失败！","danger");
				  }
			  });
		  };
	};
	
	$scope.exchangeStuffModal=function(){
		 var myOtherModal = $modal({
			 scope: $scope, 
			 template: 'sources/template/exchangeStuffDetail_edit_modal.html',
	         show : true
			 });
		  $scope.hideModal = function() {
		    myOtherModal.$promise.then(myOtherModal.hide);
		  };
		  $scope.progress="";
		  $scope.newExchangeStuff={};
		  $scope.newExchangeStuff.attachments=[];
		  $scope.saveExchangeStuff=function(){
			  exchangeStuffService.add({
				  exchangeStuff:$scope.newExchangeStuff,
				  targetId:$scope.exchangeStuff.id
			  },function(response){
				  if(response.success){
					  exchangeStuffService.get({id:response.id},function(response){
						  $scope.exchangeStuffReplys.push(response.json);
						 });
						$scope.hideModal();
				  }else{
					  alertService("发表失败！","danger");
				  }
			  });
		  };
		  
		  $scope.checkExchangeStuff=function(exchangStuff,index){
			  exchangeStuffService.add({
				  exchangeStuff:exchangStuff,
				  targetId:$scope.exchangeStuff.id
			  },function(response){
				  if(response.success){
						$scope.exchangeStuffReplys.push(exchangStuff);
						$scope.userExchangeStuffs.splice(index,1);
						$scope.hideModal();
				  }else{
					  alertService("发表失败！","danger");
				  }
			  });
		  };
		  
		  $scope.onFileSelect = function($files) {  //$files: an array of files selected, each file has name, size, and type.
			    for (var i = 0; i < $files.length; i++) {    
			    	var file = $files[i];
			    	if(file.size>5242880){
			    		alertService.show('资源图片大小不能超过5M!','danger');
			    		return;
			    	}
			    	$scope.upload = $upload.upload({
			        url: 'attachment/upload', //upload.php script, node.js route, or servlet url
			        method: 'POST',
			        //headers: {'header-key': 'header-value'},
			        //withCredentials: true,
			        //data: {myObj: $scope.myModelObj},
			        file: file, // or list of files ($files) for html5 only
			        //fileName: 'doc.jpg' or ['1.jpg', '2.jpg', ...] // to modify the name of the file(s)
			        // customize file formData name ('Content-Disposition'), server side file variable name. 
			        //fileFormDataName: myFile, //or a list of names for multiple files (html5). Default is 'file' 
			        // customize how data is added to formData. See #40#issuecomment-28612000 for sample code
			        //formDataAppender: function(formData, key, val){}
			      }).progress(function(evt) {        
			    	  $scope.progress= parseInt(100.0 * evt.loaded / evt.total)+'%';
			      }).success(function(data, status, headers, config) {        // file is uploaded successfully
			    	  $scope.newExchangeStuff.attachments.push(data.json);
			      });      //.error(...)
			      //.then(success, error, progress); 
			      // access or attach event listeners to the underlying XMLHttpRequest.
			      //.xhr(function(xhr){xhr.upload.addEventListener(...)})
			       /* alternative way of uploading, send the file binary with the file's content-type.       Could be used to upload files to CouchDB, imgur, etc... html5 FileReader is needed.        It could also be used to monitor the progress of a normal http post/put request with large data*/
			    // $scope.upload = $upload.http({...})  see 88#issuecomment-31366487 for sample code.
			  }
			};
	};
	
	$scope.showCheck=function(index){
		$scope.checkNum=index;
	};
	
	$scope.deleteExchangeStuff=function(deleteId,index){
		$http.post('exchangeStuff/delete_for_reply.json',{
			targetId:$scope.exchangeStuff.id,
			exchangeStuffId:deleteId
		}).success(function(response){
			if(response.success){
				$scope.exchangeStuffReplys.splice(index,1);
			}else{
				alertService.show('删除失败！','danger');
			}
		});
	};
	$scope.dealComplete=function(dealTarget){
		$scope.exchangeStuff.completed=true;
		dealTarget.completed=true;
		dealTarget.exchangeStuffs=[$scope.exchangeStuff];
		$http.post('exchangeStuff/deal.json',dealTarget).success(function(response){
			$scope.exchangeStuffReplys=[dealTarget];
		});
	};
});