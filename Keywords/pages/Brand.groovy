package pages
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.common.WebUiCommonHelper
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
	def clickAddBrand() {
		WebUI.click(findTestObject('Object Repository/Brand/AddBrandbutton'))
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
		actions.sendKeys(findTestObject('Object Repository/Brand/search'), "")
		for (int i = 0; i < brandName.length(); i++) {
			WebUI.sendKeys(findTestObject('Object Repository/Brand/search'), brandName.charAt(i).toString())
		}
	}

	@Keyword
	def verifyTheBrandCreated(String brandName) {
		search(brandName)
		verify.verifyElementPresent(findTestObject('Object Repository/Brand/createdBrand(brandName)',["brandName":brandName]),'the brand '+brandName+'not created')
	}
	@Keyword
	def verifyBrandPopUp() {
		String path = 'Object Repository/Brand/AddBrandPopup/'
		def elements = ["name", "Slogan", "Bio"]
		for(element in elements) {
			verify.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
		actions.click(findTestObject('Object Repository/Brand/AddBrandPopup/cancelButtonAddBrandPopup'))
	}

	@Keyword
	def addBrandWithoutfillingmandatoryDetails() {
		clickBrand()
		clickAddBrand()
		actions.click(findTestObject('Object Repository/Brand/AddBrandPopup/AddBrandButtonInAddBrandPopup'))
		actions.click(findTestObject('Object Repository/Brand/AddBrandPopup/cancelButtonAddBrandPopup'))
	}

	@Keyword
	def openExistingBrand(String brandName) {
		search(brandName)
		WebUI.scrollToElement(findTestObject('Object Repository/Brand/createdBrand(brandName)',["brandName":brandName]),60)
		WebUI.waitForElementVisible(findTestObject('Object Repository/Brand/createdBrand(brandName)',["brandName":brandName]), 30)
		actions.click(findTestObject('Object Repository/Brand/createdBrand(brandName)',["brandName":brandName]))
	}

	@Keyword
	def deleteBrand(String brandName){
		openExistingBrand(brandName)
		actions.click(findTestObject('Object Repository/Brand/Delete Button'))
	}

	@Keyword
	def verifyABrandDeleted(String brandName) {
		search(brandName)
		verify.verifyElementNotPresent(findTestObject('Object Repository/Brand/createdBrand(brandName)',["brandName":brandName]), "The Brand "+brandName+" is not deleted from the list")
	}

	@Keyword
	def editBrandName(String brandName){
		openExistingBrand(brandName)
		def brandeditedname = 'editedbrandname'+random.nextInt(10)
		WebUI.clearText(findTestObject('Object Repository/Brand/AddBrandPopup/name'))
		actions.sendKeys(findTestObject('Object Repository/Brand/AddBrandPopup/name'),brandeditedname)
		actions.click(findTestObject('Object Repository/Brand/AddBrandPopup/save button'))
		actions.click(findTestObject('Object Repository/Brand/AddBrandPopup/Close Button'))
		return brandeditedname
	}

	@Keyword
	def verifyeditedBrandName(String brandeditedname){
		search(brandeditedname)
		verify.verifyElementPresent(findTestObject('Object Repository/Brand/createdBrand(brandName)',["brandName":brandeditedname]),"Brand name is not edited")
	}

	@Keyword
	def getRandomBrandName() {
		actions.waitForElementPresent(findTestObject('Object Repository/Brand/brandCount'))
		int count = actions.getElementCount(findTestObject('Object Repository/Brand/brandCount'))
		if(count == 0) {
			return addBrand()
		}
		else {
			int index = random.nextInt(count)
			String brandName = WebUI.getText(findTestObject('Object Repository/Brand/randomBrand(index)',["index":index]))
			return brandName
		}
	}
}