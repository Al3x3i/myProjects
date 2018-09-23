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
				<h1 class="page-header">Rooster</h1>
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
							<div class="row">
								<div class="table-scrollable">
									<table class="table table-bordered tb-data">
										<thead>
											<tr>
												<th></th>
												<th class="dag-col-0">maandag</th>
												<th class="dag-col-1">dinsdag</th>
												<th class="dag-col-2">woensdag</th>
												<th class="dag-col-3">donderdag</th>
												<th class="dag-col-4">vrijdag</th>
												<th class="dag-col-5">zaterdag</th>
												<th class="dag-col-6">zondag</th>
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
										<tbody class="slaap-dienst">
											<tr class="">
												<td><span>Slaap-dienst</span></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
											</tr>
										</tbody>
										<tbody class="gap-body">
											<tr class="" id="etalon-row" style="visibility:hidden">
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
										<tbody class="schakel-dienst">
											<tr class="">
												<td><span>Schakel-dienst</span></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
											</tr>
										</tbody>
										<tbody class="gap-body">
											<tr class="">
											</tr>
										</tbody>
										<tbody class="hotel-dienst">
											<tr class="">
												<td><span>Hotel-dienst</span></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
											</tr>
										</tbody>
										<tbody class="gap-body">
											<tr class="">
											</tr>
										</tbody>
										<tbody class="kaffee-dienst">
											<tr class="">
												<td><span>Kaffee-dienst</span></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
												<td class="my-edit"></td>
											</tr>
										</tbody>
										<tbody class="gap-body">
											<tr class="">
											</tr>
										</tbody>
										<tbody class="keuken-dienst">
											<tr class="">
												<td><span>Keuken-dienst</span></td>
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

<script type="text/javascript">
	//# sourceURL=rooster.js

	var mainMainContetOriginal = $("#main_content").html()
	//functions
	$(document).ready(function() {

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

	})

	function loadDefaultProperties() {

		//Initialize calender
		$("#n-calendar").datepicker({
			todayBtn : "linked",
			todayHighlight : true,
			format : "yyyy-mm-dd",
			calendarWeeks : true,
			weekStart: 1
		}).on('changeDate', function(e) {
			highlightSelectedWeekInCalendar(".active.day");
			getTabelData(e.date);
		});
		//Highlight first week
		highlightSelectedWeekInCalendar(".today.day");


		getTabelData(new Date());

	}

/* 	function fillInDienstTableData(dataSet, trBody) {
		var trDayDates = $('.tb-data thead tr').eq(1);

		var etalonRowClone = $(".tb-data .gap-body #etalon-row").clone(true);

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
					rowCopy.css('visibility', 'visible');
					trBody.append(rowCopy);
					var lastRow = trBody.find("tr:last");
					var tdEl = lastRow.children().eq(colIndex);
					formatTableCellData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId, tableRecord.comments, tableRecord.startTime, tableRecord.endTime);
				}
			}
		}
	} */

/* 	function formatTableCellData(tdElement, dbId, date, displayName, employeeId, comments, startTime, endTime) {

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
	} */

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

	function deleteTableRecordClick(obj) {
		$(".my-popover-delete").popover('show');
	}

	function cancelPopoverDeleteCkick() {
		$(".my-popover-delete").popover('hide');
	}

	function getTabelData(selectedDate) {

		//find first day of week
		var momentDate = new moment(selectedDate);

		var firstDayMoment = momentDate.clone().startOf('isoWeek');
		var firstDay = moment().startOf('isoWeek').format('YYYY-MM-DD');
		var lastDay = moment().endOf('isoWeek').format('YYYY-MM-DD');

		$.ajax({
			url : "${contextPath}/schedules/subview/roosterdienst/getAllRecords/?start=" + firstDay + "&end=" + lastDay,
			type : "GET",
			success : function(data) {

				var tableData = JSON.parse(data);

				$("#main_content").html(mainMainContetOriginal)
				fillInDaysInTableHeader(firstDayMoment);
				
				var trBody_1 = $('.tb-data tbody.slaap-dienst');
				var slaapDienst = tableData.slaapDienst;
				workSchedule.getFormattedSlaapDienst(slaapDienst, trBody_1);
				

				var trBody_2 = $('.tb-data tbody.schakel-dienst');
				var schakeldienst = tableData.schakeldienst;
				workSchedule.getFormattedCustomHours(schakeldienst, trBody_2);
				
				/* fillInDienstTableData(schakeldienst, trBody); */
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error get data from ajax');
			}
		});
	}
</script>