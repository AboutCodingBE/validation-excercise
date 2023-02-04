namespace SensorValidation.Models
{
    public enum TaskJobs
    {
        update_firmware = 0,
        update_configuration = 1
    }
    public enum TaskStatus
    {
        Pending = 0,
        Completed = 1,
        Error = 2,
        Ongoing = 3
    }
}
