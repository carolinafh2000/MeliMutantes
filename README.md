# MeliMutantes

### Requisitos
Para poder ejecutar el proyecto se requieren las siguientes especificaciones:

* Java 11
* Maven
* DynamoDB (Version web https://docs.aws.amazon.com/es_es/amazondynamodb/latest/developerguide/DynamoDBLocal.html)
IMPORTANTE: En este caso el proyecto fue desarrollado en Ubuntu, por lo cual los comandos especificados en la instalacion para interaccion con (DynamoDB) funcionan para Linux en caso de tener otro SO revisar instrucciones para credenciales https://docs.aws.amazon.com/es_es/es_es/sdk-for-java/v1/developer-guide/credentials.html


### Instalacion

* Clonar el siguiente repositorio: https://github.com/carolinafh2000/MeliMutantes.git
* Ejecutar mvn clean/install
* Levantar DynamoDB (AWS CLI) con el siguiente comando: java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
* Configurar las siguientes credenciales DynamoDB (Digitar cada linea en AWS CLI):
  export AWS_ACCESS_KEY_ID=AKIA4YBSHN42L623VCVG
  export AWS_SECRET_ACCESS_KEY=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
  export AWS_DEFAULT_REGION=us-east-1
* Ejecutar el siguiente comando (AWS CLI) aws dynamodb list-tables --endpoint-url http://localhost:8000 para verificar que las tablas reclutamiento y seleccion se encuentren creadas
  En caso de no encontrar las tablas creadas con ese usuario, realizar la creacion con el siguiente comando: node pruebaML.js (Este archivo se encuentra en el paquete DB del proyecto)
* Ejecutar el proyecto con el siguiente comando mvn spring-boot:run

### Dominio AWS

POST: http://ec2-3-133-129-169.us-east-2.compute.amazonaws.com:8080/mutant/

GET: http://ec2-3-133-129-169.us-east-2.compute.amazonaws.com:8080/stats

### Arquitectura

![image](https://user-images.githubusercontent.com/87741723/162866686-ad0cfa45-656a-48e4-9738-c20114e34aa9.png)
