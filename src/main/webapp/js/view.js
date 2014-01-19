var view = {
	showModal: function(id) {
		$('#' + id).modal();
	},
	
	showAlert: function(id) {
		var alert = $('#' + id);
		if(alert) {
			alert.show();
			alert.addClass('in');
		}
	},
	
	hideAlert: function(id) {
		var alert = $('#' + id);
		if(alert) {
			alert.removeClass('in');
			alert.hide();
		}
	},

	showHideAlert: function(id) {
		var that = this;
		that.showAlert(id);
		window.setTimeout(function() {
			that.hideAlert(id);
		}, 3000);
	},

	hideAllAlerts: function() {
		$('#listRemoveSuccessAlert').hide();
		$('#listRefreshFailAlert').hide();
		$('#listAddFailAlert').hide();
		$('#listRemoveFailAlert').hide();
		$('#listEditFailAlert').hide();
	}
};

$(document).ready(function() {
	view.hideAllAlerts();
});
