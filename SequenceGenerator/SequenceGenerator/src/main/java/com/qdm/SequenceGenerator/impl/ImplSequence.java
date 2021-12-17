package com.qdm.SequenceGenerator.impl;

import com.arangodb.ArangoDB;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qdm.SequenceGenerator.businesslogics.DBTrasaction;
import com.qdm.SequenceGenerator.data.Database;
import com.qdm.SequenceGenerator.services.ISequence;

public class ImplSequence implements ISequence {
	public static  long empcount=0;

	@Override
	public String GetList(JsonObject inputObj) {
		try {
			System.out.print("test");
		} catch (Exception e) {
			System.out.print("test");
		}
		return "";
	}

	@Override
	public String GenerateSequence(JsonObject inputObj) {
		Database context=new Database();
		DBTrasaction dbTrans=new DBTrasaction();
		try {
			ArangoDB arangoDb=context.getArangoRootConnection();
//			String query="FOR doc IN QDMSequence FILTER doc._key=='10001' UPDATE doc WITH {Lastvalue:TO_NUMBER(doc.Lastvalue)+1} IN QDMSequence OPTIONS { exclusive: true }";
//			JsonArray resultArr=context.executeAQL("QDMTestUsers", query);
			JsonObject inpObj=new JsonObject();
			inpObj.addProperty("Lastvalue", 1);
			dbTrans.QDMSeqUpdateDocument(inputObj);
			//arangoDb.db("Vectras01")
			//return resultArr.toString();
		} catch (Exception e) {
			System.out.print("Error= "+e.getStackTrace());
		}
		finally {
			context=null;
		}
		return "";
	}
	
	@Override
	public String InsertDocuments(JsonObject inputObj) {
		Database context=new Database();
		DBTrasaction dbtran=new DBTrasaction();
		try {
			ArangoDB arangoDb=context.getArangoRootConnection();
//			String aqlQuery = " upsert " + filter + " insert " + inputObj + " " + "update  " + inputObj + " in " + collectionName
//					+ " " + " OPTIONS { exclusive: true } RETURN { doc: NEW, type: OLD ? 'update' : 'insert'} ";
//			JsonArray resultArr=context.executeAQL("QDMTestUsers", aqlQuery);

			dbtran.SaveDocument(inputObj);
			//return resultArr.toString();
		} catch (Exception e) {
			System.out.print("Error= "+e.getStackTrace());
		}
		finally {
			context=null;
		}
		return "";
	}
	
	@Override
	public String ReadDocuments(JsonObject inputObj) {
		Database context=new Database();
		DBTrasaction dbtran=new DBTrasaction();
		try {
			String collectionName="QDMUsers";
			JsonObject filter=new JsonObject();
			filter.addProperty("_key", "");
			
			ArangoDB arangoDb=context.getArangoRootConnection();
//			String aqlQuery = " upsert " + filter + " insert " + inputObj + " " + "update  " + inputObj + " in " + collectionName
//					+ " " + " OPTIONS { exclusive: true } RETURN { doc: NEW, type: OLD ? 'update' : 'insert'} ";
//			JsonArray resultArr=context.executeAQL("QDMTestUsers", aqlQuery);
			
			//arangoDb.db("Vectras01")
			return dbtran.ReadDocument();
			//return resultArr.toString();
		} catch (Exception e) {
			System.out.print("Error= "+e.getStackTrace());
		}
		finally {
			context=null;
		}
		return "";
	}

	@Override
	public String generateSequence(JsonObject inputObj) {
		DBTrasaction dbtran=new DBTrasaction();
		return dbtran.SaveandReadSequence(inputObj);
	}

}
