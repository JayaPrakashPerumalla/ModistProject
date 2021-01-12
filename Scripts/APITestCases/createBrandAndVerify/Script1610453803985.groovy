import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.json.JsonSlurper

jsonSlurper = new JsonSlurper()

Random random = new Random()
def brandName = "brandName"+random.nextInt(10000)
def slogan = "slogonCreatedForApi"+random.nextInt(10000)
def bio = "bioCreatedForApi"+random.nextInt(10000)

// Get user info
def userInfo = WebUI.callTestCase(findTestCase('APITestCases/getUserInfo'), [:], FailureHandling.STOP_ON_FAILURE)
def tenentId = userInfo.items[0].tenantId


// Create brand
def responsce = WS.sendRequest(findTestObject('Object Repository/API/Brand/createBrand', [('brandName') : brandName, ('slogan') : slogan, ('bio') : bio , ('tenentId') : tenentId]))

//Verify response code
WS.verifyResponseStatusCode(responsce, 200)

// Verify Created brand name , slogon , bio id
def userResponseJSON = jsonSlurper.parseText(responsce.getResponseText())
WS.verifyNotEqual(userResponseJSON.id, null)
WS.verifyNotEqual(userResponseJSON.id, "")
WS.verifyEqual(userResponseJSON.name, brandName)
WS.verifyEqual(userResponseJSON.slogan, slogan)
WS.verifyEqual(userResponseJSON.bio, bio)

return userResponseJSON