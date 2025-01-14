/*
Template Name: Color Admin - Responsive Admin Dashboard Template build with Twitter Bootstrap 4
Version: 4.7.0
Author: Sean Ngu
Website: http://www.seantheme.com/color-admin/admin/
*/

var handleDataTableScroller = function() {
	"use strict";
    
	if ($('#data-table-scroller').length !== 0) {
		$('#data-table-scroller').DataTable({
			ajax:           "/resources/js/demo/json/scroller_demo.json",
			deferRender:    true,
			scrollY:        300,
			scrollCollapse: true,
			scroller:       true,
			responsive: true
		});
	}
};

var TableManageScroller = function () {
	"use strict";
	return {
		//main function
		init: function () {
			handleDataTableScroller();
		}
	};
}();

$(document).ready(function() {
	TableManageScroller.init();
});