/**
 * 图片展示
 */
angular.module("shareApp_admin")
.controller('imageShowController', ['$rootScope', '$scope', function($rootScope, $scope) {
	var initImageUrl=function(){
//		var regular=/^http[s]?:\/\/([\w-]+\.)+[\w-]+([\w-./?%&=]*)?$/;
		if(($scope.imgurl==null||$scope.imgurl=='')&&$scope.defaultimageurl!=null&&$scope.defaultimageurl!=''){
			$scope.url=$scope.defaultimageurl;
		}else{
			if($scope.imgtype=='stuff'){
				$scope.url=$rootScope.baseAttachmentImgUrl+$scope.imgurl;
			}
			if($scope.imgtype=='headImg'){
				$scope.url=$rootScope.baseHeadImgUrl+$scope.imgurl;
			}
		}
	};
	$scope.$watch("imgurl",function(){
		initImageUrl();
	});
} ])
.directive("imageShow", function() {
    return {
        restrict : "EA",
        replace : true,
        scope : {
//        	imagestyle : '@',
        	imgurl : '@',
        	defaultimageurl : '@',
        	imageclass : '@',
        	imgtype:'@'
        },
        templateUrl : "sources/scripts/directives/image-show/image-show.html",
        controller : "imageShowController"
    };
});