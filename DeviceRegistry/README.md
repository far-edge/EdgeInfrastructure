# FAR-EDGE DeviceRegistry

Requirements ASP .net core 2.0

Build Instructions
1. Instal ASP .net Core 2 from 
	https://github.com/dotnet/core/blob/master/release-notes/download-archives/2.0.0-download.md

2. Install-Package Microsoft.EntityFrameworkCore.Tools
3. Update-Database (if no migration exist use Add-Migration InitialCreate)
3. Run and open http://localhost:<port>/swagger/
	

