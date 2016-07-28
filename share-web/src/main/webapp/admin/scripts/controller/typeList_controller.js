angular.module("shareApp_admin")
.controller("typeList_controller",function($rootScope,$scope,$http,$modal,typeService,exchangeStuffService,alertService,NgTableParams,$filter){
	$scope.tableParams = new NgTableParams({
        page: 1,            
        count: 10
    }, {
        total: 0, 
        getData: function($defer, params) {
        	typeService.query({
        		_count:999,
        	},function(response){
        		var data=response.types;
        		params.total(data.length);
              var orderedData = params.filter() ? $filter('filter')(data, params.filter()) : data;
               $defer.resolve($scope.types=orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        	});
        }
    });
	
	$scope.checkboxes = { 'checked': false, items: {} };

    
    $scope.$watch('checkboxes.checked', function(value) {
        angular.forEach($scope.types, function(item) {
            if (angular.isDefined(item.id)) {
                $scope.checkboxes.items[item.id] = value;
            }
        });
    });
    
    
    $scope.$watch('checkboxes.items', function(values) {
        if (!$scope.types) {
            return;
        }
        var checked = 0, unchecked = 0,
            total = $scope.types.length;
        angular.forEach($scope.types, function(item) {
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
    
    $scope.deleteTypes=function(){
    	if(window.confirm("你确定要删除选中的类型？")){
    		$http.post('type/batch_delete.json',{
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
    
    $scope.showAddTypeModal=function(){
    	 var myOtherModal = $modal({
			 scope: $scope, 
			 template: 'admin/template/add_type_modal.html',
	         show : true
			 });
		  $scope.hideModal = function() {
		    myOtherModal.$promise.then(myOtherModal.hide);
		  };
		  $scope.newType={};
		  $scope.addType=function(){
			  typeService.add($scope.newType,function(response){
					if(response.success){
						$scope.hideModal();
						$scope.tableParams.reload();
					}else{
						alertService.show("添加失败！","danger");
					}
				  });
			  };
    };
});