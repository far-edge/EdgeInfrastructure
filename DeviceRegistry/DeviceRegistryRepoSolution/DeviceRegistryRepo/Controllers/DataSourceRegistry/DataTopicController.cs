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

namespace DataSourceRegistry.Controllers
{
    [Route("registry-repo/[controller]")]
    public class DataTopicController : Controller
    {
        private DeviceRegistryContext _dbContext;
        private readonly ILogger _logger;


        public DataTopicController(DeviceRegistryContext context, ILogger<DataTopicController> logger)
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
        /// Get a List of all Data Topics
        /// </summary>
        /// <returns>A List of Data Topics</returns>
        /// <response code="200">Returns a List of Data Topics</response>
        /// <response code="404">If there are no Data Topics</response>           
        [HttpGet]
        [ProducesResponseType(typeof(List<DataTopic>), 200)]
        [ProducesResponseType(typeof(Nullable), 404)]
        public IActionResult Get()
        {
            var list = _dbContext.DataTopics
                                 .ToList();
            if (list == null)
            {
                return NotFound();
            }

            return Ok(list);
        }

        // GET api/values/5
        /// <summary>
        /// Gets a Data Topic with a specific UUID
        /// </summary>
        /// <returns>A Data Topic with a specific UUID</returns>
        /// <response code="200">Returns the requested Data Topic</response>
        /// <response code="404">If there is no Data Topic with the supplied UUID</response>    
        [HttpGet("{id}")]
        [ProducesResponseType(typeof(DataTopic), 200)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Get(Guid id)
        {
            var result = (DataTopic)_dbContext.DataTopics.Find(id);

            if (result != null)
                return Ok(result);
            else
                return NotFound(id);
        }

        // GET api/values/5/uri?uri=dsd://mydomain/mydsd
        /// <summary>
        /// Gets a Data Topic with a specific URI
        /// </summary>
        /// <returns>A Data Topic with a specific URI</returns>
        /// <response code="200">Returns the requested Data Topic</response>
        /// <response code="404">If there is no Data Topic with the supplied URI</response>
        [HttpGet("uri")]
        [ProducesResponseType(typeof(DataTopic), 200)]
        [ProducesResponseType(typeof(String), 404)]
        public IActionResult Get([FromQuery]String uri)
        {
            var result = (DataTopic)_dbContext.DataTopics
                                   .Single(dt => dt.URI == uri);


            if (result != null)
                return Ok(result);
            else
                return NotFound(uri);
        }

        // POST api/values
        /// <summary>
        /// Creates a new Data Topic
        /// </summary>
        /// <remarks>
        /// It not Ids (UUIDs) are provided, a new Data Topic a provided the 
        /// URI are unique.
        /// 
        /// Otherwise the operation fails fails.
        /// </remarks>
        /// <returns>The Data Topic as saved in the database</returns>
        /// <response code="201">The Data Topic as saved in the database</response>
        /// <response code="400">If it fails to save the Data Topic</response>
        [HttpPost]
        [ProducesResponseType(typeof(DataTopic), 201)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Post([FromBody]DataTopic value)
        {
            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {

                _dbContext.DataTopics.Add(value);
                _dbContext.SaveChanges();

                return Created("registry-repo/DataTopic", _dbContext.Entry(value).GetDatabaseValues());
            }
            catch (Exception e)
            {
                _logger.LogError(e.Message);
                return BadRequest(e.Message);
            }
        }

        // PUT api/values/5
        /// <summary>
        /// Updates a Data Topic
        /// </summary>
        /// <returns>The Updated Data Topic as saved in the database</returns>
        /// <response code="200">The Updated Data Topic as saved in the database</response>
        /// <response code="400">If it fails to update the Data Topic</response>
        [HttpPut("{id}")]
        [ProducesResponseType(typeof(DataTopic), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Put(Guid id, [FromBody]DataTopic value)
        {
            //return BadRequest();

            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                var originalDataTopic = (DataTopic)_dbContext.DataTopics.Find(id);

                _dbContext.Entry(originalDataTopic).CurrentValues.SetValues(value);
                _dbContext.SaveChanges();

                return Ok(_dbContext.Entry(value).GetDatabaseValues());
            }
            catch (Exception ex)
            {
                _logger.LogError(ex.StackTrace);
                return BadRequest();
            }
        }

        // DELETE api/values/5
        /// <summary>
        /// Deletes a Data Topic with a specific UUID
        /// </summary>
        /// <remarks>
        /// If the Data Topic is referenced by Data Source Definitions, delete is
        /// not permitted.
        /// </remarks>
        /// <returns>The UUID of the deleted Data Topic</returns>
        /// <response code="200">The UUID of the deleted Data Topic</response>
        /// <response code="400">If it fails to delete the Data Topic</response>
        /// <response code="404">If it fails to find the Data Topic to be deleted</response>
        [HttpDelete("{id}")]
        [ProducesResponseType(typeof(Guid), 200)]
        [ProducesResponseType(typeof(String), 400)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Delete(Guid id)
        {
            var dataTopicToBeDeleted = _dbContext.DataTopics.Find(id);

            var listOfDataSourcesReferencing = _dbContext.DataSourceDefinitions.Where(dsd => dsd.DataTopic.DataTopicId == id);


            if (dataTopicToBeDeleted != null && listOfDataSourcesReferencing.Count()== 0)
            {
                try
                {
                    _dbContext.Remove(dataTopicToBeDeleted);
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
                if (listOfDataSourcesReferencing.Count()>0)
                {
                    return BadRequest("The Data Topic to be deleted is being referenced in Data Source Definitions, so it cannot be deleted");
                }
                else
                    return NotFound(id);
            }
        }
    }
}
