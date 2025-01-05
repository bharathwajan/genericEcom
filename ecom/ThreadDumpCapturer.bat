@echo off
for /f "tokens=1,2" %%a in ('jps ^| findstr EcomApplication') do set PID=%%a
echo Taking thread dumps for PID: %PID%

for /l %%i in (1,1,5) do (
    jstack %PID% > ThreadDumps\thread_dump_%%i.txt
    timeout /t 1
)
