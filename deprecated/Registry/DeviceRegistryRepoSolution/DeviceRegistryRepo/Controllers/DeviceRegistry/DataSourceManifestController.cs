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
    public class DataSourceManifestController : Controller
    {
        private DeviceRegistryContext _dbContext;
        private readonly ILogger _logger;


        public DataSourceManifestController(DeviceRegistryContext context, ILogger<DataSourceManifestController> logger)
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
        /// Get a List of all Data Source Manifests
        /// </summary>
        /// <returns>A List of Data Source Manifests</returns>
        /// <response code="200">Returns a List of Data Source Manifests</response>
        /// <response code="404">If there are no Data Source Manifests</response>           
        [HttpGet]
        [ProducesResponseType(typeof(List<DataSourceManifest>), 200)]
        [ProducesResponseType(typeof(Nullable), 404)]
        public IActionResult Get()
        {
            var list = _dbContext.DataSourceManifests
                                 .Include(dsm => dsm.DataSourceDefinition)
                                 .ThenInclude(dsd => dsd.DataTopic)
                                 .Include(dsm => dsm.DataSourceDefinition)
                                 .ThenInclude(dsd => dsd.DataInterfaceSpecification)
                                 .Include(dsm => dsm.DataSourceDefinitionParameters)
                                 .ThenInclude(p => p.Parameters)
                                 .ToList();
            if (list == null)
            {
                return NotFound();
            }

            //JArray returnArray = new JArray();



            //foreach( var item in list)
            //{
            //    JObject jsonItem = new JObject(item);

            //    //find DataTopicDefintion and Add it

            //    DataSourceDefinition dsd = _dbContext.DataSourceDefinitions
            //                                         .Where(d => d.URI == item.DataSourceDefinition)
            //                                         .FirstOrDefault();

            //    if (dsd == null) continue;

            //    JObject dsdToken = new JObject(dsd);

            //    jsonItem.AddFirst(dsdToken);

            //    returnArray.Add(jsonItem);
            //}

            //return Ok(returnArray);
            return Ok(list);
        }

        // GET api/values/5
        /// <summary>
        /// Gets a Data Source Manifest with a specific UUID
        /// </summary>
        /// <returns>A Data Source Manifest with a specific UUID</returns>
        /// <response code="200">Returns the requested Data Source Manifest</response>
        /// <response code="404">If there is no Data Source Manifest with the supplied UUID</response>    
        [HttpGet("{id}")]
        [ProducesResponseType(typeof(DataSourceManifest), 200)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Get(Guid id)
        {
            var result = (DataSourceManifest)_dbContext.DataSourceManifests
                                   .Include(dsm => dsm.DataSourceDefinition)
                                   .ThenInclude(dsd => dsd.DataTopic)
                                   .Include(dsm => dsm.DataSourceDefinition)
                                   .ThenInclude(dsd => dsd.DataInterfaceSpecification)
                                   .Include(dsm => dsm.DataSourceDefinitionParameters)
                                   .ThenInclude(p => p.Parameters)
                                   .Single(dsm =>dsm.DataSourceManifestId == id);

            _dbContext.Entry(result).Reference(b => b.DataSourceDefinition).Load();
            //_dbContext.Entry(result).Collection(b => b.DataSourceDefinitionParameters).;

            if (result != null)
                return Ok(result);
            else
                return NotFound(id);
        }

        // GET api/values/5/uri?uri=dsd://mydomain/mydsd
        /// <summary>
        /// Gets a Data Source Manifest with a specific URI
        /// </summary>
        /// <returns>A Data Source Manifest with a specific URI</returns>
        /// <response code="200">Returns the requested Data Source Manifest</response>
        /// <response code="404">If there is no Data Source Manifest with the supplied URI</response>
        [HttpGet("uri")]
        [ProducesResponseType(typeof(DataSourceManifest), 200)]
        [ProducesResponseType(typeof(String), 404)]
        public IActionResult Get([FromQuery]String uri)
        {
            var result = (DataSourceManifest)_dbContext.DataSourceManifests
                                   .Include(dsm => dsm.DataSourceDefinition)
                                   .ThenInclude(dsd => dsd.DataTopic)
                                   .Include(dsm => dsm.DataSourceDefinition)
                                   .ThenInclude(dsd => dsd.DataInterfaceSpecification)
                                   .Include(dsm => dsm.DataSourceDefinitionParameters)
                                   .ThenInclude(p => p.Parameters)
                                   .Single(dsm => dsm.URI == uri);
            
            _dbContext.Entry(result).Reference(b => b.DataSourceDefinition).Load();
            //_dbContext.Entry(result).Collection(b => b.DataSourceDefinitionParameters).;

            if (result != null)
                return Ok(result);
            else
                return NotFound(uri);
        }

        // POST api/values
        /// <summary>
        /// Creates a new Data Source Manifest
        /// </summary>
        /// <remarks>
        /// It not Ids (UUIDs) are provided, a new Data Source Manifest and a new
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
        [ProducesResponseType(typeof(DataSourceManifest), 201)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Post([FromBody]DataSourceManifest value)
        {
            if (ModelState.IsValid == false)
            {
                return BadRequest(ModelState);
            }

            try
            {
                if (value.DataSourceDefinition != null)
                {
                    var dsdId = value.DataSourceDefinition.DataSourceDefinitionId;
                    var dsd = _dbContext.DataSourceDefinitions.Find(dsdId);

                    value.DataSourceDefinition = dsd;

                }

                _dbContext.DataSourceManifests.Add(value);
                _dbContext.SaveChanges();

                return Created("registry-repo/DataSourceManifest", _dbContext.Entry(value).GetDatabaseValues());
            } 
            catch (Exception e)
            {
                _logger.LogError(e.StackTrace);
                return BadRequest();
            }
        }

        // PUT api/values/5
        /// <summary>
        /// Updates a Data Source Manifest
        /// </summary>
        /// <remarks>
        /// It only updates the fields of the Data Source Manifest. The fields 
        /// of the Data Source Definitions are not updated. If another UUID is
        /// used then the Data Source Manifest points to that Data Source
        /// Definition.
        /// </remarks>
        /// <returns>The Updated Data Source Manifest as saved in the database</returns>
        /// <response code="200">The Updated Data Source Manifest as saved in the database</response>
        /// <response code="400">If it fails to update the Data Source Manifest</response>
        [HttpPut("{id}")]
        [ProducesResponseType(typeof(DataSourceManifest), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]
        public IActionResult Put(Guid id, [FromBody]DataSourceManifest value)
        {

			if (ModelState.IsValid == false)
			{
				return BadRequest(ModelState);
			}

            try
            {
                var originalDataSourceManifest = (DataSourceManifest) _dbContext.DataSourceManifests
                                                           .Include(dsm => dsm.DataSourceDefinition)
                                                           .ThenInclude(dsd => dsd.DataTopic)
                                                           .Include(dsm => dsm.DataSourceDefinition)
                                                           .ThenInclude(dsd => dsd.DataInterfaceSpecification)
                                                           .Include(dsm => dsm.DataSourceDefinitionParameters)
                                                           .ThenInclude(p => p.Parameters)
                                                           .FirstOrDefault(o => o.DataSourceManifestId == id);
                //remove all child elements
                //_dbContext.Remove(originalDataSourceManifest.DataSourceDefinitionParameters);
                //_dbContext.Remove(originalDataSourceManifest.DataSourceDefinition);

                originalDataSourceManifest.DataSourceDefinitionParameters.Parameters = value.DataSourceDefinitionParameters.Parameters;

                //find if the DataSourceDefinition has changed
                var dsdorig = _dbContext.DataSourceDefinitions.Find(value.DataSourceManifestId);

                if (dsdorig != null)
                {
                    _dbContext.Remove(originalDataSourceManifest.DataSourceDefinition);
                    originalDataSourceManifest.DataSourceDefinition = dsdorig;
                }

                //originalDataSourceManifest.DataSourceDefinition = value.DataSourceDefinition;

                _dbContext.Entry(originalDataSourceManifest).CurrentValues.SetValues(value);
                _dbContext.SaveChanges();
                return Ok(_dbContext.Entry(originalDataSourceManifest).GetDatabaseValues());
            }
            catch (Exception ex)
            {
                _logger.LogError(ex.StackTrace);
                return BadRequest();
            }
        }

        // DELETE api/values/5
        /// <summary>
        /// Deletes a Data Source Manifest with a specific UUID
        /// </summary>
        /// <returns>The UUID of the deleted Data Source Manifest</returns>
        /// <response code="200">The UUID of the deleted Data Source Manifest</response>
        /// <response code="400">If it fails to delete the Data Source Manifest</response>
        /// <response code="404">If it fails to find the Data Source Manifest to be deleted</response>
        [HttpDelete("{id}")]
        [ProducesResponseType(typeof(Guid), 200)]
        [ProducesResponseType(typeof(Nullable), 400)]
        [ProducesResponseType(typeof(Guid), 404)]
        public IActionResult Delete(Guid id)
        {
			var manifest = _dbContext.DataSourceManifests
									.Include(dsm => dsm.DataSourceDefinition)
									.ThenInclude(dsd => dsd.DataTopic)
									.Include(dsm => dsm.DataSourceDefinition)
									.ThenInclude(dsd => dsd.DataInterfaceSpecification)
									.Include(dsm => dsm.DataSourceDefinitionParameters)
									.ThenInclude(p => p.Parameters)
									.Single(d => d.DataSourceManifestId == id);

            if (manifest != null)
            {
                try
                {
                    _dbContext.Remove(manifest);
                    _dbContext.SaveChanges();
                    return Ok(id);
                } catch (Exception e)
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
