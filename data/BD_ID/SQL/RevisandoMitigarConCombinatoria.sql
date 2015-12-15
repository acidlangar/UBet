/*2,03	3,35	3
2,03	3,35	3
2,4		3,3		2,45
2,38	3,15	2,43



select (15 * 3.35 * 3.35 * 3.3 * 3.15) - 15

select 1/3.35

select 1.00/(1.00 - (1.00/3.35))
select 1.00/(1.00 - (1.00/3.35))
select 1.00/(1.00 - (1.00/3.3))
select 1.00/(1.00 - (1.00/3.15))


1.42; 1.42; 1.43; 1.46
*/
BEGIN
	DECLARE @AP_C NUMERIC(18,3)
	DECLARE @AP_1 NUMERIC(18,3)
	DECLARE @AP_2 NUMERIC(18,3)
	DECLARE @AP_3 NUMERIC(18,3)
	DECLARE @AP_4 NUMERIC(18,3)
	DECLARE @R_C NUMERIC(18,3)

	DECLARE @PER NUMERIC(18,3)
	DECLARE @G NUMERIC(18,3)

	SET @AP_C = 15.00
	SET @AP_1 = 70
	SET @AP_2 = 240
	SET @AP_3 = 800
	SET @AP_4 = 2700

	SET @R_C = @AP_C * ((3.35 * 3.35 * 3.3 * 3.15) - 1)

	SELECT @R_C RC 

	SET @PER = @AP_C
	SET @G = @AP_1 * (1.42 - 1)
	select @G GE, 1 J, @AP_1 AP, @PER PER, @G - @PER DIF

	SET @PER = @AP_C + @AP_1
	SET @G = @AP_2 * (1.42 - 1)
	select @G GE, 2 J, @AP_2 AP, @PER PER, @G - @PER DIF

	SET @PER = @AP_C + @AP_1 + @AP_2
	SET @G = @AP_3 * (1.42 - 1)
	select @G GE, 3 J, @AP_3 AP, @PER PER, @G - @PER DIF

	SET @PER = @AP_C + @AP_1 + @AP_2 + @AP_3
	SET @G = @AP_4 * (1.42 - 1)
	select @G GE, 4 J, @AP_4 AP, @PER PER, @G - @PER DIF

	SET @PER = @AP_1 + @AP_2 + @AP_3 + @AP_4
	SET @G = @R_C
	select @G GE, 4 J, 0 AP, @PER PER, @G - @PER DIF


END