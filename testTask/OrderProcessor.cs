using System.Globalization;
using Newtonsoft.Json;

namespace testTask{
    /// <summary>
    /// Provides methods for processing orders.
    /// </summary>
    public static class OrderProcessor
    {
        /// <summary>
        /// Парсирует дату и возвращает строку с названием месяца в нижнем регистре
        /// </summary>
        /// <param name="dateTime">Дата для парсинга</param>
        /// <returns>Строка с названием месяца в нижнем регистре</returns>
        private static string ParseMonth(DateTime dateTime)
        {
            CultureInfo culture = new CultureInfo("en-US");
            return dateTime.ToString("MMMM", culture).ToLower();
        }

        /// <summary>
        /// Поиск месяцев с максимальной выручкой
        /// </summary>
        /// <param name="_orders">Список заказов</param>
        /// <returns>JSON-строка с месяцами, в которых достигнута максимальная выручка</returns>
        public static string GetMaxTotalPerMonth(List<Order> orders)
        {
            var totalPerMonth = new Dictionary<string, double>();

            foreach (var order in orders)
            {
                if (order.Status != "COMPLETED")
                {
                    continue;
                }
                var month = ParseMonth(order.OrderedAt);

                if (totalPerMonth.ContainsKey(month))
                {
                    totalPerMonth[month] += order.Total;
                }
                else
                {
                    totalPerMonth[month] = order.Total;
                }
            }

            double maximum = totalPerMonth.Values.Max();
            var maxMonths = totalPerMonth.Where(month => month.Value == maximum).Select(month => month.Key).ToList();
            var result = new { months = maxMonths };
            var json = JsonConvert.SerializeObject(result);

            return json;
        }

        /// <summary>
        /// Создает новый файл формата JSON и записывает в него переданную строку.
        /// </summary>
        /// <param name="json">Строка JSON, которую необходимо записать в файл.</param>
        /// <param name="path">Путь для создания файла.</param>
        public static void MakeJsonFile(string json, string path)
        {
            using (StreamWriter writer = new StreamWriter(Path.Combine(path, "output.json")))
            {
                writer.WriteLine(json);
            }
        }
    }
}