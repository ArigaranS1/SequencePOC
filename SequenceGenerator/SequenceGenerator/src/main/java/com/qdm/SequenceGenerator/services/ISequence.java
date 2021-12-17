package com.qdm.SequenceGenerator.services;

import com.google.gson.JsonObject;
import com.qdm.SequenceGenerator.common.IService;

public interface ISequence extends IService{
	public String GetList(JsonObject inputObj);
	public String GenerateSequence(JsonObject inputObj);
	public String InsertDocuments(JsonObject inputObj);
	public String ReadDocuments(JsonObject inputObj);
	public String generateSequence(JsonObject inputObj);
}
