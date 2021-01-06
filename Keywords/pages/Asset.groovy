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
import com.kms.katalon.core.webui.common.WebUiCommonHelper


import internal.GlobalVariable
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
}
