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
			}).error(function(data, status) {
				view.showAlert('listAddFailAlert');
			});
		}
	};
	
	$scope.openRemoveItemModal = function(item) {
		if(item) {
			$scope.itemToRemove = angular.copy(item);
			view.showModal('removeYesNoModal');
		}
	};
	
	$scope.removeItem = function() {
		var item = $scope.itemToRemove;
		if(item) {
			$http.delete('qlist/item/' + item.id)
			.success(function(data) {
				$scope.itemRemoved =  angular.copy(item); // for alert
				view.showHideAlert('listRemoveSuccessAlert');
				reloadList($scope, $http);
			}).error(function(data, status) {
				view.showAlert('listRemoveFailAlert');
			});
		}
	};
	
	reloadList($scope, $http);
});

function reloadList($scope, $http) {
	$http.get('qlist/all').success(function(data) {
		$scope.items = data;
	}).error(function(data, status) {
		view.showAlert('listRefreshFailAlert');
	});
}