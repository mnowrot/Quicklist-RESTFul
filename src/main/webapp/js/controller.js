var quicklistApp = angular.module('quicklistApp', []);

quicklistApp.controller('ListManager', function($scope, $http) {
	$scope.noItems = function() {
		return !$scope.items || $scope.items.length == 0;
	};
	
	$scope.addItem = function() {
		var newItemName = $scope.newItemName;
		if(newItemName && newItemName.length > 0) {
			$http.post('qlist/add', 'newItemName=' + newItemName, 
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
		};
	};
	
	$scope.saveNewListItemOnEnter = function($event) {
		if($event.keyCode === 13) {
			$scope.addItem();
		}
	};
	
	$scope.editItem = function(item) {
		if(item) {
			$scope.itemToEdit = angular.copy(item);
		}
	};
	
	$scope.isItemEdited = function(item) {
		return item && $scope.itemToEdit && item.id === $scope.itemToEdit.id;
	};
	
	$scope.finishItemEdition = function() {
		$scope.itemToEdit = null;
		view.hideAlert('listEditFailAlert');
	};
	
	$scope.saveEditedItem = function() {
		var item = $scope.itemToEdit;
		if(item) {
			$http.put('qlist/item/' + item.id, 'editedItemName=' + item.name, 
					{headers: {'Content-Type' : 'application/x-www-form-urlencoded'}})
			.success(function(data) {
				$scope.finishItemEdition();
				reloadList($scope, $http);
			}).error(function(data, status) {
				view.showAlert('listEditFailAlert');
			});
		};
	}
	
	reloadList($scope, $http);
});

function reloadList($scope, $http) {
	$http.get('qlist/all').success(function(data) {
		$scope.items = data;
	}).error(function(data, status) {
		view.showAlert('listRefreshFailAlert');
	});
};