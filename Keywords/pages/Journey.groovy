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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import webAction.WebAction
import verification.verifications
import internal.GlobalVariable

public class Journey {

	WebAction actions = new WebAction()

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
	}
}
