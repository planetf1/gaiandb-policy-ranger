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

import com.ibm.gaiandb.Util;
import com.ibm.gaiandb.policyframework.SQLResultFilterX;

import java.sql.ResultSetMetaData;
import java.util.Arrays;

import org.apache.derby.iapi.types.DataValueDescriptor;


/**
 * Initial policy plugin for gaianDB, as part of the VirtualDataConnector project
 * 
 * To activate this policy class, you first need to add this class to Gaian's classpath.
 * You can do this in several ways:
 * 	- Place this class under <Gaian install path>/policy/
 * 	- Add the path containing the top level package of this class (i.e. "policy") to the classpath in launchGaianServer.bat(/sh)
 *  - Build a jar file with this class inside it, and place that jar in <Gaian install path>/lib/ or <Gaian install path>/lib/ext/   
 * 
 * Finally, start the Gaian node and activate your policy - this can be done with the following SQL:
 * call setconfigproperty('SQL_RESULT_FILTER', 'policy.SamplePolicyNoFilters')
 * 
 * @author jonesn@uk.ibm.com
 */
public class RangerPolicyFilter extends SQLResultFilterX {

//	Use PROPRIETARY notice if class contains a main() method, otherwise use COPYRIGHT notice.
	public static final String COPYRIGHT_NOTICE = "(c) Copyright IBM Corp. 2017";
	
	/**
	 * Policy instantiation constructor - invoked for every new query.
	 * This instance will be re-used if the calling GaianTable results from a PreparedStatement which is re-executed by the calling application. 
	 */
	public RangerPolicyFilter() {
		System.out.println("\nEntered SamplePolicyNoFilters() constructor");
	}
	
	public boolean setLogicalTable(String logicalTableName, ResultSetMetaData logicalTableResultSetMetaData) {
		System.out.println("Entered setLogicalTable(), logicalTable: " + logicalTableName + ", structure: " + logicalTableResultSetMetaData);
		return true; // allow query to continue (i.e. accept this logical table)
	}
	
	public boolean setForwardingNode(String nodeName) {
		System.out.println("Entered setForwardingNode(), forwardingNode: " + nodeName);
		return true; // allow query to continue (i.e. accept this forwardingNode)
	}
	
	public boolean setUserCredentials(String credentialsStringBlock) {
		System.out.println("Entered setUserCredentials(), credentialsStringBlock: " + credentialsStringBlock);
		return true; // allow query to continue (i.e. accept this credentialsStringBlock)
	}
	
	public int nextQueriedDataSource(String dataSourceID, String dataSourceDescription, int[] columnMappings) {
		System.out.println("Entered nextQueriedDataSource(), dataSourceID: " + dataSourceID
				+ ", dataSourceDescription: " + dataSourceDescription + ", columnMappings: " +  Util.intArrayAsString(columnMappings));
		return -1; // allow all records to be returned (i.e. don't impose a maximum number)
	}

	public boolean setQueriedColumns(int[] queriedColumns) {
		System.out.println("Entered setQueriedColumns(), queriedColumns: " + Util.intArrayAsString(queriedColumns));
		return true; // allow query to continue (i.e. accept that all these columns be queried)
	}
	
	/**
	 *  Apply policy on a batch of rows..
	 *  This is helpful if you need to send the rows to a 3rd party to evaluate policy - so you can minimize the number of round trips to it.
	 */
	public DataValueDescriptor[][] filterRowsBatch(String dataSourceID, DataValueDescriptor[][] rows) {
		System.out.println("Entered filterRowsBatch(), dataSourceID: " + dataSourceID + ", number of rows: " + rows.length );
		return rows; // no filtering - let all rows be returned as-is
	}
	
	/**
	 * This method provides generic extensibility of the Policy framework.
	 * For any new operations required in future, a new operation ID (opID) will be assigned, for which
	 * a given set of arguments will be expected, we well as a given return object.
	 */
	protected Object executeOperationImpl(String opID, Object... args) {
		System.out.println("Entered executeOperation(), opID: " + opID + ", args: " + (null == args ? null : Arrays.asList(args)) );		
		return null; // Generic return of 'null' just lets the query proceed. Otherwise, the returned object should depend on the opID.
	}

	
	/**
	 * Invoked when Derby closes the GaianTable or GaianQuery instance.
	 * This should be when the query's statement is closed by the application - but this is not guaranteed as Derby may cache it for re-use.
	 */
	public void close() {
		System.out.println("Entered close()");
	}
	
	
	/****************************************************************************************************************************************************************
	 * 									DEPRECATED / UNUSED METHODS - REQUIRED ONLY FOR COMPATIBILITY WITH 'SQLResultFilter'
	 ****************************************************************************************************************************************************************/
	
	/**
	 * This method is deprecated in favour of the same method below having 3 arguments - it is here for compatibility with SQLResultFilter
	 */
	public int nextQueriedDataSource(String dataSource, int[] columnMappings) {
		System.out.println("Entered nextQueriedDataSource() (unexpectedly), dataSource: " + dataSource + ", columnMappings: " + Util.intArrayAsString(columnMappings));
		return -1; // allow all records to be returned (i.e. don't impose a maximum number)
	}
	
	/**
	 * This method is not currently called by Gaian. 
	 */
	public int setDataSourceWrapper(String wrapperID) {
		System.out.println("Entered setDataSourceWrapper() (unexpectedly), wrapperID: " + wrapperID);
		return -1; // allow a maximum number of records to be returned
	}

	/**
	 * This method is deprecated in favour of filterRowsBatch() - it is here for compatibility with SQLResultFilter
	 */
	public boolean filterRow(DataValueDescriptor[] row) {
		System.out.println("Entered filterRow() (unexpectedly), row: " + Arrays.asList(row));
		return true; // allow this record to be returned
	}
	
}
