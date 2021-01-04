package login

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import webAction.WebAction


public class LoginForAdminDevEnvironment {

	WebAction action = new WebAction()



	@Keyword
	def login(String Email,String Password) {

		action.sendKeys(findTestObject('Object Repository/Login Credentials/Email'),Email)
		action.sendKeys(findTestObject('Object Repository/Login Credentials/Password'),Password)
		action.click(findTestObject('Object Repository/Login Credentials/EnterButton'))
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
