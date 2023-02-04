namespace SensorValidation.Models.Entities
{
    public class Sensor
    {
        public long Serial { get; set; }
        public string Type { get; set; }
        public int StatusId { get; set; }
        public string CurrentConfiguration { get; set; }
        public string CurrentFirmware { get; set; }
        public string CreatedAt { get; set; }
        public string UpdatedAt { get; set; }
        public string StatusName { get; set; }
        public long NextTask { get; set; }
        public int TaskCount { get; set; }
        public string ActivityStatus { get; set; }
        public List<string> TaskQueue { get; set; }
    }
}
