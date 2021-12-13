var available = new Array();
var binded = new Array();
var res;

$('.btn-duty-select').click(function() {
	$(this).toggleClass('active');
});

$(function() {
	//load arrays
	$('#available_duties_collection').children('div').children('label').each(function() {
		available.push($(this).attr('id'))
	})
	$('#binded_duties_collection').children('div').children('label').each(function() {
		binded.push($(this).attr('id'))
	})
	refreshDutiesInput();
});

$('#btn-bind-selected').click(function() {
	//move selected to binded box
	$('#available_duties_collection').children('div').children('label').each(function() {
		if ($(this).hasClass('active')) {
			$(this).toggleClass('active');
			$(this).parent().detach().appendTo('#binded_duties_collection');

			var index = available.indexOf($(this).attr('id'));
			if (index > -1) {
				available.splice(index, 1);
			}
			binded.push($(this).attr('id'));
		}
	})
	refreshDutiesInput();
});

$('#btn-bind-all').click(function() {
	//move selected to binded box
	$('#available_duties_collection').children('div').children('label').each(function() {
		if ($(this).hasClass('active')) {
			$(this).toggleClass('active');
		}
		$(this).parent().detach().appendTo('#binded_duties_collection');
		binded.push.apply(binded, available);
		available = [];
	})
	refreshDutiesInput();
});

$('#btn-unbind-selected').click(function() {
	//move selected to binded box
	$('#binded_duties_collection').children('div').children('label').each(function() {
		if ($(this).hasClass('active')) {
			$(this).toggleClass('active');
			$(this).parent().detach().appendTo('#available_duties_collection');

			var index = binded.indexOf($(this).attr('id'));
			if (index > -1) {
				binded.splice(index, 1);
			}
			available.push($(this).attr('id'));
		}
	})
	refreshDutiesInput();
});

$('#btn-unbind-all').click(function() {
	//move selected to binded box
	$('#binded_duties_collection').children('div').children('label').each(function() {
		if ($(this).hasClass('active')) {
			$(this).toggleClass('active');
		}
		$(this).parent().detach().appendTo('#available_duties_collection');
		available.push.apply(available, binded);
		binded = [];
	})
	refreshDutiesInput();
});

function refreshDutiesInput() {
	document.getElementById("available_duties_input").value = available.join(",");
	document.getElementById("binded_duties_input").value = binded.join(",");
}

$('#btn-suggest-execperiod').click(function() {
	var urlStr = getAbsoluteUrl('/officer/endeavour/async/exec-period')

	$.ajax({

		url: urlStr,
		type: 'GET',
		data: {
			"dutyIds": $('#binded_duties_input').val(),
			"dateFormat": $('#date_format').val()
		},
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			$('#executionStart').val(data.executionStart);
			$('#executionEnd').val(data.executionEnd);
			return true;
		}
	});

});