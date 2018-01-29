using System;
using Microsoft.AspNetCore.Http;

namespace DeviceRegistry.Business
{
    public interface IUserResolverService
    {
        string GetUser();
       
    }
    
    public class UserResolverService : IUserResolverService
	{
        public readonly IHttpContextAccessor _context;
		
        public UserResolverService(IHttpContextAccessor context)
		{
			_context = context;
		}

        public string GetUser()
		{
			return _context?.HttpContext?.User?.Identity?.Name;
		}

    }
}
