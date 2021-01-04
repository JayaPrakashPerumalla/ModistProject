package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.junit.After

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
	def searchForAProduct(String productName) {
		actions.sendKeys(findTestObject('Object Repository/Product/Filter by name'), productName)
	}

	@Keyword
	def openAnyExistingProduct(String productName){

		searchForAProduct(productName)
		WebUI.scrollToElement(findTestObject('Product/OpenAnyExistingProduct(productName)',["productName":productName]),30)
		actions.click(findTestObject('Object Repository/Product/OpenAnyExistingProduct(productName)',["productName":productName]))
	}

	def navigateToProductAssets(){

		WebUI.scrollToElement(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/ProductAssets'), 30)
	}

	@Keyword
	def dragAndDrop(int startPosition,int endPosition) {

		navigateToProductAssets()
		int count=WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/From'), 30).size()
		println count
		if(count > 1) {
			if((startPosition<=count)&&(endPosition<=count)) {
				//WebUI.dragAndDropByOffset(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/source(position)'),800,0)
				WebUI.dragAndDropToObject(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/source(position)'),findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/destination(position)'))
			}
		}
	}
}
