package com.qdm.SequenceGenerator.common;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


public class Syslog {

	/** The log. */
	private Logger _log;
	
	/**
	 * The Class LogTypes.
	 */
	private class LogTypes
	{
		
		/** The Constant error. */
		public static final String error = "error";
		
		/** The Constant debug. */
		public static final String debug = "debug";
		
		/** The Constant info. */
		public static final String info = "info";
		
		/** The Constant warn. */
		public static final String warn = "warn";
	}
	
	/**
	 * Instantiates a new sys log.
	 *
	 * @param objclass the objclass
	 */
	public Syslog(Class<?> objclass)
	{
		_log = Logger.getLogger(objclass);
	}
	
	/**
	 * Error.
	 *
	 * @param exp the exp
	 */
	public void error(Exception exp)
	{
		error(buildmessage(exp,LogTypes.error));
	}
	
	/**
	 * Error.
	 *
	 * @param message the message
	 */
	public void error(String message)
	{
		_log.error(message);
	}
	
	
	/**
	 * Debug.
	 *
	 * @param exp the exp
	 */
	public void debug(Exception exp)
	{
		debug(buildmessage(exp,LogTypes.debug));
	}
	
	/**
	 * Debug.
	 *
	 * @param message the message
	 */
	public void debug(String message)
	{
		_log.debug(message);
	}
	
	/**
	 * Info.
	 *
	 * @param exp the exp
	 */
	public void info(Exception exp)
	{
		info(buildmessage(exp,LogTypes.info));
	}
	
	/**
	 * Info.
	 *
	 * @param message the message
	 */
	public void info(String message)
	{
		_log.info(message);
	}
	
	/**
	 * Warn.
	 *
	 * @param exp the exp
	 */
	public void warn(Exception exp)
	{
		warn(buildmessage(exp,LogTypes.warn));
	}
	
	/**
	 * Warn.
	 *
	 * @param message the message
	 */
	public void warn(String message)
	{
		_log.warn(message);
	}
	
	/**
	 * Buildmessage.
	 *
	 * @param exp the exp
	 * @param type the type
	 * @return the string
	 */
	private String buildmessage(Exception exp,String type)
	{
		Writer writer = new StringWriter();
		String result = new String();
		PrintWriter printWriter = null;
		try
		{
			result = new SimpleDateFormat("yyyyMMdd hh:mm:ss").format(new Date()) + " " + type + " " + exp.getClass().getName() + " - [" + type + "  ]" + "\r\n";
			result = result + "Message: " + exp.getMessage() + "\r\n";
			
		    printWriter = new PrintWriter(writer);
			exp.printStackTrace(printWriter);
			result = result + "StackTrace" + "\r\n" + writer.toString() + "\r\n\r\n";
		}
		catch(Exception ex)
		{
			
		}
		finally
		{
			printWriter = null;
			writer = null;
		}
		return result;
	}

}
