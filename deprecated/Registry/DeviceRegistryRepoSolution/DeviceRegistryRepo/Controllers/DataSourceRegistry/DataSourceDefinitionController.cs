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


namespace DeviceSourceRegistry.Controllers
{
    [Produces("application/json")]
    [Route("registry-repo/[controller]")]
    public class DataSourceDefinitionController : Controller
    {
        private DeviceRegistryContext _dbContext;
        private readonly ILogger _logger;


        public DataSourceDefinitionController(DeviceRegistryContext context, ILogger<DataSourceDefinitionController> logger)
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
        /// Get a List of all Data Source Definitions
        /// </summary>
        /// <returns>A List of Data Source Definitions</returns>
        /// <response code="200">Returns a List of Data Source Definitions</response>
        /// <response code="404">If there are no Data Source Definitions</response>           
        [HttpGet]
        [ProducesResponseType(typeof(List<DataSourceDefinition>), 200)]
        [ProducesResponseType(typeof(Nullable), 404)]
        public IActionResult Get()
        {
            var list = _dbContext.DataSourceDefinitions                                
                                 .Include(dsd => dsd.DataTopic)
                                 .Include(dsd => dsd.DataInterfaceSpecification)
                                 .ToList();
            if (list == null)
            {
                return NotFound();
            }

            return Ok(list);
        }

        // GET api/values/5
        /// <summary>
        /// Gets a Data Source Definition with a specific UUID
        /// </summary>
        /// <returns>A Data Source Definition with a specific UUID</returns>
        /// <response code="200">Returns the requested Data Source Definition</response>
        /// <response code="404">If there is no Data Source Definition with the supplied UUID</response>    
        [HttpGet("{id}")]
        [ProducesResponseType(typeof(DataSourceDefinition), 200)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Get(Guid id)
        {
            var result = (DataSourceDefinition)_dbContext.DataSourceDefinitions
                                  .Include(dsd => dsd.DataTopic)
                                  .Include(dsd => dsd.DataInterfaceSpecification)
                                  .Single(dsd => dsd.DataSourceDefinitionId == id);

            _dbContext.Entry(result).Reference(b => b.DataInterfaceSpecification).Load();

            if (result != null)
                return Ok(result);
            else
                return NotFound(id);
        }

        // GET api/values/5/uri?uri=dsd://mydomain/mydsd
        /// <summary>
        /// Gets a Data Source Definition with a specific URI
        /// </summary>
        /// <returns>A Data Source Definition with a specific URI</returns>
        /// <response code="200">Returns the requested Data Source Definition</response>
        /// <response code="404">If there is no Data Source Definition with the supplied URI</response>
        [HttpGet("uri")]
        [ProducesResponseType(typeof(DataSourceDefinition), 200)]
        [ProducesResponseType(typeof(String), 404)]
        public IActionResult Get([FromQuery]String uri)
        {
            var result = (DataSourceDefinition)_dbContext.DataSourceDefinitions
                                   .Include(dsd => dsd.DataTopic)
                                   .Include(dsd => dsd.DataInterfaceSpecification)
                                   .Single(dsd => dsd.URI == uri);

            _dbContext.Entry(result).Reference(b => b.DataInterfaceSpecification).Load();

            if (result != null)
                return Ok(result);
            else
                return NotFound(uri);
        }

        // POST api/values
        /// <summary>
        /// Creates a new Data Source Definition
        /// </summary>
        /// <remarks>
        /// It not Ids (UUIDs) are provided, a new Data Source Definition and a new
        /// Data Topic are created provided the URI are unique.
        /// Otherwise the operation fails fails.
        /// 
        /// If an existing Data Topic is going to be used, only the 
        /// "DataTopicId" UUID needs to be provided.
        /// </remarks>
        /// <returns>The Data Source Definition as saved in the database</returns>
        /// <response code="201">The Data Source Definition as saved in the database</response>
        /// <response code="400">If it fails to save the Data Source Definition</response>
        [HttpPost]
        [ProducesResponseType(typeof(DataSourceDefinition), 201)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Post([FromBody]DataSourceDefinition value)
        {
            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                var dataTopic = _dbContext.DataTopics.Find(value.DataTopic.DataTopicId);

                if (dataTopic != null)
                {
                    value.DataTopic = dataTopic;
                }

                _dbContext.DataSourceDefinitions.Add(value);
                _dbContext.SaveChanges();

                return Created("registry-repo/DataSourceDefinition", _dbContext.Entry(value).GetDatabaseValues());
            }
            catch (Exception e)
            {
                _logger.LogError(e.StackTrace);
                return BadRequest();
            }
        }

        // PUT api/values/5
        /// <summary>
        /// Updates a Data Source Definition
        /// </summary>
        /// <remarks>
        /// It only updates the fields of the Data Source Definition. The fields 
        /// of the Data Topic are not updated. If another UUID in the Data Topic is
        /// used then the Data Source Definition points to that Data Topic.
        /// </remarks>
        /// <returns>The Updated Data Source Definition as saved in the database</returns>
        /// <response code="200">The Updated Data Source Definition as saved in the database</response>
        /// <response code="400">If it fails to update the Data Source Definition</response>
        [HttpPut("{id}")]
        [ProducesResponseType(typeof(DataSourceDefinition), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Put(Guid id, [FromBody]DataSourceDefinition value)
        {

            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                var originalDataSourceDefinition = (DataSourceDefinition)_dbContext.DataSourceDefinitions.Find(id);

                                                           
                //remove all child elements
                _dbContext.Remove(originalDataSourceDefinition.DataInterfaceSpecification);

                originalDataSourceDefinition.DataInterfaceSpecification = value.DataInterfaceSpecification;
                originalDataSourceDefinition.DataTopic = value.DataTopic;
                _dbContext.Entry(originalDataSourceDefinition).CurrentValues.SetValues(value);
                _dbContext.SaveChanges();

                return Ok(_dbContext.Entry(originalDataSourceDefinition).GetDatabaseValues());
            }
            catch (Exception ex)
            {
                _logger.LogError(ex.StackTrace);
                return BadRequest();
            }
        }

        // DELETE api/values/5
        /// <summary>
        /// Deletes a Data Source Definition with a specific UUID
        /// </summary>
        /// <returns>The UUID of the deleted Data Source Definition</returns>
        /// <response code="200">The UUID of the deleted Data Source Definition</response>
        /// <response code="400">If it fails to delete the Data Source Definition</response>
        /// <response code="404">If it fails to find the Data Source Definition to be deleted</response>
        [HttpDelete("{id}")]
        [ProducesResponseType(typeof(Guid), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Delete(Guid id)
        {
            var dataSourceDefinitionToBeDeleted = _dbContext.DataSourceDefinitions.Find(id);
                               

            if (dataSourceDefinitionToBeDeleted != null)
            {
                try
                {
                    _dbContext.Remove(dataSourceDefinitionToBeDeleted.DataInterfaceSpecification);
                    _dbContext.Remove(dataSourceDefinitionToBeDeleted);
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
