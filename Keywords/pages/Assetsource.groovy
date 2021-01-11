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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import verification.Verification
import webAction.WebAction

public class Assetsource {


	WebAction actions = new WebAction()
	Random random = new Random()
	Verification verifications = new Verification()


	@Keyword
	def navigateToAssetSourceDetailsTab() {

		actions.click(findTestObject('Object Repository/AssetSoure/Asset Source Tab'))
		verifications.verifyElementPresent(findTestObject('Object Repository/AssetSoure/Add Asset Source Button'), "")
	}

	@Keyword
	def clickAddAssetSource() {

		actions.click(findTestObject('Object Repository/AssetSoure/Add Asset Source Button'))
		WebUI.waitForPageLoad(30)
	}

	@Keyword
	def addAssetSource() {

		navigateToAssetSourceDetailsTab()
		def sourcename = "RandomAsset"+random.nextInt(1000)
		clickAddAssetSource()
		actions.sendKeys(findTestObject('Object Repository/AssetSoure/Source'), sourcename)
		actions.click(findTestObject('Object Repository/AssetSoure/Asset Source button on popup'))
		return 	sourcename
	}

	@Keyword
	def searchForAnAssetSource(String sourcename) {
		actions.sendKeys(findTestObject('Object Repository/AssetSoure/Search'), sourcename)
	}

	@Keyword
	def verifyAssetSourceAdded(String sourcename) {

		searchForAnAssetSource(sourcename)
		verifications.verifyElementPresent(findTestObject('Object Repository/AssetSoure/AssetSourceName',["sourcename":sourcename]), "The AssetSource "+sourcename+" is not added in the list" )
	}

	@Keyword
	def verifyAssetSourcePopUp() {

		String path = 'Object Repository/AssetSource/'
		
		def elements = ["Source", "Tenant", "Cancel Button", "Asset Source button on popup",]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
	}

	@Keyword
	def openAnyExistingAssetSource(String sourcename) {

		searchForAnAssetSource(sourcename)
		WebUI.scrollToElement(findTestObject('Object Repository/AssetSoure/AssetSourceName',["sourcename":sourcename]),60)
		actions.click(findTestObject('Object Repository/AssetSoure/AssetSourceName',["sourcename":sourcename]))
		verifications.verifyElementPresent(findTestObject('Object Repository/AssetSoure/AssetSourceEdit(save button)'),"page is not navigated to asset source edit page")
	}
	@Keyword
	def deleteAnAssetSource(String sourcename){

		openAnyExistingAssetSource(sourcename)
		actions.click(findTestObject('Object Repository/AssetSoure/Delete Button'))
	}

	@Keyword
	def verifyAssetSourceDeleted(String sourcename) {

		searchForAnAssetSource(sourcename)
		verifications.verifyElementNotPresent(findTestObject('Object Repository/AssetSoure/AssetSourceName',["sourcename":sourcename]), "The AssetSource "+sourcename+" is not deleted from the list")
	}

	@Keyword
	def editAnAssetSource(String sourcename){

		openAnyExistingAssetSource(sourcename)
		def sourceeditedname = 'editedsourcesname'+random.nextInt(10)
		WebUI.clearText(findTestObject('Object Repository/AssetSoure/AssetSourceEdit(Source)'))
		actions.sendKeys(findTestObject('Object Repository/AssetSoure/AssetSourceEdit(Source)'),sourceeditedname)
		actions.click(findTestObject('Object Repository/AssetSoure/AssetSourceEdit(save button)'))
		actions.click(findTestObject('Object Repository/AssetSoure/Close Button'))
		return sourceeditedname
	}

	@Keyword
	def closeAssetSourceEditpage(){
		actions.click(findTestObject('Object Repository/AssetSoure/Close Button'))
		verifications.verifyElementPresent(findTestObject('Object Repository/AssetSoure/Search'), "AssetSource edit page is not getting closed")
	}

	@Keyword
	def cancelAssetSourcePopup(){
		actions.click(findTestObject('Object Repository/AssetSoure/Cancel Button'))
		verifications.verifyElementPresent(findTestObject('Object Repository/AssetSoure/Search'), "AssetSource popup is not getting closed")
	} 
	
	
}




