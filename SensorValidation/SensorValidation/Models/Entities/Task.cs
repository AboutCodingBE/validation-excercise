
using System.ComponentModel.DataAnnotations;

namespace SensorValidation.Models.Entities
{
    public class TaskModel
    {
        [Key]
        public int Id { get; set; }
        public long SensorSerial { get; set; }
        public string Type { get; set; }
        public TaskStatus StatusId { get; set; }
        public int AttemptCount { get; set; }
        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
        public string FileId { get; set; }
        public string StatusName { get; set; }
    }
}
