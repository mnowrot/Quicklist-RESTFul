var quicklistApp = angular.module('quicklistApp', []);

quicklistApp.controller('ListManager', function($scope, $http) {
	$scope.noItems = function() {
		return !$scope.items || $scope.items.length == 0;
	};
	
	$scope.addItem = function() {
		var newItemName = $scope.newItemName;
		if(newItemName && newItemName.length > 0) {
			$http.post('qlist/add', 'listItemName=' + newItemName, 
					{headers: {'Content-Type' : 'application/x-www-form-urlencoded'}})
			.success(function(data) {
				$scope.newItemName = '';
				reloadList($scope, $http);
			});
		}
	};
	
	$scope.removeItem = function(item) {
		if(item) {
			$http.delete('qlist/item/' + item.id)
			.success(function(data) {
				reloadList($scope, $http);
			});
		}
	};
	
	reloadList($scope, $http);
});

function reloadList($scope, $http) {
	$http.get('qlist/all').success(function(data) {
		$scope.items = data;
	});
}