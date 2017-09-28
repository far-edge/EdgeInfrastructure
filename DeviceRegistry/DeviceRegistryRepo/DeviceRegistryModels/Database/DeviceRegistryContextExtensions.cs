using System;
using System.Collections.Generic;
using System.Linq;
using DeviceRegistryModels.Database;
using DeviceRegistryModels.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Query;

namespace DeviceRegistryModels.Database
{
    public static class DeviceRegistryContextExtensions
    {

        public static IQueryable<T> getEntityByURI<T>(this IQueryable<T> entityTable, String UUID) where T : BaseEntity
        {
            return entityTable.Where(dt => dt.URI == UUID);
        }

        public static void EnsureSeedData(this DeviceRegistryContext context)
        {
            if (!context.Database.GetPendingMigrations().Any())
            {
                if (!context.DataSourceDefinitions.Any())
                {
                    var dis = context.DataInterfaceSpecifications.Add( new DataInterfaceSpecification
                    {
                        CommunicationProtocol = "MQTT",
                        // https://github.com/mqtt/mqtt.github.io/wiki/URI-Scheme
                        CommunicationProtocolDetails = "mqtt://username:password@host.domain:port", //mqtt[s]://[username][:password]@host.domain[:port]
                                                                                                          // http://activemq.apache.org/mqtt.html depend on the specific brokcer
                        ConnectionParameters = "?transport.defaultKeepAlive=KeepAlliveTime"
                    }).Entity;


                    var datasource = 
                        context.DataSourceDefinitions.Add(
                            new DataSourceDefinition {
                            URI = "dsd://faredge/senml-measurerments",
                            Type = "SENML-MQTT",
                            DataTopic = new DataTopic {
                                DataModelDescription = "Senml measurements",
                                URI = "dtopic://faredge/senml"
                            },
                            DataInterfaceSpecification = dis

                        }).Entity;

                   // context.Update(datasource);

                    List<Parameter> parameterList = new List<Parameter>()
                    {
                        context.Parameters.Add(new Parameter {Key = "username", Value = "root"}).Entity,
                        context.Parameters.Add(new Parameter {Key = "password", Value = ":myPass"}).Entity,
                        context.Parameters.Add(new Parameter {Key = "host", Value = "activemq"}).Entity,
                        context.Parameters.Add(new Parameter {Key = "domain", Value = "local"}).Entity,
                        context.Parameters.Add(new Parameter {Key = "port", Value = "1883"}).Entity,
                        context.Parameters.Add(new Parameter {Key = "topic", Value = "/DeviceTopic/SENMLData"}).Entity,
                        context.Parameters.Add(new Parameter {Key="KeepAlliveTime", Value="60000"}).Entity
                    };

                   

                    DataInterfaceSpecificationParameters dsdp = context.DataInterfaceSpecificationParameteres.Add(new DataInterfaceSpecificationParameters()
                    {
                        Parameters = parameterList
                    }).Entity;


                    context.DataSourceManifests.Add(
                        new DataSourceManifest {
                        DataSourceDefinition = datasource,
                        URI = "dsrc://Device1/MQTT-SENML",
                        DataSourceDefinitionParameters = dsdp,
                        MACAddress = "00:33:13:AC:1F:CB"
                            
                        }
                    );

                    context.SaveChanges();
                }
            }
        }
    }
}
