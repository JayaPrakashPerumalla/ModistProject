import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper


Random random = new Random()  

jsonSlurper = new JsonSlurper()

def productName = "productName"+random.nextInt(10000)
println productName

def description = "descriptionCreatedForApiTest"+random.nextInt(10000)

def response = WS.sendRequest(findTestObject('Object Repository/API/Product/createProduct',[('productName') : productName,('description') : description,('published'):true]))

def userResponseJSON = jsonSlurper.parseText(response.getResponseText())

println "userResponseJSON"+userResponseJSON

WS.verifyNotEqual(userResponseJSON.id, null)

WS.verifyNotEqual(userResponseJSON.id, "")


WS.verifyEqual(userResponseJSON.name, productName)

WS.verifyEqual(userResponseJSON.description, description)

WS.verifyEqual(userResponseJSON.published, true)

return userResponseJSON





