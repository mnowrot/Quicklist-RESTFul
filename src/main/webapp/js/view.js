var view = {
	showModal: function(id) {
		$('#' + id).modal();
	},
	
	showAlert: function(id) {
		$('#' + id).show();
		$('#' + id).addClass('in');
	},

	showHideAlert: function(id) {
		this.showAlert(id);
		window.setTimeout(function() {
			var alert = $('#' + id);
			if(alert) {
				alert.removeClass('in');
				alert.hide();
			}
		}, 3000);
	},

	hideAllAlerts: function() {
		$('#listRemoveSuccessAlert').hide();
		$('#listRefreshFailAlert').hide();
		$('#listAddFailAlert').hide();
		$('#listRemoveFailAlert').hide();
	}
};

$(document).ready(function() {
	view.hideAllAlerts();
});
