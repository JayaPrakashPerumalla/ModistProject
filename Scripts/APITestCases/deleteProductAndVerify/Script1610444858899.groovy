import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.json.JsonSlurper

jsonSlurper = new JsonSlurper()

// Create a product   
def productInfo = WebUI.callTestCase(findTestCase('Test Cases/APITestCases/createProductAndVerify'), [:], FailureHandling.STOP_ON_FAILURE)
def productId = productInfo.id

// Delete the product
def response = WS.sendRequest(findTestObject('Object Repository/API/Product/productDelete', ["productId" : productId]))
WS.verifyResponseStatusCode(response, 200)

// Verify deleted product
def allProductsInfo = WS.sendRequest(findTestObject('Object Repository/API/Product/getAllProducts'))
WS.verifyResponseStatusCode(allProductsInfo, 200)
def allProductsInfoJSON = jsonSlurper.parseText(allProductsInfo.getResponseText())
for(int i =0 ;i<allProductsInfoJSON.items.size();i++)
{
	WS.verifyNotEqual(allProductsInfoJSON.items[i].id, productId, FailureHandling.STOP_ON_FAILURE)
}
