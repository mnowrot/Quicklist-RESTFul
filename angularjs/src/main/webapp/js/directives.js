quicklistApp.directive('focusId', function() {
	return function(scope, elem, attr) {
		scope.$on('applyFocus', function(e, name) {
			if (name === attr.focusId && elem.is(':visible')) {
				elem[0].focus();
			}
		});
	};
});