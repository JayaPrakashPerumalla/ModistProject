package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys
import verification.Verification
import webAction.WebAction

public class Asset {

	WebAction actions = new WebAction()
	Random random = new Random()
	Verification verifications = new Verification()
	@Keyword
	def clickAssetTab() {
		actions.click(findTestObject('Object Repository/Asset/Asset/AssetPageElements/assetTab'))
	}

	@Keyword
	def clickAddAssetButton() {
		actions.click(findTestObject('Object Repository/Asset/Asset/AssetPageElements/AddAssetButton'))
	}

	@Keyword
	def searchForAsset(String assetName) {
		actions.sendKeys(findTestObject('Object Repository/Asset/Asset/AssetPageElements/search'), "")
		for (int i = 0; i < assetName.length(); i++) {
			WebUI.sendKeys(findTestObject('Object Repository/Asset/Asset/AssetPageElements/search'),assetName.charAt(i).toString())
		}
	}


	@Keyword
	def getRandomAssetName() {

		WebUI.waitForPageLoad(10)

		def assetCount = actions.getElementCount(findTestObject('Object Repository/Asset/Asset/AssetCount'))

		if(assetCount == 0) {
			//create or add product
			return addAsset()
		}
		else {
			def index = random.nextInt(assetCount)

			if(index==0) {
				index=index+1
			}
			def assetName =  WebUI.getText(findTestObject('Object Repository/Asset/Asset/randomAsset(index)',["index":index]))

			String asset=""
			while(assetName.equals(asset)) {
				index=index+1
				assetName =  WebUI.getText(findTestObject('Object Repository/Asset/Asset/randomAsset(index)',["index":index]))
			}

			return assetName
		}
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
		WebUI.uploadFile(findTestObject('Object Repository/Asset/Asset/addAsset/chooseFile'),userDir + '\\car 2.jpg')
		selectTenant()
		actions.click(findTestObject('Asset/Asset/addAsset/addAssetButtonInAddAssetPopup'))
		WebUI.waitForElementNotPresent(findTestObject('Object Repository/Asset/Asset/uploadStatusIcon'), 10)

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
		verifications.verifyElementNotPresent(findTestObject('Object Repository/Asset/Asset/assetname(assetName)',["assetName":assetName]), 'Asset '+assetName+" is  present related to section", 30)
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
		actions.scrollToElement(findTestObject('Object Repository/Asset/Asset/assetEditpage/sourceName(sourceName)',["sourceName":sourceName]))
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/Asset/assetEditpage/sourceName(sourceName)',["sourceName":sourceName]),'source '+sourceName+'is not updated',5)
		actions.click(findTestObject('Object Repository/Asset/Asset/assetEditpage/close'))
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
		actions.click(findTestObject('Object Repository/Asset/Asset/AddAssetPopEle/Cancel'))
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

