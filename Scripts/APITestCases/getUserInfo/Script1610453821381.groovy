import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

jsonSlurper = new JsonSlurper()

def userResponse = WS.sendRequest(findTestObject('Object Repository/API/User/userInfo'))


WS.verifyResponseStatusCode(userResponse, 200)

def userResponseJSON = jsonSlurper.parseText(userResponse.getResponseText())

println "loginJSON.name"+userResponseJSON.items[0].name

WS.verifyEqual(userResponseJSON.items[0].name, "Qualitlabs")

return userResponseJSON