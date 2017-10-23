﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace DeviceRegistryModels.Models
{
    //All base entities should be exposed as Resources and
    //have URI. Also all Ids are Guid
    public partial class BaseEntity
    {
        [JsonIgnore]
        [DataType(DataType.DateTime)]
        public virtual DateTime? DateCreated { get; set; }

        [JsonIgnore]
        public virtual String UserCreated { get; set; }

        [JsonIgnore]
        [DataType(DataType.DateTime)]
        public virtual DateTime? DateModified { get; set; }

        [JsonIgnore]
        public virtual String UserModified { get; set; }

        public virtual String URI { get; set; }
    }

    public partial class DataTopic : BaseEntity
    {
        [Key]
        public virtual Guid DataTopicId { get; set; }

        public virtual String DataModelDescription { get; set; }
    }

    //embedded
    public partial class DataInterfaceSpecification
    {
        [Key]
        [JsonIgnore]
        public virtual Guid DataInterfaceSpecificationId { get; set; }

        public virtual String CommunicationProtocol { get; set; }

        public virtual String CommunicationProtocolDetails { get; set; }

        public virtual String ConnectionParameters { get; set; }

    }


    public partial class DataSourceDefinition : BaseEntity
    {
        //private ICollection<DataInterfaceSpecification> _dataInterfaceSpecifications;

        [Key]
        public virtual Guid DataSourceDefinitionId { get; set; }

        public virtual String Type { get; set; }

        public virtual DataTopic DataTopic { get; set; }

        public virtual DataInterfaceSpecification DataInterfaceSpecification {get; set; }

    }

    //embedded
    public partial class DataInterfaceSpecificationParameters
    {
        private ICollection<Parameter> _Parameters;
        
        [Key]
        [JsonIgnore]
        public virtual Guid DataInterfaceSpecificationParametersId { get; set; }

        public virtual ICollection<Parameter> Parameters 
        { 
            get
            {
                return _Parameters ?? (_Parameters = new HashSet<Parameter>());
            }
            set
            {
                if (value == null) throw new ArgumentNullException(nameof(value));

                _Parameters = value;
            }
        }
    }

    //embedded
    public partial class Parameter
    {
        [Key]
        [JsonIgnore]
        public virtual Guid ParameterId { get; set; }

        public virtual String Key { get; set; }
        public virtual String Value { get; set; }
    }


    public partial class DataSourceManifest : BaseEntity
    {
        [Key]
        //[JsonIgnore]
        public virtual Guid DataSourceManifestId { get; set; }

        //[Key] [Url]
        //public virtual String UUID { get; set; }

        // it is a string reference to the UUID of the Data Source Definition
        public virtual DataSourceDefinition DataSourceDefinition { get; set; }

        public virtual DataInterfaceSpecificationParameters DataSourceDefinitionParameters { get; set; }

        public virtual String MACAddress { get; set; }
    }

    public class DataConsumerManifest : BaseEntity
    {
        [Key]
        public virtual Guid DataConsumerManifestId { get; set; }

        private ICollection<DataSourceDefinition> _dataSourceDefinitions;

        public virtual String MACAddress { get; set; }

        public virtual ICollection<DataSourceDefinition> DataSourceDefinitions
        {
            get
            {
                return _dataSourceDefinitions ?? (_dataSourceDefinitions = new HashSet<DataSourceDefinition>());
            }

            set
            {
                if (value == null) throw new ArgumentNullException(nameof(value));

                _dataSourceDefinitions = value;
            }
        }

    }

	public partial class DataChannelDescriptor : BaseEntity
	{
        [Key]
        public virtual Guid DataChannelDescriptorId { get; set; }

        public virtual DataSourceManifest DataSourceManifest { get; set; }

        public virtual DataConsumerManifest DataConsumerManifest { get; set; }

        [DataType(DataType.DateTime)]
        public virtual DateTime ValidFrom { get; set; }

        [DataType(DataType.DateTime)]
        public virtual DateTime ExpirationDateTime { get; set; }

	}
}
