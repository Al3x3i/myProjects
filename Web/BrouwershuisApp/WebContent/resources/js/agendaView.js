var angedaViewObject = {

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

	modalDienstSaveClickHandler : function(viewName, jsonData, callbackFunction) {

		var urlAddress = "${contextPath}/schedules/subview/" + viewName
				+ "/patchRecord"

		$.ajax({
			url : urlAddress,
			type : "POST",
			contentType : "application/json",
			data : jsonData,
			success : function(data) {

				callbackFunction(data)
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error save data from ajax');
			}
		});
	},

	modalDienstEditClickHandler : function(viewName, jsonData, callbackFunction) {

		var urlAddress = "${contextPath}/schedules/subview/" + viewName
				+ "/putRecord"

		$.ajax({
			url : urlAddress,
			type : "POST",
			contentType : "application/json",
			data : jsonData,
			success : function(data) {
				callbackFunction(data)
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error save data from ajax');
			}
		});

	},

	modalDienstDeleteClickHandler : function(viewName, data, callbackFunction) {

		var urlAddress = "${contextPath}/schedules/subview/" + viewName
				+ "/deleteRecord"

		$.ajax({
			url : urlAddress,
			type : "DELETE",
			// dataType : "text",
			contentType : "application/json",
			data : jsonData,
			success : function(data) {

				callbackFunction(data)
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error save data from ajax');
			}
		});
	},

	modalDienstGetAllClickHandler : function(viewName, firstDay, lastDay) {

		var urlAddress = "${contextPath}/schedules/subview/" + viewName
				+ "/getAllRecords/?start=" + firstDay + "&end=" + lastDay

		$.ajax({
			url : urlAddress,
			type : "GET",
			success : function(data) {

				var tableData = JSON.parse(data);

				cleanContentTableRecords();
				fillInDaysInTableHeader(firstDayMoment);

				var data = tableData.vvvdienst;
				fillInDienstTableData(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error get data from ajax');
			}
		});
	},

	modalDienstGetRecordDetails : function(id) {
		var urlAddress = "${contextPath}/schedules/subview/slaapdienst/getRecordDetails?id="
				+ id

		$.ajax({
			url : urlAddress,
			type : "GET",
			contentType : "application/json",
			success : function(data) {
				callbackFunction(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Data cannot be retrieved!');
			}
		});
	}
}