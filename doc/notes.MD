GaianDB service definition

This is defined in conf/ranger-servicedef-gaiandb.json

* It's unclear what "id:" is other than being unique. For now setting to 99
* The guid has been generated randomly
* The resource matcher being used is the default (RangerDefaultResourceMatcher) - will review later if this is sufficient
* Supported resource types are a) schema b) table c) column 
* The only accessType is "select"
* No configuration elements are needed currently. I've added a dummy entry as a reminder
* The same masking as supported by Hive is included in the config - although all won't necessarily be implemented to begin with
* Row filtering definitions (for schema, table) have been left in place

Some additional docs on stack config can be found at <href>https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=53741207</href>

Row level filtering and column masking is covered at 
<href>https://cwiki.apache.org/confluence/display/RANGER/Row-level+filtering+and+column-masking+using+Apache+Ranger+policies+in+Apache+Hive</href>

Deployment

This servicedef can be pushed to ranger using something like:
<pre>
curl -v -u admin:admin -X POST -H "Accept: application/json" -H "Content-Type: application/json" -d @ranger-servicedef-gaiandb.json http://localhost:6080/service/public/v2/api/servicedef
</pre>