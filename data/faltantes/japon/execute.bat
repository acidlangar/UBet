for %%G in (*.sql) do sqlcmd /S wks0316 /d id_bt -E -i"%%G"
pause