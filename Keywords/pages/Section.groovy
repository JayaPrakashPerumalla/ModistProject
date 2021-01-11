package pages

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import webAction.WebAction

import verification.Verification

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

		actions.sendKeys(findTestObject('Object Repository/Section/filterByName'), sectionName)
		WebUI.waitForPageLoad(30)
	}

	@Keyword
	def addSection() {

		
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
		
		clickOnAddSectionButton()
		String path = 'Object Repository/Section/addSectionPopupFields/'
		def elements = ["Title", "subTitle", "Tenant", "cancel", "addSection"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}
}
