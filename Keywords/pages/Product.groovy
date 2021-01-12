package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import verification.Verification
import webAction.WebAction

public class Product {


	WebAction actions = new WebAction()
	Random random = new Random()
	Verification verifications = new Verification()
	WebDriver driver = DriverFactory.getWebDriver()
	Actions builder = new Actions(driver)



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
	def addProductButtonInAddProductPopup() {
		actions.click(findTestObject('Object Repository/Product/addproductButtonInAddProductPopUp'))
	}

	@Keyword
	def getRandomProductNmae() {

		clickOnProductTab()
		def productCount = actions.getElementCount(findTestObject('Object Repository/Product/productsCount'))
		if(productCount == 0) {
			//create or add product
			return addProduct()
		}
		else {
			def index = random.nextInt(productCount)
			if(index == 0) {
				index = index +1
			}
			def productName =  WebUI.getText(findTestObject('Object Repository/Product/randomProduct(index)',["index":index]))
			return productName
		}
	}



	@Keyword
	def searchForAProduct(String productName) {
		actions.sendKeys(findTestObject('Object Repository/Product/Filter by name'), productName)
		WebUI.waitForPageLoad(10)
	}

	@Keyword
	def verifyProductAdded(String productName) {

		searchForAProduct(productName)
		verifications.verifyElementPresent(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]), "The product "+productName+" is not added in the list" )
	}

	@Keyword
	def verifyProductNotPresent(String productName) {

		searchForAProduct(productName)
		verifications.verifyElementNotPresent(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]), "The product "+productName+" is still shown in list" )
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
	def verifyProductPresent(String productName) {

		searchForAProduct(productName)
		verifications.verifyElementPresent(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]), "The product "+productName+" is not shown in list" )
	}



	@Keyword
	def openAnyExistingProduct(String productName){

		searchForAProduct(productName)
		WebUI.scrollToElement(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]),30)
		actions.click(findTestObject('Object Repository/Product/productName(Name)',["productName":productName]))
	}

	@Keyword
	def editProductName(){

		def editedNameName = "RandomProduct"+random.nextInt(1000)
		WebUI.delay(5)
		WebUI.clearText(findTestObject('Object Repository/Product/ProductEditPage/productNameInput'))
		actions.sendKeys(findTestObject('Object Repository/Product/ProductEditPage/productNameInput'), editedNameName)
		actions.click(findTestObject('Object Repository/Product/ProductEditPage/saveChangesButton'))
		actions.click(findTestObject('Object Repository/Product/ProductEditPage/closeButton'))
		return editedNameName
	}

	@Keyword
	def deleteProduct(def productName){

		searchForAProduct(productName)
		actions.click(findTestObject('Object Repository/Product/productCheckBox(productName)',["productName":productName]))
		actions.click(findTestObject('Object Repository/Product/productDeleteButton'))
		WebUI.acceptAlert()
	}

	@Keyword
	def verifyFooterSectionButtons(){
		String path = 'Object Repository/Product/ProductEditPage/'
		List elements = ["closeButton", "deleteButton", "saveChangesButton"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "In footer "+element+" is not present" )
		}
	}


	def navigateToProductAssets(){

		WebUI.scrollToElement(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/ProductAssets'), 30)
	}


	def navigateToRelatedAssets(){

		WebUI.scrollToElement(findTestObject('Object Repository/Product/ProductEditPage/RelatedAssets/relatedAssetsField'), 30)
	}

	@Keyword
	def clickDeleteButtonInProductEditPage(){

		actions.click(findTestObject('Object Repository/Product/ProductEditPage/deleteButton'))
	}

	@Keyword
	def clickCloseButtonInProductEditPage(){

		actions.click(findTestObject('Object Repository/Product/ProductEditPage/closeButton'))
	}

	@Keyword
	def verifyAddProductButton(){

		verifications.verifyElementPresent(findTestObject('Object Repository/Product/addProductButton'), "Add product button is not present")
	}

	@Keyword
	def verifyProductEditPage() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Product/ProductEditPage/productEditPageVerification'), "you are not in product edit page")
	}

	@Keyword
	def verifyUrlAfterClosingProduct() {
		String url = WebUI.getUrl()
		if((url.contains('dashboard'))) {
			KeywordUtil.markPassed('you are navigated back to dashboard page')
		}
		else {
			KeywordUtil.markFailed("you are not navigated to dashboard page")
		}
	}

	@Keyword
	def changePositionOfItemInProductAssets(TestObject sourceObject, int noOfPositions, boolean isRightDirection) {

		// Navigate to the product edit page
		navigateToProductAssets();

		// Get draggable id and position of item
		WebElement sourceElement = WebUiBuiltInKeywords.findWebElement(sourceObject);
		def draggableid = sourceElement.getAttribute("data-rbd-draggable-id")
		def prePosition = getPositionOfItem(draggableid)

		// Change position of item
		builder.clickAndHold(sourceElement).moveByOffset(5, -5).build().perform();
		int x = (noOfPositions * 200 < 800) ? (noOfPositions * 200) : 800
		int y = -50
		if( ! isRightDirection){
			x = -x;
		}

		builder.moveByOffset(x, y).build().perform();
		Thread.sleep(2000);
		builder.release().build().perform();

		// Get position of item after changing position
		Thread.sleep(1000)
		def postPosition = getPositionOfItem(draggableid)

		// Assert position of item
		if(isRightDirection){
			WebUI.verifyEqual(prePosition + noOfPositions, postPosition, FailureHandling.STOP_ON_FAILURE)
		}
		else {
			WebUI.verifyEqual(prePosition - noOfPositions, postPosition, FailureHandling.STOP_ON_FAILURE)
		}
	}

	@Keyword
	def changePositionOfItemInRelatedAssets(TestObject sourceObject, int noOfPositions, boolean isRightDirection) {

		// Navigate to product edit page
		navigateToRelatedAssets()

		// Get draggable id and position of item
		WebElement sourceElement = WebUiBuiltInKeywords.findWebElement(sourceObject);
		def draggableid = sourceElement.getAttribute("data-rbd-draggable-id")
		def prePosition = getPositionOfItem(draggableid)

		// Change position of item
		builder.clickAndHold(sourceElement).moveByOffset(5, -5).build().perform();
		int x = (noOfPositions * 200 < 800) ? (noOfPositions * 200) : 800
		int y = -50
		if( ! isRightDirection){
			x = -x;
		}

		builder.moveByOffset(x, y).build().perform();
		Thread.sleep(2000);
		builder.release().build().perform();

		// Get position of item after changing position
		Thread.sleep(1000)
		def postPosition = getPositionOfItem(draggableid)

		// Assert position of item
		if(isRightDirection){
			WebUI.verifyEqual(prePosition + noOfPositions, postPosition, FailureHandling.STOP_ON_FAILURE)
		}
		else {
			WebUI.verifyEqual(prePosition - noOfPositions, postPosition, FailureHandling.STOP_ON_FAILURE)
		}
	}

	@Keyword
	def clickingLeft() {

		if (! (WebUI.verifyElementPresent(findTestObject('Object Repository/journeyPortal/rightDirectionDisabled'), 30, FailureHandling.OPTIONAL)))
			actions.click(findTestObject('Object Repository/journeyPortal/leftDirectionButton'))
	}

	@Keyword
	def clickingRight() {

		if(! (WebUI.verifyElementPresent(findTestObject('Object Repository/journeyPortal/rightDirectionDisabled'), 30, FailureHandling.OPTIONAL)))
			actions.click(findTestObject('Object Repository/journeyPortal/rightDirectionButton'))
	}


	def getPositionOfItem(String draggableId) {
		List<WebElement> productAssetsList = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/productAssetsItems'), 30)
		for(int i=1;i<=productAssetsList.size();i++) {
			def itemId = productAssetsList[i-1].getAttribute("data-rbd-draggable-id")
			if(itemId.equals(draggableId))
				return i
		}
	}

	def getIdOfProduct(TestObject currentObject) {

		// Get data-Id
		WebElement sourceElement = WebUiBuiltInKeywords.findWebElement(currentObject);
		def dataId = sourceElement.getAttribute("data-id")
		return dataId
	}

	@Keyword
	def verifyTheProductImageChange(String dataId1, String dataId2) {
		if(!(dataId1.equals(dataId2))) {
			KeywordUtil.markPassed("clicked successfully")
		}
	}
}
