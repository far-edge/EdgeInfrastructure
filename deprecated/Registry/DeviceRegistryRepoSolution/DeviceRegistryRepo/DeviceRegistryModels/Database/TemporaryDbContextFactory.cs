using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.Extensions.Configuration;

using DeviceRegistryModels.Database;

namespace DeviceRegistry.DeviceRegistryModels.Database
{
    public class TemporaryDbContextFactory : IDesignTimeDbContextFactory<DeviceRegistryContext>
    {
        //////// 
        public DeviceRegistryContext CreateDbContext(string[] args)
        {
            var optionsBuilder = new DbContextOptionsBuilder<DeviceRegistryContext>();
            optionsBuilder.UseSqlite("Data Source=device.db");

            return new DeviceRegistryContext(optionsBuilder.Options);

        }


    }
}
