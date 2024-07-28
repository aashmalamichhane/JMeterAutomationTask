import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def POST_URL=vars.get("POST_URL");
def MODIFIED_RESPONSE_FILE_NAME=vars.get('MODIFIED_RESPONSE_FILE_NAME');
def GET_RESPONSE_FILE_NAME=vars.get('GET_RESPONSE_FILE_NAME');

// json library for groovy
def jsonSlurper = new JsonSlurper()

// Get the current path
def currentPath = new File('.').absolutePath

// input file name and path to fetch GET Todos data saved in file
def inputFilePath = "${currentPath}/${GET_RESPONSE_FILE_NAME}"

// output file name and path to store manipulated GET Todos data in file
def outputFilePath = "${currentPath}/${MODIFIED_RESPONSE_FILE_NAME}"

// Read the input file content
println("Reading JSON data from : ${inputFilePath}")
def inputFile = new File(inputFilePath)
def getContent = inputFile.text

// Parse the JSON content
def jsonData = jsonSlurper.parseText(getContent)

// Modify value of completed for all odd "userId" and even "id"
jsonData.each {
    jsonObject -> if (jsonObject.userId % 2 != 0 && jsonObject.id % 2 == 0) {
        jsonObject.completed = !jsonObject.completed
    }
}

// Convert the modified JSON data back to a JSON string
def jsonOutput = JsonOutput.toJson(jsonData)

// Write the modified JSON data to the output file
def outputFile = new File(outputFilePath)
outputFile.withWriter('UTF-8') {
    writer -> writer.writeLine(jsonOutput)
}

// Log the output file path for confirmation
println ("Modified JSON data written to: ${MODIFIED_RESPONSE_FILE_NAME}")


def post = new URL(POST_URL).openConnection();
post.setRequestMethod("POST")
post.setDoOutput(true)
post.setRequestProperty("Content-Type", "application/json")
post.getOutputStream().write(jsonOutput.getBytes("UTF-8"));
def responseCode = post.getResponseCode();
if (responseCode.equals(201)) {
   println("POST Response Code :: ${responseCode} ")
    SampleResult.setResponseCode("201");
    def postResponse = post.getInputStream().getText();
    def postMap = jsonSlurper.parseText(postResponse)
    def jsonList = postMap.findAll {
        key, value -> key.isInteger()
    }.collect {
        it.value
    }
    def sortedList1 = jsonList.sort {
    	  // gives the list ordered by id,userId, title, and completed.
        a, b -> a.id <=> b.id ?: a.userId <=> b.userId ?: a.title <=> b.title ?: a.completed <=> b.completed
    }
    def sortedList2 = jsonData.sort {
        a, b -> a.id <=> b.id ?: a.userId <=> b.userId ?: a.title <=> b.title ?: a.completed <=> b.completed
    }
    // == compares list size, order and each pair of element of same index
    def matchContent = sortedList1 == sortedList2
    if (matchContent) {
    	   SampleResult.setResponseData("The POST Request response content matches the posted content");
        println("The POST Request response content matches the posted content")
    } else  {
    	   SampleResult.setResponseData("The POST Request response content does not matches the posted content");
        println("The POST Request response content does not matches the posted content")
    }
} else  {
    SampleResult.setResponseData("Something went wrong with API.");
    log.error("Something went wrong with API")
}

