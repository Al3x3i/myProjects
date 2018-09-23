/**
 * 
 */

var workSchedule = new function() {

	function DienstObject() {
		this.id = "";
		this.date = "";
		this.employeeId = "";
		this.displayName = "";
		this.comments = "";
		this.startTime = "";
		this.endTime = "";
	}
	
	
	function SlaapDienstObject() {
		this.id = "";
		this.date = "";
		this.employeeId = "";
		this.displayName = "";
	}
	
	
	this.getFormattedSlaapDienst = function(dataSet, trBody) {
		var trDayDates = $('.tb-data thead tr').eq(1);

		var etalonRowClone = $(".tb-data .slaap-dienst tr:nth-child(1)").clone(true);
		
		//Remove Row name "Slaap Dienst", use css to keep height size of the cell
		etalonRowClone.eq(0).children().eq(0).css("color", "transparent");
		
		for (var index = 0; index < dataSet.length; index++) {

			var tableRecord = dataSet[index];
			var recordDate = tableRecord.date;
			// find column by date
			var colIndex = -1;

			for (var dayIndex = 0; dayIndex < trDayDates.children().length; dayIndex++) {

				var dayCell = trDayDates[0].children[dayIndex];
				var date = $(dayCell).data("date");

				if (recordDate == date) {
					colIndex = $(dayCell).parent().children().index($(dayCell));
					break;
				}
			}
			if (colIndex != -1) {
				var isUpdated = false;
				for (var rowIndex = 0; rowIndex < trBody.children().length; rowIndex++) {

					var row = trBody.children().eq(rowIndex);

					var tdEl = row.children().eq(colIndex);

					if (tdEl.text() == "") {
						formatSlaapDienstData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId);
						isUpdated = true;
						break;
					}
				}

				if (isUpdated == false) {
					var rowCopy = etalonRowClone.clone(true);
					trBody.append(rowCopy);
					var lastRow = trBody.find("tr:last");
					var tdEl = lastRow.children().eq(colIndex);
					formatSlaapDienstData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId);
				}
			}
		}
	}

	function formatSlaapDienstData(tdElement, dbId, date, displayName, employeeId) {

		var cellObject = new SlaapDienstObject();
		cellObject.id = dbId;
		cellObject.date = date;
		cellObject.displayName = displayName;
		cellObject.employeeId = employeeId;

		tdElement.data('sp-data', cellObject);
		tdElement.text(displayName + " +2");
	}

	this.getFormattedCustomHours = function(dataSet, trBody) {
		var trDayDates = $('.tb-data thead tr').eq(1);

		var etalonRowClone = $(".tb-data .gap-body #etalon-row").clone(true);

		for (var index = 0; index < dataSet.length; index++) {

			var tableRecord = dataSet[index];
			var recordDate = tableRecord.date;
			// find column by date
			var colIndex = -1;

			for (var dayIndex = 0; dayIndex < trDayDates.children().length; dayIndex++) {

				var dayCell = trDayDates[0].children[dayIndex];
				var date = $(dayCell).data("date");

				if (recordDate == date) {
					colIndex = $(dayCell).parent().children().index($(dayCell));
					break;
				}
			}
			if (colIndex != -1) {
				var isUpdated = false;
				for (var rowIndex = 0; rowIndex < trBody.children().length; rowIndex++) {

					var row = trBody.children().eq(rowIndex);

					var tdEl = row.children().eq(colIndex);

					if (tdEl.text() == "") {
						formatCustomHoursData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId, tableRecord.comments, tableRecord.startTime, tableRecord.endTime);
						isUpdated = true;
						break;
					}
				}

				if (isUpdated == false) {
					var rowCopy = etalonRowClone.clone(true);
					rowCopy.css('visibility', 'visible');
					trBody.append(rowCopy);
					var lastRow = trBody.find("tr:last");
					var tdEl = lastRow.children().eq(colIndex);
					formatCustomHoursData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId, tableRecord.comments, tableRecord.startTime, tableRecord.endTime);
				}
			}
		}
	}

	function formatCustomHoursData(tdElement, dbId, date, displayName, employeeId, comments, startTime, endTime) {

		var cellObject = new DienstObject();
		cellObject.id = dbId;
		cellObject.date = date;
		cellObject.displayName = displayName;

		cellObject.employeeId = employeeId;
		cellObject.comments = comments;
		cellObject.startTime = startTime;
		cellObject.endTime = endTime;

		tdElement.data('sp-data', cellObject);

		var inputText = "";

		if (typeof startTime != 'undefined' && typeof endTime != 'undefined') {

			var formattedStart = getFormattedHours(startTime);
			var formattedEnd = getFormattedHours(endTime);

			inputText = formattedStart + '-' + formattedEnd;

			function getFormattedHours(myTime) {
				var customTime = myTime.split(":");
				var customHours = "";
				var customMinutes = "";
				if (customTime[0] > 12) {
					customHours = customTime[0] - 12;
				} else {
					customHours = customTime[0];
				}
				var result;
				if (customTime[1] == 30) {
					result = 'h' + customHours
				} else if (customTime[1] == 00) {
					result = customHours;
				} else {
					result = myTime;
				}

				return result;
			}
		}

		if (typeof displayName != 'undefined') {
			inputText = inputText + " " + displayName;
		}

		if (typeof comments != 'undefined') {
			inputText += " " + comments
		}
		if (inputText.length == 0) {
			inputText = "empty";
		}
		tdElement.text(inputText);
	}

}