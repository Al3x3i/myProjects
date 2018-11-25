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

#modal-slaap-dienst .info-group {
	display: inline-block;
}

#modal-slaap-dienst .info-group select {
	color: #000000;
	background-color: #FFF;
	border: 1px solid #aaa;
	margin: 2px;
	border-radius: 3px;
	box-shadow: 0px 1px 2px #ccc inset;
	height: 26px;
	width: 150px;
	margin-left: 15px;
}

#modal-slaap-dienst .info-group .header-line {
	height: 10px;
	background: #3385e4;
}

#modal-slaap-dienst #modal-input tr td:nth-child(1) {
	width: 40px;
}

#modal-slaap-dienst #modal-input tr td:nth-child(2) {
	width: 50px;
}

#modal-slaap-dienst #modal-input tr td:nth-child(3) {
	width: 50px;
	text-align: center;
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
				<h1 class="page-header">Slaap dienst data</h1>
			</div>
		</div>
		<!-- /.row -->
		<div class="row content-row">
			<div class="">
				<div class="panel panel-default">
					<div class="panel-heading vu-panel">
						<!--END Panel Header  -->
					</div>
					<div class="panel-body slaap-dienst">
						<div class="n-container" id="rooster-uren">
							<button class="btn btn-success" onClick="openAddSlaapDienstModal()">
								<i class="glyphicon glyphicon-plus"></i> Toevoeg dienst
							</button>
							<button class="btn btn-primary bt-my-edit disabled" onClick="openEditSlaapDienstModal()">
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
											<tr class="tr-slaap-dienst">
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
<div class="modal fade" id="modal-slaap-dienst" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Toevoeg slaap dienst</h4>
			</div>
			<div class="modal-body">
				<div class="info-group">
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
						<select id="select-employee" name="medewerker-select">
							<option value="-1">Kies medewerker</option>
						</select>
					</div>

					<i>Dienst:</i>
					<table id="modal-input" class="table">
					<thead>
							<tr>
								<th>Dag</th>
								<th>Datum</th>
								<th>Kies</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Man</td>
								<td><label id="">1</label></td>
								<td><input type="checkbox" /></td>
							</tr>
							<tr>
								<td>Din</td>
								<td><label id="">1</label></td>
								<td><input type="checkbox" /></td>
							</tr>
							<tr>
								<td>Woe</td>
								<td><label id="">1</label></td>
								<td><input type="checkbox" /></td>
							</tr>
							<tr>
								<td>Don</td>
								<td><label id="">1</label></td>
								<td><input type="checkbox" /></td>
							</tr>
							<tr>
								<td>Vri</td>
								<td><label id="">1</label></td>
								<td><input type="checkbox" /></td>
							</tr>
							<tr>
								<td>Zat</td>
								<td><label id="">1</label></td>
								<td><input type="checkbox" /></td>
							</tr>
							<tr>
								<td>Zon</td>
								<td><label id="">1</label></td>
								<td><input type="checkbox" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" id="modal-save" class="btn btn-primary" onClick="modalSlaapDienstSaveClick();">Save</button>
				<button type="button" id="modal-save-edit" class="btn btn-primary" onClick="modalSlaapDienstSaveEditClick();">Save changes</button>
			</div>
		</div>
	</div>
</div>

<div style="display: none">
	<div id="my-popover-content">
		<p class="confirmation-content" style="display: block;">This might be dangerous</p>
		<div class="confirmation-buttons text-center">
			<div class="btn-group">
				<a href="#" class="btn btn-success" onClick="confirmPopverDeleteCkick()">
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
	//# sourceURL=slaapdienst.js

	//Objects
	var viewProperties = new function() {

		this.selectedTableCell = "";
	}

	//the object which is received from back-end
	function SlaapDienstObject() {
		this.id = "";
		this.date = "";
		this.employeeId = "";
		this.displayName = "";
	}

	function SlaapDienstCellData() {
		this.date = "";
		this.employeeId = "";
	}

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

		$('.slaap-dienst .tb-data tr').on('click', '.my-edit', function(event) {
			   
			if (viewProperties.selectedTableCell != "") {
				$(viewProperties.selectedTableCell).removeClass("active");
			}

			if (event.target != viewProperties.selectedTableCell) {
				if (event.target.innerHTML != "") {

					$(event.target).addClass("active");
					$(".slaap-dienst .bt-my-edit").removeClass("disabled");
					$(".slaap-dienst .bt-my-remove").removeClass("disabled");
				} else {
					$(".slaap-dienst .bt-my-edit").addClass("disabled");
					$(".slaap-dienst .bt-my-remove").addClass("disabled");
				}
				viewProperties.selectedTableCell = event.target;
			} else {
				$(".slaap-dienst .bt-my-edit").addClass("disabled");
				$(".slaap-dienst .bt-my-remove").addClass("disabled");
				viewProperties.selectedTableCell = "";
			}
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

		
		fillInEmployeeNamesInModal();

		getTabelData(new Date());

	}

	function fillInSlaapDienstTableData(dataSet) {
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

		var cellObject = new SlaapDienstObject();
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
		fillInModalSlaapDienst(medewerkers);
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

	function fillInModalSlaapDienst(medewerkers) {

		for (var i = 0; i < medewerkers.length; i++) {
			var name = medewerkers[i].firstName[0] + ". " + medewerkers[i].lastName;
			var option = $('<option />');
			option.attr('value', medewerkers[i].DT_RowId).text(name);
			$("#select-employee").append(option);
		}
	}

	function initSlaapDienstModal() {

		var calendar = $('#n-calendar').data();
		var momentDate = new moment(calendar.datepicker.viewDate);

		var tempDay = momentDate.startOf('isoWeek')

		var inputTable = $("#modal-input tbody");
		for (var index = 0; index < inputTable.children().length; index++) {

			var trEl = inputTable.children().eq(index);

			$(trEl).data("date", tempDay.format('YYYY-MM-DD'));
			trEl.children().eq(1).text(momentDate.format("DD-MM"))

			tempDay.add(1, "days");
		}

		var year = momentDate.year();
		var week = momentDate.isoWeek();
		
		$(".info-group .year").text(year);
		$(".info-group .month").text(momentDate.format("MMMM"));
		$(".info-group .week").text(week);
	}

	function openAddSlaapDienstModal() {

		initSlaapDienstModal();

		var trRows = $('#modal-slaap-dienst #modal-input tbody tr');

		for (var index = 0; index < trRows.length; index++) {
			var el = $(trRows[index]);
			el.css("display", "table-row");

			var inputEl = el.find("input");
			inputEl.prop('checked', false);
			inputEl.prop("disabled", false);
		}
		$("#modal-slaap-dienst #modal-save").css("display", "inline-block");
		$("#modal-slaap-dienst #modal-save-edit").css("display", "none");

		$('#modal-slaap-dienst').modal('show'); // show bootstrap modal
	}

	function openEditSlaapDienstModal() {

		initSlaapDienstModal();

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var recordData = $(selectedRecord).data('sp-data');

			$.ajax({
				url : "${contextPath}/schedules/subview/slaapdienst/getRecordDetails?id=" + recordData.id,
				type : "GET",
				contentType : "application/json",
				success : function(data) {

					if (data != "undefined") {
						var momentDate = new moment(data.weekDate);
						var formattedDate = momentDate.format('YYYY-MM-DD');

						var tableRows = $("#modal-slaap-dienst #modal-input tbody tr");

						$(tableRows).each(function() {

							var element = $(this);
							var elData = element.data("date");
							if (elData == formattedDate) {

								var inputEl = element.find("input");
								if (inputEl != null) {
									inputEl.prop('checked', true);
									inputEl.prop("disabled", true);

									//set right employee in dropDown list
									var selectEmployee = $("#modal-slaap-dienst #select-employee");
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

						$("#modal-slaap-dienst #modal-save").css("display", "none");
						$("#modal-slaap-dienst #modal-save-edit").css("display", "inline-block");
						$('#modal-slaap-dienst').modal('show'); // show bootstrap modal

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

	function modalSlaapDienstSaveClick() {

		var emplployee = $("#modal-slaap-dienst #select-employee :selected");
		var employeeId = emplployee.val();
		var inputData = $("#modal-slaap-dienst #modal-input")

		var selected = [];
		$('#modal-slaap-dienst #modal-input td input:checked').each(function() {
			selected.push($(this));
		});

		if (selected.length == 0)
			return null;

		var updateItems = [];
		for (var index = 0; index < selected.length; index++) {

			var el = selected[index];
			var trEl = $(el).closest('tr')

			var updateData = new SlaapDienstCellData();
			updateData.date = trEl.data("date");
			updateData.employeeId = employeeId;

			updateItems.push(updateData);
		}

		var jsonData = JSON.stringify(updateItems);

		if (employeeId != "-1") {
			$.ajax({
				url : "${contextPath}/schedules/subview/slaapdienst/putRecord",
				type : "PUT",
				//dataType : "text",
				contentType : "application/json",
				data : jsonData,
				success : function(data) {

					if (data.status) {

						var calendar = $('#n-calendar').data();
						var selectedDate = calendar.datepicker.viewDate;

						getTabelData(selectedDate);

						$('#modal-slaap-dienst').modal('hide');
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
	}

	function modalSlaapDienstSaveEditClick() {

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var selectedEmployee = $("#modal-slaap-dienst #select-employee :selected");
			var recordData = $(selectedRecord).data('sp-data');

			if (selectedEmployee.val() == recordData.employeeId || selectedEmployee.val() < 0) {
				return;
			}

			var updateObject = new SlaapDienstObject();
			updateObject.id = recordData.id;
			updateObject.date = recordData.date;

			updateObject.displayName = selectedEmployee.text();
			updateObject.employeeId = selectedEmployee.val();

			var jsonData = JSON.stringify(updateObject);

			$.ajax({
				url : "${contextPath}/schedules/subview/slaapdienst/patchRecord",
				type : "POST",
				contentType : "application/json",
				data : jsonData,
				success : function(data) {
					if (data.status) {

						formatTableCellData($(viewProperties.selectedTableCell), updateObject.id, updateObject.date, updateObject.displayName, updateObject.employeeId);

						$(viewProperties.selectedTableCell).removeClass("active");
						$(".slaap-dienst .bt-my-edit").addClass("disabled");
						$(".slaap-dienst .bt-my-remove").addClass("disabled");

						$('#modal-slaap-dienst').modal('hide');

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

	function confirmPopverDeleteCkick() {

		var selectedRecord = viewProperties.selectedTableCell;

		if (selectedRecord != "") {

			var recordData = $(selectedRecord).data('sp-data');

			var deleteObject = new SlaapDienstObject();
			deleteObject.id = recordData.id;
			deleteObject.date = recordData.date;
			deleteObject.displayName = recordData.displayName;
			deleteObject.employeeId = recordData.employeeId;

			var jsonData = JSON.stringify(deleteObject);

			$.ajax({
				url : "${contextPath}/schedules/subview/slaapdienst/deleteRecord",
				type : "DELETE",
				//dataType : "text",
				contentType : "application/json",
				data : jsonData,
				success : function(data) {
					if (data.status) {
						$(selectedRecord).text("");
						$(selectedRecord).removeData("sp-data");

						$(viewProperties.selectedTableCell).removeClass("active");
						$(".slaap-dienst .bt-my-edit").addClass("disabled");
						$(".slaap-dienst .bt-my-remove").addClass("disabled");

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

	function cancelPopverDeleteCkick() {
		$(".my-popover-delete").popover('hide');
	}

	function getTabelData(selectedDate) {

		//find first day of week
		var momentDate = new moment(selectedDate);

		var firstDayMoment = momentDate.clone().startOf('isoWeek');
		var firstDay = moment().startOf('isoWeek').format('YYYY-MM-DD');
		var lastDay = moment().endOf('isoWeek').format('YYYY-MM-DD');

		$.ajax({
			url : "${contextPath}/schedules/subview/slaapdienst/getAllRecords/?start=" + firstDay + "&end=" + lastDay,
			type : "GET",
			success : function(data) {

				var tableData = JSON.parse(data);

				cleanContentTableRecords();
				fillInDaysInTableHeader(firstDayMoment);

				var slaapDienstData = tableData.slaapDienst; //Slaap Dienst
				fillInSlaapDienstTableData(slaapDienstData);
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