<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>



<style>
.page-sidebar {
	width: 210px;
	position: absolute;
	height: auto;
	left: 0;
	bottom: 0;
	top: 0;
	z-index: 2;
}

.page-sidebar .sidebar-inner {
	position: relative;
	width: auto;
	height: 100%;
}

#main_content {
	margin-left: 80px;
}

#main_content .title-row {
	height: 100px;
}

.modal-dialog#new_employee input.form-check-input{
	width:15px;
	height:15px;
}


</style>

<div id="main_content">
	<div class="container" id="employees">
		<div class="row title-row">
			<div class="col-lg-12">
				<h1 class="page-header">Medewerker gegevens</h1>
			</div>
		</div>

		<div class="row">

			<div class="col-xs-12">
				<div class="panel panel-default">
					<div class="panel-heading emp-panel"></div>

					<div class="panel_content">
						<br />
						<button class="btn btn-success" onclick="add_person()">
							<i class="glyphicon glyphicon-plus"></i> Add Person
						</button>
						<button class="btn btn-default" onclick="reload_table()">
							<i class="glyphicon glyphicon-refresh"></i> Reload
						</button>
						<br /> <br />
						<table id="table" class="table table-striped table-bordered" cellspacing="0" width="100%">
							<thead>
								<tr>
									<th></th>
									<th>First name</th>
									<th>Last name</th>
									<th>Address</th>
									<th>Gender</th>
									<th>Date Of Birth</th>
									<th>Is active</th>
									<th>Edit / Delete</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th></th>
									<th>First name</th>
									<th>Last name</th>
									<th>Gender</th>
									<th>Address</th>
									<th>Date Of Birth</th>
									<th>Is active</th>
									<th>Edit / Delete</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>




<script type="text/javascript">
	//# sourceURL=employees.js

	var save_method; //for save method string
	var table;
	var editor;
	$(document).ready(function() {

		var result = "";
		$.get("${pageContext.request.contextPath}/employees/getAll", function(data) {
			result = data;
			
		}); 
		
		//datatables
		table = $('#table').DataTable({
			"ajax" : {
				"url" : "${pageContext.request.contextPath}/employees/getAll",
				"type" : "GET"
			},

			"columns" : [ {
				"data" : "DT_RowId",
				"visible" : false,
			}, {
				"data" : "firstName"
			}, {
				"data" : "lastName"
			}, {
				"data" : "address"
			}, {
				"data" : "gender"
			}, {
				"data" : "dateOfBirth"
			}, {
				"data" : "employeeActive"
			}, {
				data : null,
				className : "center",
				defaultContent : '<a href="" class="editor_edit">Edit</a> / <a href="" class="editor_delete" ">Delete</a>'
			} ],
			"order" : [ [ 1, 'asc' ] ]
		}); 

		// Edit record
		$('#table').on('click', 'a.editor_edit', function(e) {
			e.preventDefault();

			//get hidden ID
			var data1 = table.row($(this).parents('tr')).data()["DT_RowId"];

			edit_person(data1);
		});

		$('#table').on('click', 'a.editor_delete', function(e) {
			e.preventDefault();

			//get hidden ID
			var data1 = table.row($(this).parents('tr')).data()["DT_RowId"];

			delete_person(data1);
		});

		//datepicker
		$('.datepicker').datepicker({
			//autoclose : true,
			format : "yyyy-mm-dd"

		//dateFormat : 'yy-mm-dd'

		//todayHighlight : true,
		//orientation : "top auto",
		//todayBtn : true,
		//todayHighlight : true,
		});

		//set input/textarea/select event when change value, remove class error and remove text help block 
		$("input").change(function() {
			$(this).parent().parent().removeClass('has-error');
			$(this).next().empty();
		});
		$("textarea").change(function() {
			$(this).parent().parent().removeClass('has-error');
			$(this).next().empty();
		});
		$("select").change(function() {
			$(this).parent().parent().removeClass('has-error');
			$(this).next().empty();
		});

		var jsonContracts = '${allContracts}';
		var contracts = JSON.parse(jsonContracts);

		//Set all contracts
		$(contracts).each(function(key, value) {
			var option = $('<option />');
			option.attr('value', value.id).text(value.name);
			$("#emp-contract").append(option);
		})
	});

	function add_person() {
		save_method = 'add';
		$('#form')[0].reset(); // reset form on modals
		$('.form-group').removeClass('has-error'); // clear error class
		$('.help-block').empty(); // clear error string
		$('#modal_form').modal('show'); // show bootstrap modal
		$('.modal-title').text('Add Person'); // Set Title to Bootstrap modal title
	}

	function edit_person(id) {
		save_method = 'update';
		$('#form')[0].reset(); // reset form on modals
		$('.form-group').removeClass('has-error'); // clear error class
		$('.help-block').empty(); // clear error string

		var url = "${pageContext.request.contextPath}/employees/getEmployee?id=" + id;
		//Ajax Load data from ajax
		$.ajax({
			//async: true,
			url : url,
			type : "GET",
			dataType : "JSON",
			success : function(data) {

				var dateOfBirth = moment(data.dateOfBirth).format('YYYY-MM-DD');
				var jsonObject = JSON.stringify(data);

				$('[name="DT_RowId"]').val(data.id);
				$('[name="firstName"]').val(data.firstName);
				$('[name="lastName"]').val(data.lastName);
				$('[name="aliasName"]').val(data.displayName);
				
				
				$('[name="gender"]').val(data.gender);
				$('[name="address"]').val(data.address);

				$('[name="dateOfBirth"]').datepicker("setDate", dateOfBirth);

				$('[name="employeeActive"]').prop('checked', data.enabled);
				
				$('[name="contract"]').val(data.contract.id)
				
				
				if (data.user != null){
					$('[name="username"]').val(data.user.username);
					
					if (data.user.roles[0].name == "ROLE_ADMIN"){
						$('[name="admin"]').prop('checked', true);
					}
					
				}

				$('#modal_form').modal('show'); // show bootstrap modal when complete loaded
				$('.modal-title').text('Edit Person'); // Set title to Bootstrap modal title

			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error get data from ajax');
			}
		});
	}

	function reload_table() {
		table.ajax.reload(null, false); //reload datatable ajax 
	}

	function save() {
		$('#btnSave').text('saving...'); //change button text
		$('#btnSave').attr('disabled', true); //set button disable 
		var url;

		if (save_method == 'add') {
			url = "${pageContext.request.contextPath}/employees/addEmployee";
		} else {
			url = "${pageContext.request.contextPath}/employees/editEmployee";
		}

		var oElements = {};
		$('#form [name]').each(function() {
			oElements[this.name] = this.value;
		});

		//delete oElements.id;
		//var formData = jQuery('#modal_form #form').serialize();
		var formData = jQuery('#modal_form #form').serialize();
		
		
		// Below function requires RequestBody and name of the serialized object template and contentType : 'application/json; charset=utf-8',
		//var oElements = {};
		//$('#form [name]').each(function() {
		//	oElements[this.name] = this.value;
		//});

		//formData = JSON.stringify(oElements)
		
		//var newDate = new Date(oElements["dateOfBirth"]);
		//oElements["dateOfBirth"] = newDate;

		/* 		var jsonObject = $('#form').serializeObject();
		//var jsonObject = JSON.stringify( $("#form").serializeArray() );
		 var userJson = JSON.stringify(jQuery('#form').serialize()); */
		// ajax adding data to database
		$.ajax({
			url : url,
			type : "POST",
			data : formData,
			//contentType : 'application/json; charset=utf-8',
			//dataType : 'json',
			success : function(data) {

				if (data.status) //if success close modal and reload ajax table
				{
					$('#modal_form').modal('hide');
					reload_table();
				} else {
					
					if (data.inputerror != 'undefined'){
						$.each(data.inputerror, function(key, value) {
							var msg = value;
							$('[name="' + key + '"]').parent().parent().addClass('has-error'); //select parent twice to select div form-group class and add has-error class
						});
					}

				}
				$('#btnSave').text('save'); //change button text
				$('#btnSave').attr('disabled', false); //set button enable 

			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error adding / update data');
				$('#btnSave').text('save'); //change button text
				$('#btnSave').attr('disabled', false); //set button enable 

			}
		});
	}

	function delete_person(id) {
		if (confirm('Are you sure delete this data?')) {
			// ajax delete data to database

			var url = "${pageContext.request.contextPath}/employees/deleteEmployee?id=" + id;

			$.ajax({
				url : url,
				type : "POST",
				//dataType : "JSON",
				success : function(data) {
					//if success reload ajax table

					if (data.status) {
						$('#modal_form').modal('hide');
						reload_table();
					} else {
						alert('Error deleting data');
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('Error deleting data');
				}
			});

		}
	}
</script>

<!-- Bootstrap modal -->
<div class="modal fade" id="modal_form" role="dialog">
	<div class="modal-dialog" id="new_employee">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title">Person Form</h3>
			</div>
			<div class="modal-body form">
				<form action="#" id="form" modelAttribute="userForm" class="form-horizontal">
				
					<form:errors path="username"></form:errors>
				
					<input type="hidden" value="" name="DT_RowId" />
					<div class="form-body">
						<div class="form-group">
							<label class="control-label col-md-3">First Name</label>
							<div class="col-md-9">
								<input name="firstName" placeholder="First Name" class="form-control" type="text"> <span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Last Name</label>
							<div class="col-md-9">
								<input name="lastName" placeholder="Last Name" class="form-control" type="text"> <span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Alias Name</label>
							<div class="col-md-9">
								<input name="aliasName" placeholder="Alias Name" class="form-control" type="text"> <span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Gender</label>
							<div class="col-md-9">
								<select name="gender" class="form-control">
									<option value="">--Select Gender--</option>
									<option value="Male">Male</option>
									<option value="Female">Female</option>
								</select>
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Address</label>
							<div class="col-md-9">
								<textarea name="address" placeholder="Address" class="form-control"></textarea>
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Date of Birth</label>
							<div class="col-md-9">
								<input name="dateOfBirth" placeholder="yyyy-mm-dd" class="form-control datepicker" type="text"> <span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="form-check-label col-md-3 text-right">Is Active</label>
							<div class="col-md-9">
								<input name="employeeActive" placeholder="Is Active" class="form-check-input" type="checkbox" value=true checked="checked"> <span class="help-block"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3">Employee contract</label>
							<div class="col-md-9">
								<select name="contract" class="form-control" id="emp-contract">
								</select>
								<span class="help-block"></span>
							</div>
						</div>

						<hr />
						<h2 class="form-signin-heading">Create employee account</h2>

						<div class="form-group">
							<label class="form-check-label col-md-3 text-right">Is Admin</label>
							<div class="col-md-9">
								<input name="admin" placeholder="Is Admin" class="form-check-input" type="checkbox" value=true> <span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Username</label>
							<div class="col-md-9">
								<input name="username" placeholder="Username" class="form-control" type="text"> <span class="help-block"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3">Password</label>
							<div class="col-md-9">
								<input name="password" placeholder="Password" class="form-control" type="password"> <span class="help-block"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3">Confirm password</label>
							<div class="col-md-9">
								<input name="confirmPassword" placeholder="Confirm password" class="form-control" type="password"> <span class="help-block"></span>
							</div>
						</div>

					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="btnSave" onclick="save()" class="btn btn-primary">Save</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- End Bootstrap modal -->