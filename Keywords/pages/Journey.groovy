package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.annotation.Keyword

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

	@Keyword
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

		WebUI.waitForElementPresent(findTestObject('Object Repository/Journey/journey cloning/cloned Journey (journeyName))',["journeyName":duplicatejourneyName]), 30, FailureHandling.OPTIONAL)

		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/journey cloning/cloned Journey (journeyName))',["journeyName":duplicatejourneyName]), " The expected "+duplicatejourneyName+ " is not Cloned ")
	}
	
	@Keyword
	def cloneJourney(String journeyName) {

		searchJourney(journeyName)

		verifications.verifyElementPresent(findTestObject('Journey/journey cloning/threedots(journeyName)',["journeyName":journeyName]),'The expected journey '+journeyName+' is not available')

		actions.click(findTestObject('Journey/journey cloning/threedots(journeyName)',["journeyName":journeyName]))

		actions.click(findTestObject('Object Repository/Journey/journey cloning/cloneButton'))
	}
	
}
