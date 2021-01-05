package pages
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import verification.Verification
import webAction.WebAction

public class Brand {

	WebAction actions = new WebAction()
	Verification verify = new Verification() 
	Random random = new Random()

	@Keyword
	def clickBrand() {
		WebUI.click(findTestObject('Object Repository/Brand/clickOnBrandTab'))
	}
	@Keyword
	def addBrand() {

		String brandName = "Brand"+random.nextInt(1000)

		actions.click(findTestObject('Object Repository/Brand/AddBrandbutton'))

		actions.sendKeys(findTestObject('Object Repository/Brand/AddBrandPopup/name'),brandName)

		actions.click(findTestObject('Object Repository/Brand/AddBrandPopup/AddBrandButtonInAddBrandPopup'))
		
		return brandName
	}
	
	def search(String brandName) { 
		actions.sendKeys(findTestObject('Object Repository/Brand/search'),brandName)
	}
	
	@Keyword
	def verifyTheBrandCreated(String brandName) {
		verify.verifyElementPresent(findTestObject(''),'the brand')
	}
}
