#基于哪个镜像
FROM java

#将本地文件夹挂载到当前容器
VOLUME  /tmp

#赋值文件到容器
ADD user-server-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'

ENV JAVA_OPTS=""

#声明暴露的端口
EXPOSE 8080

#配置容器启动后执行的命令
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]