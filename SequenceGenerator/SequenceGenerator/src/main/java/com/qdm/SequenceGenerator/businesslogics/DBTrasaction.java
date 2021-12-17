package com.qdm.SequenceGenerator.businesslogics;

import java.util.concurrent.locks.ReentrantLock;

import com.arangodb.ArangoDB;
import com.arangodb.model.TransactionOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qdm.SequenceGenerator.data.Database;
import com.qdm.SequenceGenerator.impl.ImplSequence;

public class DBTrasaction {
	
	public String SaveandReadSequence(JsonObject inputObj) {
		try {
			String colName="Employees";
			WriteLock(colName, "empcode");
			ReadSequence(colName, "empcode");
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			
		}
		return "";
	}
	public String SaveDocument(JsonObject inputObj) {
		ArangoDB arangoDB;
		Database context=new Database();
		ReentrantLock lock = new ReentrantLock();
		String seq = null;
		try {
			lock.lock();
			arangoDB=context.getArangoRootConnection();
			String colName="Employees";
			seq = QDMsequenceread(colName,"empcode");
            inputObj.addProperty("empcode", seq);
			String transactionAction = "function (id) {" + "var db = require('internal').db; "
					+ "db."+colName+".save("+inputObj+");"
					+" }";
			
			TransactionOptions options = new TransactionOptions();
			//options.exclusiveCollections("QDMUsers");
			options.writeCollections(colName);
			String response = arangoDB.db("QDMTestUsers").transaction(transactionAction, String.class, options);
		System.out.println("Transaction Save");
		} catch (Exception e) {
			System.out.print("Test");
		}
		finally {
			lock.unlock();
		}
		return "";
	}
	
	
	public String QDMSeqUpdateDocument(JsonObject inputObj) {
		ArangoDB arangoDB;
		Database context=new Database();
		try {
			arangoDB=context.getArangoRootConnection();
			//String transactionAction="db.QDMUsers.save("+inputObj+");";
			String transactionAction = "function (id) {" + "var db = require('internal').db; "
					+ "db.QDMSequence.update('10002',"+inputObj+");"
					+" }";
			
			TransactionOptions options = new TransactionOptions();
		 options.exclusiveCollections("QDMSequence");
			options.writeCollections("QDMSequence");
			String response = arangoDB.db("QDMTestUsers").transaction(transactionAction, String.class, options);
		System.out.println("Transaction Update");
		} catch (Exception e) {
			System.out.print("Test");
		}
		return "";
	}
	
	public String ReadDocument() {
		ArangoDB arangoDB;
		Database context = new Database();
		try {
			String colName="Employees";
			arangoDB = context.getArangoRootConnection();
			// String transactionAction="db.QDMUsers.save("+inputObj+");";
			String transactionAction = "function (id) {" + "var db = require('internal').db; "
					+ "return db."+colName+".toArray();" + " }";

			TransactionOptions options = new TransactionOptions();
			options.readCollections("Employees");
			String response = arangoDB.db("QDMTestUsers").transaction(transactionAction, String.class, options);
			System.out.println("Transaction Read");
			return response;
		} catch (Exception e) {
			System.out.print("Test");
		}
		return "";
	}
	
	public String QDMsequenceread(String entityName,String fieldName) {
		ArangoDB arangoDB;
		Database context = new Database();
		try {
			String seqcolName="QDMSequence";
			String seqconfName="QDMSequenceConfig";
			arangoDB = context.getArangoRootConnection();
		
			String transaction = "function (id) {" + "var db = require('internal').db; "
					+"var doc = db."+seqconfName+".toArray().filter(function(value){return value.entityName=='"+entityName+"' && value.fieldName=='"+fieldName+"'});"
					+"var seqrule = doc[0].rule;"
					+"return seqrule;"
					+"}";
			TransactionOptions opt = new TransactionOptions();
			opt.exclusiveCollections(seqconfName);
			opt.readCollections(seqconfName);
			String ruleresponse = arangoDB.db("QDMPlatform_test").transaction(transaction, String.class, opt);
			
			String transactionAction = "function (id) {"
					+"var db = require('internal').db;" 
					+"var doc=db."+seqcolName+".toArray().filter(function(value){return value.entityName=='"+entityName+"' && value.fieldName=='"+fieldName+"'});"
					+"var updoc=db."+seqcolName+".update(doc[0]._key,{Lastvalue:(parseInt(doc[0].Lastvalue)+1)});"
					+"var seqdoc=doc[0].Lastvalue+(parseInt(doc[0].incrementBy));"
					+"return seqdoc;" 
					+"}";
			
			TransactionOptions options = new TransactionOptions();
			options.exclusiveCollections(seqcolName);
			options.readCollections(seqcolName);
			String seqresponse = arangoDB.db("QDMTestUsers").transaction(transactionAction, String.class, options);
			String response = ruleresponse +"_"+seqresponse;
			System.out.println("Transaction Read");
			return response;
		} catch (Exception e) {
			System.out.print("Test");
		}
		return "";
	}
	
	public String  WriteLock(String entityName,String fieldName) {
		ArangoDB arangoDB;
		Database context = new Database();
		try {
			arangoDB = context.getArangoRootConnection();
			String colName="QDMSequence";
//			String transactionAction = "function (id) {" + "var db = require('internal').db; "
//			        +"var doc=db."+colName+".toArray().filter(function(value){value.entityName=='"+entityName+"'}); "
//			        +" doc.Lastvalue==doc.Lastvalue+1; "
//					+ " return db."+colName+".update(doc._key,doc);" + " }";
			
			String transactionAction = "function (id) {" + "var db = require('internal').db; "
			        +"var doc=db."+colName+".toArray().filter(function(value){return value.entityName=='"+entityName+"' && value.fieldName=='"+fieldName+"'}); "
			        +"var updoc=db."+colName+".update(doc[0]._key,{Lastvalue:(parseInt(doc[0].Lastvalue)+1)}); "
					+ " return updoc;" + " }";

			TransactionOptions options = new TransactionOptions();
			options.exclusiveCollections(colName);
			options.writeCollections(colName);
			options.readCollections(colName);
			String response = arangoDB.db("QDMTestUsers").transaction(transactionAction, String.class, options);
			System.out.println("Transaction writelock response= "+response);
		} catch (Exception e) {
			System.out.print("write lock test");
		}
		return "";
	}
	
	public String  ReadSequence(String entityName,String fieldName) {
		ArangoDB arangoDB;
		Database context = new Database();
		try {
			arangoDB = context.getArangoRootConnection();
			String colName="QDMSequence";
//			String transactionAction = "function (id) {" + "var db = require('internal').db; "
//			        +"var doc=db."+colName+".toArray().filter(function(value){value.entityName=='"+entityName+"'}); "
//			        +" doc.Lastvalue==doc.Lastvalue+1; "
//					+ " return db."+colName+".update(doc._key,doc);" + " }";
			
			String transactionAction = "function (id) {" + "var db = require('internal').db; "
			        +"var doc=db."+colName+".toArray().filter(function(value){return value.entityName=='"+entityName+"' && value.fieldName=='"+fieldName+"'}); "
			  
					+ " return doc;" + " }";

			TransactionOptions options = new TransactionOptions();
			//options.exclusiveCollections(colName);
			options.writeCollections(colName);
			//options.readCollections(colName);
			String response = arangoDB.db("QDMTestUsers").transaction(transactionAction, String.class, options);
			System.out.println("Transaction read response= "+response);
		} catch (Exception e) {
			System.out.print("read sequence test");
		}
		return "";
	}
}
