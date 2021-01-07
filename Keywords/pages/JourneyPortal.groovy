package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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

	@Keyword
	def clickOnCart() {
		actions.click(findTestObject('Object Repository/Journey/JourneyPortal/Product/Cart'))
	}

	@Keyword
	def verifyElementOnCartIfNoItems() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyPortal/Product/verifyNoItemsInTheCart'), "you are not in cart page")
	}

	@Keyword
	def clickFullScreenIcon() {
		actions.click(findTestObject('Object Repository/Journey/JourneyPortal/Section/FullScreenIcon'))
	}

	@Keyword
	def verifyTheFullScreenView() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Journey/JourneyPortal/Section/verifyFullScreenView'), "you are not in full screen view")
	}

	@Keyword
	def openAnyProductInTheJourneyPortal() {

		int count = actions.getElementCount(findTestObject('Object Repository/Journey/JourneyPortal/Product/toGetProductsCountInTheCart'))
		int index = random.nextInt(count)

		actions.scrollToElement(findTestObject('Object Repository/Journey/JourneyPortal/Product/openAnyProduct(index)',["index":index+1]))
		actions.click(findTestObject('Object Repository/Journey/JourneyPortal/Product/openAnyProduct(index)',["index":index+1]))
	}

	@Keyword
	def clickAddToCartButton() {
		int orderCount = random.nextInt(10)
		orderCount = orderCount+1
		for(int i=0;i<orderCount;i++) {
			actions.click(findTestObject('Object Repository/Journey/JourneyPortal/Product/AddToCart'))
		}
		actions.click(findTestObject('Object Repository/Journey/JourneyPortal/Product/closeButton'))
		return orderCount
		WebUI.refresh()
	}

	@Keyword
	def getProductsCount() {
		
		String ProductsCount = WebUI.getAttribute(findTestObject('Object Repository/Journey/JourneyPortal/Product/toGetProductsCountInTheCart'), 'data-count')
		int productsCount = Integer.parseInt(ProductsCount)
		return productsCount
	}

	@Keyword
	def verifyWhetherOrderedAndAvailableProductsCountAreSame(int orderCount, int productsCount) {
		if(orderCount == productsCount) {
			KeywordUtil.markPassed('you have selected '+orderCount + ' products ')
		}
		else {
			KeywordUtil.markFailed('you have selected '+orderCount+' products '+', but you have only '+productsCount)
		}
	}
}
