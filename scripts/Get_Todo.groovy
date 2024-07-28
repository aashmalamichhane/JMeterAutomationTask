 
def GET_URL=vars.get("GET_URL");
def GET_RESPONSE_FILE_NAME=vars.get('GET_RESPONSE_FILE_NAME');

def get = new URL(GET_URL).openConnection();
def responseCode = get.getResponseCode();
println("GET Response Code :: " + responseCode);
if (responseCode.equals(200)) {
    response = get.getInputStream().getText()
    // Get the current path
    def currentPath = new File('.').absolutePath
    // Define the file name and path
    def filePath = "${currentPath}/${GET_RESPONSE_FILE_NAME}"
    // Create a new file
    def file = new File(filePath)
    println("Writing Response to File");
    println("File Path :: " + filePath);
    file.withWriter('UTF-8') {
        writer -> writer.writeLine(response)
    }
    println("Writing Response Completed");
} else  {
    println("ERROR :: Could not fetch Todos");
}
