To run the project:
Just run the command:
`./gradlew clean build bootRun`
This should spin up the application in the default 8080 port.
You can go to the REST url of `localhost:8080/v1/order/cost`
and use the json input below.

Input 1:
```
{
	"itemDetails": [
		{
			"name": "APPLE",
			"quantity": "1001"
		},
		{
			"name": "ORANGE",
			"quantity": "3"
		}

	]
}
```

You can use invalid inputs to test out some javax validations.

I have completed the task in Kotlin and I have used a simple json response as output. There are more than a few assumptions and specific steps I have followed as you mentioned that some of the instructions were intentionally non-specific so that I can use my judgment. Let me cover them one by one.

1. I created the solution following "sort of" a microservice architecture. As you can see, I have 1 controller class and 1 service class that does the work for us. Obviously, the problem was not designed for a web service type of application specifically, but I took that approach to demonstrate an effective way of using the web application design using Controller, Service, Request Model, Response Model, Request Object Validation, API path, REST methods etc. From here on, I will call this project a web service.
I have used the spring boot (2.X) framework along with the reactive netty server (instead of tomcat).

2. I have created a GlobalExceptionHandler which handles all of the global exceptions that happen for a known or an unknown reason in the service. It obviously can be massaged more and more cases can be added depending on the complexity of the project. In this case, I only added Bad Requests (400) and generic 500 case. I have also created a generic ErrorResponse class to capture the errorResponse details of an error. It contains a few fields which explains what the API uses to communicated with a user. Just want to add that this exception handler file goes a long way in making sure that the contract stays in tact when an exception happens or more so that we do not leak any implementation details to the consumer when something goes wrong. This file can be modified as needed.

3. I have designed the request in the JSON structure as I have done the implementation as a web application. I have used my own judgment for the json structure. The input for the service is given in the readme markdown file.

4. The controller is pretty simple. Once you start up the application, you can go the `localhost:8080/v1/order/cost` and use the input provided in the readme to get the response back. I did all of the development locally and obviously did not deploy it to any of the servers.

5. BadRequestException is just an exception that I added as a proof of concept. In an ideal scenario, the project will have more types of exceptions.

6. The service class is where the bulk of the work is happening and the major calculation is done. I have appended the response to a response object which I return at the end back to the controller. In an ideal API world, the service layer would not be returning a response object. It would be returning the model object back to the controller which then parses out the model object  to the response object. This is what an ideal microservice architecture would be. Since I do not have the model object, I just returned the response object from the service back to the controller.
Also, keeping the microservice design pattern in mind, I did not do any of the calculation logic outside of the service as the loose coupling of structures is in play.

7. I have the resources directory which has the application.yaml, application-dev.yaml etc files. This is where spring boot comes in handy. But, since my application does not have any properties which I thought would be useful to put in there, I did not bother about it.
This is where I would have the configuration for the kafka messaging service on env specific file. Depending on the env I am running the application with, the exact config for the kafka would be picked up.

8. I did some basic validation for the user input. For eg: if the name is empty, that is considered as a bad request (400 response code) or a negative value for quantity. More validation or refined validations could be done based on specific scenarios or validations needed. Obviously, some custom validations could be added as well based on the needs. For now, I just added the javax validations to keep it simple.

9. I did unit testing using JUnit5, WebClient, Mockito and so on. For the most part, the unit tests are self explanatory. The controller does the unit testing for the validations as well as the success scenarios. And the service unit tests actually test out the accuracy of the results. I have also used a few parameterized tests. In certain unit tests, more assertions can be added to make sure that the response is well asserted. More of that will also be done in the SOA tests (QA specific testing). So, I did not worry about those here.

11. I have used ktlint as a liter to keep me (or any developer in check! :-) ). I know some people are strongly against it as ktlint can get annoying very quickly. But it has its benefits in terms of writing good formatted code and proper import statements. For anyone who dislikes it, they can easily remove the ktlint dependency from the build.gradle file.

12. I have used gradle as that is my favorite dependency management tool. Some people prefer Maven. Hopefully, no one prefers ANT anymore. :-)
Gradle for me is superior to maven.

13. I did not use any of the spring boot actuator functions that are provided out of the box. I do not think anything like that was required for this project. But, since I did the project in spring boot, I wanted to mention that I did not work on those parts specifically.

14. I did not worry about the OPEN API swagger documentation for this project.

15. For Kafka - Please download the Kafka and start your zookeeper instance. Once the zookeeper instance starts, you can start the kafka server from the command line.
Once the kafka server is running, you can create the kafka topic. In this project, I have defined the topic as ``KAFKA_TOPIC`` with the desired partition and number of nodes. Once the project publishes messages, you can see those published messages onto the kafka topic.
Considering the kafka call takes certain time and considering that the project should not be blocked, the kafka client call should be async. This has not been covered for this project.