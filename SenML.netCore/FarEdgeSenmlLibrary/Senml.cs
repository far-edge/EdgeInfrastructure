using System;
using System.Collections;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace FarEdgeSenmlLibrary
{
	public class Senml
	{
		public List<SenmlEntryProxy> entries { get; set; }

		public Senml()
		{
			entries = new List<SenmlEntryProxy>();
		}

        public Senml(String json)
        {
            entries = new List<SenmlEntryProxy>();

            List<SenmlEntry> senmlEntries = JsonConvert.DeserializeObject<List<SenmlEntry>>(json);

            foreach (SenmlEntry senmlEntry in senmlEntries)
            {
                SenmlEntryProxy senmlEntryProxy = new SenmlEntryProxy();
                senmlEntryProxy.value = senmlEntry;
                this.entries.Add(senmlEntryProxy);
            }
        }

		public string toJsonString()
		{
            JsonSerializerSettings settings = new JsonSerializerSettings();
            settings.NullValueHandling = NullValueHandling.Ignore;

            List<SenmlEntry> myList = new List<SenmlEntry>();
            entries.ForEach(x => myList.Add(x.value));

            return JsonConvert.SerializeObject(myList,settings);
		}
	}

    public class SenmlEntryProxy
    {
        public SenmlEntry value { get; set; }

        public SenmlEntryProxy()
        {
            
        }

        public SenmlEntryProxy(String json)
        {
            this.value = JsonConvert.DeserializeObject<SenmlEntry>(json);
        }
    }

	public class SenmlEntry
	{
        public SenmlEntry()
        {
            
        }

        public SenmlEntry(String json)
        {
               
            SenmlEntry entry  = JsonConvert.DeserializeObject<SenmlEntry>(json);

        }

		//Base Fields

		/// <summary>
		/// This is a string that is prepended to the names found in the entries.
		/// </summary>
		/// <value>The name of the base.</value>
        /// 
        [JsonProperty(PropertyName = "bn")]
		public String BaseName { get; set; }

        [JsonProperty(PropertyName = "bt")]
		public Int64? BaseTime { get; set; } //in timeticks

        [JsonProperty(PropertyName = "bu")]
		public String BaseUnit { get; set; }

        [JsonProperty(PropertyName = "bv")]
        public double? BaseValue { get; set; }

        [JsonProperty(PropertyName = "bs")]
        public double? BaseSum { get; set; }

        [JsonProperty(PropertyName = "bver")]
		public String Version { get; set; }

		//Regular Fields
        [JsonProperty(PropertyName = "n")]
		public String Name { get; set; }

        [JsonProperty(PropertyName = "t")]
		public Int64? Time { get; set; }

        [JsonProperty(PropertyName = "u")]
		public String Unit { get; set; }

        [JsonProperty(PropertyName = "v")]
        public double? Value { get; set; }

        [JsonProperty(PropertyName = "s")]
        public double? Sum { get; set; }

        [JsonProperty(PropertyName = "ut")]
		public Int64? UpdateTime { get; set; }

        [JsonProperty(PropertyName = "vs")]
		public String StringValue { get; set; }

        [JsonProperty(PropertyName = "vb")]
		public bool? BooleanValue { get; set; }

        [JsonProperty(PropertyName = "vd")]
		public String DataValue { get; set; }

        [JsonProperty(PropertyName = "l")]
		public String Link { get; set; }


		public string ToJsonString()
		{
            JsonSerializerSettings settings = new JsonSerializerSettings();
            settings.NullValueHandling = NullValueHandling.Ignore;

            return JsonConvert.SerializeObject(this, settings);
		}
	}
}
