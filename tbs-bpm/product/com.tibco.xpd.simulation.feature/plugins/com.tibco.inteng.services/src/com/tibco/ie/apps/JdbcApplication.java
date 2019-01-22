/* 
 ** 
 **  MODULE:             $RCSfile: UrlServerApplication.java $ 
 **                      $Revision: 1.4 $ 
 **                      $Date: 2005/05/22 13:55:45Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: UrlServerApplication.java $ 
 **    Revision 1.4  2005/05/22 13:55:45Z  tstephen 
 **    changes related to refactoring of default bundle in inteng-web (and test for it)  
 **    Revision 1.3  2005/03/01 19:28:01Z  KamleshU 
 **    Changes made due to change in the structure of the extended attributes 
 **    Revision 1.2  2004/12/06 12:53:17Z  TimST 
 **    javadoc only  
 **    Revision 1.1  2004/11/10 09:24:36Z  Timst 
 **    Initial revision 
 */
package com.tibco.ie.apps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.tibco.intEng.JDBCApplicationType;
import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.XpdlBusinessException;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.XpdlSimpleData;

/**
 * Exec some arbitrary SQL and pass results back to the process.
 * 
 * <p>
 * Required extended attributes: Defined by the JDBCApplication element in the 
 * <code>IntEngXPDLExtensions.xsd</code>
 * </p>
 * 
 * @author Tim
 */
public class JdbcApplication extends AbstractApplication implements
		AutomaticApplication {

	/**
	 * Public name for this application. Also used to identify relevant extended
	 * attribute.
	 */
	public static final String JDBC_APP_NAME = "JDBCApplication";

	private static final String EL_ROOT = "result-set";

	private static final String EL_ROW = "row";

	/**
	 * Logger for this class.
	 */
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * Default constructor.
	 */
	public JdbcApplication() {
	}

	/**
	 * @param args The last argument is assumed to be the place to put the 
	 * results. All other arguments are assumed to be SQL parameters, and 
	 * an attempt to bind them (in order) to the <code>PreparedStatement</code>  
	 * will be made.
	 * @throws
	 * @see com.staffware.ie.apps.XpdlAutomaticApplication#invoke(String,
	 *      ExtendedAttribute[], XpdlData[])
	 */
	public void invoke(String applicationId, ExtendedAttribute[] exts,
			XpdlData[] args) throws XpdlBusinessException {
		log.info("enter: invoke");

		// assume last position is output variable
		XpdlData outData = args[args.length - 1];
		// all others should be SQL params
		List paramData = new ArrayList() ; 
		// annoyingly cannot do this with Arrays.asList as remove is not 
		// supported in resultant list 
		for (int i = 0 ; i < args.length - 1 ; i++) { 
			paramData.add(args[i]) ; 
		}
		
		ExtendedAttribute jdbcConfig = getExtendedAttribute(JDBC_APP_NAME, exts);
		Connection conn = null;
		XmlCursor cur = null;
		try {
			if (jdbcConfig == null) {
				XpdlException e = new XpdlException(
						"Missing Extended Attribute '" + JDBC_APP_NAME
								+ "' for applicationId= " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			log.info("Have config: " + jdbcConfig);

			String xmlString = jdbcConfig.getXmlString();
			XmlObject xmlObject = XmlObject.Factory.parse(xmlString);
			cur = xmlObject.newCursor();
			if (!cur.toFirstChild()) {
				XpdlException e = new XpdlException(
						"Missing Extended Attribute '" + JDBC_APP_NAME
						+ "' for applicationId= " + applicationId);
				log.error(e.getMessage(), e);
				throw e;
			}
			JDBCApplicationType type = (JDBCApplicationType) (cur.getObject().changeType(JDBCApplicationType.type));
			conn = getConnection(applicationId, type);

			if (type.getQuery() != null) {
				execQuery(applicationId, paramData, outData, conn, type);
			} else if (type.getUpdate() != null) {
				// TODO: support updates
				log.error("Support for JDBC update not implemented");
			} else {
				log.error("No SQL found to exec");
			}

		} catch (XpdlDataFormatException e) {
			String msg = "Data format exception in " + "application (ID: "
					+ applicationId + "): " + e.getMessage();
			log.warn(msg);
			XpdlException e1 = new XpdlException(msg, e);
			log.error(msg, e);
			throw e1;
		} catch (XmlException ex) {
			XpdlException e = new XpdlException(
					"Unable to parse Extended Attribute '" + JDBC_APP_NAME
							+ "' for applicationId= " + applicationId, ex);
			log.error(e.getMessage(), ex);
			throw e;
		} catch (NamingException e) {
			// TODO: Should be diff exception type???
			XpdlException e2 = new XpdlException(
					"Unable to find datasource specified for applicationId: "
							+ applicationId, e);
			log.error(e2.getMessage(), e);
			throw e2;
		} catch (SQLException e) {
			// TODO: Should be diff exception type???
			XpdlException e2 = new XpdlException(
					"Unable to exec SQL specified for applicationId= "
							+ applicationId, e);
			log.error(e2.getMessage(), e);
			throw e2;
		} finally {
			try {
				if (cur != null) {
					cur.dispose();
				}
			} catch (Exception e) {
				; // ignore
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// could be SQLException or NullPointerException
				; // ignore
			}
		}

		log.info("exit invoke");
	}

	private void execQuery(String applicationId, List paramData, 
			XpdlData outData, Connection conn, JDBCApplicationType type) 
	throws SQLException, XpdlDataFormatException {
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try { 
			log.warn("About to exec: \n" + type.getQuery());
			ps = conn.prepareStatement(type.getQuery());
			int j = 1 ;
			for (Iterator it = paramData.iterator() ; it.hasNext() ; j++) {
				try { 
					XpdlSimpleData param = (XpdlSimpleData) it.next();
					ps.setString(j, (String) param.getValue()) ;
//					int tmp = Integer.valueOf(
//							(String) param.getValue()).intValue();
//					log.warn("Binding param val: " + tmp) ; 
//					ps.setInt(j, tmp) ;
//					This not implemented in Int Eng
//					if ("STRING".equals(param.getType())) { 
//						ps.setString(j, (String) param.getValue()) ; 
//					} else if ("FLOAT".equals(param.getType())) { 
//						ps.setFloat(j, Float.valueOf(
//								(String) param.getValue()).floatValue()) ; 
//					} else if ("INTEGER".equals(param.getType())) { 
//						ps.setInt(j, Integer.valueOf(
//								(String) param.getValue()).intValue()) ; 
//					} else if ("DATETIME".equals(param.getType())) {
//						String msg = "DATETIME is not a supported parameter for "
//								+ JDBC_APP_NAME; 
//						throwXpdlException(msg, null); 
//					} else if ("BOOLEAN".equals(param.getType())) {
//						// TODO: Not at all sure this will work...
//						ps.setString(j, (String) param.getValue()) ; 
//					} else { 
//						// This rarely works for SQL Srvr, hope for the best...
//						ps.setObject(j, param.getValue()) ;
//					}
				} catch (ClassCastException e) {
					String msg = "JDBC parameters must be XPDL simple types " 
						+ "at this time" ;
					throwXpdlException(msg, e);  
				}
			}
			boolean results = ps.execute();
			if (results) {
				StringBuffer xmlResults = new StringBuffer();
				xmlResults.append("<" + EL_ROOT + ">");
				rs = ps.getResultSet();
				ResultSetMetaData meta = rs.getMetaData();
				// This used to prevent repeated warnings about XML data 
				boolean xmlWarning = false;
				while (rs.next()) {
					xmlResults.append("<" + EL_ROW + ">");
	
					for (int i = 0; i < meta.getColumnCount(); i++) {
						// open column tag
						xmlResults.append("<");
						xmlResults.append(meta.getColumnName(i + 1));
						xmlResults.append(">");
	
						// add the column's data
						// TODO: should provide column type ???
						String val = rs.getObject(meta.getColumnName(i + 1)).toString();
						val = val.trim(); 
						
						if (!xmlWarning && val.startsWith("<") && val.endsWith(">")) { 
							log.warn("It looks like you may have XML in column: "
									+ meta.getColumnName(i + 1)
									+ ", this is not supported.") ; 
							xmlWarning = true ; 
						}
						xmlResults.append(val);
	
						// close column tag
						xmlResults.append("</");
						xmlResults.append(meta.getColumnName(i + 1));
						xmlResults.append(">");
					}
					xmlResults.append("</" + EL_ROW + ">");
				}
				xmlResults.append("</" + EL_ROOT + ">");
	
				outData.setValue(xmlResults.toString());
				if (log.isDebugEnabled()) { 
					log.debug("xpdl data returned: " + outData);
				} 
			}
		} catch (SQLException e) {
			// TODO: Should be diff exception type???
			XpdlException e2 = new XpdlException(
					"Unable to exec SQL specified for applicationId= "
							+ applicationId, e);
			log.error(e2.getMessage(), e);
			throw e2;
		} finally { 
			try {
				// this closes ResultSet too (as long as it still in scope!)
				ps.close();
			} catch (Exception e) {
				// could be SQLException or NullPointerException 
				; // ignore
			}

		}
	}

	private void throwXpdlException(String msg, Exception cause) {
		XpdlException e2 = new XpdlException(msg, cause) ; 
		log.error(msg, e2); 
		throw e2;
	}

	private Connection getConnection(String applicationId,
			JDBCApplicationType type) throws NamingException, SQLException {
		log.info("entry: getConnection");
		log.warn("jdbc config: " + type);
		log.warn("JdbcConnection: " + type.getJdbcConnection());
		Connection conn = null;
		if (type.getDataSourceName() != null) {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(type.getDataSourceName());
			conn = ds.getConnection();
		} else if (type.getJdbcConnection() != null) {
			log.warn("The JDBC connection element should only be used in "
					+ "unit tests, when a JNDI tree is unavailable.");

			try {
				conn = DriverManager.getConnection(type.getJdbcConnection().getUrl(),
						type.getJdbcConnection().getUsername(), 
						type.getJdbcConnection().getPassword());
			} catch (SQLException e) {
				log.error("May be missing database driver?");
				log.error("  If so, use jdbc.drivers system property");
				throw e;
			}
		} else {
			XpdlException e = new XpdlException(
					"Must specify either DataSource or other connection info"
							+ " for applicationId: " + applicationId);
			log.error(e.getMessage(), e);
			throw e;
		}
		log.info("exit: getConnection: " + conn);
		return conn;
	}

}