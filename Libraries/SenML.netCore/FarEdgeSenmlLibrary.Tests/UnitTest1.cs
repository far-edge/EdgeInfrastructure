using System;
using Xunit;
using FarEdgeSenmlLibrary;

namespace FarEdgeSenmlLibrary.Tests
{
	public class FarEdgeSenmlLibrary_Should
	{
		[Fact]
		public String TestSerialize()
		{
			Senml message = new Senml();
			SenmlEntry entry = new SenmlEntry()
			{
				BaseName = "test",
				BaseTime = 1324134,
				BaseUnit = "V",
				BaseValue = 103.1,
				Name = "sensor 1",
				Value = 3.3,
				Version = "10"
			};
            SenmlEntryProxy proxy = new SenmlEntryProxy();
            proxy.value = entry;
			
            message.entries.Add(proxy);


			//Console.WriteLine(message.toJsonString());

            return message.toJsonString();
		}

        [Fact]
        public void TestDeserialize()
        {
            String json = this.TestSerialize();

            Senml message = new Senml(json);

           // Console.WriteLine((message.toJsonString()));

            Assert.True(json.Equals(message.toJsonString()));

        }

        [Fact]
        public string TestMultipleSerialize()
        {
            Senml message = new Senml(this.TestSerialize());
            //SenmlEntryProxy proxy1 = new SenmlEntryProxy();
            //proxy1.value = new SenmlEntry(this.TestSerialize());

            //message.entries.Add(proxy1);

            SenmlEntryProxy proxy2 = new SenmlEntryProxy();


			
            SenmlEntry entry = new SenmlEntry()
			{
				BaseName = "test",
				BaseTime = 1324134,
				BaseUnit = "T",
				BaseValue = 10231.1,
				Name = "sensor 2",
				Value = 34.4,
				Version = "10"
			};

            proxy2.value = entry;
            message.entries.Add(proxy2);

			Console.WriteLine(message.toJsonString());

			return message.toJsonString();

        }

        [Fact]
		public void MultipleTestDeserialize()
		{
			String json = this.TestMultipleSerialize();

			Senml message = new Senml(json);

			//Console.WriteLine((message.toJsonString()));

			Assert.True(json.Equals(message.toJsonString()));

		}
	}
}
