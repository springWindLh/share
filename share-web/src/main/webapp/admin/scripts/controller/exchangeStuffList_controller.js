angular.module("shareApp_admin")
.controller("exchangeStuffList_controller",function($rootScope,$scope,$http,exchangeStuffService,alertService,userService,$modal,$upload){
	$scope.currentStuffPage=1;
	$scope.exchangeStuffs=[];
	$scope.searchKey='';
	$scope.getExchangeStuffs=function(){
		if($scope.count==0||$scope.count<=20){
			alert("没有更多了！");
			return;
		}
		exchangeStuffService.query({
			_page:$scope.currentStuffPage,
			_count:20,
			//_query:'completed,false',
			_desc:'createTime'
		},function(response){
			$scope.exchangeStuffs=$scope.exchangeStuffs.concat(response.exchangeStuffs);
			$scope.count=response.exchangeStuffs.length;
		});
	};
	
	$scope.getExchangeStuffs();
	
	$scope.showMore=function(){
		$scope.currentStuffPage+=1;
		if($scope.searchKey==''){
			$scope.getExchangeStuffs();
		}else{
			$scope.getExchangeStuffsByKeyWords();
		}
	};
	
	$scope.exchangeStuffModal=function(){
		if(!$rootScope.checkLogin()){
			return;
		}
		 var myOtherModal = $modal({
			 scope: $scope, 
			 template: 'sources/template/exchangeStuff_edit_modal.html',
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
				  exchangeStuff:$scope.newExchangeStuff
			  },function(response){
				  if(response.success){
					  exchangeStuffService.get({id:response.id},function(response){
						  $scope.exchangeStuffs.unshift(response.json);
					  });
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
	
	$scope.getExchangeStuffsByKeyWords=function(){
		if(($scope.count==0||$scope.count<=50)&&$scope.currentStuffPage!=1){
			alert("没有更多了！");
			return;
		}
		$http.get('exchangeStuff/search.json',{params:{
			_page:$scope.currentStuffPage,
			_count:50,
			keyWords:$scope.searchKey}
		}).success(function(response){
				$scope.count=response.exchangeStuffs.length;
				$scope.exchangeStuffs=$scope.exchangeStuffs.concat(response.exchangeStuffs);
		});
	};
	
	$scope.search=function(){
		$scope.currentStuffPage=1;
		$scope.exchangeStuffs=[];
		$scope.getExchangeStuffsByKeyWords();
		if($scope.searchKey!=null&&$scope.searchKey!=''){
			$scope.clearTextVisible=true;
		}
	};
	$scope.clearTextVisible=false;
	$scope.clearKeyWords=function(){
		$scope.searchKey='';
		$scope.currentStuffPage=1;
		$scope.exchangeStuffs=[];
		$scope.count=999;
		$scope.clearTextVisible=false;
		$scope.getExchangeStuffs();
	};
	$scope.statess=[{"id":2,"value":"全部"},{"id":1,"value":"进行中"},{"id":0,"value":"已结束"}];
	$scope.selectedState=2;
	$scope.globalCompleted='';
	$scope.$watch('selectedState',function(value){
		if(value==2){
			$scope.globalCompleted='';
		}
		if(value==1){
			$scope.globalCompleted=false;
		}
		if(value==0){
			$scope.globalCompleted=true;
		}
	});
});