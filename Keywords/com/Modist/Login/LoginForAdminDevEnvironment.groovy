package com.Modist.Login

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable


public class LoginForAdminDevEnvironment {



	@Keyword
	def enterEmail(String Email) {

		WebUI.sendKeys(findTestObject('Object Repository/Login Credentials/Email'), Email, FailureHandling.STOP_ON_FAILURE)
	}

	@Keyword
	def enterPassword(String Password) {
		WebUI.sendKeys(findTestObject('Object Repository/Login Credentials/Password'), Password, FailureHandling.CONTINUE_ON_FAILURE)
	}

	@Keyword
	def clickEnterButton() {
		WebUI.click(findTestObject('Object Repository/Login Credentials/EnterButton'))
	}

	@Keyword
	def login() {

		enterEmail(GlobalVariable.Email)
		enterPassword(GlobalVariable.Password)
		clickEnterButton()
	}


	@Keyword
	def wait(int seconds) {
		WebUI.delay(seconds)
	}

	@Keyword
	def OpenWebSite() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(GlobalVariable.Url)
	}
}
