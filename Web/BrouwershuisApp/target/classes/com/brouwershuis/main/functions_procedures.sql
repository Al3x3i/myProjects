DROP PROCEDURE IF EXISTS P_ADDWORK_SCHEDULE;
/*
DELIMITER $$
*/
CREATE PROCEDURE P_ADDWORK_SCHEDULE(
arg1 time,
arg2 time,
arg3 date, 
arg4 int, 
arg5 varchar(15),
arg6 varchar(255),
OUT arg7 int)
  LANGUAGE SQL
BEGIN

	SET @shift_id = (SELECT s.ID FROM shift s WHERE s.name = arg5);
    
    IF @shift_id IS NOT NULL
    THEN
		INSERT INTO work_schedule ( STARTTIME, ENDTIME, WEEKDATE, EMPLOYEE_FK, SHIFT_FK, COMMENTS) 
		SELECT arg1,arg2, arg3, arg4, @shift_id, arg6;
    END IF;
    
SET arg7 =  ROW_COUNT();

END;
/*
$$
DELIMITER ;

*/

DROP PROCEDURE IF EXISTS P_ADDCONTRACT_HOURS;
/*
DELIMITER $$
*/
CREATE PROCEDURE P_ADDCONTRACT_HOURS(
s_date Date, 
e_date Date, 
f_time int,
e_fk int,
OUT return_value int)
  LANGUAGE SQL
BEGIN
	SET return_value = 0;
	IF s_date < e_date
	THEN
		SET @counter = (SELECT count(*) FROM contract_hours c
		WHERE c.START_DATE = s_date
		AND c.END_DATE = e_date
		AND	c.EMPLOYEE_FK = e_fk);

		INSERT INTO contract_hours(START_DATE, END_DATE, FIXED_TIME, EMPLOYEE_FK)
		SELECT s_date, e_date, f_time, e_fk
		WHERE @counter = 0;

		SET return_value =  ROW_COUNT();
	END IF;
END;
/*
$$
DELIMITER ;
*/
