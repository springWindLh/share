angular.module("shareApp_admin")
.controller("pageNavigationController",function($scope){
	
	$scope.$watch("pagesize",function(newData,oldData,scope){
		if(newData!=oldData){
		$('.pagination').jqPagination({
			max_page:newData,
		    paged: function(page) {
		    	$scope.$emit("page",page);
		    }
		});	
		}
	});
	
}).directive("pageNavigation",function(){
	return{
		 restrict : "EA",
	     replace : true,
	     scope:{
	    	pagesize:"="
	     },
	     templateUrl : "sources/scripts/directives/page-navigation/page-navigation.html",
	     controller : "pageNavigationController"
	};
});