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
    public class DataChannelDescriptorController : Controller
    {
        private DeviceRegistryContext _dbContext;
        private readonly ILogger _logger;


        public DataChannelDescriptorController(DeviceRegistryContext context, ILogger<DataChannelDescriptorController> logger)
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
        /// Get a List of all Data Channel Descriptors
        /// </summary>
        /// <remarks>
        /// Only the UUIDs of the Data Source Manifest and the Data Consumer
        /// Manifest are shown.
        /// </remarks>
        /// <returns>A List of Data Channel Descriptors</returns>
        /// <response code="200">Returns a List of Data Channel Descriptors</response>
        /// <response code="404">If there are no Data Channel Descriptors</response>           
        [HttpGet]
        [ProducesResponseType(typeof(List<DataChannelDescriptor>), 200)]
        [ProducesResponseType(typeof(Nullable), 404)]
        public IActionResult Get()
        {
            var list = _dbContext.DataChannelDescriptors
                                 .Include(dcd => dcd.DataSourceManifest.DataSourceManifestId)
                                 .Include(dcd => dcd.DataConsumerManifest.DataConsumerManifestId)
                                 .ToList();
            if (list == null)
            {
                return NotFound();
            }
           
            return Ok(list);
        }

        // GET api/values/5
        /// <summary>
        /// Gets a Data Channel Descriptor with a specific UUID
        /// </summary>
        /// <returns>A Data Channel Descriptor with a specific UUID</returns>
        /// <response code="200">Returns the requested Data Channel Descriptor</response>
        /// <response code="404">If there is no Data Channel Descriptor with the supplied UUID</response>    
        [HttpGet("{id}")]
        [ProducesResponseType(typeof(DataChannelDescriptor), 200)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Get(Guid id)
        {
            var result = (DataChannelDescriptor)_dbContext.DataChannelDescriptors
                                   .Include(dcd => dcd.DataSourceManifest.DataSourceManifestId)
                                   .Include(dcd => dcd.DataConsumerManifest.DataConsumerManifestId)
                                    .Single(dsm => dsm.DataChannelDescriptorId == id);

            if (result != null)
                return Ok(result);
            else
                return NotFound(id);
        }

        // GET api/values/5/uri?uri=dsd://mydomain/mydsd
        /// <summary>
        /// Gets a Data Channel Descriptor with a specific URI
        /// </summary>
        /// <returns>A Data Channel Descriptor with a specific URI</returns>
        /// <response code="200">Returns the requested Data Channel Descriptor</response>
        /// <response code="404">If there is no Data Channel Descriptor with the supplied URI</response>
        [HttpGet("uri")]
        [ProducesResponseType(typeof(DataChannelDescriptor), 200)]
        [ProducesResponseType(typeof(String), 404)]

        public IActionResult Get([FromQuery]String uri)
        {
            var result = (DataChannelDescriptor)_dbContext.DataChannelDescriptors
                                   .Include(dcd => dcd.DataSourceManifest.DataSourceManifestId)
                                   .Include(dcd => dcd.DataConsumerManifest.DataConsumerManifestId)
                                   .Single(dcd => dcd.URI == uri);


            if (result != null)
                return Ok(result);
            else
                return NotFound(uri);
        }

        // POST api/values
        /// <summary>
        /// Creates a new Data Channel Descriptor
        /// </summary>
        /// <remarks>
        /// Only the ids of the Data Source and Data Consumer Manifests need
        /// to be provided. But it works if the whole manifests are provided.
        /// </remarks>
        /// <returns>The Data Channel Descriptor as saved in the database</returns>
        /// <response code="201">The Data Channel Descriptor as saved in the database</response>
        /// <response code="400">If it fails to save theData Channel Descriptor</response>
        [HttpPost]
        [ProducesResponseType(typeof(DataChannelDescriptor), 201)]
        [ProducesResponseType(typeof(String), 400)]
        public IActionResult Post([FromBody]DataChannelDescriptor value)
        {
            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                //find manifests
                var dsd = _dbContext.DataSourceManifests.Find(value.DataSourceManifest.DataSourceManifestId);
                var dcd = _dbContext.DataConsumerManifests.Find(value.DataConsumerManifest.DataConsumerManifestId);

                if (dsd != null && dcd != null)
                {
                    value.DataConsumerManifest = dcd;
                    value.DataSourceManifest = dsd;

                    _dbContext.DataChannelDescriptors.Add(value);
                    _dbContext.SaveChanges();
                    return Created("registry-repo/DataConsumerManifest", _dbContext.Entry(value).GetDatabaseValues());
                }
                else
                {
                    return BadRequest("The Manifests do not exist in the registry");
                }

            }
            catch (Exception e)
            {
                _logger.LogError(e.StackTrace);
                return BadRequest();
            }
        }

        // PUT api/values/5
        /// <summary>
        /// Updates a Data Channel Descriptor
        /// </summary>
        /// <remarks>
        /// It only updates the fields of the Data Channel Descriptor. The fields 
        /// of the Data Source Definitions are not updated. If another UUIDs for
        /// manifests are used then the Data Channel Descriptort points to that 
        /// manifests.
        /// </remarks>
        /// <returns>The Updated Data Channel Descriptor as saved in the database</returns>
        /// <response code="200">The Updated Data Channel Descriptor as saved in the database</response>
        /// <response code="400">If it fails to update the Data Channel Descriptor</response> 
        [HttpPut("{id}")]
        [ProducesResponseType(typeof(DataChannelDescriptor), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]       
        public IActionResult Put(Guid id, [FromBody]DataChannelDescriptor value)
        {
            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                var originalDataChannelDescriptor = (DataChannelDescriptor)_dbContext.DataChannelDescriptors
                                                           .Include(dcd => dcd.DataSourceManifest.DataSourceManifestId)
                                                           .Include(dcd => dcd.DataConsumerManifest.DataConsumerManifestId)
                                                           .FirstOrDefault(o => o.DataChannelDescriptorId == id);

                DataSourceManifest dataSourceManifest = (from dsm in _dbContext.DataSourceManifests
                                                         where dsm.DataSourceManifestId == value.DataSourceManifest.DataSourceManifestId
                                                         select dsm).FirstOrDefault();

                DataConsumerManifest dataConsumerManifest = (from dcm in _dbContext.DataConsumerManifests
                                                             where dcm.DataConsumerManifestId == value.DataConsumerManifest.DataConsumerManifestId
                                                             select dcm).SingleOrDefault();

                if (dataSourceManifest != null)
                {
                    originalDataChannelDescriptor.DataSourceManifest = dataSourceManifest;
                }

                if (dataConsumerManifest != null)
                {
                    originalDataChannelDescriptor.DataConsumerManifest = dataConsumerManifest;
                }

                _dbContext.Entry(originalDataChannelDescriptor).CurrentValues.SetValues(value);
                _dbContext.SaveChanges();
                return Ok(_dbContext.Entry(originalDataChannelDescriptor).GetDatabaseValues());
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
            var descriptor = _dbContext.DataChannelDescriptors.Find(id);
                                   

            if (descriptor != null)
            {
                try
                {
                    _dbContext.Remove(descriptor);
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
