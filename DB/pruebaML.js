
/**
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of
 * the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
*/
var AWS = require("aws-sdk");

AWS.config.update({
  region: "us-east-1",
  endpoint: "http://localhost:8000"
});

var dynamodb = new AWS.DynamoDB();
var docClient = new AWS.DynamoDB.DocumentClient();


/*Tabla que va a contener todos los ADN*/
var params = {TableName: 'reclutamiento', KeySchema: [ {AttributeName: 'Dna',KeyType: 'HASH',}],
AttributeDefinitions: [ { AttributeName: 'Dna', AttributeType: 'S',	}],
ProvisionedThroughput: {ReadCapacityUnits: 1, WriteCapacityUnits: 1, }};
dynamodb.createTable(params, function(err, data) { if (err) ; else (data); });

/*Tabla que va a funcionar como contador de humanos y mutantes*/
var params = {TableName: 'seleccion',KeySchema: [{AttributeName: 'dna',KeyType: 'HASH',}],
AttributeDefinitions: [{AttributeName: 'dna',AttributeType: 'S',}],
ProvisionedThroughput: {ReadCapacityUnits: 1,WriteCapacityUnits: 1,}};
dynamodb.createTable(params, function(err, data) { if (err) ; else (data);});

var params = {TableName: 'seleccion', Item: {dna: 'DNA',count_mutant_dna: 0,count_human_dna: 0}};
console.log("Calling PutItem"); (params);
docClient.put(params, function(err, data) { if (err); else console.log("PutItem returned successfully");});