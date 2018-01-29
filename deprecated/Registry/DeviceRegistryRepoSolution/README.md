# FAR-EDGE DeviceRegistry

Requirements :
	 ASP .net core 2.0
	  https://github.com/dotnet/core/blob/master/release-notes/download-archives/2.0.0-download.md

Build Instructions:
1. Open Project
2. Open Package Manager Console (in Visual Studio) (Tools > NuGet Package Manager > Package Manager Console) and run  
	Install-Package Microsoft.EntityFrameworkCore.Tools
3. Create a Migration (Add-Migration InitialCreate)
4. Update Database (Update-Database) 
5. Run/debug open a browser at  http://localhost:port/swagger/
	

