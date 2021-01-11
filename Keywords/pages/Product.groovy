package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
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
	def dragAndDrop(TestObject sourceObject, int noOfPositions, boolean isRightDirection) {

		navigateToProductAssets()

		WebElement sourceElement = WebUiBuiltInKeywords.findWebElement(sourceObject);

		def id = sourceElement.getAttribute("data-rbd-draggable-id")

		println "id : " + id

		sourceElement.findElements(By.tagName("img"))

		Thread.sleep(2000);


		//builder.clickAndHold(elementS).moveToElement(elementD).build().perform();

		builder.clickAndHold(sourceElement).moveByOffset(5, -5).build().perform();


		int x = (noOfPositions * 200 < 800) ? (noOfPositions * 200) : 800

		int y = -50

		if( ! isRightDirection){

			x = -x;

		}

		println "x : " + x

		builder.moveByOffset(x, y).build().perform();

		//for(int i = 0; i< 40; i++ ){

		//	builder.moveByOffset(5, -2).build().perform();

		//	//Thread.sleep(1000);

		//	WebElement elementM = driver.findElement(By.xpath("(//div[@class='draggable-item'])[1]"));

		//	println 'i : ' + i

		//	println 'id : ' + elementM.getAttribute("data-rbd-draggable-id")

		//}

		Thread.sleep(2000);

		builder.release().build().perform();
	}
}
