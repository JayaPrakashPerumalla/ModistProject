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
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable
import verification.Verification
import webAction.WebAction

public class Assetsource {


	WebAction actions = new WebAction()
	Random random = new Random()
	Verification verifications = new Verification()


	@Keyword
	def navigateToAssetSourceDetailsTab() {

		actions.click(findTestObject('AssetSource/Asset Source Tab'))
		verifications.verifyElementPresent(findTestObject('AssetSource/Add Asset Source Button'), "")
	}

	@Keyword
	def clickAddAssetSource() {

		actions.click(findTestObject('AssetSource/Add Asset Source Button'))
		WebUI.waitForPageLoad(30)
	}

	@Keyword
	def addAssetSource() {

		navigateToAssetSourceDetailsTab()
		def sourcename = "RandomAssetSource"+random.nextInt(1000)
		clickAddAssetSource()
		actions.sendKeys(findTestObject('AssetSource/Source'), sourcename)
		actions.click(findTestObject('AssetSource/Asset Source button on popup'))
		return 	sourcename
	}

	@Keyword
	def searchForAnAssetSource(String sourceName) {
		actions.sendKeys(findTestObject('AssetSource/Search'), "")
		for (int i = 0; i < sourceName.length(); i++) {
			WebUI.sendKeys(findTestObject('AssetSource/Search'), sourceName.charAt(i).toString())
		}
	}

	@Keyword
	def verifyAssetSourceAdded(String sourcename) {

		searchForAnAssetSource(sourcename)
		verifications.verifyElementPresent(findTestObject('AssetSource/AssetSourceName',["sourcename":sourcename]), "The AssetSource "+sourcename+" is not added in the list" )
	}

	@Keyword
	def verifyAssetSourcePopUp() {

		String path = 'Object Repository/AssetSource/'

		def elements = ["sourceLabel", "Tenant", "Cancel Button", "Asset Source button on popup",]
		for(element in elements) {
			verifications.verifyElementPresent(findTestObject(path+element), "The element "+element+" is not present")
		}
		actions.click(findTestObject('AssetSource/Cancel Button'))
	}

	@Keyword
	def openAnyExistingAssetSource(String sourcename) {

		searchForAnAssetSource(sourcename)
		WebUI.scrollToElement(findTestObject('AssetSource/AssetSourceName',["sourcename":sourcename]),60)
		actions.click(findTestObject('AssetSource/AssetSourceName',["sourcename":sourcename]))
		verifications.verifyElementPresent(findTestObject('AssetSource/AssetSourceEdit(save button)'),"page is not navigated to asset source edit page")
	}
	@Keyword
	def deleteAnAssetSource(String sourcename){

		openAnyExistingAssetSource(sourcename)
		actions.click(findTestObject('AssetSource/Delete Button'))
	}

	@Keyword
	def verifyAssetSourceDeleted(String sourcename) {

		searchForAnAssetSource(sourcename)
		verifications.verifyElementNotPresent(findTestObject('AssetSource/AssetSourceName',["sourcename":sourcename]), "The AssetSource "+sourcename+" is not deleted from the list")
	}

	@Keyword
	def editAnAssetSource(String sourcename){

		openAnyExistingAssetSource(sourcename)
		def sourceeditedname = 'editedsourcesname'+random.nextInt(10)
		WebUI.clearText(findTestObject('AssetSource/AssetSourceEdit(Source)'))
		actions.sendKeys(findTestObject('AssetSource/AssetSourceEdit(Source)'),sourceeditedname)
		actions.click(findTestObject('AssetSource/AssetSourceEdit(save button)'))
		actions.click(findTestObject('AssetSource/Close Button'))
		return sourceeditedname
	}

	@Keyword
	def closeAssetSourceEditpage(){
		actions.click(findTestObject('AssetSource/Close Button'))
		verifications.verifyElementPresent(findTestObject('AssetSource/Search'), "AssetSource edit page is not getting closed")
	}

	@Keyword
	def cancelAssetSourcePopup(){
		actions.click(findTestObject('AssetSource/Cancel Button'))
		verifications.verifyElementPresent(findTestObject('AssetSource/Search'), "AssetSource popup is not getting closed")
	}
}




