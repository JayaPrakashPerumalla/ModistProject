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
		actions.click(findTestObject('Asset/AssetPageElements/assetTab'))
		WebUI.delay(10)
	}

	@Keyword
	def clickAddAsset() {

		actions.click(findTestObject('Asset/AssetPageElements/AddAssetButton'))
	}

	@Keyword
	def searchForAsset(String assetName) {
		actions.sendKeys(findTestObject('Asset/AssetPageElements/filterByName'),assetName)
		WebUI.waitForPageLoad(10)
		WebUI.delay(5)
	}


	@Keyword
	def getRandomAssetName() {

		clickAssetTab()
		def assetCount = actions.getElementCount(findTestObject('Object Repository/Asset/AssetCount'))
		if(assetCount == 0) {
			//create or add product
			return addAsset()
		}
		else {
			def index = random.nextInt(assetCount)
			if(index == 0) {
				index = index +1
			}
			def assetName =  WebUI.getText(findTestObject('Object Repository/Asset/randomAsset(index)',["index":index]))

			return assetName
		}
	}




	@Keyword
	def addAsset() {

		clickAssetTab()
		clickAddAsset()

		String assetName = "Author"+random.nextInt(1000)

		actions.sendKeys(findTestObject('Object Repository/Asset/addAsset/Author'), assetName)
		//action.click(findTestObject('Object Repository/Asset/addAsset/chooseFile'))
		WebUI.delay(5)
		String userDir = System.getProperty('user.dir')
		WebUI.uploadFile(findTestObject('Object Repository/Asset/addAsset/chooseFile'),userDir + '\\ima.jpg')
		actions.click(findTestObject('Object Repository/Asset/addAsset/clickTenant'))
		actions.click(findTestObject('Object Repository/Asset/addAsset/selectTenent'))

		actions.click(findTestObject('Object Repository/Asset/addAsset/AddAsset'))
		return assetName
	}


	@Keyword
	def VerifyAssetCreated(String assetName) {
		searchForAsset(assetName)
		verifications.verifyElementPresent(findTestObject('Asset/assetname(assetName)',["assetName":assetName]),assetName+"not present")
	}

	@Keyword
	def deleteAsset(String assetName) {
		searchForAsset(assetName)
		actions.click(findTestObject('Asset/delAssetCheckbox(assetName)',["assetName":assetName]))
		actions.click(findTestObject('Object Repository/Asset/delIcon'))
		WebUI.acceptAlert()
	}

	@Keyword
	def VerifyAssetNotPresent(String assetName) {
		searchForAsset(assetName)
		verifications.verifyElementNotPresent(findTestObject('Asset/assetname(assetName)',["assetName":assetName]),assetName+"not present")
	}


	@Keyword
	def searchBox() {
		String assetName=getRandomAssetName()
		searchForAsset(assetName)
		verifications.verifyElementPresent(findTestObject('Asset/assetname(assetName)',["assetName":assetName]),assetName+"not present")
	}

	@Keyword
	def searchBoxInvalidName(String assetName) {
		clickAssetTab()
		searchForAsset(assetName)
		verifications.verifyElementNotPresent(findTestObject('Asset/assetname(assetName)',["assetName":"asdf"]),"asdf is not present")
	}

	@Keyword
	def clickAnyAsset() {
		String assetName= getRandomAssetName()
		searchForAsset(assetName)
		actions.click(findTestObject('Object Repository/Asset/anyAsset(assetName)',["assetName":assetName]))
		return assetName
	}

	@Keyword
	def verifyUrlOfEditPage() {
		String newUrl = WebUI.getUrl()

		if(!newUrl.contains("asset")) {
			KeywordUtil.markFailed('not navigating to asst edit page Url')
		}
	}

	@Keyword
	def closeButtonInAssetEditPage() {
		clickAnyAsset()
		actions.click(findTestObject('Object Repository/Asset/assetEditpage/close'))
	}

	@Keyword
	def cancleButtonInAddAsset() {

		clickAssetTab()
		clickAddAsset()
		String assetName = "Author"+random.nextInt(1000)

		actions.sendKeys(findTestObject('Object Repository/Asset/addAsset/Author'), assetName)
		actions.click(findTestObject('Asset/addAsset/cancleButton'))

		return assetName
	}

	@Keyword
	def editSourceInAssetPage() {

		clickAssetTab()
		def assetName = clickAnyAsset()
		actions.click(findTestObject('Asset/assetEditpage/sourceDropdown'))
		def sourceName = selectRandomSourceName()
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/sourceSelect(sourceName)',["sourceName":"toyotae2"]))
		actions.click(findTestObject('Object Repository/Asset/assetEditpage/Save'))
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/Public'))
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/publicSelect'))
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/Save'))
		actions.click(findTestObject('Object Repository/Asset/assetEditpage/close'))
		return ["sourceName":sourceName , "assetName":assetName]
	}

	@Keyword
	def verifyEditAsset(def assetName, def sourceName) {

		searchForAsset(assetName)
		actions.click(findTestObject('Object Repository/Asset/anyAsset(assetName)',["assetName":assetName]))
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/assetEditpage/sourceName(sourceName)',["sourceName":sourceName]),sourceName+'is not getting reflecting',5)
	}

	@Keyword
	def selectRandomSourceName() {

		def sourceCount = actions.getElementCount(findTestObject('Object Repository/Asset/assetEditpage/sourceCount'))

		if(sourceCount == 0) {
			//close source
			actions.click(findTestObject('Object Repository/Asset/assetEditpage/Save'))
		}
		else {
			def index = random.nextInt(sourceCount)
			if(index == 0) {
				index = index +1
			}
			actions.click(findTestObject('Object Repository/Asset/assetEditpage/randomSource(index)',["index":index]))
			def sourceName =  WebUI.getText(findTestObject('Object Repository/Asset/assetEditpage/randomSource(index)',["index":index]))
			return sourceName
		}
	}



	@Keyword
	def verifyAddAssetPopUp() {


		clickAssetTab()
		clickAddAsset()
		String path = 'Object Repository/Asset/AddAssetPopEle/'
		def elements = ["Author", "Quote", "AssetUrl", "UploadPhoto", "Tenant", "Cancel", "AddAsset"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}

	@Keyword
	def verifyAssetEditPage() {

		clickAssetTab()
		clickAnyAsset()
		String path = 'Object Repository/Asset/assetEditPageElements/'
		def  elements = ["AssetEdit", "Content", "Photo", "Tags", "Source", "Tenant", "Public", "Author", "Created", "Updated"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}

	@Keyword
	def verifyAssetPage() {

		clickAssetTab()
		String path = 'Object Repository/Asset/AssetPageElements/'
		def  elements = ["AddAssetButton", "filterByName", "sourceTab", "created", "updated", "Autor", "content"]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}
}
