# Movie Rental System

_Group Burri, Mastrandrea, Schmid_

Git-Flow documentation: https://www.atlassian.com/git/tutorials/comparing-workflows
General git style guide: https://github.com/agis/git-style-guide

# Database
## Run the database server:
```shell
java -cp ~/.m2/repository/org/hsqldb/hsqldb/2.4.0/hsqldb-2.4.0.jar org.hsqldb.server.Server --database.0 file:db.dat --dbname.0 mrsdb
```

## Running database access tools:
```shell
java -cp ~/.m2/repository/org/hsqldb/hsqldb/2.4.0/hsqldb-2.4.0.jar org.hsqldb.util.DatabaseManagerSwing
```
