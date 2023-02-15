using Microsoft.EntityFrameworkCore;
using SensorValidation.Models;
using SensorValidation.Models.Entities;
using TaskStatus = SensorValidation.Models.TaskStatus;

namespace SensorValidation.Persistence
{
    public static class ModelBuilderExtensions
    {
        public static void SeedSensors(this ModelBuilder builder)
        {
            builder.Entity<SensorModel>().HasData(new SensorModel
            {
                Serial = 1,
                CreatedAt = DateTime.UtcNow.AddDays(-20),
                UpdatedAt = DateTime.UtcNow.AddDays(-20),
                ActivityStatus = SensorActivityStatus.Error.ToString(),
                CurrentConfiguration = "a.config",
                CurrentFirmware = "FW:23-015-2023:03",
                StatusName = SensorStatus.Idle.ToString(),
                NextTask = 1,
                TaskCount = 3,
                StatusId = 1,
                Type = "TS50X",
            });
            builder.Entity<SensorModel>().HasData(new SensorModel
            {
                Serial = 2,
                CreatedAt = DateTime.UtcNow.AddDays(-20),
                UpdatedAt = DateTime.UtcNow.AddDays(-20),
                ActivityStatus = SensorActivityStatus.Error.ToString(),
                CurrentConfiguration = "b.config",
                CurrentFirmware = "FW:23-015-2023:03",
                StatusName = SensorStatus.Idle.ToString(),
                NextTask = 2,
                TaskCount = 3,
                StatusId = 1,
                Type = "TS50X"
            });
        }

        public static void SeedTasks(this ModelBuilder builder)
        {
            builder.Entity<TaskModel>().HasData(new TaskModel
            {
                Id = 1,
                CreatedAt = DateTime.UtcNow.AddDays(-19),
                UpdatedAt = DateTime.UtcNow.AddDays(-19),
                AttemptCount = 3,
                FileId = "",
                SensorSerial = 2,
                StatusId = TaskStatus.Pending,
                StatusName = TaskStatus.Pending.ToString(),
                Type = TaskJobs.update_firmware.ToString(),
            });
            builder.Entity<TaskModel>().HasData(new TaskModel
            {
                Id = 2,
                CreatedAt = DateTime.UtcNow.AddDays(-19),
                UpdatedAt = DateTime.UtcNow.AddDays(-19),
                AttemptCount = 3,
                FileId = "",
                SensorSerial = 1,
                StatusId = TaskStatus.Pending,
                StatusName = TaskStatus.Pending.ToString(),
                Type = TaskJobs.update_firmware.ToString(),
            });
        }
        public static void SeedTaskSensors(this ModelBuilder builder)
        {
            builder.Entity<TaskSensorModel>().HasData(new TaskSensorModel
            {
                Id = 1,
                FileId = Guid.NewGuid().ToString(),
                SensorSerial = 2,
                Type = TaskJobs.update_configuration.ToString(),
            });
            builder.Entity<TaskSensorModel>().HasData(new TaskSensorModel
            {
                Id = 2,
                FileId = Guid.NewGuid().ToString(),
                SensorSerial = 1,
                Type = TaskJobs.update_firmware.ToString(),
            });
        }
    }
}
