using System.ComponentModel.DataAnnotations;

namespace SensorValidation.Models.Entities
{
    public class SensorModel
    {
        [Key]
        public long Serial { get; set; }
        public string Type { get; set; }
        public int StatusId { get; set; }
        public string CurrentConfiguration { get; set; }
        public string CurrentFirmware { get; set; }
        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
        public string StatusName { get; set; }
        public long NextTask { get; set; }
        public int TaskCount { get; set; }
        public string ActivityStatus { get; set; }
        public List<TaskSensorModel> TaskQueue { get; set; }
    }
}
