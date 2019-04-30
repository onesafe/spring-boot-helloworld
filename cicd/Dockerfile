FROM docker.4pd.io/base-image-openjdk8:1.0

COPY hello-world-0.0.1-SNAPSHOT.jar /app/

COPY start.sh /app/

EXPOSE 51006

ENTRYPOINT ["/bin/bash", "/app/start.sh"]