angular.module("shareApp_admin")
.controller("schoolInit_controller",function($rootScope,$scope,$http,userService,exchangeStuffService,alertService,NgTableParams,$filter,$upload){
	$scope.onFileSelect = function($files) {  //$files: an array of files selected, each file has name, size, and type.
		$scope.initFlag=false;
		$scope.errorMsg="";
		for (var i = 0; i < $files.length; i++) {    
	    	var file = $files[i];
	    	if(file.size>5242880){
	    		alertService.show('文件大小不能超过5M!','danger');
	    		return;
	    	}
	    	$scope.upload = $upload.upload({
	        url: 'school/import_json', //upload.php script, node.js route, or servlet url
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
	    	  $scope.progressVisble=true;
	    	 // $scope.progress= parseInt(100.0 * evt.loaded / evt.total)+'%';
	    	  $scope.progressMsg="导入中……";
	      }).success(function(data, status, headers, config) {
	    	  // file is uploaded successfully
	    	 $scope.initFlag=true;
	    	 if(data.success){
	    		 $scope.resultState="成功";
	    		 $scope.count=data.id;
	    		 $scope.state=true;
	    		 $scope.progressMsg="导入完成";
	    	 }else{
	    		 $scope.resultState="失败";
	    		 $scope.state=false;
	    		 $scope.progressMsg="导入失败";
	    	 }
	      }).error(function(){
	    	  $scope.initFlag=true;
	    	  $scope.resultState="失败";
	    	  $scope.errorMsg="文件内容格式错误";
	    	  $scope.progressMsg="导入中断";
	    	  $scope.count=0;
	    	  $scope.state=false;
	      });      //.error(...)
	      //.then(success, error, progress); 
	      // access or attach event listeners to the underlying XMLHttpRequest.
	      //.xhr(function(xhr){xhr.upload.addEventListener(...)})
	       /* alternative way of uploading, send the file binary with the file's content-type.       Could be used to upload files to CouchDB, imgur, etc... html5 FileReader is needed.        It could also be used to monitor the progress of a normal http post/put request with large data*/
	    // $scope.upload = $upload.http({...})  see 88#issuecomment-31366487 for sample code.
	  }
	};
});