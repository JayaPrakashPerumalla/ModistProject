package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import verification.Verification
import webAction.WebAction

public class Product {


	WebAction actions = new WebAction()
	Random random = new Random()
	Verification verifications = new Verification()


	@Keyword
	def clickOnProductTab() {

		actions.click(findTestObject('Object Repository/Product/productTab'))
	}

	@Keyword
	def clickOnAddProductButton() {

		actions.click(findTestObject('Object Repository/Product/addProductButton'))
	}

	@Keyword
	def addProduct() {

		clickOnProductTab()
		def productName = "RandomProduct"+random.nextInt(1000)
		clickOnAddProductButton()
		actions.sendKeys(findTestObject('Object Repository/Product/productNameInput'), productName)
		actions.sendKeys(findTestObject('Object Repository/Product/descriptionFieldInput'), "Sample text")
		actions.click(findTestObject('Object Repository/Product/addproductButtonInAddProductPopUp'))
		return 	productName
	}



	@Keyword
	def searchForAProduct(String productName) {
		actions.sendKeys(findTestObject('Object Repository/Product/Filter by name'), productName)
	}

	@Keyword
	def verifyProductAdded(String productName) {

		searchForAProduct(productName)
		verifications.verifyElementPresent(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]), "The product "+productName+" is not added in the list" )
	}

	@Keyword
	def verifyProductPopUp() {

		String path = 'Object Repository/Product/'
		def elements = ["productNameInput", "descriptionFieldInput", "cancelButtonInAddProductPopUp", "addproductButtonInAddProductPopUp", "publishedDropDown"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}



	@Keyword
	def openAnyExistingProduct(String productName){

		searchForAProduct(productName)
		WebUI.scrollToElement(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]),30)
		actions.click(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]))
	}

	@Keyword
	def verifyFooterSectionButtons(){
		String path = 'Object Repository/Product/ProductEditPage/'
		List elements = ["closeButton", "deleteButton", "saveChangesButton"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "In footer "+element+" is not present" )
		}
	}

	@Keyword
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
