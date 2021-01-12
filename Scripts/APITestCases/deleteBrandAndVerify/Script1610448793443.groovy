import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.json.JsonSlurper

jsonSlurper = new JsonSlurper()

// Create a brand
def brandInfo = WebUI.callTestCase(findTestCase('APITestCases/createBrandAndVerify'), [:], FailureHandling.STOP_ON_FAILURE)
def brandId = brandInfo.id

println "brandId"+brandId

// Delete the brand
def response = WS.sendRequest(findTestObject('Object Repository/API/Brand/deleteBrand', ["brandId" : brandId]))
WS.verifyResponseStatusCode(response, 200)

// Verify deleted brand
def allBrandsInfo = WS.sendRequest(findTestObject('Object Repository/API/Brand/getAllBrands'))
WS.verifyResponseStatusCode(allBrandsInfo, 200)
def allBrandsInfoJSON = jsonSlurper.parseText(allBrandsInfo.getResponseText())
for(int i =0 ;i<allBrandsInfoJSON.items.size();i++)
{
	WS.verifyNotEqual(allBrandsInfoJSON.items[i].id, brandId, FailureHandling.STOP_ON_FAILURE)
}