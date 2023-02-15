using Microsoft.EntityFrameworkCore;
using SensorValidation.Models.Entities;

namespace SensorValidation.Persistence
{
    public class SensorValidationDbContext : DbContext
    {
        public SensorValidationDbContext(DbContextOptions<SensorValidationDbContext> option) : base(option)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.SeedSensors();
            modelBuilder.SeedTasks();
            modelBuilder.SeedTaskSensors();
        }
        public DbSet<SensorModel> Sensors { get; set; }
        public DbSet<TaskModel> Tasks { get; set; }
        public DbSet<TaskSensorModel> TaskSensors { get; set; }
    }
}
