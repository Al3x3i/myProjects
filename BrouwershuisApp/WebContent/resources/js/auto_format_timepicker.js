function runAutoFormatTimepicker(validTimeCallBack) {

	$(document).on("blur", '*[data-timepicker]', function() {

		var element = $(this);

		var value = element.val();
		if (value.length == 0) {
			
			var res = element.data("prev");
			if(res != null){
				if (typeof validTimeCallBack !== "undefined") {
					try{
						validTimeCallBack(element);
					}catch(err){
						//alert(err);
					}
				}
			}
			return;
		}

		var times = value.split(":");
		var newValue = times.join("");

		if (newValue.length == 1)
			newValue = "0" + newValue + "00";
		if (newValue.length == 2)
			newValue = newValue + "00";
		if (newValue.length == 3)
			newValue = "0" + newValue;

		/*
		 * The first number (hours) is either: a number between 0 and 19 -->
		 * [0-1]?[0-9] (allowing single digit number) or a number between 20 -
		 * 23 --> 2[0-3]
		 */
		var regEx = "^([0-1]?[0-9]|[2][0-3])([0-5][0-9])$"; // example: 800
		// or 0800 or
		// 2359
		var isValid = false;
		if (newValue.match(regEx)) {
			var hours = newValue.slice(0, 2);
			var minutes = newValue.slice(2, 4);

			newValue = hours + ":" + minutes;
			element.data("prev",true);
			isValid = true;
		}

		if (isValid) {
			$(this).val(newValue);
			
			//execute callback
			if (typeof validTimeCallBack !== "undefined") {
				
				try{
					validTimeCallBack(element);
				}catch(err){
					//alert(err);
				}
			}
		} 

		
	});

	$('*[data-timepicker]').attr('autocomplete', 'off').keydown(function(e) {

		// Input Value var
		var inputValue = $(this).val();

		// Make sure keypress value is a Number
		if ((e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8) {

			// Make sure first value is not greater than 2
			if (inputValue.length == 0) {
				if (e.keyCode > 49) {
					e.preventDefault();
					$(this).val(2);
				}
			}

			// Make sure second value is not greater than 4
			else if (inputValue.length == 1 && e.keyCode != 8) {
				e.preventDefault();
				if (e.keyCode > 50) {
					
					var f_value = e.target.value;
					
					if(f_value == 1){
						e.target.value = f_value + e.key;
					}else{
						$(this).val(inputValue + '3:');
					}
				} else {
					$(this).val(inputValue + String.fromCharCode(e.keyCode) + ':');
				}
			}

			else if (inputValue.length == 2 && e.keyCode != 8) {
				e.preventDefault();
				if (e.keyCode > 52) {
					$(this).val(inputValue + ':5');
				} else {
					$(this).val(inputValue + ':' + String.fromCharCode(e.keyCode));
				}
			}

			// Make sure that third value is not greater than 5
			else if (inputValue.length == 3 && e.keyCode != 8) {
				if (e.keyCode > 52) {
					e.preventDefault();
					$(this).val(inputValue + '5');
				}
			}

			// Make sure only 5 Characters can be input
			else if (inputValue.length > 4 && e.keyCode != 8) {
				e.preventDefault();
				return false;
			}
		}

		// Prevent Alpha and Special Character inputs
		else {
			console.log("1");
			e.preventDefault();
			return false;
		}
	}); // End Timepicker KeyUp function

}
