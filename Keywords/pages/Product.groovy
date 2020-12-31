package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import webAction.WebAction

public class Product {


	WebAction actions = new WebAction()

	@Keyword
	def clickOnProductTab() {

		actions.click(findTestObject('Object Repository/Product/productTab'))
	}
	@Keyword
	def openAnyExistingProduct(String productName){

		actions.sendKeys(findTestObject('Object Repository/Product/Filter by name'), productName)
		WebUI.scrollToElement(findTestObject('Product/OpenAnyExistingProduct(productName)',["productName":productName]),30)
		actions.click(findTestObject('Object Repository/Product/OpenAnyExistingProduct(productName)',["productName":productName]))
	}

	@Keyword
	def productEditPage(){

		WebUI.scrollToElement(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/ProductAssets'), 30)
		int count=WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/From'), 30).size()
		println count
	}
}
