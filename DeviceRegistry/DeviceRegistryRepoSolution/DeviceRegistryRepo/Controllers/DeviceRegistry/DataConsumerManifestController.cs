using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Dynamic;

using Microsoft.AspNetCore.Mvc;
using DeviceRegistryModels.Database;
using DeviceRegistryModels.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

using Newtonsoft.Json.Linq;

namespace DeviceRegistry.Controllers
{
    [Produces("application/json")]
    [Route("registry-repo/[controller]")]
    public class DataConsumerManifestController : Controller
    {
        private DeviceRegistryContext _dbContext;
        private readonly ILogger _logger;


        public DataConsumerManifestController(DeviceRegistryContext context, ILogger<DataSourceManifestController> logger)
        {
            try
            {
                _dbContext = context;
                _logger = logger;

            }
            catch (Exception e)
            {
                _logger.LogError(e.StackTrace);
            }
        }

        // GET: api/values
        /// <summary>
        /// Get a List of all Data Consumer Manifests
        /// </summary>
        /// <returns>A List of Data Consumer Manifests</returns>
        /// <response code="200">Returns a List of Data Consumer Manifests</response>
        /// <response code="404">If there are no Data Consumer Manifests</response>           
        [HttpGet]
        [ProducesResponseType(typeof(List<DataConsumerManifest>), 200)]
        [ProducesResponseType(typeof(Nullable), 404)]
        public IActionResult Get()
        {
            var list =  _dbContext.DataConsumerManifests
                                 .Include(dcm => dcm.DataSourceDefinitions)
                                 .ToList();
            if (list == null)
            {
                return NotFound();
            }

            return Ok(list);
        }

        // GET api/values/5
        /// <summary>
        /// Gets a Data Consumer Manifest with a specific UUID
        /// </summary>
        /// <returns>A Data Consumer Manifest with a specific UUID</returns>
        /// <response code="200">Returns the requested Data Consumer Manifest</response>
        /// <response code="404">If there is no Data Consumer Manifest with the supplied UUID</response>    
        [HttpGet("{id}")]
        [ProducesResponseType(typeof(DataConsumerManifest), 200)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Get(Guid id)
        {
            var result = (DataConsumerManifest)_dbContext.DataConsumerManifests
                                                       .Include(dsm => dsm.DataSourceDefinitions)
                                                       .Single(dsm => dsm.DataConsumerManifestId == id);

            _dbContext.Entry(result).Reference(b => b.DataSourceDefinitions).Load();
            //_dbContext.Entry(result).Collection(b => b.DataSourceDefinitionParameters).;

            if (result != null)
                return Ok(result);
            else
                return NotFound(id);
        }

        // GET api/values/5/uri?uri=dsd://mydomain/mydsd
        /// <summary>
        /// Gets a Data Consumer Manifest with a specific URI
        /// </summary>
        /// <returns>A Data Consumer Manifest with a specific URI</returns>
        /// <response code="200">Returns the requested Data Consumer Manifest</response>
        /// <response code="404">If there is no Data Consumer Manifest with the supplied URI</response>
        [HttpGet("uri")]
        [ProducesResponseType(typeof(DataConsumerManifest), 200)]
        [ProducesResponseType(typeof(String), 404)]
        public IActionResult Get([FromQuery]String uri)
        {
            var result = (DataConsumerManifest)_dbContext.DataConsumerManifests
                                   .Include(dsm => dsm.DataSourceDefinitions)                                 
                                   .Single(dsm => dsm.URI == uri);

            _dbContext.Entry(result).Reference(b => b.DataSourceDefinitions).Load();
            //_dbContext.Entry(result).Collection(b => b.DataSourceDefinitionParameters).;

            if (result != null)
                return Ok(result);
            else
                return NotFound(uri);
        }

        // POST api/values
        /// <summary>
        /// Creates a new Data Consumer Manifest
        /// </summary>
        /// <remarks>
        /// It not Ids (UUIDs) are provided, a new Data Consumer Manifest and a new
        /// Data Source Defininition are created provided the URI are unique.
        /// Otherwise the operation fails fails.
        /// 
        /// If an existing Data Source Definitions is going to be used, only the 
        /// "DataSourceDefinitionId" UUID needs to be provided.
        /// </remarks>
        /// <returns>The Data Source Manifest as saved in the database</returns>
        /// <response code="201">The Data Source Manifest as saved in the database</response>
        /// <response code="400">If it fails to save the Data Source Manifest</response>
        [HttpPost]
        [ProducesResponseType(typeof(DataConsumerManifest), 201)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Post([FromBody]DataConsumerManifest value)
        {
            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                if (value.DataSourceDefinitions != null)
                {
                    //get from the database datasourcedefinitions
                    List<DataSourceDefinition> dataSourceDefinitions = new List<DataSourceDefinition>();

                    foreach (DataSourceDefinition requestDataSourceDefinition in value.DataSourceDefinitions)
                    {
                        DataSourceDefinition dataSourceDefinition = 
                            _dbContext.DataSourceDefinitions.Find(requestDataSourceDefinition.DataSourceDefinitionId);

                        if (dataSourceDefinition != null)
                        {
                            dataSourceDefinitions.Add(dataSourceDefinition);
                        }
                    }

                    value.DataSourceDefinitions = dataSourceDefinitions;

                }

                _dbContext.DataConsumerManifests.Add(value);
                _dbContext.SaveChanges();

                return Created("registry-repo/DataConsumerManifest", _dbContext.Entry(value).GetDatabaseValues());
            }
            catch (Exception e)
            {
                _logger.LogError(e.StackTrace);
                return BadRequest();
            }
        }

        // PUT api/values/5
        /// <summary>
        /// Updates a Data Consumer Manifest
        /// </summary>
        /// <remarks>
        /// It only updates the fields of the Data Consumer Manifest. The fields 
        /// of the Data Source Definitions are not updated. If another UUID is
        /// used then the Data Consumer Manifest points to that Data Source
        /// Definition.
        /// </remarks>
        /// <returns>The Updated Data Consumer Manifest as saved in the database</returns>
        /// <response code="200">The Updated Data Consumer Manifest as saved in the database</response>
        /// <response code="400">If it fails to update the Data Consumer Manifest</response>
        [HttpPut("{id}")]
        [ProducesResponseType(typeof(DataConsumerManifest), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Put(Guid id, [FromBody]DataConsumerManifest value)
        {
           

            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                var originalDataConsumerManifest = (DataConsumerManifest)_dbContext.DataConsumerManifests
                                                           .Include(dsm => dsm.DataSourceDefinitions)
                                                           .FirstOrDefault(o => o.DataConsumerManifestId == id);
                //remove all child elements
                //_dbContext.Remove(originalDataSourceManifest.DataSourceDefinitionParameters);
                //_dbContext.Remove(originalDataSourceManifest.DataSourceDefinition);

                //originalDataSourceManifest.DataConDefinitionParameters.Parameters = value.DataSourceDefinitionParameters.Parameters;

                ////find if the DataSourceDefinition has changed
                //var dsdorig = _dbContext.DataSourceDefinitions.Find(value.DataSourceManifestId);

                //if (dsdorig != null)
                //{
                //    _dbContext.Remove(originalDataSourceManifest.DataSourceDefinition);
                //    originalDataSourceManifest.DataSourceDefinition = dsdorig;
                //}

                ////originalDataSourceManifest.DataSourceDefinition = value.DataSourceDefinition;

                if (originalDataConsumerManifest.DataSourceDefinitions.All(value.DataSourceDefinitions.Contains) 
                    && originalDataConsumerManifest.DataSourceDefinitions.Count == value.DataSourceDefinitions.Count)
                {
                    //no need to do anything   
                }
                else
                {
                    //use values form value
                    //get from the database datasourcedefinitions
                    List<DataSourceDefinition> dataSourceDefinitions = new List<DataSourceDefinition>();

                    foreach (DataSourceDefinition requestDataSourceDefinition in value.DataSourceDefinitions)
                    {
                        DataSourceDefinition dataSourceDefinition =
                            _dbContext.DataSourceDefinitions.Find(requestDataSourceDefinition.DataSourceDefinitionId);

                        if (dataSourceDefinition != null)
                        {
                            dataSourceDefinitions.Add(dataSourceDefinition);
                        }
                    }

                    value.DataSourceDefinitions = dataSourceDefinitions;
                }

                _dbContext.Entry(originalDataConsumerManifest).CurrentValues.SetValues(value);
                _dbContext.SaveChanges();
                return Ok(value);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex.StackTrace);
                return BadRequest();
            }
        }

        // DELETE api/values/5
        /// <summary>
        /// Deletes a Data Consumer Manifest with a specific UUID
        /// </summary>
        /// <returns>The UUID of the deleted Data Consumer Manifest</returns>
        /// <response code="200">The UUID of the deleted Data Consumer Manifest</response>
        /// <response code="400">If it fails to delete the Data Consumer Manifest</response>
        /// <response code="404">If it fails to find the Data Consumer Manifest to be deleted</response>
        [HttpDelete("{id}")]
        [ProducesResponseType(typeof(Guid), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Delete(Guid id)
        {
            var manifest = _dbContext.DataConsumerManifests
                                     .Single(d => d.DataConsumerManifestId == id);

            if (manifest != null)
            {
                try
                {
                    _dbContext.Remove(manifest);
                    _dbContext.SaveChanges();
                    return Ok(id);
                }
                catch (Exception e)
                {
                    _logger.LogError(e.StackTrace);
                    return BadRequest();
                }
            }
            else
            {
                return NotFound(id);
            }
        }
    }
}
