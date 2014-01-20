quicklistApp.controller('ListManager', function($scope, $http, sendFocus) {
	$scope.noItems = function() {
		return !$scope.items || $scope.items.length == 0;
	};
	
	$scope.addItem = function() {
		var newItemName = $scope.newItemName;
		if(newItemName && newItemName.length > 0) {
			$http.post('/quicklist-core/qlist/add', 'newItemName=' + encodeURIComponent(newItemName), 
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
			$http.delete('/quicklist-core/qlist/item/' + item.id)
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
			sendFocus('inputListItemName');
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
			$http.put('/quicklist-core/qlist/item/' + item.id, 'editedItemName=' + encodeURIComponent(item.name), 
					{headers: {'Content-Type' : 'application/x-www-form-urlencoded'}})
			.success(function(data) {
				$scope.finishItemEdition();
				reloadList($scope, $http);
			}).error(function(data, status) {
				view.showAlert('listEditFailAlert');
			});
		};
	}
	
	$scope.saveEditedListItemOnEnter = function($event) {
		if($event.keyCode === 13) {
			$scope.saveEditedItem();
		}
	};
	
	reloadList($scope, $http);
});

function reloadList($scope, $http) {
	$http.get('/quicklist-core/qlist/all').success(function(data) {
		$scope.items = data;
	}).error(function(data, status) {
		view.showAlert('listRefreshFailAlert');
	});
};