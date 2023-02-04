
namespace SensorValidation.Models.Entities
{
    public class Task
    {
        public int Id { get; set; }
        public long SensorSerial { get; set; }
        public string Type { get; set; }
        public int StatusId { get; set; }
        public int AttemptCount { get; set; }
        public string CreatedAt { get; set; }
        public string UpdatedAt { get; set; }
        public string FileId { get; set; }
        public string StatusName { get; set; }
    }
}
