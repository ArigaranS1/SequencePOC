package com.qdm.SequenceGenerator.services;

import java.util.Hashtable;

import com.qdm.SequenceGenerator.common.IService;

public class services {
	private static Hashtable<String, Object> _serviceHashtable = new Hashtable<String, Object>();
	private services() { }
	
	@SuppressWarnings("deprecation")
	public static IService get(String serviceName) throws Exception
    {
		IService _svc=null;
        if (!_serviceHashtable.containsKey(serviceName))
        {
            String _classname = serviceName;
            try
            {
            	 Class<?> clazz = Class.forName(serviceName);
                _svc = (IService)clazz .newInstance();
            	 
                if (_svc != null) { _serviceHashtable.put(serviceName, _svc); }
                else throw new Exception("Fail to Load Class for [{0}] [{1}]" + serviceName + _classname);
            }
            catch (Exception e)
            {
                throw new Exception("Fail to Load Class for [{0}] [{1}]" + serviceName + _classname + e.getMessage());
            }
        }
        return (IService)_serviceHashtable.get(serviceName); 
    }
	
	 public static class ServicePoints 
	 {
    	public static final String SEQUENCESERVICE = "com.qdm.SequenceGenerator.impl.ImplSequence";
    	
	 }
}
