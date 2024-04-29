using Newtonsoft.Json;

namespace testTask{
    /// <summary>
    /// Вспомогательный класс для десериализации JSON-строки в список
    /// </summary>
    public class Order
    {
        
        [JsonProperty("user_id")]
        public required string UserId { get; set; }

        [JsonProperty("ordered_at")]
        public DateTime OrderedAt { get; set; }

        [JsonProperty("status")]
        public required string Status { get; set; }

        [JsonProperty("total")]
        public double Total { get; set; }
    }
}