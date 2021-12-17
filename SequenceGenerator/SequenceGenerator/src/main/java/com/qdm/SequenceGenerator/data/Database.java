package com.qdm.SequenceGenerator.data;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.google.gson.JsonArray;
import com.qdm.SequenceGenerator.common.JSON;
import com.qdm.SequenceGenerator.common.Settings;

public class Database {
	static ArangoDB rootconnection;
	static ArangoDB readconnection;
	
	
	public ArangoDB getArangoRootConnection() {
		// ArangoDB arangoDB = null;
		try {
			String isClusterNode = (String) Settings.get("cluster_node") == null ? "false"
					: Settings.get("cluster_node");
			if (isClusterNode.equals("true")) {
//				if (Database.rootconnection == null)
//					Database.rootconnection = new ArangoDB.Builder()
//							.host(Settings.get("ArangoDB_Server"), Integer.parseInt(Settings.get("ArangoDB_Port")))
//							.user(Settings.get("ArangoDB_Username")).password(Settings.get("ArangoDB_Password"))
//							.useSsl(true).sslContext(new CommonFunctions().createSslContext()).build();
			} else {
				if (Database.rootconnection == null)
					Database.rootconnection = new ArangoDB.Builder()
							.host(Settings.get("ArangoDB_Server"), Integer.parseInt(Settings.get("ArangoDB_Port")))
							.user(Settings.get("ArangoDB_Username")).password(Settings.get("ArangoDB_Password"))
							.build();
			}

		} catch (Exception e) {
			System.out.print("test");
		//	_log.error(e);
		}
		return Database.rootconnection;
	}
	
	public JsonArray executeAQL(String db_name, String query) {
		JsonArray jresponse = new JsonArray();
		ArangoCursor<String> cursor = null;
		ArangoDB arangoDB;
		String cursorvalue = "";
		try {
			arangoDB = getArangoRootConnection();
			cursor = arangoDB.db(db_name).query(query, null, null, String.class);
			while (cursor.hasNext()) {
				cursorvalue = cursor.next();

				try {
					jresponse.add(JSON.DeserializeObject(cursorvalue).getAsJsonObject());
				} catch (Exception ex) {
					try {
						jresponse.add(JSON.DeserializeObject(cursorvalue).getAsJsonArray());
					} catch (Exception e) {
						try {
							cursorvalue = cursorvalue.replace("\\", "\\\\");
							jresponse.add(JSON.DeserializeObject(cursorvalue).getAsJsonObject());
						} catch (Exception e2) {
//							if (Utilites.isNumeric(cursorvalue)) {
//								jresponse.add(Integer.parseInt(cursorvalue));
//							} else {
//								jresponse.add(cursorvalue);
//							}
						}
					}
				}
			}
		} catch (Exception exp) {
		System.out.print("Error= "+exp);
		} finally {
			cursor = null;
			// arangoDB = null;
		}
		return jresponse;
	}


}
