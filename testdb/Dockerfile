FROM blacklabelops/hsqldb
RUN apt-get update
RUN -d -p 9091:9091 --name hsqldb-test blacklabelops/hsqldb
COPY hsqldb-test:/opt/hsqldb/hsqldb.jar .
RUN docker run -it --rm \
      --link hsqldb-test:hsqldb \
    	-v $(pwd)/dbscripts:/scripts \
    	blacklabelops/hsqldb sqltool
EXPOSE
VOLUME /testdata