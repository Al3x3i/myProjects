<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="imagePath" value="${pageContext.request.contextPath}/resources/img" />

<style>
/* @media print {
	body * {
		visibility: hidden;
	}
	body #main_content {
		visibility: visible;
	}
} */

#vak_year {
	color: darkorange;
}

#vakantie-uren table {
	width: 450px;
	text-align: center;
	float: left;
}

#vakantie-uren table:FIRST-CHILD {
	margin-right: 20px;
}

#vakantie-uren table th {
	text-align: center;
}

<!--
Day name width -->#vakantie-uren table th:FIRST-CHILD {
	width: 80px;
}

#vakantie-uren .tr-total {
	height: 30px;
	paddding: 1px;
}

#vakantie-uren .tr-werken {
	text-align: left;
	font-weight: bold;
	height: 30px;
}

#vakantie-uren .tr-vakantie-uren {
	text-align: left;
	font-weight: bold;
	height: 30px;
}

#vakantie-uren .total-header {
	width: 50px;
	font-weight: bold;
}

#vakantie-uren .tb-hours td {
	margin: 0;
	padding: 0;
	vertical-align: middle;
}

#vakantie-uren .tb-hours input {
	margin: 0;
	padding: 0;
	border-width: 0px;
	height: 30px;
	text-align: center;
}

#vakantie-uren .tb-hours .tr-werken span {
	margin-left: 20px;
	color: blue;
}

#vakantie-uren .tb-hours .tr-werken .te-werken-input {
	/*   background: antiquewhite; */
	width: 80px;
	display: inline;
	margin-left: 5px;
	outline: 0;
	border-width: 0 0 2px 0;
	border-color: blue;
}

#vakantie-uren .tb-hours .tr-vakantie-uren span {
	margin-left: 20px;
	color: blue;
}

#vakantie-uren .tb-hours [class^="tb-total-willwork"] .te-werken-input:focus
	{
	border-color: green
}

.emp-name {
	width: 100%;
	text-align: right;
	padding-right: 15px;
}

.panel-heading.vu-panel {
	width: 100%;
	padding: 0 0 0 20px;
	color: #ffdb99;
	font-weight: bold;
}

#vakantie-uren .invalid-time {
	background: red;
}

#vakantie-uren .invalid-week {
	visibility: hidden;
}

.vu-control-col {
	margin-bottom: 5px;
}

.vu-control-col-y1 {
	width: 100px;
	display: inline-block;
}

.vu-control-col-y2 {
	display: inline-block;
}

.total-details p {
	font-size: 1.2em;
	font-weight: bold;
}

#vakantie-uren .extraTable-row {
	display: none;
}

#main_content {
	padding-left: 270px;
	display: inline-block;
}

#main_content .title-row {
	height: 100px;
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
			</br>
			<div class="total-details">
				<p>
					Totale werkuren: <span id="group-total-workinghours"></span>
				</p>
				<p>
					Totaal vakantie-uren: <span id="group-total-vacationHours"></span>
				</p>
				<p>
					Totaal verschil: <span id="group-total-differeceHours"></span>
				</p>
			</div>
			<br>
		</div>
	</div>
</aside>


<div id="main_content">


	<div class="" id="printableArea">

		<!-- Page Heading -->
		<div class="row title-row">
			<div class="">
				<h1 class="page-header">
					Vakantie kaart <span id="vak_year">asd</span>
				</h1>
			</div>
		</div>
		<!-- /.row -->

		<input type="button" onclick="printDiv('main_content')" value="print a div!" /> 

		<div class="row">
			<div class="">
				<div class="panel panel-default">
					<div class="panel-heading vu-panel"></div>

					<div class="panel-body">

						<div class="vu-control">

							<div class="vu-control-col">
								<div class="vu-control-col-y1">
									<label class="emp-name">Naam: </label>
								</div>

								<div class="vu-control-col-y2">
									<select name="employee" class="form-control" id="dropEmployees" onchange="btnEmployeeSelected(this)">
										<option value="-1">Select Employee</option>
									</select>
								</div>
							</div>

							<div class="vu-control-col">
								<div class="vu-control-col-y1">
									<label class="emp-name">Week: </label>
								</div>
								<div class="vu-control-col-y2">
									<button type="button" class="btn btn-info active" value="1" onclick="btnWeekRangeClick(this)">First part</button>
									<button type="button" class="btn btn-info" value="2" onclick="btnWeekRangeClick(this)">Second part</button>
								</div>
							</div>

							<div class="vu-control-col">
								<div class="vu-control-col-y1">
									<label class="emp-name">Action </label>
								</div>
								<div class="vu-control-col-y2">
									<button type="button" class="btn btn-primary" onclick="btnSaveTableDataClick()">Save table data</button>
								</div>
							</div>
							<span class="help-block"></span>
						</div>

						<div class="n-container" id="vakantie-uren">

							<c:set var="alphabet" value="${fn:split('ZO,MA,DI,WOE,DO,FR,ZA', ',')}" scope="application" />

							<c:set var="tb_count" value="0" scope="page" />

							<c:forEach var="x" begin="1" end="4" varStatus="loop_1">
								<c:set var="tb_count" value="${tb_count + 1}" scope="page" />

								<c:if test="${loop_1.index==4}">
									<c:set value="extraTable-row" var="extraTableRow" />
									<c:set value="extraTable" var="extraClass" />
								</c:if>

								<div class="row ${extraTableRow}">
									<div class="col-md-12">
										<c:forEach var="rowTableCounter" begin="0" end="1" varStatus="loop_2">

											<table id="tb-${tb_count}" class="table table-bordered ${extraClass}">
												<thead>
													<tr>
														<th></th>
														<th id="" colspan="2"></th>
														<th id="" colspan="2"></th>
														<th id="" colspan="2"></th>
														<th id="" colspan="2">&nbsp;</th>
													</tr>
													<tr>
														<c:forEach var="x" begin="1" end="9">
															<c:choose>
																<c:when test="${x == 1}">
																	<td></td>
																</c:when>
																<c:when test="${x != 1 && x % 2 ==0}">
																	<td>G</td>
																</c:when>
																<c:when test="${x != 1 && x % 2 ==1}">
																	<td>V</td>
																</c:when>
															</c:choose>
														</c:forEach>
													</tr>
												</thead>

												<tbody class="tb-hours">

													<c:forEach var="x" begin="0" end="6">
														<tr class="tr-hours">

															<c:set var="col_count" value="0" scope="page" />

															<c:forEach var="i" begin="1" end="9">

																<c:if test="${i!= 1 && i % 2 == 0}">
																	<c:set var="col_count" value="${col_count + 1}" scope="page" />
																</c:if>

																<td><c:if test="${i == 1}">
																		<!-- DayName cell-->
																		<c:out value="${alphabet[x]}" />
																	</c:if> <c:if test="${i > 1}">
																		<!-- Hours input cell-->
																		<c:choose>
																			<c:when test="${ i % 2 ==0}">
																				<input class="h-record timepicker form-control work-hours col-${col_count} " />
																			</c:when>
																			<c:when test="${ i % 2 ==1}">
																				<input class="h-record timepicker form-control vacation-hours col-${col_count}" />
																			</c:when>
																		</c:choose>
																	</c:if></td>
															</c:forEach>
														</tr>
													</c:forEach>

													<tr class="tr-total">
														<td class="total-header">Total:</td>
														<td class="col-total-hours"></td>
														<td class="col-total-hours"></td>
														<td class="col-total-hours"></td>
														<td class="col-total-hours"></td>
														<td class="col-total-hours"></td>
														<td class="col-total-hours"></td>
														<td class="col-total-hours"></td>
														<td class="col-total-hours"></td>
													</tr>
													<tr class="tr-werken">
														<td></td>
														<td colspan="4">
															<div class="tb-total-willwork">
																Te werken: <input id="tb-total-willwork-${tb_count}" type="text" class="form-control te-werken-input toTime timepicker">
															</div>
														</td>
														<td colspan="4">Gewerkt:<span id="tb-total-worked-${tb_count}"></<span></td>
													</tr>
													<tr class="tr-vakantie-uren">
														<td></td>
														<td colspan="4">Vakantie-uren:<span id="tb-total-vacation-${tb_count}"></<span></td>
														<td colspan="4">Verschil:<span id="tb-total-difference-${tb_count}"></<span></td>
													</tr>
												</tbody>
											</table>

											<c:if test="${loop_2.index==0}">
												<c:set var="tb_count" value="${tb_count + 1}" scope="page" />
											</c:if>

										</c:forEach>
									</div>
								</div>


							</c:forEach>

						</div>

					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
	</div>
</div>


<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
	<script>
		var adminUser = true;
	</script>
</sec:authorize>


<script type="text/javascript">
	//# sourceURL=declareHours.js

	function printDiv(divName) {
		var prtContent  = document.getElementsByTagName("html")
		var WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');
		WinPrint.document.write(prtContent[0].innerHTML);
		
		
		/*
		var DocumentContainer = document.getElementById(divName);

		var styles = document.getElementsByTagName('style');

		var originalContents = document.body.innerHTML;

		var WindowObject = window.open('', "PrintWindow", "width=750,height=650,top=50,left=50,toolbars=no,scrollbars=yes,status=no,resizable=yes");

		var css = "<style> #vakantie-uren table { width: 450px; text-align: center; float: left;} </style>"

		var style = "<style>"
		for (var x = 0; x < styles.length; x++) {
			style += styles[x].innerHTML;
		}
		style += "</style>"

		var css = [];

		for (var sheeti = 0; sheeti < document.styleSheets.length; sheeti++) {
			var sheet = document.styleSheets[sheeti];
			var rules = ('cssRules' in sheet) ? sheet.cssRules : sheet.rules;
			for (var rulei = 0; rulei < rules.length; rulei++) {
				var rule = rules[rulei];
				if ('cssText' in rule)
					css.push(rule.cssText);
				else
					css.push(rule.selectorText + ' {\n' + rule.style.cssText + '\n}\n');
			}
		}

		WindowObject.document.writeln(style);

		WindowObject.document.writeln(DocumentContainer.innerHTML);

		WindowObject.document.close();
		WindowObject.focus();
		WindowObject.print();
		WindowObject.close();
		*/
	}

	var vakantieCalendarObject = new VakantieCalendar();

	//Singleton ViewModel object
	var viewProperties = new function() {
		this.weekRange = 1;
		this.userId = "";
		this.totalWorkedHours = "";
		this.totalVacationHours = "";
		this.tableData = "";

		this.getActiveTableCount = function() {
			if (this.weekRange == 1) {
				return 6;
			} else {
				return 8;
			}
		}

		this.reset = function() {
			this.weekRange = "";
			this.userId = "";
		}
	};

	function UpdateData() {
		this.employeeID;
		this.hoursCellData = [];
		this.contractHoursData = [];
	}

	function HoursCellData() {
		this.id;
		this.date;
		this.hours;
		this.hoursType;
	}

	function ContractHoursData() {
		this.id;
		this.startDate;
		this.endDate;
		this.fixedTime;
	}

	//functions
	$(document).ready(function() {
		loadDecareHoursDefaultProperties(calculateVacationHours, calendarClickCallBack);

		var data = {}
		data.value = viewProperties.employeeId;
		btnEmployeeSelected(data)
	});
	
	
	function calendarClickCallBack(){
		removeAllInputsFromWorkingHoursTable();
		refreshVacationTable();
	}

	//#Button clicks Handlers
	//Button Week range clicked
	function btnWeekRangeClick(buttonObject) {

		var divGroup = $(".vu-panel-weekControl");
		divGroup.removeClass(".vu-panel-weekControl");
		divGroup.find("button").each(function(key, value) {
			value.classList.remove("active");
		});

		buttonObject.classList.add("active");

		viewProperties.weekRange = buttonObject.value;

		if (viewProperties.weekRange == 1) {
			hideExtraTables();
			updateWeekTitlesInTable_FirstSection();
		} else if (viewProperties.weekRange == 2) {
			showExtraTables();
			updateWeekTitlesInTable_SecondSection();
		}

		if (viewProperties.tableData != "") {
			fillVacationTableWithData(viewProperties.tableData, true);
		}
		
		calculateVacationHours();
	}

	//Button Drop down changed
	function btnEmployeeSelected(element) {
		viewProperties.employeeId = element.value;

		removeAllInputsFromWorkingHoursTable()
		if (viewProperties.employeeId == -1) {
			calculateVacationHours();
		} else {
			refreshVacationTable();
		}
	}

	function removeAllInputsFromWorkingHoursTable() {

		var tablesGroup = $("#vakantie-uren table");

		var tables;
		if (viewProperties.weekRange == 1) {
			tables = tablesGroup.not("[class*=extraTable]").find(".tb-hours");
		} else {
			tables = tablesGroup.find(".tb-hours");
		}

		var allWorkHoursInput = tables.find("input.work-hours");
		var allVacationHoursInput = tables.find("input.vacation-hours");

		cleanWorkingHoursTableData(allWorkHoursInput);
		cleanWorkingHoursTableData(allVacationHoursInput);

	}

	function refreshVacationTable() {

		var empId = viewProperties.employeeId;

		if (empId == null || empId == '') {
			return;
		}

		var calendar = $('#n-calendar').data();
		var date = calendar.datepicker.viewDate;
		var year = date.getFullYear();

		var firstDay = getFirstDayOfFirstWeek(year);
		var lastDay = getLastDayOfLastWeek(year);

		var beginDate = firstDay.format('YYYY-MM-DD')
		var endDate = lastDay.format('YYYY-MM-DD')

		$.ajax({
			url : "${pageContext.request.contextPath}/working_hours/getTableData?empId=" + empId + "&start=" + beginDate + "&end=" + endDate,
			type : "GET",
			dataType : "JSON",
			success : function(data) {
				fillVacationTableWithData(data, true);

				//if while updating didn,t occure any issue then keep the data
				viewProperties.tableData = data;
				calculateVacationHours();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error get data from ajax');
			}
		});
	}

	function getFirstDayOfFirstWeek(year) {
		var momentDate = new moment(year + "-01-01", moment.ISO_8601);
		var weekDayNumber = momentDate.day();

		var firstDay = "";
		if (weekDayNumber > 4) {
			firstDay = momentDate.add(7 - weekDayNumber, 'day');
		} else {
			firstDay = momentDate.add(-weekDayNumber, 'day');
		}

		return firstDay;
	}

	function getLastDayOfLastWeek(year) {
		var momentDate = new moment(year + "-12-31", moment.ISO_8601);
		var weekDayNumber = momentDate.day();

		var lastDay = "";
		if (weekDayNumber >= 4) {
			lastDay = momentDate.add(7 - weekDayNumber - 1, 'day');

		} else {
			lastDay = momentDate.add(-weekDayNumber - 1, 'day');

		}
		return lastDay;
	}

	//Button calculate working hours
	function calculateVacationHours() {

		var tablesGroup = $("#vakantie-uren");
		var tables = tablesGroup.find("table");

		//table loop
		for (var tableCounter = 1; tableCounter <= tables.length; tableCounter++) {

			var tableObject = $("#tb-" + tableCounter);
			calculateTableHours(tableObject);
		}

		//summarize all Working hours
		var totalHoursTags = $('#vakantie-uren [id *="tb-total-worked-"]')
		var totalWorkedHours = calculateTotalHours(totalHoursTags);
		$('#group-total-workinghours').text(totalWorkedHours);

		//summarize all Vacation hours
		var totalVacationTags = $('#vakantie-uren [id *="tb-total-vacation-"]')
		var totalVacationHours = calculateTotalHours(totalVacationTags);
		$('#group-total-vacationHours').text(totalVacationHours);

		//summarize all Difference hours
		var totalDifferenceTags = $('#vakantie-uren [id *="tb-total-difference-"]')
		var totalDifferenceHours = calculateTotalHours(totalDifferenceTags);
		$('#group-total-differeceHours').text(totalDifferenceHours);

	}

	//Button Save data clicked
	function btnSaveTableDataClick() {

		if (viewProperties.employeeId > 0) {

			var updateData = new UpdateData();
			updateData.employeeId = $("#dropEmployees :selected").val();

			var tables = $("#vakantie-uren");
			var updatedInputs = tables.find(".time-updated");

			//Loop #1
			var hoursCells = updatedInputs.filter(".h-record");
			hoursCells.each(function(key, value) {
				var cellData = $(value).data('wh-data');
				cellData.hours = value.value;
				updateData.hoursCellData.push(cellData);
			});

			//Loop #2
			var teWerkenInput = updatedInputs.filter(".te-werken-input");
			teWerkenInput.each(function(key, value) {
				var inputData = $(value).data('start_end_date');
				inputData.fixedTime = value.value;
				updateData.contractHoursData.push(inputData);
			});

			if (updateData.contractHoursData.length != 0 || updateData.hoursCellData.length != 0) {
				var jsonData = JSON.stringify(updateData);

				$.ajax({
					url : "${pageContext.request.contextPath}/working_hours/saveTableData",
					type : "POST",
					dataType : "text",
					contentType : "application/json",
					data : jsonData,
					success : function(data) {
						refreshVacationTable();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert('Error get data from ajax');
					}
				});
			}
		}
	}

	//#Fill UI
	function fillEmployeesDropDown() {

		var jsonObject = '${allEmployees}';
		var parsedObject = JSON.parse(jsonObject);

		$.each(parsedObject.Employees, function(key, value) {

			var name = value.firstName[0] + ". " + value.lastName;
			var option = $('<option />');
			option.attr('value', value.DT_RowId).text(name);
			$("#dropEmployees").append(option);
		})

		if (parsedObject.Employees.length != 0) {
			$("#dropEmployees").val(1)
		}
		viewProperties.employeeId = $("#dropEmployees :selected").val();

	}

	//Fill in Vacation table with data
	function fillVacationTableWithData(tableData, removeUpdatedClass) {

		//first part
		if (viewProperties.weekRange == 1) {
			hideExtraTables();
			updateWeekTitlesInTable_FirstSection();
		} else if (viewProperties.weekRange == 2) {
			showExtraTables();
			updateWeekTitlesInTable_SecondSection();
		}

		var contractHours = tableData.contractHours;

		var tablesGroup = $("#vakantie-uren table");

		var tables;
		if (viewProperties.weekRange == 1) {
			tables = tablesGroup.not("[class*=extraTable]").find(".tb-hours");
		} else {
			tables = tablesGroup.find(".tb-hours");
		}

		for (var t = 0; t < tables.length; t++) {

			var inputTag = $(tables[t]).find(".te-werken-input");

			if (typeof adminUser === 'undefined')
				inputTag.attr("disabled", true);

			var start_end_date = inputTag.data('start_end_date');

			inputTag.removeClass("filled");

			if (typeof start_end_date !== "undefined") {

				for (var x = 0; x < contractHours.length; x++) {

					if (contractHours[x].startDate == start_end_date.startDate) {
						inputTag.addClass("filled");
						inputTag.removeData('start_end_date');
						inputTag.data('start_end_date', contractHours[x]);
						inputTag.val(contractHours[x].fixedTime);
						break;
					}
				}
			}
		}

		var allWorkHoursInput = tables.find("input.work-hours");
		var allVacationHoursInput = tables.find("input.vacation-hours");

		fillCellWithData(tableData.workingCells, allWorkHoursInput);
		fillCellWithData(tableData.vacationCells, allVacationHoursInput);

		function fillCellWithData(hoursData, inputTags) {

			for (var index = 0; index < inputTags.length; index++) {

				var tag = $(inputTags[index]);

				if (removeUpdatedClass == true) {
					tag.removeClass("filled");
					tag.removeClass("time-updated");
					tag.removeClass("valid-time");
				}

				var inputData = $(inputTags[index]).data('wh-data');

				for (var counter = 0; counter < hoursData.length; counter++) {
					var temp = hoursData[counter];

					if (inputData.date == temp.date && inputData.hoursType == temp.hoursType) {
						var inputTag = $(inputTags[index]);
						inputTag.addClass("filled");
						var inputData = inputTag.data('wh-data');
						inputData.hours = temp.hours;
						inputData.id = temp.id;
						$(inputTags[index]).val(temp.hours);
						break;
					}
				}
			}
		}
	}

	function showExtraTables() {
		var rowTables = $(".extraTable-row").show();
	}

	function hideExtraTables() {
		var rowTables = $(".extraTable-row").css("display", "");
	}

	function cleanWorkingHoursTableData(inputTags) {

		for (var index = 0; index < inputTags.length; index++) {

			var tag = $(inputTags[index]);

			tag.removeClass("filled");
			tag.removeClass("time-updated");
			tag.removeClass("valid-time");

			//first remove previous and copy only date and hours type
			var inputData = tag.data('wh-data');
			tag.removeData()

			var w_cellData = new HoursCellData();
			w_cellData.date = inputData.date;
			w_cellData.hoursType = inputData.hoursType;

			tag.data('wh-data', w_cellData);
			tag.val("");
		}

	}

</script>
