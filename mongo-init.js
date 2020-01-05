// After starting the mongo container do the following to create a database user for the application
//
// Connect to the container:
// winpty docker exec -it mongodb bash
//
// Connect to mongo shell as admin:
// mongo --authenticationDatabase "admin" -u "admindb" -p "admindb"
//
// Run the following statement

use pipelinedb
db.createUser(
  {
    user: "clientdb",
    pwd:  "clientdb",
    roles: [ { role: "readWrite", db: "pipelinedb" } ]
  }
)
