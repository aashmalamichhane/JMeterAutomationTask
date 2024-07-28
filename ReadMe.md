 
# JMeter API Automation
This repository contains a JMeter test plan and Groovy scripts to perform the following tasks:
1. Send a GET request to `https://jsonplaceholder.typicode.com/todos` with a query parameter to limit the results to 5 items.
2. Verify the response code received from the above GET request.
3. Save the response obtained from the GET request in a file named `GET_Response.json`.
4. Modify the content of `GET_Response.json` to toggle/negate the value held for the `completed` attribute (true to false and false to true) for all odd `userId` and even `id` values.
5. Store the modified content in a file named `POST_Request.json`.
6. Send a POST request to `https://jsonplaceholder.typicode.com/todos` with the modified content from `POST_Request.json`.
7. Validate the response code and the response content to ensure the POST request response content matches the posted content.

## Prerequisites
- JMeter installed (version 5.4.1 or later)
- Groovy version 3.0.20.

## Setup Instructions
1. Clone this repository(https://github.com/aashmalamichhane/JMeterAutomationTask.git) to your local machine.

## Running the Test Plan
1. Open JMeter and load the `JMeter_API_Automation_task.jmx` file.
2. Execute the test plan and check the results in the View Results Tree listener.

