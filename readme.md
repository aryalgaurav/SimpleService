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
			"quantity": "5"
		},
		{
			"name": "ORANGE",
			"quantity": "3"
		}

	]
}
```

You can use invalid inputs to test out some javax validations.
