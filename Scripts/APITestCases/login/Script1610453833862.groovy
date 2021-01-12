import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import internal.GlobalVariable

jsonSlurper = new JsonSlurper()

def responseLogin = WS.sendRequest(findTestObject('Object Repository/API/login'))

//println "responseLogin.statusCode"+responseLogin.statusCode

WS.verifyResponseStatusCode(responseLogin, 200)

def loginJSON = jsonSlurper.parseText(responseLogin.getResponseText())

println "loginJSON.email"+loginJSON.email

WS.verifyEqual(loginJSON.email, GlobalVariable.Email)

