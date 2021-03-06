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

#modal-dienst .info-group.table-responsive {
	width: 100%;
}

#modal-dienst .info-group select {
	color: #000000;
	background-color: #FFF;
	border: 1px solid #aaa;
	margin: 2px;
	border-radius: 3px;
	box-shadow: 0px 1px 2px #ccc inset;
}

#modal-dienst .info-group #select-employee {
	width: 150px;
	margin-left: 15px;
}


#modal-dienst .info-group .header-line {
	height: 10px;
	background: #3385e4;
}



/*
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
*/
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
				<h1 class="page-header">VVV dienst</h1>
			</div>
		</div>
		<!-- /.row -->
		<div class="row content-row">
			<div class="">
				<div class="panel panel-default">
					<div class="panel-heading vu-panel">
						<!--END Panel Header  -->
					</div>
					<div class="panel-body agenda-dienst">
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
										<tbody>
											<tr class="tr-agenda-dienst">
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
								<td style="vertical-align: middle;">
									<select id="modal-day-selector">
										<option value="0">Man</option>
										<option value="1">Din</option>
										<option value="2">Woe</option>
										<option value="3">Don</option>
										<option value="4">Vri</option>
										<option value="5">Zat</option>
										<option value="6">Zon</option>
									</select>
								</td>
								<td style="vertical-align: middle;"><label id="modal-selected-date" class="align-middle" >00-00</label></td>
								<td><input class="tb-input form-control timepicker" type="text" name="start" value="" placeholder="12:00" data-timepicker /></td>
								<td><input class="tb-input form-control timepicker" type="text" name="end" value="" placeholder="12:00" data-timepicker /></td>
								<td><label id="time-range"></label></td>
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
				<a href="#" class="btn btn-danger" onClick="cancelPopverDeleteCkick()">
					<i class="glyphicon glyphicon-ban-circle"></i> Stoooop!
				</a>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	//# sourceURL=vvvdienst.js

	//Objects
	var viewProperties = new function() {

		this.selectedTableCell = "";
	}

	//the object which is received from back-end
	function AgendaDienstObject() {
		this.id = "";
		this.date = "";
		this.employeeId = "";
		this.displayName = "";
	}

	function AgendaDienstCellData() {
		this.date = "";
		this.employeeId = "";
	}

	//functions
	$(document).ready(function() {

		initDefault();
		
		loadDefaultProperties();
	})
	
	function initDefault()
	{
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

		$('.agenda-dienst .tb-data tr').on('click', '.my-edit', function(event) {
			   
			if (viewProperties.selectedTableCell != "") {
				$(viewProperties.selectedTableCell).removeClass("active");
			}

			if (event.target != viewProperties.selectedTableCell) {
				if (event.target.innerHTML != "") {

					$(event.target).addClass("active");
					$(".agenda-dienst .bt-my-edit").removeClass("disabled");
					$(".agenda-dienst .bt-my-remove").removeClass("disabled");
				} else {
					$(".agenda-dienst .bt-my-edit").addClass("disabled");
					$(".agenda-dienst .bt-my-remove").addClass("disabled");
				}
				viewProperties.selectedTableCell = event.target;
			} else {
				$(".agenda-dienst .bt-my-edit").addClass("disabled");
				$(".agenda-dienst .bt-my-remove").addClass("disabled");
				viewProperties.selectedTableCell = "";
			}
		});
		
		new inputTimeHanlder(function()
		{
			//This is call back handler, when any data is updated in the in "Begin" and "End" cell, this function will be called
			var startTime = $(".timepicker[name='start']").val()
			var endTime = $(".timepicker[name='end']").val()
			
			if(startTime.length == 0 || endTime.length == 0)
			{
				return;
			}
			
			var formattedTime = angedaViewObject.getStartEndTimeRange(startTime, endTime)
			
			$("#time-range").val(formattedTime)
			$("#time-range").text(formattedTime)
		});
	}

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
		
		$("#modal-day-selector").change(function() {
			var selectedIndex = $("#modal-day-selector :selected").index()
			var calendar = $('#n-calendar').data();
		 	var momentDate = new moment(calendar.datepicker.viewDate);
		 	momentDate.add(selectedIndex, "days");
			setDayInModalinTable(momentDate)
		});
		
		//Highlight first week
		highlightSelectedWeekInCalendar(".today.day");

		fillInEmployeeNamesInModal();
	
		// Get data from DB
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
						formatTableCellData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId);
						isUpdated = true;
						break;
					}
				}

				if (isUpdated == false) {
					var rowCopy = etalonRowClone.clone(true);
					trBody.append(rowCopy);
					var lastRow = trBody.find("tr:last");
					var tdEl = lastRow.children().eq(colIndex);
					formatTableCellData(tdEl, tableRecord.id, tableRecord.date, tableRecord.displayName, tableRecord.employeeId);
				}
			}
		}
	}

	function formatTableCellData(tdElement, dbId, date, displayName, employeeId) {

		var cellObject = new AgendaDienstObject();
		cellObject.id = dbId;
		cellObject.date = date;
		cellObject.displayName = displayName;
		cellObject.employeeId = employeeId;

		tdElement.data('sp-data', cellObject);
		tdElement.text(displayName + " +2");
	}

	function fillInEmployeeNamesInModal() {
		var jsonObject = JSON.parse('${allEmployees}');
		var medewerkers = jsonObject.medewerkers;

		//fill in slaap dienst modal with data
		fillInModalAgendaDienst(medewerkers);
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

	function fillInModalAgendaDienst(medewerkers) {

		for (var i = 0; i < medewerkers.length; i++) {
			var name = medewerkers[i].firstName[0] + ". " + medewerkers[i].lastName;
			var option = $('<option />');
			option.attr('value', medewerkers[i].DT_RowId).text(name);
			$("#select-employee").append(option);
		}
	}

	function initDienstModal() {

		var calendar = $('#n-calendar').data();
		var momentDate = new moment(calendar.datepicker.viewDate);

		var year = momentDate.year();
		var week = momentDate.isoWeek();
		
		$(".info-group .year").text(year);
		$(".info-group .month").text(momentDate.format("MMMM"));
		$(".info-group .week").text(week);
		
		setDayInModalinTable(momentDate)
	}
	
	function setDayInModalinTable(momentDate)
	{
		var inputTable = $("#modal-input tbody");
		
		// Select the date of fist day
		var trEl = inputTable.children().eq(0);
		$(trEl).data("date", momentDate.format('YYYY-MM-DD'));
		trEl.children().eq(1).text(momentDate.format("DD-MM"))
	}

	function openAddDienstModal() {

		initDienstModal();

		var trRows = $('#modal-dienst #modal-input tbody tr');

		for (var index = 0; index < trRows.length; index++) {
			var el = $(trRows[index]);
			el.css("display", "table-row");

			var inputEl = el.find("input");
			inputEl.prop('checked', false);
			inputEl.prop("disabled", false);
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

			modalDienstGetRecordDetails
			
			angedaViewObject.getRecordDetailsCallBack(data)
			{
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
								inputEl.prop('checked', true);
								inputEl.prop("disabled", true);

								//set right employee in dropDown list
								var selectEmployee = $("#modal-dienst #select-employee");
								selectEmployee.children().each(function() {

									var optionValue = $(this).val();
									if (data.employee.id == optionValue) {
										$(this).prop("selected", true);
									}
								});
							}
						} else {
							//hide unused rows
							element.css("display", "none");
						}
					});

					$("#modal-dienst #modal-save").css("display", "none");
					$("#modal-dienstt #modal-save-edit").css("display", "inline-block");
					$('#modal-dienst').modal('show'); // show bootstrap modal

				} else {
					alert('Data cannot be retrieved!');
				}
			}
		}
	}

	function modalDienstSaveClick() {

		var emplployee = $("#modal-dienst #select-employee :selected");
		var employeeId = emplployee.val();
		var inputData = $("#modal-dienst #modal-input")

		var selected = [];
		$('#modal-dienst #modal-input td input:checked').each(function() {
			selected.push($(this));
		});

		if (selected.length == 0)
			return null;

		var updateItems = [];
		for (var index = 0; index < selected.length; index++) {
			var el = selected[index];
			var trEl = $(el).closest('tr')

			var updateData = new AgendaDienstCellData();
			updateData.date = trEl.data("date");
			updateData.employeeId = employeeId;

			updateItems.push(updateData);
		}

		var jsonData = JSON.stringify(updateItems);

		angedaViewObject.modalDienstSaveClickHandler("vvvdienst", jsonData, saveCallBack)

		function saveCallBack(requestStatus)
		{
			if (requestStatus) {

				var calendar = $('#n-calendar').data();
				var selectedDate = calendar.datepicker.viewDate;

				getTabelData(selectedDate);

				$('#modal-dienst').modal('hide');
			} else {
				alert('Data was not saved');
			}
		}
	}

	function modalSlaapDienstSaveEditClick() {

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var selectedEmployee = $("#modal-dienst #select-employee :selected");
			var recordData = $(selectedRecord).data('sp-data');

			if (selectedEmployee.val() == recordData.employeeId || selectedEmployee.val() < 0) {
				return;
			}

			var updateObject = new AgendaDienstObject();
			updateObject.id = recordData.id;
			updateObject.date = recordData.date;

			updateObject.displayName = selectedEmployee.text();
			updateObject.employeeId = selectedEmployee.val();

			var jsonData = JSON.stringify(updateObject);
			
			function saveEditCallBack(status)
			{
				if (data.status) {

					formatTableCellData($(viewProperties.selectedTableCell), updateObject.id, updateObject.date, updateObject.displayName, updateObject.employeeId);

					$(viewProperties.selectedTableCell).removeClass("active");
					$(".agenda-dienst .bt-my-edit").addClass("disabled");
					$(".agenda-dienst .bt-my-remove").addClass("disabled");

					$('#modal-dienst').modal('hide');

					viewProperties.selectedTableCell = "";
				} else {
					alert('Data was not saved');
				}
			}
		}
	}

	function confirmPopoverDeleteCkick() {

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var recordData = $(selectedRecord).data('sp-data');

			var deleteObject = new AgendaDienstObject();
			deleteObject.id = recordData.id;
			deleteObject.date = recordData.date;
			deleteObject.displayName = recordData.displayName;
			deleteObject.employeeId = recordData.employeeId;

			var jsonData = JSON.stringify(deleteObject);

			angedaViewObject.modalDienstDeleteClickHandler("vvvdienst", jsonData, deleteCallBack)
			
			function deleteCallBack(data)
			{
				if (data.status) {
					$(selectedRecord).text("");
					$(selectedRecord).removeData("sp-data");

					$(viewProperties.selectedTableCell).removeClass("active");
					$(".agenda-dienst .bt-my-edit").addClass("disabled");
					$(".agenda-dienst .bt-my-remove").addClass("disabled");

					$(".my-popover-delete").popover('hide');

					viewProperties.selectedTableCell = "";
				} else {
					alert('Data was not saved');
				}
			}
		}
	}

	function deleteTableRecordClick(obj) {
		$(".my-popover-delete").popover('show');
	}

	function cancelPopverDeleteCkick() {
		$(".my-popover-delete").popover('hide');
	}

	function getTabelData(selectedDate) {

		//find first day of week
		var momentDate = new moment(selectedDate);

		var firstDayMoment = momentDate.clone().startOf('isoWeek');
		var firstDay = moment().startOf('isoWeek').format('YYYY-MM-DD');
		var lastDay = moment().endOf('isoWeek').format('YYYY-MM-DD');

		angedaViewObject.modalDienstGetAllClickHandler("vvvdienst", firstDay, lastDay)

		function deleteCallBack()
		{
			var tableData = JSON.parse(data);

			cleanContentTableRecords();
			fillInDaysInTableHeader(firstDayMoment);

			var data = tableData.vvvdienst; //VVV Dienst
			fillInDienstTableData(data);
		}
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