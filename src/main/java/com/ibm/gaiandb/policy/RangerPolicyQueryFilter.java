/*
 * (C) Copyright IBM Corp. 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations 
 * under the License.
 */

package com.ibm.gaiandb.policy;

import java.sql.ResultSetMetaData;
import com.ibm.gaiandb.Util;
import com.ibm.gaiandb.Logger;
import com.ibm.gaiandb.policyframework.SQLQueryFilter;
import java.util.Arrays;
import org.apache.derby.iapi.types.DataValueDescriptor;
import com.ibm.gaiandb.policyframework.SQLQueryElements; 

/*
 * Initial policy plugin for gaianDB, as part of the VirtualDataConnector project
 * 
 * This interface defines the methods that will be called by GaianDB to transform the incoming SQL or the SQL propagated to another node
 * or the SQL executed against a physical data source federated by the local node.
 * For each incoming query, a query ID is generated which will be the same for every resulting propagated query or query against
 * a physical data source.
 * 
 * This interface only currently makes it possible to reduce the number of queried cols or change/remove/add predicates to the qualifiers.
 * 
 * To activate this policy class, you first need to add this class to Gaian's classpath.
 * You can do this in several ways:
 * 	- Place this class under <Gaian install path>/policy/
 * 	- Add the path containing the top level package of this class (i.e. "policy") to the classpath in launchGaianServer.bat(/sh)
 *  - Build a jar file with this class inside it, and place that jar in <Gaian install path>/lib/ or <Gaian install path>/lib/ext/   
 * 
 * Finally, start the Gaian node and activate your policy - this can be done with the following SQL:
 * call setconfigproperty('SQL_QUERY_FILTER', 'policy.SamplePolicyNoFilters')
 * 
 * @author jonesn@uk.ibm.com
 */

public class RangerPolicyQueryFilter implements SQLQueryFilter {

	public static final String COPYRIGHT_NOTICE = "(c) Copyright IBM Corp. 2017";

	// Initialize gaianDB logging
	private static final Logger logger = new Logger( "RangerQueryFilter", 25 );
	
	/**
	 * Policy instantiation constructor - invoked for every new query.
	 * This instance will be re-used if the calling GaianTable results from a PreparedStatement which is re-executed by the calling application. 
	 */
	public RangerPolicyQueryFilter() {
		logger.logDetail("\nEntered RangerPolicyQueryFilter() constructor");
		System.out.println("\nEntered RangerPolicyQueryFilter() constructor **LOOK!!!**");
	}
	
	// Note: originalSQL encompasses more than the query. It may describe a join on multiple logical tables including the one here.
	public boolean applyIncomingSQLFilter( String queryID, String logicalTable, ResultSetMetaData logicalTableMetaData, String originalSQL, SQLQueryElements queryElmts )
	{
		logger.logDetail("Entered ApplyIncomingSQLFilter(), queryID: " + queryID + ", logicalTable: " + logicalTable + ", originalSQL: " + originalSQL);
		return true; // allow query to continue (i.e. accept this logical table)
	}

	public boolean applyPropagatedSQLFilter( String queryID, String nodeID, SQLQueryElements queryElmts )
	{
		logger.logDetail("Entered applyPropogatedSQLFilter(), queryID: " + queryID + ", nodeID: " + nodeID);
		return true; // allow query to continue (i.e. accept this logical table)	
	}

	// Note there is no columnsMapping structure to know which of the columns in the physical data source are actually being queried if
	// a mapping is specified in the GaianDb config for the data source.. this can be added as a future extension.
	public boolean applyDataSourceSQLFilter( String queryID, String dataSourceID, SQLQueryElements queryElmts )
	{
		logger.logDetail("Entered applyDataSourceSQLFilter(), queryID: " + queryID + ", dataSourceID: " + dataSourceID);
		return true; // allow query to continue (i.e. accept this logical table)	
	}

}
