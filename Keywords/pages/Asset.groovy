package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import verification.Verification
import webAction.WebAction

public class Asset {

	WebAction actions = new WebAction()
	Random random = new Random()
	Verification verifications = new Verification()

	@Keyword
	def elementsInTheAssetTable() {
		int count = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Asset/Verify elemetns in Asset/elements in Asset Table'), 30).size()
		for (int i=0;i<count;i++) {
			String elementName = WebUI.getText(findTestObject('Object Repository/Asset/Verify elemetns in Asset/Printing element name in the table',["index":i]))
			println elementName
		}
	}
	@Keyword
	def clickAsset() {
		actions.click(findTestObject('Object Repository/Asset/assetTab'))
	}

	@Keyword
	def openExistingAsset() {

		int count = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Asset/getIndexToPickRandomAsset'), 30).size()
		int index = random.nextInt(count)
		actions.click(findTestObject('Object Repository/Asset/openExistingAsset(index)',["index":index+1]))
		def assetName=WebUI.getText(findTestObject('Object Repository/Asset/openExistingAsset(index)',["index":index+1]))
		return assetName
	}

	@Keyword
	def verfiyFieldsInAssetEditPage() {

		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyContentField'),'The field Content is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyPhotoField'),'The field Photo is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyTagsField'),'The field Tags is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifySourceField'),'The field Source is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyTenantField'),'The field Tenant is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyPublicField'),'The field Public is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyAuthorField'),'The field Author is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyCreatedField'),'The field Created is not present')
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/AssetEdit/verifyUpdatedField'),'The field Updated is not present')
	}

	@Keyword
	def clickAssetTab() {
		actions.click(findTestObject('Object Repository/Asset/Asset/AssetPageElements/assetTab'))
		WebUI.delay(10)
	}

	@Keyword
	def clickAddAssetButton() {

		actions.click(findTestObject('Object Repository/Asset/Asset/AssetPageElements/AddAssetButton'))
	}

	@Keyword
	def searchForAsset(String assetName) {

		actions.sendKeys(findTestObject('Object Repository/Asset/Asset/AssetPageElements/search'),assetName)

		WebUI.waitForPageLoad(30)

		//actions.waitForElementPresent(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]))
		actions.waitForElementPresent(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]))

	}


	@Keyword
	def getRandomAssetName() {

		//actions.waitForElementPresent('Object Repository/Asset/Asset/AssetCount')
		WebUI.waitForPageLoad(10)

		def assetCount = actions.getElementCount(findTestObject('Object Repository/Asset/Asset/AssetCount'))

		if(assetCount == 0) {
			//create or add product
			return addAsset()
		}
		else {
			def index = random.nextInt(assetCount)

			def assetName =  WebUI.getText(findTestObject('Object Repository/Asset/Asset/randomAsset(index)',["index":index+1]))

			/*String asset=""
			 while(assetName.equals(asset))
			 {
			 assetName =  WebUI.getText(findTestObject('Object Repository/Asset/Asset/randomAsset(index)',["index":index+1]))
			 println assetName
			 } */ 

			return assetName
		}

	}

	@Keyword
	def searchBox(String assetName) {

		actions.sendKeys(findTestObject('Object Repository/Asset/search'), assetName)
	}
	@Keyword
	def selectTenant() {

		actions.click(findTestObject('Object Repository/Asset/Asset/addAsset/clickTenant'))

		actions.click(findTestObject('Object Repository/Asset/Asset/addAsset/selectTenent'))
	}

	@Keyword
	def addAsset() {

		clickAddAssetButton()

		String assetName = "Author"+random.nextInt(1000)

		actions.sendKeys(findTestObject('Object Repository/Asset/Asset/addAsset/Author'), assetName)

		String userDir = System.getProperty('user.dir')

		WebUI.uploadFile(findTestObject('Object Repository/Asset/Asset/addAsset/chooseFile'),userDir + '\\bike4.jpg')

		selectTenant()

		actions.click(findTestObject('Asset/Asset/addAsset/addAssetButtonInAddAssetPopup'))

		return assetName
	}

	@Keyword
	def addAssetwithFileSize() {

		clickAddAssetButton()

		String assetName = "Author"+random.nextInt(1000)

		actions.sendKeys(findTestObject('Object Repository/Asset/Asset/addAsset/Author'), assetName)

		String userDir = System.getProperty('user.dir')

		WebUI.uploadFile(findTestObject('Object Repository/Asset/Asset/addAsset/chooseFile'),userDir + '\\2.6mb.jpg')

		selectTenant()

		actions.click(findTestObject('Asset/Asset/addAsset/addAssetButtonInAddAssetPopup'))

		WebUI.delay(90)
 

		return assetName
	}


	@Keyword
	def VerifyAssetIsPresent(String assetName) {

		searchForAsset(assetName)

		//actions.waitForElementPresent(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]))
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]),assetName+"not present")
	}

	@Keyword
	def deleteAsset(String assetName) {


		searchForAsset(assetName)

		actions.click(findTestObject('Object Repository/Asset/Asset/delAssetCheckbox(assetName)',["assetName":assetName]))

		actions.click(findTestObject('Object Repository/Asset/Asset/delIcon'))

		WebUI.acceptAlert()
	}

	@Keyword
	def VerifyAssetNotPresent(String assetName) {

		searchForAsset(assetName)

		verifications.verifyElementNotPresent(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]), 'Asset '+assetName+" is not present", 30)
	}


	@Keyword
	def verifyUrlOfassetEditPage(String assetName) { 
		
        searchForAsset(assetName) 
		
		actions.click(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]))
		
	    String newUrl = WebUI.getUrl() 

		if(!newUrl.contains('asset')) { 
			
			KeywordUtil.markFailed('not navigating to asst edit page Url')
		}
	}

	@Keyword
	def closeButtonInAssetEditPage() {

		def assetName = getRandomAssetName()  
		searchForAsset(assetName)
		
		actions.click(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]))

		actions.click(findTestObject('Object Repository/Asset/Asset/assetEditpage/close'))
	}

	@Keyword
	def cancelButtonInAddAssetPopup() {

		clickAddAssetButton()

		String assetName = "Author"+random.nextInt(1000)

		actions.sendKeys(findTestObject('Object Repository/Asset/Asset/addAsset/Author'), assetName)
		actions.click(findTestObject('Object Repository/Asset/Asset/addAsset/cancelButton'))

		return assetName
	}

	@Keyword
	def editSourceInAssetEditPage() {

		//String assetName = openExistingAsset()
		String assetName = getRandomAssetName()   
		
		searchForAsset(assetName)
		 
		actions.click(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]))
		
	     WebUI.scrollToElement(findTestObject('Object Repository/Asset/Asset/assetEditpage/sourceDropdown'), 30)
         
		actions.click(findTestObject('Object Repository/Asset/Asset/assetEditpage/sourceDropdown'))

		def sourceName = selectRandomSourceName()

		actions.click(findTestObject('Object Repository/Asset/Asset/assetEditpage/Save'))

		actions.click(findTestObject('Object Repository/Asset/Asset/assetEditpage/close'))

		return ["sourceName":sourceName , "assetName":assetName]
	}

	@Keyword
	def verifySourceInEditAssetPage(def assetName, def sourceName) {

		searchForAsset(assetName)

		actions.click(findTestObject('Object Repository/Asset/Asset/anyAsset(assetName)',["assetName":assetName]))

		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/Asset/assetEditpage/sourceName(sourceName)',["sourceName":sourceName]),'source '+sourceName+'is not updated',5)
	}

	@Keyword
	def selectRandomSourceName() {

		def sourceCount = actions.getElementCount(findTestObject('Object Repository/Asset/Asset/assetEditpage/sourceCount'))

		if(sourceCount == 0) {
			//close source
			actions.click(findTestObject('Object Repository/Asset/Asset/assetEditpage/Save'))
		}
		else {
			def index = random.nextInt(sourceCount)
			if(index == 0) {
				index = index +1
			}
			actions.click(findTestObject('Object Repository/Asset/Asset/assetEditpage/randomSource(index)',["index":index]))
			def sourceName =  WebUI.getText(findTestObject('Object Repository/Asset/Asset/assetEditpage/randomSource(index)',["index":index]))
			return sourceName
		}
	}



	@Keyword
	def verifyAddAssetPopUp() {


		clickAssetTab()
		clickAddAssetButton()
		String path = 'Object Repository/Asset/Asset/AddAssetPopEle/'
		def elements = ["Author", "Quote", "AssetUrl", "UploadPhoto", "Tenant", "Cancel", "AddAsset"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}

	@Keyword
	def verifyAssetEditPage() {

		def assetName = getRandomAssetName()
       
		actions.click(findTestObject('Object Repository/Asset/Asset/anyAsset(assetName)',["assetName":assetName]))
		
		String path = 'Object Repository/Asset/Asset/assetEditPageElements/'
		def  elements = ["AssetEdit", "Content", "Photo", "Tags", "Source", "Tenant", "Public", "Author", "Created", "Updated"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}
	@Keyword
	def verifyAssetPage() {

		String path = 'Object Repository/Asset/Asset/AssetPageElements/'

		def  elements = ["AddAssetButton", "filterByName", "sourceTab", "created", "updated", "Autor", "content"]

		for(element in elements) {

			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}

	@Keyword
	def verifyWarningMessageOfAddAsset() {
		
		actions.waitForElementPresent(findTestObject('Object Repository/Asset/improperErrorMessage'))
		verifications.verifyElementNotPresent(findTestObject('Object Repository/Asset/improperErrorMessage'), "no proper message shown")
	}
}

