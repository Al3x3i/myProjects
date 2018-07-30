function VakantieCalendar() {

	this.setDatesIntoTableCells = function(startWeekData) {

		var weekData = startWeekData;
		var activeTables = viewProperties.getActiveTableCount();

		// table loop

		for (var tableCounter = 1; tableCounter <= activeTables; tableCounter++) {

			var tableObject = $("#tb-" + tableCounter);

			var tableTbHours = tableObject.find("tbody");
			var trHours = tableTbHours.find(".tr-hours");

			var daysRow = tableObject.find("thead").children().eq(0);
			var validWeeks = daysRow.children().not("[class*=invalid-week]");

			var weeks = [];
			for (var i = 1; i < validWeeks.length; i++) {
				// Get weekDays for four weeks
				weeks.push(this.getWeekDatesBySunday(weekData));
			}

			if (weeks.length != 0) {
				var start_end_date = {
					startDate : weeks[0][0],
					endDate : weeks[weeks.length - 1][6],
				}
				var inputTag = tableObject.find(".te-werken-input");
				inputTag.val("");
				inputTag.data('start_end_date', start_end_date);
			}

			// weeks loop
			for (var weekCounter = 0; weekCounter < weeks.length; weekCounter++) {

				// day loop
				for (var dayCounter = 0; dayCounter < trHours.length; dayCounter++) {
					var trRow = trHours[dayCounter];

					// tr row loop, start from one because first is day name
					// week has two cells for each day
					var tempIndex = 1 + weekCounter * 2;

					// Working cell
					var w_cellData = new HoursCellData();
					w_cellData.date = weeks[weekCounter][dayCounter];
					w_cellData.hoursType = 1;

					var tdTag = trRow.children[tempIndex];
					var intputTag = $(tdTag).find("input").first();
					intputTag.data('wh-data', w_cellData);
					intputTag.val("");

					// Vacation cell
					var v_cellData = new HoursCellData();
					v_cellData.date = weeks[weekCounter][dayCounter];
					v_cellData.hoursType = 2;

					var tdTag = trRow.children[tempIndex + 1];
					var intputTag = $(tdTag).find("input").first();
					intputTag.data('wh-data', v_cellData);
					intputTag.val("");
				}
			}
		}
	}

	this.getWeekDatesBySunday = function(date) {

		var momentDate = date;

		var weekDayNumber = momentDate.day(); // Sunday = 0
		var firstDay = "";
		if (weekDayNumber > 4) {
			firstDay = momentDate.add(7 - weekDayNumber, 'day');
		} else {
			firstDay = momentDate.add(-weekDayNumber, 'day');
		}

		return this.getWeekDates(firstDay);
	}

	// Get dates for one week from passed date parameter
	this.getWeekDates = function(date) {
		var week = [ date.format('YYYY-MM-DD') ];
		var i = 6;
		while (i--) {
			date.add(1, 'day');
			week.push(date.format('YYYY-MM-DD'));
		}
		return week;
	}
}



// Init function
function loadDecareHoursDefaultProperties(timeChangeCallBack, calendarClickCallBack) {

	// set table input handler, responsible for time format handling
	new inputTimeHanlder(timeChangeCallBack);

	// Initialize calender
	$("#n-calendar").datepicker({
		todayBtn : "linked",
		todayHighlight : true,
		format : "yyyy-mm-dd",
		calendarWeeks : true,
	}).on('changeDate', function(e) {
		highlightSelectedWeekInCalendar(".active.day");
		setYearInPageTitle();
		calendarClickCallBack();
		// getTabelData(e.date);
	});

	highlightSelectedWeekInCalendar(".today.day");

	fillEmployeesDropDown();
	updateWeekTitlesInTable_FirstSection();
	setYearInPageTitle()
}

function setYearInPageTitle(){
	var date = $('#n-calendar').data().datepicker.viewDate
	var year = date.getFullYear();
	
	panel_header = $("#vak_year").first();
	panel_header.text(" (" + year + ") ")
	
}

function highlightSelectedWeekInCalendar(selector) {
	var calendar = $("#n-calendar");

	var activeDay = calendar.find(selector);
	var parent = activeDay.parent();

	for (var index = 1; index < parent[0].children.length; index++) {
		$(parent[0].children[index]).addClass("calendar-row-active");
	}
}

function hideUnusedTableColumns(tableObject, hideFromWeekColumn) {
	var extraTables = $(".extraTable-row");
	var thead = $(tableObject).find("thead");
	var tbody = $(tableObject).find("tbody");

	var theadRows = thead.children();
	var tbodyRows = tbody.children();

	var declarationTypeRow = theadRows.eq(0);
	for (var i = 1; i < declarationTypeRow.children().length; i++) {

		var cell = declarationTypeRow.children().eq(i);
		if (i < hideFromWeekColumn) {
			cell.removeClass('invalid-week'); // Enable

		} else {
			cell.addClass('invalid-week'); // Disable

		}
	}

	if (hideFromWeekColumn != 1) {
		hideFromWeekColumn = 3;
	}

	var declarationTypeRow = theadRows.eq(1);
	for (var i = 1; i < declarationTypeRow.children().length; i++) {

		var cell = declarationTypeRow.children().eq(i);
		if (i < hideFromWeekColumn) {
			cell.removeClass('invalid-week'); // Enable

		} else {
			cell.addClass('invalid-week'); // Disable

		}
	}

	for (var i = 0; i < 8; i++) {

		var declarationTypeRow = tbodyRows.eq(i);
		for (var y = 1; y < declarationTypeRow.children().length; y++) {

			var cell = declarationTypeRow.children().eq(y).find("input");
			if (y < hideFromWeekColumn) {
				cell.removeClass('invalid-week'); // Enable

			} else {
				cell.addClass('invalid-week'); // Disable

			}
		}

	}

}

function showExtraTables() {
	var rowTables = $(".extraTable-row").show();// .removeClass("extraTable");
}

function calculateTableHours(tableObject) {
	var tbody = tableObject.children('tbody');

	var columnHoursTotal = 0;
	var columnMinutesTotal = 0;
	var totalWorkingHours = 0;
	var totalWorkingMinutes = 0;
	var totalVacationgHours = 0;
	var totalVacationMinutes = 0;

	var columnNr = 1; // skip first, this is day name
	var countOfColumns = tbody.children().eq(0).children().length;

	while (columnNr < countOfColumns) {

		// 7 - because seven days
		for (var x = 0; x < 7; x++) {

			var trEl = tbody.children().eq(x); // row

			var tdEl = trEl.children().eq(columnNr); // row has td

			var inputEl = tdEl.children().eq(0).val();

			if (inputEl != null && inputEl !== '') {

				var hourMinute = inputEl.split(':');
				var hour = Number(hourMinute[0]);
				var minute = Number(hourMinute[1]);

				// working hours
				if (columnNr % 2 == 1) {
					totalWorkingHours += hour;
					totalWorkingMinutes += minute;
				}
				// vacation hours
				else {
					totalVacationgHours += hour;
					totalVacationMinutes += minute;
				}

				columnHoursTotal += hour;
				columnMinutesTotal += minute;
			}
		}

		// Summirize all hours and update total cells
		var rowTotal = tbody.children().eq(7);
		var totalCell = rowTotal.children().eq(columnNr);

		var extraHours = Math.floor(columnMinutesTotal / 60)
		columnHoursTotal += extraHours;
		var realMinutes = columnMinutesTotal % 60;

		totalCell.html(columnHoursTotal + ':' + realMinutes);

		columnHoursTotal = 0;
		columnMinutesTotal = 0;
		columnNr++;
	}

	// count all Working hours
	var extraHours = Math.floor(totalWorkingMinutes / 60)
	totalWorkingHours += extraHours;
	totalWorkingMinutes = totalWorkingMinutes % 60;

	var trWorkEl = tbody.children().eq(8);
	var totalHoursEl = trWorkEl.children().eq(2); // number 3 because its
													// place
	totalHoursEl.find('span').html(totalWorkingHours + ':' + totalWorkingMinutes);

	// Count all Vacation hours
	var extraHours = Math.floor(totalVacationMinutes / 60)
	totalVacationgHours += extraHours;
	totalVacationMinutes = totalVacationMinutes % 60;

	var trVacationEl = tbody.children().eq(9);
	var totalHoursEl = trVacationEl.children().eq(1); // number 2 because its
														// place
	totalHoursEl.find('span').html(totalVacationgHours + ':' + totalVacationMinutes);

	var totalWorkedSeconds = getTimeInSeconds(totalWorkingHours, totalWorkingMinutes, 0);
	var totalVacationSeconds = getTimeInSeconds(totalVacationgHours, totalVacationMinutes, 0);

	calculateVerschil(tbody, totalWorkedSeconds, totalVacationSeconds);
}

function getTimeInSeconds(hours, minutes, seconds) {
	var total = hours * 60 * 60;
	total += minutes * 60;
	return total;
}

function getTimFromSeconds(totalSeconds) {
	var hasMinus = "";
	if(totalSeconds < 0){
		hasMinus = "-";
		totalSeconds = totalSeconds * -1;
	}
	
	var hours = Math.floor(totalSeconds / 3600);
	var minutes = (totalSeconds - hours * 3600) / 60;
	
	if (minutes < 10) {
		minutes = "0" +minutes;
	}
	return hasMinus + hours + ":" + minutes;
}

function calculateVerschil(tbody, workedSeconds, vacationSeconds) {

	var declaredHours = tbody.find("[id^=tb-total-willwork]");
	if (declaredHours != "undefined") {
		var declaredVal = declaredHours.val();
		if (declaredVal != "undefined" || declaredVal != "") {

			var hours = 0;
			var minutes = 0;

			var splittedTime = declaredVal.split(":")
			if (splittedTime.length >= 1) {
				hours = splittedTime[0];
			}
			if (splittedTime.length == 2) {
				minutes = splittedTime[1];
			}
			
			var totalWillWorkSeconds = getTimeInSeconds(hours, minutes, 0);
			var differenceTimeSeconds = workedSeconds - totalWillWorkSeconds;

			var differenceTImeFormatted = getTimFromSeconds(differenceTimeSeconds);
			
			var differenceEl = tbody.find("[id^=tb-total-difference]");
			differenceEl.html(differenceTImeFormatted);
			
			
		} else {
			declaredVal = 0;
		}

	}
}

function calculateTotalHours(tagsGroup) {

	var totalTime = "";

	var totalHours = 0;
	var totalMinutes = 0;

	tagsGroup.each(function(key, value) {
		var spanText = $(value).text();

		var hourMinute = spanText.split(':');
		var hour = Number(hourMinute[0]);
		var minute = Number(hourMinute[1]);

		totalHours += hour;
		totalMinutes += minute;
	});
	var extraHours = Math.floor(totalMinutes / 60)
	totalHours += extraHours;
	totalMinutes = totalMinutes % 60;

	return totalHours + ":" + totalMinutes;
}

function updateWeekTitlesInTable_FirstSection() {

	var date = $('#n-calendar').data().datepicker.viewDate
	var momentDate = moment(date, 'YYYY-MM-DD', true);
	var weekData = momentDate.startOf('year');
	vakantieCalendarObject.setDatesIntoTableCells(weekData);

	var weekCounter = 1;
	for (var count = 1; count <= 6; count++) {

		var tableObject = document.getElementById("tb-" + count);

		// Insert week Title
		for (var x = 1; x < 5; x++) {
			var weekCell = tableObject.rows[0].cells[x];
			weekCell.id = "wk-" + weekCounter;
			weekCell.innerHTML = "Week " + weekCounter;
			weekCounter++;
		}
	}
}

function updateWeekTitlesInTable_SecondSection() {

	var date = $('#n-calendar').data().datepicker.viewDate
	var year = date.getFullYear();
	var momentDate = new moment().weekYear(year);
	momentDate = momentDate.isoWeek(25);
	var weekDayNumber = momentDate.day(); // Sunday = 0
	// get first day of week nr 25
	var weekData = momentDate.add(-weekDayNumber, 'day');

	vakantieCalendarObject.setDatesIntoTableCells(weekData.clone());

	var weekCounter = 25;

	var nrOfWeeksInYear = momentDate.isoWeeksInYear();

	for (var count = 1; count <= 8; count++) {

		var tableObject = document.getElementById("tb-" + count);

		// Insert week Title
		for (var x = 1; x < 5; x++) {

			if (weekCounter > nrOfWeeksInYear) {
				var weekCell = tableObject.rows[0].cells[x];
				weekCell.id = "";
				weekCell.innerHTML = "";

				hideUnusedTableColumns(tableObject, x);
				break;
			} else {
				var weekCell = tableObject.rows[0].cells[x];
				weekCell.id = "wk-" + weekCounter;
				weekCell.innerHTML = "Week " + weekCounter;
			}
			weekCounter++;
		}
	}
}