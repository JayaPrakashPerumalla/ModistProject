package com.Modist.Logout

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
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

public class LogoutForAdminDevEnvironment {


	@Keyword
	def clickingOnProfileIcon() {

		WebUI.click(findTestObject('Object Repository/OptionsUnderProfileIcon/ProfileIcon'))
	}

	def verifyCloseButtonAvailable() {
		WebUI.verifyElementPresent(findTestObject('Object Repository/CommonButtons/Close'), 0)
	}

	@Keyword
	def logout() {
		WebUI.click(findTestObject('Object Repository/OptionsUnderProfileIcon/LogoutOption'))
	}
	
	@Keyword
	def closeBrowser() {
		WebUI.closeBrowser()
	}
}
