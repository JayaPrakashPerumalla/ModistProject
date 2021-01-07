package pack

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.Random

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable
import webAction.WebAction
import verification.Verification

public class Asset {
	WebAction  action = new WebAction()
	Verification verifications=new Verification();

	Random random = new Random()

	@Keyword
	def clickAssetTab() {
		action.click(findTestObject('Asset/AssetPageElements/assetTab'))
		WebUI.delay(10)
	}

	@Keyword
	def clickAddAsset() {

		action.click(findTestObject('Asset/AssetPageElements/AddAssetButton'))
	}

	@Keyword
	def searchForAsset(String assetName) {
		action.sendKeys(findTestObject('Asset/AssetPageElements/filterByName'),assetName)
		WebUI.waitForPageLoad(10)
		WebUI.delay(5)
	}


	@Keyword
	def getRandomAssetName() {

		clickAssetTab()
		def assetCount = action.getElementCount(findTestObject('Object Repository/Asset/AssetCount'))
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

		action.sendKeys(findTestObject('Object Repository/Asset/addAsset/Author'), assetName)
		//action.click(findTestObject('Object Repository/Asset/addAsset/chooseFile'))
		WebUI.delay(5)
		String userDir = System.getProperty('user.dir')
		WebUI.uploadFile(findTestObject('Object Repository/Asset/addAsset/chooseFile'),userDir + '\\ima.jpg')
		action.click(findTestObject('Object Repository/Asset/addAsset/clickTenant'))
		action.click(findTestObject('Object Repository/Asset/addAsset/selectTenent'))

		action.click(findTestObject('Object Repository/Asset/addAsset/AddAsset'))
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
		action.click(findTestObject('Asset/delAssetCheckbox(assetName)',["assetName":assetName]))
		action.click(findTestObject('Object Repository/Asset/delIcon'))
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
		action.click(findTestObject('Object Repository/Asset/anyAsset(assetName)',["assetName":assetName]))
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
		action.click(findTestObject('Object Repository/Asset/assetEditpage/close'))
	}

	@Keyword
	def cancleButtonInAddAsset() {

		clickAssetTab()
		clickAddAsset()
		String assetName = "Author"+random.nextInt(1000)

		action.sendKeys(findTestObject('Object Repository/Asset/addAsset/Author'), assetName)
		action.click(findTestObject('Asset/addAsset/cancleButton'))

		return assetName
	}

	@Keyword
	def editSourceInAssetPage() {

		clickAssetTab()
		def assetName = clickAnyAsset()
		action.click(findTestObject('Asset/assetEditpage/sourceDropdown'))
		def sourceName = selectRandomSourceName()
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/sourceSelect(sourceName)',["sourceName":"toyotae2"]))
		action.click(findTestObject('Object Repository/Asset/assetEditpage/Save'))
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/Public'))
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/publicSelect'))
		//action.click(findTestObject('Object Repository/Asset/assetEditpage/Save'))
		action.click(findTestObject('Object Repository/Asset/assetEditpage/close'))
		return ["sourceName":sourceName , "assetName":assetName]
	}

	@Keyword
	def verifyEditAsset(def assetName, def sourceName) {

		searchForAsset(assetName)
		action.click(findTestObject('Object Repository/Asset/anyAsset(assetName)',["assetName":assetName]))
		verifications.verifyElementPresent(findTestObject('Object Repository/Asset/assetEditpage/sourceName(sourceName)',["sourceName":sourceName]),sourceName+'is not getting reflecting',5)
	}

	@Keyword
	def selectRandomSourceName() {

		def sourceCount = action.getElementCount(findTestObject('Object Repository/Asset/assetEditpage/sourceCount'))

		if(sourceCount == 0) {
			//close source
			action.click(findTestObject('Object Repository/Asset/assetEditpage/Save'))
		}
		else {
			def index = random.nextInt(sourceCount)
			if(index == 0) {
				index = index +1
			}
			action.click(findTestObject('Object Repository/Asset/assetEditpage/randomSource(index)',["index":index]))
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






