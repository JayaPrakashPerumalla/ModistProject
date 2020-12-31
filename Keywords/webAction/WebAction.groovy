package webAction

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

public class WebAction {


	int waitTime = GlobalVariable.defaultWaitTime

	@Keyword
	def click(TestObject element) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.click(element)
	}
	
	@Keyword
	def sendKeys(TestObject element, String text) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.clearText(element)
		WebUI.sendKeys(element, text)
	}
	
	
	@Keyword
	def wait(TestObject element, int maxWaitTime = waitTime) {
		WebUI.waitForElementPresent(element, maxWaitTime)
		WebUI.waitForElementVisible(element, maxWaitTime)
	}

	@Keyword
	def getElementCount(TestObject element, int waitTimeLocal = 5) {

		def count
		if(!(WebUI.verifyElementPresent(element, waitTimeLocal, FailureHandling.OPTIONAL))) {
			count = 0
		}
		else {
			count = WebUiCommonHelper.findWebElements(element, waitTime).size()
		}
		return count
	}
	
	@Keyword
	def getColumnIndex(List headers, String columName){

		WebElement table = HTMLTableHelper.identifyTableByColumnHeaders(headers, 10,  FailureHandling.CONTINUE_ON_FAILURE)
		return HTMLTableHelper.getColumnIndexByHeader(table, columName, FailureHandling.STOP_ON_FAILURE)
	}
}
