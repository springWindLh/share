angular.module("shareApp")
.controller("myStuffList_controller",function($rootScope,$scope,$http,stuffService,exchangeStuffService,alertService,NgTableParams,$filter){
	
	$scope.tableParams = new NgTableParams({
        page: 1,            
        count: 10
    }, {
    	groupBy:'free',
        total: 0, 
        getData: function($defer, params) {
        	stuffService.query({
        		_count:999,
        		_operator:'=',
        		_query:'user.id,'+$rootScope.currentUserId
        	},function(response){
        		var data=response.stuffs;
        		params.total(data.length);
        		var orderedData = params.sorting() ?
                        $filter('orderBy')(data, params.orderBy()) :
                        data;
                        orderedData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData;
               $defer.resolve($scope.stuffs=orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        	});
        }
    });
	
	$scope.checkboxes = { 'checked': false, items: {} };

    
    $scope.$watch('checkboxes.checked', function(value) {
        angular.forEach($scope.stuffs, function(item) {
            if (angular.isDefined(item.id)) {
                $scope.checkboxes.items[item.id] = value;
            }
        });
    });
    
    
    $scope.$watch('checkboxes.items', function(values) {
        if (!$scope.stuffs) {
            return;
        }
        var checked = 0, unchecked = 0,
            total = $scope.stuffs.length;
        angular.forEach($scope.stuffs, function(item) {
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
    
    $scope.deleteStuffs=function(){
    	if(window.confirm("你确定要删除选中的资源记录？")){
    		$http.post('stuff/batch_delete.json',{
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
    	$scope.tableParams.filter({title:searchKey});
    };
    $scope.cancelAllSelected=function(){
    	$scope.checkboxes = { 'checked': false, items: {} };
    };
    
    $scope.updateStuff=function(stuff){
    	stuffService.update({id:stuff.id},stuff);
    };
    
    $scope.overShare=function(stuff){
    	stuff.completed=true;
    	$scope.updateStuff(stuff);
    };
    
    $scope.recoverShare=function(stuff){
    	stuff.completed=false;
    	$scope.updateStuff(stuff);
    };
    
   //交换区 
    $scope.es_tableParams = new NgTableParams({
        page: 1,            
        count: 10
    }, {
    	groupBy:'completed',
        total: 0, 
        getData: function($defer, params) {
        	exchangeStuffService.query({
        		_count:999,
        		_operator:'=',
        		_query:'user.id,'+$rootScope.currentUserId
        	},function(response){
        		var es_data=response.exchangeStuffs;
        		params.total(es_data.length);
        		var orderedData = params.sorting() ?
                        $filter('orderBy')(es_data, params.orderBy()) :
                        es_data;
                        orderedData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData;
               $defer.resolve($scope.exchangeStuffs=orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        	});
        }
    });
	
	$scope.es_checkboxes = { 'checked': false, items: {} };

    
    $scope.$watch('es_checkboxes.checked', function(value) {
        angular.forEach($scope.exchangeStuffs, function(item) {
            if (angular.isDefined(item.id)) {
                $scope.es_checkboxes.items[item.id] = value;
            }
        });
    });
    
    
    $scope.$watch('es_checkboxes.items', function(values) {
        if (!$scope.exchangeStuffs) {
            return;
        }
        var es_checked = 0, es_unchecked = 0,
            total = $scope.exchangeStuffs.length;
        angular.forEach($scope.exchangeStuffs, function(item) {
            es_checked   +=  ($scope.es_checkboxes.items[item.id]) || 0;
            es_unchecked += (!$scope.es_checkboxes.items[item.id]) || 0;
        });
        if ((es_unchecked == 0) || (es_checked == 0)) {
            $scope.es_checkboxes.checked = (es_checked == total);
        }
       
        angular.element(document.getElementById("select_all_es")).prop("indeterminate", (es_checked != 0 && es_unchecked != 0));
        $scope.es_hasSelected=false;
    	for(var key in $scope.es_checkboxes.items){
    		if($scope.es_checkboxes.items[key]){
    			$scope.es_hasSelected=true;
    			break;
    		}
    		$scope.es_hasSelected=false;
    	}
    }, true);
    
    var es_getSelectedIds=function(){
    	var es_ids=[];
    	for(var key in $scope.es_checkboxes.items){
    		if($scope.es_checkboxes.items[key]){
    			es_ids.push(key);
    		}
    	}
    	return es_ids.join(",");
    };
    
    $scope.deleteExchangeStuffs=function(){
    	if(window.confirm("你确定要删除选中的资源记录？")){
    		$http.post('exchangeStuff/batch_delete.json',{
    			ids:es_getSelectedIds()
    		}).success(function(response){
    			 if (response.success) {
                 	$scope.es_tableParams.reload();
                 	$scope.es_cancelAllSelected();
                 } else {
                	 alertService.show("删除失败！","danger");
                 }
    		});
    	}
    };
    
    $scope.es_search=function(searchKey){
    	$scope.es_tableParams.filter({title:searchKey});
    };
    $scope.es_cancelAllSelected=function(){
    	$scope.es_checkboxes = { 'checked': false, items: {} };
    };
    
    $scope.updateExchangeStuff=function(exchangeStuff){
    	exchangeStuffService.update({id:exchangeStuff.id},exchangeStuff,function(response){
    		if(response.success){
    			$scope.es_tableParams.reload();
    		}
    	});
    };
    
    $scope.es_overShare=function(exchangeStuff){
    	exchangeStuff.completed=true;
    	$scope.updateExchangeStuff(exchangeStuff);
    };
    
    $scope.es_recoverShare=function(exchangeStuff){
    	exchangeStuff.completed=false;
    	$scope.updateExchangeStuff(exchangeStuff);
    };
    
    
});