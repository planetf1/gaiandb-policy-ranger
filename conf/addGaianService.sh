#!/bin/sh
# --
# Sample script to deploy new service definition to ranger
# ---
curl -u admin:admin -X POST -H "Accept: application/json" -H "Content-Type: application/json" \
--data @ranger-servicedef-myservice.json http://localhost:6080/service/plugins/definitions