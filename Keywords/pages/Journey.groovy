package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.codehaus.groovy.tools.shell.completion.KeywordSyntaxCompletor

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import verification.Verification
import webAction.WebAction

public class Journey {



	WebAction actions = new WebAction()
	Verification verifications = new Verification()

	Random random = new Random()


	@Keyword
	def clickOnJourneyTab() {

		actions.click(findTestObject('Object Repository/Journey/Journeytab'))
	}


	def clickAddJourneyButton() {

		actions.click(findTestObject('Object Repository/Journey/Add Journey/AddJourneyButton'))
	}

	@Keyword
	def addJorney() {

		String journeyName = "journeyName"+random.nextInt(1000)
		clickAddJourneyButton()
		actions.sendKeys(findTestObject('Object Repository/Journey/Add Journey/journeyName'), journeyName)
		actions.click(findTestObject('Object Repository/Journey/Add Journey/addJourneyInAddJourneyPopUp'))
		return journeyName
	}

	@Keyword
	def openExistingJourney(String journeyName) {

		searchJourney(journeyName)
		actions.click(findTestObject('Object Repository/Journey/clickVerifyingJourney(journeyName)',["journeyName":journeyName]))
	}

	def searchJourney(def journeyName) {

		actions.sendKeys(findTestObject('Object Repository/Journey/journrySearchInput'), journeyName)
	}

	@Keyword
	def verifyJourneyAdded(def journeyName) {

		searchJourney(journeyName)
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyName(journeyName)',["journeyName":journeyName]), " The expected "+journeyName+ " is not created ")
	}

	@Keyword
	def deleteJourney(def journeyName) {
		searchJourney(journeyName)
		actions.click(findTestObject('Object Repository/Journey/journeyNameCheckBox(name)',["journeyName":journeyName]))
		actions.click(findTestObject('Object Repository/Journey/journeyNameDeleteButton'))
		WebUI.acceptAlert()
	}

	@Keyword
	def verifyJourneyDeleted(def journeyName) {

		searchJourney(journeyName)
		verifications.verifyElementNotPresent(findTestObject('Object Repository/Journey/JourneyName(journeyName)',["journeyName":journeyName]), " The expected "+journeyName+ " is not deleted ")
	}

	@Keyword
	def verifyJourneyCloned(def journeyName) {

		String duplicatejourneyName = journeyName+' '+'(duplicate)'
		searchJourney(duplicatejourneyName)

		WebUI.waitForElementPresent(findTestObject('Object Repository/Journey/journey cloning/clonedJourney(journeyName)',["journeyName":duplicatejourneyName]), 30, FailureHandling.OPTIONAL)

		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/journey cloning/clonedJourney(journeyName)',["journeyName":duplicatejourneyName]), " The expected "+duplicatejourneyName+ " is not Cloned ")
	}

	@Keyword
	def cloneJourney(String journeyName) {

		searchJourney(journeyName)

		WebUI.delay(30)

		actions.click(findTestObject('Journey/journey cloning/threedots(journeyName)',["journeyName":journeyName]))

		actions.click(findTestObject('Object Repository/Journey/journey cloning/cloneButton'))
	}

	@Keyword
	def createAccessCodeInJourney(String journeyName, String accessCode, String useLimit) {

		openExistingJourney(journeyName)

		//actions.click(findTestObject('Object Repository/Journey/clickVerifyingJourney(journeyName)',["journeyName":journeyName]))
		actions.scrollToElement(findTestObject('Object Repository/Journey/Current Access Codes/currentAccessCodeField'))
		actions.click(findTestObject('Object Repository/Journey/Current Access Codes/plusButton'))
		actions.sendKeys(findTestObject('Object Repository/Journey/Current Access Codes/accessCode'), accessCode)
		WebUI.sendKeys(findTestObject('Object Repository/Journey/Current Access Codes/codeUsageCount'), useLimit)
		actions.click(findTestObject('Object Repository/Journey/Current Access Codes/getCodeButton'))
		actions.click(findTestObject('Object Repository/CommonButtons/Close'))
		return accessCode
	}



	@Keyword
	def verifyTheCreatedAccessCode(String journeyName, String accesscode) {
		
		openExistingJourney(journeyName)

		actions.scrollToElement(findTestObject('Object Repository/Journey/Current Access Codes/currentAccessCodeField'))
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/Current Access Codes/verify(code)',["code":accesscode]), 'The Code' +accesscode +' is not created')
		WebUI.delay(15)
		actions.click(findTestObject('Object Repository/CommonButtons/Close'))
	}

	@Keyword
	def getRandomJourneyName() {
		
		WebUI.delay(5)
		int count = actions.getElementCount(findTestObject('Object Repository/Journey/toGetJourneysCount'))
		int index = random.nextInt(count)
		def journeyName = WebUI.getText(findTestObject('Object Repository/Journey/toPickrandomJourney(index)',["index":index+1]))
		return journeyName
	}

	@Keyword
	def getRandomAccessCode() {
		String accessCode = 'access'+random.nextInt(20)
		return accessCode
	}

	@Keyword
	def getRandomCount() {
		int count = random.nextInt(10)
		return count
	}

	@Keyword
	def getExistingJourneyAccesscode(String journeyName) {

		searchJourney(journeyName)
		openExistingJourney(journeyName)
		actions.scrollToElement(findTestObject('Object Repository/Journey/Current Access Codes/currentAccessCodeField'))
		int count = actions.getElementCount(findTestObject('Object Repository/Journey/Current Access Codes/getAccessCodesCount'))

		int index = random.nextInt(count)
		String accessCode = WebUI.getText(findTestObject('Object Repository/Journey/Current Access Codes/getAccessCodeText(index)',["index":index+1]))
		return accessCode
	}

	@Keyword
	def enterAccessCodeInJourney(String accessCode) {
		actions.sendKeys(findTestObject('Object Repository/Journey/JourneyPortal/enterAccessCode'), accessCode)
	}

	@Keyword
	def clickEnterButton() {
		actions.click(findTestObject('Object Repository/Journey/JourneyPortal/EnterButton'))
	}

	@Keyword
	def sectionPageVerification() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyPortal/Section/sectionPage'),"The display is not a SectionPage")
	}

	@Keyword
	def errorLoginCheck() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyPortal/warningMessageForInvalidAccessCode'), "your have entered a valid access code")
	}

	@Keyword
	def typeOfBackGroundToScrollingBackground() {

		actions.scrollToElement(findTestObject('Object Repository/Journey/typeField/clickOnType'))

		actions.click(findTestObject('Object Repository/Journey/typeField/clickOnType'))

		actions.click(findTestObject('Object Repository/Journey/typeField/ScrollingBackGround'))

		clickSaveButton()
	}

	@Keyword
	def typeOfBackGroundToFixed() {

		actions.scrollToElement(findTestObject('Object Repository/Journey/typeField/clickOnType'))

		actions.click(findTestObject('Object Repository/Journey/typeField/clickOnType'))

		actions.click(findTestObject('Object Repository/Journey/typeField/FixedBackGround'))

		clickSaveButton()
	}

	def clickSaveButton() {

		actions.click(findTestObject('Object Repository/CommonButtons/saveButton'))
	}

	@Keyword
	def verifyWhetherImageSuccessfullyUploaded() {

		verifications.verifyElementPresent(findTestObject('Object Repository/OptionsUnderProfileIcon/Profile Options/successMessageAfterUploading'), "image not uploaded")
	}
}
