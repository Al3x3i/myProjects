function doWork() {
	alert("AAA")
}

angedaViewObject = {

	getStartEndTimeRange : function(startTime, endTime) {
		var totalHours = 0;
		var totalMinutes = 0;

		var startHourMinute = startTime.split(':');
		var startHour = Number(startHourMinute[0]);
		var startMinute = Number(startHourMinute[1]);

		var endHourMinute = endTime.split(':');
		var endHour = Number(endHourMinute[0]);
		var endMinute = Number(endHourMinute[1]);

		function getTimeInMinutes(hours, minutes) {
			var total = hours * 60;
			total += minutes;
			return total;
		}

		function getFormattedTimeFromMinutes(totalMinuts) {

			var hours = Math.floor(totalMinuts / 60);
			var minutes = (totalMinuts % 60);

			if (minutes < 10) {
				minutes = "0" + minutes;
			}
			return hours + ":" + minutes;
		}

		var totalStartMinutes = getTimeInMinutes(startHour, startMinute)
		var totalEndMinutes = getTimeInMinutes(endHour, endMinute)

		var totalMinutes = 0;

		if (totalStartMinutes > totalEndMinutes) {
			totalMinutes = (24 * 60) - totalStartMinutes + totalEndMinutes;
		} else {
			totalMinutes = totalEndMinutes - totalStartMinutes;
		}

		var formattedTime = getFormattedTimeFromMinutes(totalMinutes);
		return formattedTime;
	}
}

angedaViewBackEndObject = {

	modalDienstSaveClickHandler : function(callbackFunction) {
		$.ajax({
			url : "${contextPath}/schedules/subview/slaapdienst/putRecord",
			type : "PUT",
			// dataType : "text",
			contentType : "application/json",
			data : jsonData,
			success : function(data) {

				if (data.status) {

					var calendar = $('#n-calendar').data();
					var selectedDate = calendar.datepicker.viewDate;

					getTabelData(selectedDate);

					$('#modal-dienst').modal('hide');
					callbackFunction(true)
				} else {
					alert('Data was not saved');
					callbackFunction(false)
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error save data from ajax');
			}
		});
	}

}
