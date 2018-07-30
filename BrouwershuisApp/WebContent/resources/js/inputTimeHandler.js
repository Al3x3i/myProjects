function inputTimeHanlder(callBack) {

	var timeChangeCallBack = callBack;
	
	(function run() {

		$(document).on("keyup", ".timepicker", function() {
			replaceDotByColon(this);
		});

		$(document).on("keydown", ".timepicker", function(event) {
			if (event.keyCode == 13)// enter
			{
				var isCustom = $(this).hasClass("te-werken-input");
				formatTime(this, $(this).hasClass("timepicker-negative"),isCustom);
				
				if (timeChangeCallBack && typeof(timeChangeCallBack) == "function"){
					timeChangeCallBack()
				}
			}
		});
		
		$(document).on("blur", ".timepicker", function() {
			// format time from 0800 to 08:00
			var isCustom = $(this).hasClass("te-werken-input");
			formatTime(this, $(this).hasClass("timepicker-negative"),isCustom);
			
			if (timeChangeCallBack && typeof(timeChangeCallBack) == "function"){
				timeChangeCallBack()
			}
		});

	})();

	
	// convert string to date-time
	function toDate(dStr) {
		var now = new Date();
		now.format("HH:mm");

		now.setHours(parseFloat(dStr.substr(0, 2)));
		now.setMinutes(parseFloat(dStr.substr(3, 5)));

		return now;
	}

	function replaceDotByColon(element) {
		var value = $(element).val();
		$(element).val(value.replace('.', ':'));
	}
	
	function formatTime(element, isNegativeTime, isCustom)
	{
	    var value = $(element).val();
	    if (value.length == 0) {
	    	if($(element).hasClass("filled")){
	    		$(element).addClass("time-updated");
	    	}
	    	
	    	return;
	    }

		var timeArray =null;
		if(!isCustom){
			timeArray = formatTimeValue(value, isNegativeTime);
		}
		else{
			timeArray = formatTimeValueCustom(value, isNegativeTime);
		}	    
	    var doNothing = timeArray[0];
	    var newValue = timeArray[1];
	    var isValid = timeArray[2];

	    if (!doNothing)
	    {
	        $(element).val(newValue);
	        if (!isValid)
	        {
	            $(element).removeClass("valid-time");
	        	$(element).addClass("invalid-time");
	            $(element).removeClass("time-updated");
	        }
	        else
	        {
	        	$(element).removeClass("invalid-time");
	        	$(element).addClass("valid-time");
	            $(element).addClass("time-updated");
	        }
	    }
	}
	
	function formatTimeValue(val, isNegativeTime)
	{
	    var value = val;
	    if (value.length == 0)
	    {
	        return [true, "0", false];
	    }

	    var allowedChars = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
	    var newValue = "";
	    var isValid = false;

	    $.each(value.split(""), function (indexOfChar, valueOfChar)
	    {
	        $.each(allowedChars, function (indexOfAllowedChar, valueOfAllowedChar)
	        {
	            if (valueOfChar == valueOfAllowedChar)
	            {
	                newValue += valueOfChar;
	            }
	        });
	    });

	    if (newValue.length == 1) newValue = "0" + newValue + "00";
	    if (newValue.length == 2) newValue = newValue + "00";
	    if (newValue.length == 3) newValue = "0" + newValue;

	    var regEx = "^([0-1]?[0-9]|[2][0-3])([0-5][0-9])$"; // example: 800 or 0800 or 2359
	    
	    if (newValue.match(regEx)) {
			var hours = newValue.slice(0, 2);
			var minutes = newValue.slice(2, 4);
			
			newValue = hours + ":" + minutes;
			isValid = true;
		}

		// if (isNegativeTime)
		// {
		// newValue = "-" + newValue;
		// }

	    return [false, newValue, isValid];
	}
	
	function formatTimeValueCustom(val, isNegativeTime){
		var value = val;
	    if (value.length == 0)
	    {
	        return [true, "0", false];
	    }

	    var newValue = val;
	    var isValid = false;

	    var times = value.split(":");
	    var regEx_1 = "^[0-9]*$"
	    var regEx_2 = "^([0-5][0-9])$"
	    	
	    if(times.length == 2){
	    	
	    	var hours = times[0];
	    	var minutes = times[1];
	    	
	    	if(minutes.length == 1){
	    		minutes +="0";
	    	}
	    		
	    	if(hours.match(regEx_1) && minutes.match(regEx_2)){
	    		newValue = hours + ":" + minutes;
	    		isValid = true;
	    	}	
	    }else if(times.length == 1){
	    	var hours = times[0];
	    	if(hours.match(regEx_1)){
	    		newValue = hours;
	    		isValid = true;
	    	}
	    }
	    
	    return [false, newValue, isValid];
	}
	
}

