//winpty docker exec -it mongodb bash

//mongo --authenticationDatabase "admin" -u "admindb" -p "admindb"

use pipeline
db.createUser(
  {
    user: "clientdb",
    pwd:  "clientdb",
    roles: [ { role: "readWrite", db: "pipeline" } ]
  }
)
