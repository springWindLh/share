angular.module("shareApp")
.controller("userCenter_controller",function($rootScope,$scope,$http,$timeout,userService,alertService,$upload){
	userService.get({id:$rootScope.currentUserId},function(response){
		$scope.user=response.json;
	});
	
	$scope.updateInfo=function(){
		userService.update($scope.user,function(response){
			if(response.success){
				alertService.show("更新成功！","success");
			}else{
				alertService.show(response.message,"danger");
			}
		});
	};
	
	$scope.updatePassword=function(){
		if($scope.newPassword!=$scope.newPassword2){
			alertService.show("密码输入 不一致！","danger");
			return;
		}
		$http.post('user/update_password.json',{
			oldPassword:$scope.oldPassword,
			newPassword:$scope.newPassword
		}).success(function(response){
			if(response.success){
				alertService.show("更新成功！","success");
				$scope.oldPassword="";
				$scope.newPassword="";
				$scope.newPassword2="";
			}else{
				alertService.show(response.message,"danger");
			}
		});
	};
	
	$scope.onFileSelect = function($files) {  //$files: an array of files selected, each file has name, size, and type.
	    for (var i = 0; i < $files.length; i++) {    
	    	var file = $files[i];
	    	if(file.size>5242880){
	    		alertService.show('上传头像大小不能超过5M!','danger');
	    		return;
	    	}
	    	$scope.upload = $upload.upload({
	        url: 'user/upload_headImg', //upload.php script, node.js route, or servlet url
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
	    	  console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	      }).success(function(data, status, headers, config) {        // file is uploaded successfully
	    	 if($scope.user.headImg==undefined){
	    		 $scope.user.headImg={};
	    		 userService.get({id:$rootScope.currentUserId},function(response){
		    			$scope.user=response.json;
		    		});
	    	 }
	    	  $scope.user.headImg.path=data.json.path;
	      });  
	    	//.error(...)
	      //.then(success, error, progress); 
	      // access or attach event listeners to the underlying XMLHttpRequest.
	      //.xhr(function(xhr){xhr.upload.addEventListener(...)})
	       /* alternative way of uploading, send the file binary with the file's content-type.       Could be used to upload files to CouchDB, imgur, etc... html5 FileReader is needed.        It could also be used to monitor the progress of a normal http post/put request with large data*/
	    // $scope.upload = $upload.http({...})  see 88#issuecomment-31366487 for sample code.
	  }
	};
});
