package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement;

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
	def dragAndDrop() {

		//TestObject obj = '(//label[text()='Product Assets']/..//div[@class='droppable-container'])[2]'
		//navigateToProductAssets()
		WebUI.delay(5)
		WebUI.navigateToUrl('https://dev-admin.modist.co/product/30406')
		WebUI.delay(15)
		//int sourceId = Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/From'),'data-rbd-droppable-id'))
		//int targetId = Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/dest'),'data-rbd-droppable-id'))
		//WebUI.dragAndDropToObject(findTestObject('Object Repository/Product/Test/source'), findTestObject('Object Repository/Product/Test/target'))
		WebElement element = driver.findElement(By.xpath('//div[@data-rbd-droppable-id="29836"]'))

		Point point = element.getLocation()
		int xcord = point.getX();
		println "xcord"+xcord
		int ycord = point.getY();
		println "ycord"+ycord
		WebUI.dragAndDropByOffset(findTestObject('Object Repository/Product/Test/source'), 600, 150)
		WebUI.dragAndDropToObject(findTestObject('Object Repository/Product/Test/source'), findTestObject('Object Repository/Product/Test/target'))
		//Point position = Windows.getElementPosition(findTestObject('Object Repository/Product/Test/source'))

		WebUI.delay(5)

		//println sourceId
		//	println targetId
		//Get left position of destination
		//def leftPosition = WebUI.getElementLeftPosition(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/destination(position)'))
		//def rightPosition = WebUI.getElementRightPosition(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/destination(position)'))

		//println "leftPosition: " + leftPosition

		//WebUI.dragAndDropByOffset(findTestObject('Object Repository/Product/ProductEditPage/ProductAssets/source(position)'), leftPosition+10, leftPosition)
	}

	@Keyword
	def changePositionOfItem(TestObject sourceObject, int noOfPositions, boolean isRightDirection) {

		WebElement sourceElement = WebUiBuiltInKeywords.findWebElement(sourceObject);

		def id = sourceElement.getAttribute("data-rbd-draggable-id")
		println "id : " + id
		
		List<WebElement> itemList = sourceElement.findElements(By.xpath("/parent::div/../div/div"))
		println itemList.size()
        for(element in itemList )
		{
			println "elements "+element.getAttribute("data-rbd-draggable-id")
		}
		sourceElement.findElements(By.tagName("img"))

		WebDriver driver = DriverFactory.getWebDriver()

		//Using Action class for drag and drop.

		Actions builder=new Actions(driver);

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
