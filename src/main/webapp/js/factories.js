quicklistApp.factory('sendFocus', function($rootScope, $timeout) {
	return function(name) {
		$timeout(function() {
			$rootScope.$broadcast('applyFocus', name);
		});
	};
});