package login

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import webAction.WebAction


public class LoginForAdminDevEnvironment {

	WebAction action = new WebAction()
	@Keyword
	def login(String Email,String Password) {
		if(WebUI.verifyElementPresent(findTestObject('Object Repository/Login Credentials/Email'), 4, FailureHandling.OPTIONAL)) {
			errorLogin()
			action.sendKeys(findTestObject('Object Repository/Login Credentials/Email'),Email)
			action.sendKeys(findTestObject('Object Repository/Login Credentials/Password'),Password)
			action.click(findTestObject('Object Repository/Login Credentials/EnterButton'))
		}
	}

	def errorLogin() {
		if(WebUI.verifyElementPresent(findTestObject('Object Repository/errorMessageForInvalidCredentials'), 4, FailureHandling.OPTIONAL)) {
			action.click(findTestObject('Object Repository/CommonButtons/Close'))
		}
	}

	@Keyword
	def verifyErrorMessageForInvalidLogin() {
		WebUI.verifyElementPresent(findTestObject('Object Repository/errorMessageForInvalidCredentials'), 4)
	}
}
