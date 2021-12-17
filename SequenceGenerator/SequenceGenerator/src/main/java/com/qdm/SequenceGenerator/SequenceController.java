package com.qdm.SequenceGenerator;

import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.qdm.SequenceGenerator.common.JSON;
import com.qdm.SequenceGenerator.services.ISequence;
import com.qdm.SequenceGenerator.services.services;

@RestController
public class SequenceController {
    
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/InsertDocuments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String InsertDocuments(@RequestBody HashMap map) throws Exception {

		try {

			ISequence service = (ISequence) services.get(services.ServicePoints.SEQUENCESERVICE);
			return service.InsertDocuments((JsonObject) JSON.DeserializeObject(JSON.Serialize(map))).toString();
		} catch (Exception exp) {
			//_log.error(exp);
			return exp.getMessage();
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ReadDocuments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String ReadDocuments(@RequestBody HashMap map) throws Exception {

		try {

			ISequence service = (ISequence) services.get(services.ServicePoints.SEQUENCESERVICE);
			return service.ReadDocuments((JsonObject) JSON.DeserializeObject(JSON.Serialize(map))).toString();
		} catch (Exception exp) {
			//_log.error(exp);
			return exp.getMessage();
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/generateSequence", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String generateSequence(@RequestBody HashMap map) throws Exception {

		try {

			ISequence service = (ISequence) services.get(services.ServicePoints.SEQUENCESERVICE);
			return service.generateSequence((JsonObject) JSON.DeserializeObject(JSON.Serialize(map))).toString();
		} catch (Exception exp) {
			//_log.error(exp);
			return exp.getMessage();
		}
	}
}
