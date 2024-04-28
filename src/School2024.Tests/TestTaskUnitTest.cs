namespace School2024.Tests
{
	using School2024.TestTask;
	using System.Text.Json;
	using System.Text;

	[TestFixture]
	public class Tests
	{
# if DEBUG 
		private const string INPUTING_PATH = "..\\..\\..\\..\\input.json";
#else
		const string INPUTING_PATH = "..\\input.json";
#endif

		private Dictionary<string, List<string>> DEFAULT_RESULT = new Dictionary<string, List<string>>(1)
		{
			{ "months", new List<string>() {"march", "december"} }
		};

		[Test, Order(0)]
		public void GetAnalyticsToString_ByInputingPath_ShouldReturnString()
		{
			TestTaskWorker worker = new TestTaskWorker(INPUTING_PATH);

			Dictionary<string, List<string>>? result = JsonSerializer.Deserialize <Dictionary<string, List<string>>> (worker.GetAnalyticsToString());

			Assert.That(
				result,
				Is.EqualTo(DEFAULT_RESULT)
				);
		}

		[Test, Order(1)]
		public void GetAnalyticsToString_ByEmptyPath_ShouldReturlArgumentNullException()
		{
			Assert.That(
				() => new TestTaskWorker(null),
				Throws.TypeOf<ArgumentNullException>()
				);
		}

		[Test, Order(2)]
		public void GetAnalyticsToJsonFile_WithOutputingPath_ShouldWrite()
		{
			TestTaskWorker worker = new TestTaskWorker(INPUTING_PATH);

			worker.GetAnalyticsToJsonFile("../result.json");

			using FileStream fileStream = new FileStream("../result.json", FileMode.Open, FileAccess.Read);

			byte[] jsonInBytes = new byte[fileStream.Length];
				
			fileStream.Read(jsonInBytes, 0, jsonInBytes.Length);

			string json = Encoding.ASCII.GetString(jsonInBytes);

			Dictionary<string, List<string>>? result = JsonSerializer.Deserialize<Dictionary<string, List<string>>>(json);

			Assert.That(
				result,
				Is.EqualTo(DEFAULT_RESULT)
				);
		}
	}
}
