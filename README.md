# Spring boot REST-API Application example

PostgreSQL と接続するバージョン

** 作成中 **


## アプリケーションの概要

URLの取得

~~~
$ kubectl get svc rest-java
NAME        TYPE           CLUSTER-IP      EXTERNAL-IP                       
rest-java   LoadBalancer   172.21.104.58   5b1013c8-jp-tok.lb.appdomain.cloud
~~~

API

* GET http://<k8s.domain.com>:8080/api/v1/person
* GET http://<k8s.domain.com>:8080/api/v1/person/{id}
* PUT http://<k8s.domain.com>:8080/api/v1/person/{id}
* DELETE http://<k8s.domain.com>:8080/api/v1/person/{id}
* POST http://<k8s.domain.com>:8080/api/v1/person



![]("docs/sshot-1.png")




## Javaアプリのビルド方法

コンテナ上でmavenを利用してJavaアプリケーションをビルドするので、必要な環境は、Docker CEがインストールされたパソコンだけです。その他、パソコン側にJava,maven などをインストールしてビルド環境を作ることは不要です。

~~~
git clone https://github.com/takara9/rest-java
cd rest-java
docker build --tag <dockerHub userid>/rest-java:0.1 .
~~~

実行例

~~~
maho:rest-java maho$ docker build --tag maho/rest-java:0.1 .
Sending build context to Docker daemon  200.7kB
Step 1/10 : FROM maven:3.6.3-jdk-14 AS build
3.6.3-jdk-14: Pulling from library/maven
bce8f778fef0: Pull complete 
2778faef3420: Pull complete 
266caac2c4da: Pull complete 
3d0e38026f11: Pull complete 
c1e24dbfb3fd: Pull complete 
c115f982cabf: Pull complete 
Digest: sha256:fc36633f1a757547979a5b3570009ecf65b56d01f18973bad0c505e85deae256
Status: Downloaded newer image for maven:3.6.3-jdk-14
 ---> b47eb3b5b269
Step 2/10 : COPY src src
 ---> 8664fe9a2610
Step 3/10 : COPY pom.xml pom.xml
 ---> e6cc3b452b8d
Step 4/10 : RUN  mvn clean package
 ---> Running in 163b1469aa54
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/2.3.1.RELEASE/spring-boot-starter-parent-2.3.1.RELEASE.pom

...

[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:09 min
[INFO] Finished at: 2020-07-19T13:04:38Z
[INFO] ------------------------------------------------------------------------
Removing intermediate container 163b1469aa54
 ---> 5a9adaf54296
Step 5/10 : FROM openjdk:14-alpine
14-alpine: Pulling from library/openjdk
4167d3e14976: Pull complete 
345cf19c142c: Pull complete 
Digest: sha256:b8082268ef46d44ec70fd5a64c71d445492941813ba9d68049be6e63a0da542f
Status: Downloaded newer image for openjdk:14-alpine
 ---> 8273876b08aa
Step 6/10 : RUN addgroup -S spring && adduser -S spring -G spring
 ---> Running in b243a44bce99
Removing intermediate container b243a44bce99
 ---> 64fdc1df60cd
Step 7/10 : USER spring:spring
 ---> Running in f07dba8e5eb0
Removing intermediate container f07dba8e5eb0
 ---> 55eebe198d43
Step 8/10 : ARG JAR_FILE=target/*.jar
 ---> Running in 954c84cff666
Removing intermediate container 954c84cff666
 ---> 6794345e3f40
Step 9/10 : COPY --from=build ${JAR_FILE} app.jar
 ---> 36ddbf1930b8
Step 10/10 : ENTRYPOINT ["java","-jar","/app.jar"]
 ---> Running in df40febe9d8f
Removing intermediate container df40febe9d8f
 ---> 096d150d6555
Successfully built 096d150d6555
Successfully tagged maho/rest-java:0.1
~~~


## コンテナへのリポジトリ登録方法

Docker HubのユーザーID maho の部分を置換えてプッシュしてください。

~~~
maho:rest-java maho$ docker login
Authenticating with existing credentials...
Login Succeeded
maho:rest-java maho$ docker push maho/rest-java:0.1
The push refers to repository [docker.io/maho/rest-java]
c2d802271821: Pushed 
0b6bfec2df39: Pushed 
f199ad476e12: Mounted from library/openjdk 
531743b7098c: Mounted from library/openjdk 
0.1: digest: sha256:4bf303aa76e8f8f90ed06c8a4ccf41fe102c26d1912c6fa0019fc76b4b984bc5 size: 1161
~~~


## Kubernetesへのデプロイ方法

デプロイ

~~~
kubectl apply -k k8s/base
~~~

削除

~~~
kubectl delete -k k8s/base
~~~


