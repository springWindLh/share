angular.module("shareApp")
.controller("shareStuff_controller",function($rootScope,$scope,$http,$timeout,stuffService,alertService,typeService,$upload){	
	typeService.query({
		_count:999		
	},function(response){
		$scope.types=response.types;
	});
	$scope.newStuff={};
	$scope.newStuff.attachments=[];
	$scope.shareTypes=[{name:'免费',value:true},{name:'付费',value:false}];
	$scope.save=function(){
		stuffService.add($scope.newStuff,function(response){
			if(response.success){
				alertService.show('分享成功！','success');
				$scope.newStuff={};
				$scope.newStuff.attachments=[];
				$scope.progress="";
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
	    	  $scope.newStuff.attachments.push(data.json);
	      });      //.error(...)
	      //.then(success, error, progress); 
	      // access or attach event listeners to the underlying XMLHttpRequest.
	      //.xhr(function(xhr){xhr.upload.addEventListener(...)})
	       /* alternative way of uploading, send the file binary with the file's content-type.       Could be used to upload files to CouchDB, imgur, etc... html5 FileReader is needed.        It could also be used to monitor the progress of a normal http post/put request with large data*/
	    // $scope.upload = $upload.http({...})  see 88#issuecomment-31366487 for sample code.
	  }
	};
});