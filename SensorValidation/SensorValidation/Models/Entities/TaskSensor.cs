using System.ComponentModel.DataAnnotations;

namespace SensorValidation.Models.Entities
{
    public class TaskSensorModel
    {
        [Key]
        public int Id { get; set; }
        public long SensorSerial { get; set; }
        public string FileId { get; set; }
        public string Type { get; set; }
        public SensorModel Sensor { get; set; }

    }

}
