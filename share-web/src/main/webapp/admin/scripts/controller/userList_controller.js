angular.module("shareApp_admin")
.controller("userList_controller",function($rootScope,$scope,$http,userService,exchangeStuffService,alertService,NgTableParams,$filter){

	$scope.tableParams = new NgTableParams({
        page: 1,            
        count: 10
    }, {
    	groupBy:'valid',
        total: 0, 
        getData: function($defer, params) {
        	userService.query({
        		_count:999,
        	},function(response){
        		var data=response.users;
        		params.total(data.length);
        		var orderedData = params.sorting() ?
                        $filter('orderBy')(data, params.orderBy()) :
                        data;
                        orderedData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData;
               $defer.resolve($scope.users=orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        	});
        }
    });
	
	$scope.checkboxes = { 'checked': false, items: {} };

    
    $scope.$watch('checkboxes.checked', function(value) {
        angular.forEach($scope.users, function(item) {
            if (angular.isDefined(item.id)) {
                $scope.checkboxes.items[item.id] = value;
            }
        });
    });
    
    
    $scope.$watch('checkboxes.items', function(values) {
        if (!$scope.users) {
            return;
        }
        var checked = 0, unchecked = 0,
            total = $scope.users.length;
        angular.forEach($scope.users, function(item) {
            checked   +=  ($scope.checkboxes.items[item.id]) || 0;
            unchecked += (!$scope.checkboxes.items[item.id]) || 0;
        });
        if ((unchecked == 0) || (checked == 0)) {
            $scope.checkboxes.checked = (checked == total);
        }
       
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
        $scope.hasSelected=false;
    	for(var key in $scope.checkboxes.items){
    		if($scope.checkboxes.items[key]){
    			$scope.hasSelected=true;
    			break;
    		}
    		$scope.hasSelected=false;
    	}
    }, true);
    
    var getSelectedIds=function(){
    	var ids=[];
    	for(var key in $scope.checkboxes.items){
    		if($scope.checkboxes.items[key]){
    			ids.push(key);
    		}
    	}
    	return ids.join(",");
    };
    
    $scope.deleteUsers=function(){
    	if(window.confirm("你确定要删除选中的用户？")){
    		$http.post('user/batch_delete.json',{
    			ids:getSelectedIds()
    		}).success(function(response){
    			 if (response.success) {
                 	$scope.tableParams.reload();
                 	$scope.cancelAllSelected();
                 } else {
                	 alertService.show("删除失败！","danger");
                 }
    		});
    	}
    };
    
    $scope.search=function(searchKey){
    	$scope.tableParams.filter({name:searchKey});
    };
    $scope.cancelAllSelected=function(){
    	$scope.checkboxes = { 'checked': false, items: {} };
    };
    
    $scope.updateUser=function(user){
    	userService.update({id:user.id},user,function(response){
    		if(response.success){
    			$scope.tableParams.reload();
    		}
    	});
    };
    
    $scope.forbid=function(user){
    	user.valid=false;
    	$scope.updateUser(user);
    };
    
    $scope.active=function(user){
    	user.valid=true;
    	$scope.updateUser(user);
    };
    
});