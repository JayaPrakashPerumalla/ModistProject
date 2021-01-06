package pages

import com.kms.katalon.core.annotation.Keyword
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import verification.Verification
import webAction.WebAction

public class JourneyPortal {

	WebAction actions = new WebAction()
	Verification verifications = new Verification()
	Random random = new Random()

	@Keyword
	def openExistingSection() {
		int count = actions.getElementCount(findTestObject('Object Repository/Journey/JourneyPortal/Section/getSectionsCount'))
		int index = random.nextInt(count)

		actions.click(findTestObject('Object Repository/Journey/JourneyPortal/Section/openSection(index)',["index":(index+1)]))
	}

	@Keyword
	def verifyFixedBackGroundCheck() {

		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyPortal/fixedAndScrollingDetect'),"you are in ScrollingBackGround")
	}

	@Keyword
	def verifyScrollingBackGroundCheck() {
		verifications.verifyElementNotPresent(findTestObject('Object Repository/Journey/JourneyPortal/fixedAndScrollingDetect'),"you are in Fixed BackGround")
	}

	@Keyword
	def sectionPageCheck() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyPortal/Section/sectionPage'), "you are not in section page")
	}

	@Keyword
	def productPageCheck() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyPortal/Product/productPage'), "you are not in Product Page")
	}
}
