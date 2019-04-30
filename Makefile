.PHONY: helloworld

helloworld:
	mvn clean package -U
	cp target/hello-world-0.0.1-SNAPSHOT.jar cicd/

clean:
	rm -rf cicd/hello-world-0.0.1-SNAPSHOT.jar