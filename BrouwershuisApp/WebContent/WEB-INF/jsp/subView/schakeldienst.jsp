<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="imagePath" value="${pageContext.request.contextPath}/resources/img" />

<style>
#main_content {
	padding-left: 270px;
	display: inline-block;
	width: 100%;
	max-width: 1200px;
}

#main_content .title-row {
	height: 100px;
	margin-right: 0;
}

#main_content .content-row {
	margin-right: 0;
}

.n-sidebar .calendar-row-active {
	background: darkorange;
	border-radius: 0;
}

#rooster-uren button.disabled {
	pointer-events: none;
}

#rooster-uren table.tb-data {
	table-layout: fixed;
}

#rooster-uren table tr {
	height: 38px;
}

#rooster-uren table.tb-data .date>th:first-child {
	text-align: left;
}

#rooster-uren table.tb-data .date>th {
	text-align: right;
}

#rooster-uren .tb-data tr .my-edit.active {
	background-color: #acbad4;
}

#rooster-uren .tb-data tr .my-edit:hover {
	background-color: #f6f6f6;
}

#rooster-uren .tb-data tr .my-edit:hover.active {
	background-color: #aab7d1;
}

#modal-dienst .info-group {
	display: inline-block;
}

#modal-dienst .info-group select {
	color: #000000;
	background-color: #FFF;
	border: 1px solid #aaa;
	margin: 2px;
	border-radius: 3px;
	box-shadow: 0px 1px 2px #ccc inset;
	height: 26px;
	width: 150px;
	margin-left: 15px;
	padding: 0;
	display: inline-block;
}

#modal-dienst #modal-input tr td {
	vertical-align: middle;
	text-align: center;
}

#modal-dienst #modal-input tr td label {
	margin: 0;
}

#modal-dienst #modal-input tr td:nth-child(1) {
	width: 40px;
}

#modal-dienst #modal-input tr td:nth-child(2) {
	width: 50px;
}

#modal-dienst #modal-input tr td:nth-child(3) {
	width: 50px;
	text-align: center;
}

#modal-dienst #modal-input tr .tb-input {
	width: 60px;
	text-align: center;
}

#modal-dienst #modal-input tr .tb-input.comment {
	text-align: left;
	width: 100%;
}
</style>

<aside id="sidebar" class="page-sidebar sidebar-fixed">
	<div id="sidebar-inner">
		<div id="sidebar_top">
			<div class="n-sidebar">
				<div id="n-calendar"></div>
			</div>
		</div>
		<div id="sidebar_content">

			<h3>Information</h3>
			<br />
		</div>
	</div>
</aside>


<div id="main_content">

	<div class="">
		<!-- Page Heading -->
		<div class="row title-row">
			<div class="">
				<h1 class="page-header">Schakel dienst data</h1>
			</div>
		</div>
		<!-- /.row -->
		<div class="row content-row">
			<div class="">
				<div class="panel panel-default">
					<div class="panel-heading vu-panel">
						<!--END Panel Header  -->
					</div>
					<div class="panel-body dienst">
						<div class="n-container" id="rooster-uren">
							<button class="btn btn-success" onClick="openAddDienstModal()">
								<i class="glyphicon glyphicon-plus"></i> Toevoeg dienst
							</button>
							<button class="btn btn-primary bt-my-edit disabled" onClick="openEditDienstModal()">
								<i class="glyphicon glyphicon-edit"></i> Bewerk dienst
							</button>
							<button class="btn btn-danger bt-my-remove disabled my-popover-delete" data-placement="top" role="button" data-toggle="popover" onClick="deleteTableRecordClick(this)">
								<i class="glyphicon glyphicon glyphicon-remove"></i> Verwijderen dienst
							</button>
							<div class="row">
								<div class="table-scrollable">
									<table class="table table-bordered tb-data">
										<thead>
											<tr>
												<th></th>
												<th class="dag-col-0">zondag</th>
												<th class="dag-col-1">maandag</th>
												<th class="dag-col-2">dinsdag</th>
												<th class="dag-col-3">woensdag</th>
												<th class="dag-col-4">donderdag</th>
												<th class="dag-col-5">vrijdag</th>
												<th class="dag-col-6">zaterdag</th>
											</tr>
											<tr class="date">
												<th>Datum</th>
												<th>0</th>
												<th>1</th>
												<th>2</th>
												<th>3</th>
												<th>4</th>
												<th>5</th>
												<th>6</th>
											</tr>
										</thead>
										<tbody>
											<tr class="">
												<td>&nbsp;</td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<!--END Content wrapper  -->
						</div>
						<!--END Panel Body  -->
					</div>
				</div>
				<!--END ROW  -->
			</div>
		</div>
	</div>
</div>

<!-- Bootstrap modal -->
<div class="modal fade" id="modal-dienst" role="dialog">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Toevoeg schakel dienst</h4>
			</div>
			<div class="modal-body">
				<div class="info-group table-responsive"">
					<div class="info">
						<span class="">Year:</span>
						<label class="lb">
							<i class="">&nbsp;<span class="year">2017</span></i>
						</label>
					</div>
					<div class="info">
						<span class="">Month:</span>
						<label class="lb">
							<i class="">&nbsp;<span class="month">August</span></i>
						</label>
					</div>
					<div class="info">
						<span class="">Week:</span>
						<label class="lb">
							<i class="">&nbsp;<span class="week">32</span></i>
						</label>
					</div>

					<div class="info">
						<span class="">Medewerker:</span>
						<select class="form-control" id="select-employee" name="medewerker-select">
							<option value="-1">Kies medewerker</option>
						</select>
					</div>

					<!-- <div class="info">
						<span class="">Opties:</span>
						<div class="checkbox">
							<label>
								<input type="checkbox" id="open-shift" value="Vrij dienst" onClick="vrijdDienstCheckBoxClick(this)">Vrij dienst
							</label>
						</div>

					</div> -->


					<div class="dienst-title">
						<i>Dienst:</i>
					</div>

					<table id="modal-input" class="table">
						<thead>
							<tr>
								<th>Dag</th>
								<th>Datum</th>
								<th>Begin</th>
								<th>Eind</th>
								<th>Looptijd</th>
								<th>Commentaar</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Zon</td>
								<td><label id="">1</label></td>
								<td><input class="tb-input form-control" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label class="time-range"></label></td>
								<td><input class="tb-input form-control comment" type="text" /></td>
							</tr>
							<tr>
								<td>Man</td>
								<td><label id="">1</label></td>
								<td><input class="tb-input form-control" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label class="time-range"></label></td>
								<td><input class="tb-input form-control comment" type="text" /></td>
							</tr>
							<tr>
								<td>Din</td>
								<td><label id="">1</label></td>
								<td><input class="tb-input form-control" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label class="time-range"></label></td>
								<td><input class="tb-input form-control comment" type="text" /></td>
							</tr>
							<tr>
								<td>Woe</td>
								<td><label id="">1</label></td>
								<td><input class="tb-input form-control" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label class="time-range"></label></td>
								<td><input class="tb-input form-control comment" type="text" /></td>
							</tr>
							<tr>
								<td>Don</td>
								<td><label id="">1</label></td>
								<td><input class="tb-input form-control" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label class="time-range"></label></td>
								<td><input class="tb-input form-control comment" type="text" /></td>
							</tr>
							<tr>
								<td>Vri</td>
								<td><label id="">1</label></td>
								<td><input class="tb-input form-control" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label class="time-range"></label></td>
								<td><input class="tb-input form-control comment" type="text" /></td>
							</tr>
							<tr>
								<td>Zat</td>
								<td><label id="">1</label></td>
								<td><input class="tb-input form-control" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label class="time-range"></label></td>
								<td><input class="tb-input form-control comment" type="text" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" id="modal-save" class="btn btn-primary" onClick="modalDienstSaveClick();">Save</button>
				<button type="button" id="modal-save-edit" class="btn btn-primary" onClick="modalDienstSaveEditClick();">Save changes</button>
			</div>
		</div>
	</div>
</div>

<div style="display: none">
	<div id="my-popover-content">
		<p class="confirmation-content" style="display: block;">This might be dangerous</p>
		<div class="confirmation-buttons text-center">
			<div class="btn-group">
				<a href="#" class="btn btn-success" onClick="confirmPopoverDeleteCkick()">
					<i class="glyphicon glyphicon-share-alt"></i> Continue
				</a>
				<a href="#" class="btn btn-danger" onClick="cancelPopoverDeleteCkick()">
					<i class="glyphicon glyphicon-ban-circle"></i> Stoooop!
				</a>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	//# sourceURL=schakel.js

	//Objects
	var viewProperties = new function() {

		this.selectedTableCell = "";
	}

	//the object which is received from back-end
	function DienstObject() {
		this.id = "";
		this.date = "";
		this.employeeId = "";
		this.displayName = "";
		this.comments = "";
		this.startTime = "";
		this.endTime = "";
	}

	function DienstCellData() {
		this.date = "";
		this.employeeId = "";
		this.startTime = "";
		this.endTime = "";
	}

	function calculateDurationTime(element) {

		var trEl = element.closest("tr");

		var validItems = trEl.find("*[data-timepicker]");

		var isValid = false;

		if (validItems.length == 2) {

			var timeRangeEl = trEl.find(".time-range");

			var start = validItems.filter("*[name='start']");
			var end = validItems.filter("*[name='end']");

			str1 = start.val().split(":")
			str2 = end.val().split(':');
			var result = "";

			if (str1.length == 2 && str2.length == 2) {
				isValid = true;

				//calculate the difference
				//var finalStr = m(str1[0], str2[0])+":"+m(str1[1], str2[1])+":"+m(str1[2], str2[2]);
				var first = moment(start.val(), "HH:mm");
				var second = moment(end.val(), "HH:mm");

				var isNegative = false;
				var timeDif = second - first;

				if (timeDif < 0) {
					isNegative = true;
					timeDif = Math.abs(timeDif);
					timeDif = 86400000 - timeDif
					//timeDif = Math.abs(timeDif);
				}

				tempTIme = moment.duration(timeDif);

				var hours = tempTIme.hours();
				var minutes = Math.floor(Math.log10(tempTIme.minutes()) + 1) == 1 ? "0" + tempTIme.minutes() : tempTIme.minutes() != 0 ? tempTIme.minutes() : "00";
				//var res = moment.utc(moment(end.val(),"HH:mm").diff(moment(start.val(),"HH:mm"))).format("HH:mm")

				//var difference = m(str1[0], str2[0])+":"+m(str1[1], str2[1]);
				result = hours + ":" + minutes;
			}

			if (isValid == false) {
				timeRangeEl.removeClass("valid-time");
				timeRangeEl.addClass("invalid-time");
			} else {
				timeRangeEl.removeClass("invalid-time");
				timeRangeEl.addClass("valid-time");
			}

			timeRangeEl.text(result);
		}

	}

	//functions
	$(document).ready(function() {

		runAutoFormatTimepicker(calculateDurationTime);

		var popOverContent = $("#my-popover-content");

		$(".my-popover-delete").popover({
			title : "Is it ok?",
			content : popOverContent.html(),
			html : true,
			trigger : "manual"
		});

		//The Twitter Bootstrap popover dismiss after clicking outside it's boundaries
		$('body').on('click', function(e) {
			//did not click a popover toggle or popover
			if ($(e.target).data('toggle') !== 'popover' && $(e.target).parents('.popover.in').length === 0) {
				$('[data-toggle="popover"]').popover('hide');
			}
		});

		//bootstrap bug, needs two clicks after calling 'hide'
		$('body').on('hidden.bs.popover', function(e) {
			$(e.target).data("bs.popover").inState.click = false;
		});

		loadDefaultProperties();

		$('.dienst .tb-data tr .my-edit').on('click', function(event) {

			if (viewProperties.selectedTableCell != "") {
				$(viewProperties.selectedTableCell).removeClass("active");
			}

			if (event.target != viewProperties.selectedTableCell) {
				if (event.target.innerHTML != "") {

					$(event.target).addClass("active");
					$(".dienst .bt-my-edit").removeClass("disabled");
					$(".dienst .bt-my-remove").removeClass("disabled");
				} else {
					$(".dienst .bt-my-edit").addClass("disabled");
					$(".dienst .bt-my-remove").addClass("disabled");
				}
				viewProperties.selectedTableCell = event.target;
			} else {
				$(".dienst .bt-my-edit").addClass("disabled");
				$(".dienst .bt-my-remove").addClass("disabled");
				viewProperties.selectedTableCell = "";
			}
		});

	})

	function loadDefaultProperties() {

		//Initialize calender
		$("#n-calendar").datepicker({
			todayBtn : "linked",
			todayHighlight : true,
			format : "yyyy-mm-dd",
			calendarWeeks : true
		}).on('changeDate', function(e) {
			highlightSelectedWeekInCalendar(".active.day");
			getTabelData(e.date);
		});
		//Highlight first week
		highlightSelectedWeekInCalendar(".today.day");

		var first = $("#n-calendar");
		//find first day of week
		var momentDate = new moment(new Date());
		var weekDay = momentDate.weekday();

		if (weekDay != 0) {
			momentDate = momentDate.add(-weekDay, 'days');
		}

		fillInEmployeeNamesInModal();

		getTabelData(new Date());

	}

	function fillInDienstTableData(dataSet) {
		var trDayDates = $('.tb-data thead tr').eq(1);
		var trBody = $('.tb-data tbody');

		var etalonRowClone = $(".tb-data tbody tr:nth-child(1)").clone(true);

		for (var index = 0; index < dataSet.length; index++) {

			var tableRecord = dataSet[index];
			var recordDate = tableRecord.date;
			//find column by date
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
						formatTableCellData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId, tableRecord.comments, tableRecord.startTime, tableRecord.endTime);
						isUpdated = true;
						break;
					}
				}

				if (isUpdated == false) {
					var rowCopy = etalonRowClone.clone(true);
					trBody.append(rowCopy);
					var lastRow = trBody.find("tr:last");
					var tdEl = lastRow.children().eq(colIndex);
					formatTableCellData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId, tableRecord.comments, tableRecord.startTime, tableRecord.endTime);
				}
			}
		}
	}

	function formatTableCellData(tdElement, dbId, date, displayName, employeeId, comments, startTime, endTime) {

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

	function fillInEmployeeNamesInModal() {
		var jsonObject = JSON.parse('${allEmployees}');
		var medewerkers = jsonObject.medewerkers;

		//fill in dienst modal with data
		fillInModalDienst(medewerkers);
	}

	function highlightSelectedWeekInCalendar(selector) {
		var calendar = $("#n-calendar");
		var activeDay = calendar.find(selector);
		var parent = activeDay.parent();

		for (var index = 1; index < parent[0].children.length; index++) {
			$(parent[0].children[index]).addClass("calendar-row-active");
		}
	}

	function fillInDaysInTableHeader(dayOfWeek) {
		var trDayDates = $('.tb-data thead tr').eq(1);

		//start from one because first column is empty
		for (var index = 1; index < 8; index++) {
			var dayCell = trDayDates[0].children[index];
			dayCell.innerHTML = dayOfWeek.format("DD-MM");
			$(dayCell).data("date", dayOfWeek.format('YYYY-MM-DD'));
			dayOfWeek.add(1, "days");
		}
	}

	function fillInModalDienst(medewerkers) {

		for (var i = 0; i < medewerkers.length; i++) {
			var name = medewerkers[i].firstName[0] + ". " + medewerkers[i].lastName;
			var option = $('<option />');
			option.attr('value', medewerkers[i].DT_RowId).text(name);
			$("#select-employee").append(option);
		}
	}

	function initDienstModal() {

		//remove all employees from drop down box
		var calendar = $('#n-calendar').data();
		var momentDate = new moment(calendar.datepicker.viewDate);
		var weekDay = momentDate.weekday();

		if (weekDay != 0) {
			momentDate = momentDate.add(-weekDay, 'days');
		}

		var year = momentDate.year();
		var week = momentDate.isoWeek();
		var inputTable = $("#modal-input tbody");

		for (var index = 0; index < inputTable.children().length; index++) {

			var trEl = inputTable.children().eq(index);

			$(trEl).data("date", momentDate.format('YYYY-MM-DD'));
			trEl.children().eq(1).text(momentDate.format("DD-MM"))

			momentDate.add(1, "days");
		}

		$(".info-group .year").text(year);
		$(".info-group .month").text(momentDate.format("MMMM"));
		$(".info-group .week").text(week);
	}

	function openAddDienstModal() {

		initDienstModal();

		var trRows = $('#modal-dienst #modal-input tbody tr');

		for (var index = 0; index < trRows.length; index++) {
			var el = $(trRows[index]);
			el.css("display", "table-row");
			el.removeClass('selected');
			//clear input
			var inputEls = el.find("input");
			inputEls.each(function() {
				$(this).val("");
			});

			//clear time range
			var timeRanges = el.find(".valid-time, .invalid-time");
			timeRanges.each(function() {
				$(this).text("");
				$(this).removeClass("valid-time invalid-time")
			});

			//inputEl.prop('checked', false);
			//inputEl.prop("disabled", false);
		}
		$("#modal-dienst #modal-save").css("display", "inline-block");
		$("#modal-dienst #modal-save-edit").css("display", "none");

		$('#modal-dienst').modal('show'); // show bootstrap modal
	}

	function openEditDienstModal() {

		initDienstModal();

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var recordData = $(selectedRecord).data('sp-data');

			$.ajax({
				url : "${contextPath}/schedules/subview/schakeldienst/getRecordDetails?id=" + recordData.id,
				type : "GET",
				contentType : "application/json",
				success : function(data) {

					if (data != "undefined") {
						var momentDate = new moment(data.weekDate);
						var formattedDate = momentDate.format('YYYY-MM-DD');

						var tableRows = $("#modal-dienst #modal-input tbody tr");

						$(tableRows).each(function() {

							var element = $(this);
							var elData = element.data("date");
							if (elData == formattedDate) {

								var inputEl = element.find("input");
								if (inputEl != null) {
									/* inputEl.prop('checked', true);
									inputEl.prop("disabled", true); */

									//set right employee in dropDown list
									var selectEmployee = $("#modal-dienst #select-employee");
									selectEmployee.children().each(function() {

										var optionValue = $(this).val();

										if (data.employee == null) {
											data.employee = new Object();
											data.employee.id = -1;
										}

										if (data.employee.id == optionValue) {
											$(this).prop("selected", true);
										}
									});

									var start = element.find("*[name='start']");
									var end = element.find("*[name='end']");
									var comments = element.find(".comment");

									start.val(data.startTime);
									end.val(data.endTime);
									calculateDurationTime(element);

									comments.val(data.comments);
									element.addClass("selected");
									element.css("display", "table-row");
								}
							} else {
								//hide unused rows
								element.css("display", "none");
							}
						});

						$("#modal-dienst #modal-save").css("display", "none");
						$("#modal-dienst #modal-save-edit").css("display", "inline-block");
						$('#modal-dienst').modal('show'); // show bootstrap modal

					} else {
						alert('Data cannot be retrieved!');
					}

				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('Data cannot be retrieved!');
				}
			});

		}
	}

	/* 	function vrijdDienstCheckBoxClick(obj) {

	 var emplployee = $("#modal-dienst #select-employee");

	 if (obj.checked) {
	 emplployee.prop("disabled", true);
	 emplployee.css("background-color", "#eee")
	 } else {
	 emplployee.prop("disabled", false);
	 emplployee.css("background-color", "#fff")
	 }
	 } */

	function modalDienstSaveClick() {

		var emplployee = $("#modal-dienst #select-employee :selected");

		var employeeId = emplployee.val() != -1 ? emplployee.val() : null;

		var trRows = $("#modal-dienst #modal-input tbody tr");
		var updateItems = [];
		for (var rowIndex = 0; rowIndex < trRows.length; rowIndex++) {

			var row = trRows.eq(rowIndex);

			var start = row.find("*[name='start']").val();
			var end = row.find("*[name='end']").val();
			var comments = row.find(".comment").val();

			if (start.length == 5 && end.length == 5) {

				var updateData = new DienstCellData();
				updateData.date = row.data("date");
				updateData.employeeId = employeeId;
				updateData.startTime = start;
				updateData.endTime = end;
				updateData.comments = comments.trim();
				updateItems.push(updateData);
			}
		}

		if (updateItems.length == 0)
			return;

		var jsonData = JSON.stringify(updateItems);

		$.ajax({
			url : "${contextPath}/schedules/subview/schakeldienst/putRecord",
			type : "PUT",
			//dataType : "text",
			contentType : "application/json",
			data : jsonData,
			success : function(data) {

				if (data.status) {

					var calendar = $('#n-calendar').data();
					var selectedDate = calendar.datepicker.viewDate;

					getTabelData(selectedDate);

					$('#modal-dienst').modal('hide');
					//reload_table();
				} else {
					alert('Data was not saved');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error save data from ajax');
			}
		});
	}

	function modalDienstSaveEditClick() {

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var selectedEmployee = $("#modal-dienst #select-employee :selected");
			var recordData = $(selectedRecord).data('sp-data');

			var updatedRow = $("#modal-dienst .selected");

			var start = updatedRow.find("*[name='start']").val();
			var end = updatedRow.find("*[name='end']").val();
			var comments = updatedRow.find(".comment").val();

			var updateObject = new DienstObject();
			updateObject.id = recordData.id;
			updateObject.date = recordData.date;

			updateObject.displayName = selectedEmployee.val() != -1 ? selectedEmployee.text() : undefined;
			updateObject.employeeId = selectedEmployee.val() != -1 ? selectedEmployee.val() : null;
			updateObject.startTime = start;
			updateObject.endTime = end;
			updateObject.comments = comments.trim();

			var jsonData = JSON.stringify(updateObject);

			$.ajax({
				url : "${contextPath}/schedules/subview/schakeldienst/patchRecord",
				type : "PATCH",
				contentType : "application/json",
				data : jsonData,
				success : function(data) {
					if (data.status) {

						formatTableCellData($(viewProperties.selectedTableCell), updateObject.id, updateObject.date, updateObject.displayName, updateObject.employeeId, updateObject.comments,
								updateObject.startTime, updateObject.endTime);

						$(viewProperties.selectedTableCell).removeClass("active");
						$(".dienst .bt-my-edit").addClass("disabled");
						$(".dienst .bt-my-remove").addClass("disabled");

						$('#modal-dienst').modal('hide');

						viewProperties.selectedTableCell = "";
					} else {
						alert('Data was not saved');
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('Error save data from ajax');
				}
			});
		}
	}

	function confirmPopoverDeleteCkick() {

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var recordData = $(selectedRecord).data('sp-data');

			var deleteObject = new DienstObject();
			deleteObject.id = recordData.id;
			deleteObject.date = recordData.date;
			deleteObject.displayName = recordData.displayName;
			deleteObject.employeeId = recordData.employeeId;

			var jsonData = JSON.stringify(deleteObject);

			$.ajax({
				url : "${contextPath}/schedules/subview/schakeldienst/deleteRecord",
				type : "DELETE",
				//dataType : "text",
				contentType : "application/json",
				data : jsonData,
				success : function(data) {
					if (data.status) {
						$(selectedRecord).text("");
						$(selectedRecord).removeData("sp-data");

						$(viewProperties.selectedTableCell).removeClass("active");
						$(".dienst .bt-my-edit").addClass("disabled");
						$(".dienst .bt-my-remove").addClass("disabled");

						$(".my-popover-delete").popover('hide');

						viewProperties.selectedTableCell = "";
					} else {
						alert('Data was not saved');
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('Error save data from ajax');
				}
			});
		}
	}

	function deleteTableRecordClick(obj) {
		$(".my-popover-delete").popover('show');
	}

	function cancelPopoverDeleteCkick() {
		$(".my-popover-delete").popover('hide');
	}

	function getTabelData(selectedDate) {
		var calendarTag = $("#n-calendar");
		//find first day of week
		var momentDate = new moment(selectedDate);
		var weekDay = momentDate.weekday();

		if (weekDay != 0) {
			momentDate = momentDate.add(-weekDay, 'days');
		}
		var firstDayMoment = momentDate.clone();
		var firstDay = momentDate.format('YYYY-MM-DD');
		momentDate.add(6, 'days');
		var lastDay = momentDate.format('YYYY-MM-DD');

		$.ajax({
			url : "${contextPath}/schedules/subview/schakeldienst/getAllRecords/?start=" + firstDay + "&end=" + lastDay,
			type : "GET",
			success : function(data) {

				var tableData = JSON.parse(data);

				cleanContentTableRecords();
				fillInDaysInTableHeader(firstDayMoment);

				var schakeldienst = tableData.schakeldienst;
				fillInDienstTableData(schakeldienst);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error get data from ajax');
			}
		});
	}

	function cleanContentTableRecords() {

		var tablesGroup = $("#rooster-uren");
		var tables = tablesGroup.find("table");

		//tables loop
		for (var tableCounter = 0; tableCounter < tables.length; tableCounter++) {

			var tableTbHours = $(tables[tableCounter]).find("tbody");

			//tr rows
			var children = tableTbHours.children();

			for (var childrenCounter = 0; childrenCounter < children.length; childrenCounter++) {
				var trRow = children[childrenCounter];

				//remove text and jquery data

				for (var cellIndex = 0; cellIndex < $(trRow).children().length; cellIndex++) {

					var tdTag = $(trRow).children().eq(cellIndex);
					tdTag.text("");
					tdTag.removeData("sp-data");
				}
			}
		}
	}
</script>