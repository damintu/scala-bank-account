FROM mozilla/sbt
WORKDIR /build
ADD . .
RUN sbt stage

FROM gcr.io/distroless/java
WORKDIR /app
COPY --from=0 /build/target/universal/stage/lib/* ./lib/
COPY --from=0 /build/target/universal/stage/conf/ ./conf/
EXPOSE 9000
ENTRYPOINT ["java", "-Duser.dir=/app", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-cp", "conf/:lib/*"]
CMD ["play.core.server.ProdServerStart"]