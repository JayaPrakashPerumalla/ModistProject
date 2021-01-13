package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import verification.Verification
import webAction.WebAction

public class Section {
	WebAction actions = new WebAction()

	Random random = new Random()

	Verification verifications = new Verification()

	@Keyword
	def clickOnSectionTab() {

		actions.click(findTestObject('Object Repository/Section/SectionTab'))
	}

	@Keyword
	def clickOnAddSectionButton() {

		actions.click(findTestObject('Object Repository/Section/AddSectionButton'))
	}

	@Keyword
	def searchForSection(def sectionName) {

		actions.sendKeys(findTestObject('Object Repository/Section/filterByName'), "")
		for (int i = 0; i < sectionName.length(); i++) {
			WebUI.sendKeys(findTestObject('Object Repository/Journey/journrySearchInput'), sectionName.charAt(i).toString())
		}
	}

	@Keyword
	def addSection() {

		clickOnSectionTab()
		clickOnAddSectionButton()
		def sectionName = "RandomSection"+random.nextInt(1000)
		actions.sendKeys(findTestObject('Object Repository/Section/addSection/title'),sectionName)
		actions.sendKeys(findTestObject('Object Repository/Section/addSection/subtitle'),"New Section sample")
		actions.click(findTestObject('Object Repository/Section/addSection/tenant'))
		actions.click(findTestObject('Object Repository/Section/addSection/tenantSelect'))
		actions.click(findTestObject('Object Repository/Section/addSection/addSection'))

		return sectionName
	}



	@Keyword
	def verifySectionAdded(def sectionName) {
		searchForSection(sectionName)
		verifications.verifyElementPresent(findTestObject('Object Repository/Section/anySection(sectionName)',["sectionName":sectionName]), sectionName+"is not present",30)
	}

	@Keyword
	def  verifyElementsInAddSectionPopUp() {
		clickOnSectionTab()
		clickOnAddSectionButton()
		String path = 'Object Repository/Section/addSectionPopupFields/'
		def elements = ["Title", "subTitle", "Tenant", "cancel", "addSection"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
		actions.click(findTestObject('Object Repository/Section/addSection/cancelButton'))
		
	}

	@Keyword
	def cancelButtonInAddSectionPopup() {

		actions.click(findTestObject('Object Repository/Section/addSection/cancelButton'))
		verifications.verifyElementPresent(findTestObject('Object Repository/Section/filterByName'), "Section popup is not getting closed")
	}
}
