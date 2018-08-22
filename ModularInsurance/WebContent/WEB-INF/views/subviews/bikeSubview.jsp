<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="width: 100%; text-align: center; margin-bottom: 80px;">
	<h4>Bike Insurance Form</h4>
</div>

<div class="col-md-8">
	<form id="my-form">
		<div class="form-group row">
			<label for="inputFirstName" class="col-sm-4 col-form-label">First Name</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="inputFirstName" placeholder="First Name" name="firstName">
			</div>
		</div>
		<div class="form-group row">
			<label for="inputSecondName" class="col-sm-4 col-form-label">Second Name</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="inputSecondName" placeholder="Second Name" name="secondName">
			</div>
		</div>
		<div class="form-group row">
			<label for="inputchooseType" class="col-sm-4 col-form-label">Bike Type</label>
			<div class="col-sm-8">
				<select id="inputchooseType" class="form-control" name="productType">
					<option selected>Choose...</option>
					<option value="Suzuki">Suzuki</option>
					<option value="BMW">BMW</option>
				</select>
			</div>
		</div>
		<div class="form-group row">
			<label for="inputYear" class="col-sm-4 col-form-label">Your age</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="inputYear" placeholder="Your age" name="yourAge">
			</div>
		</div>
		<div class="form-group row">
			<label for="inputOriginalPrice" class="col-sm-4 col-form-label">Price</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="inputOriginalPrice" placeholder="Original Price" name="productOriginalPrince">
			</div>
		</div>

	</form>
	<div class="input-group-append" style="float: right;">
		<button type="submit" class="btn btn-secondary" onClick="onCalculateClick()">Calculate</button>
	</div>
</div>


<div class="col-md-4" style="margin-top: -45px;">
	<h4 class="d-flex justify-content-between align-items-center mb-3">
		<span class="text-muted">Result</span>
	</h4>
	<ul class="list-group mb-3">
		<li class="list-group-item d-flex justify-content-between lh-condensed">
			<div>
				<h6 class="my-0">Insurance Prince</h6>
				<small class="text-muted" id="year-payment">Year Payment</small>
			</div>
			<span class="text-muted" id="insurance-price"></span>
		</li>
	</ul>
</div>

<script>
	//# sourceURL=bike.js
	function onCalculateClick() {

		if (validateForm()) {
			alert("Please check the form")
			return;
		}

		var formData = $('#my-form');
		var formObject = getFormData(formData)
		var jsondata = JSON.stringify(formObject)

		console.log("the message");
		$.ajax({
			url : "subview/Bike",
			type : "POST",
			dataType : "text",
			contentType : "application/json",
			data : jsondata,
			//contentType : 'application/json; charset=utf-8',
			//dataType : 'json',
			success : function(data) {

				document.getElementById("insurance-price").innerHTML = data
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Occured error while sending the data, please check the form');
			}
		});
	}

	function getFormData(rawData) {
		var temArray = rawData.serializeArray();

		  dataObj = {};

		  $(temArray).each(function(i, field){
			  dataObj[field.name] = field.value;
		  });
		
		var person = {
			firstName : dataObj["firstName"],
			secondName : dataObj["secondName"]
		};

		var result_array = {};
		result_array["person"] = person;
		
		result_array["type"] = dataObj["productType"];
		result_array["yourAge"] = dataObj["yourAge"];
		result_array["price"] = dataObj["productOriginalPrince"];
		
		return result_array;
	}

	function validateForm() {
		var empty = $("#my-form").parent().find("input, select").filter(function() {

			if (this.value == "" || this.value == "Choose...") {
				return this;
			}

			if (this.name == "yourAge" &&  parseInt(this.value) < 18) {
				return this;
			}
		});
		return empty.length > 0 ? true : false;
	}
</script>


