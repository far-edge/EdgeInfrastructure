using System;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using DeviceRegistryModels.Models;
using DeviceRegistry.Business;
using Microsoft.AspNetCore;

namespace DeviceRegistryModels.Database
{

    public class DeviceRegistryContext : DbContext
    {
        public DeviceRegistryContext(DbContextOptions<DeviceRegistryContext> options)
            : base(options)
        { }

        public DbSet<DataSourceDefinition> DataSourceDefinitions { get; set; }
        public DbSet<DataSourceManifest> DataSourceManifests { get; set; }
        public DbSet<DataConsumerManifest> DataConsumerManifests { get; set; }
        public DbSet<DataChannelDescriptor> DataChannelDescriptors { get; set; }
        public DbSet<DataTopic> DataTopics { get; set; }
        public DbSet<DataInterfaceSpecification> DataInterfaceSpecifications { get; set; }
        public DbSet<DataInterfaceSpecificationParameters> DataInterfaceSpecificationParameteres { get; set; }
        public DbSet<Parameter> Parameters { get; set; }
       

        //private UserResolverService _userResolverService;
        private String _user;

        public DeviceRegistryContext(UserResolverService resolverService, DbContextOptions<DeviceRegistryContext> options) : base(options)
        {
            //_userResolverService = resolverService;
            _user = resolverService.GetUser();

        }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<DataTopic>()
                   .HasIndex(dt => dt.URI)
                   .IsUnique(true);

            builder.Entity<DataSourceDefinition>()
                   .HasIndex(ds => ds.URI)
                   .IsUnique(true);

            builder.Entity<DataSourceManifest>()
                   .HasIndex(dsm => dsm.URI)
                   .IsUnique(true);

            builder.Entity<DataConsumerManifest>()
                   .HasIndex(dcm => dcm.URI)
                   .IsUnique(true);
            
            builder.Entity<DataChannelDescriptor>()
                   .HasIndex(dcd => dcd.URI)
                   .IsUnique(true);

        }


        public override int SaveChanges()
        {
            try
            {
                this.ChangeTracker.DetectChanges();

                foreach (var entry in this.ChangeTracker.Entries()
                         .Where(e => e.State == EntityState.Added || e.State == EntityState.Modified))
                {
                    if (entry.Entity is BaseEntity && entry.State == EntityState.Added)
                    {
                        ((BaseEntity)entry.Entity).DateCreated = DateTime.UtcNow;
                        ((BaseEntity)entry.Entity).DateModified = DateTime.UtcNow;
                        if (_user != null)
                        {
                            ((BaseEntity)entry.Entity).UserCreated = _user;
                            ((BaseEntity)entry.Entity).UserModified = _user;
                        }
                        else
                        {
                            ((BaseEntity)entry.Entity).UserCreated = "Anonymous";
                            ((BaseEntity)entry.Entity).UserModified = "Anonymous";
                        }
                    }
                    else if (entry.Entity is BaseEntity && entry.State == EntityState.Modified)
                    {
                        ((BaseEntity)entry.Entity).DateModified = DateTime.UtcNow;
                        if (_user != null)
                        {
                            ((BaseEntity)entry.Entity).UserModified = _user;
                        }
                        else
                        {
                            ((BaseEntity)entry.Entity).UserModified = "Anonymous";
                        }
                    }
                }

                ChangeTracker.AutoDetectChangesEnabled = false;
                var result = base.SaveChanges();
                ChangeTracker.AutoDetectChangesEnabled = true;

                return result;
            } catch (Exception ex)
            {
                throw ex;
            }
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlite("Data Source=device.db");
        }
    }
}
