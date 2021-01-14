
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable
import login.LoginForAdminDevEnvironment

class Login {

	public static JsonSlurper jsonSlurper = new JsonSlurper()
	LoginForAdminDevEnvironment loginPage = new LoginForAdminDevEnvironment()

	//@BeforeTestSuite
	@BeforeTestCase
	@Keyword
	def OpenWebSite() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(GlobalVariable.Url)
		loginPage.login(GlobalVariable.Email,GlobalVariable.Password)
	}

	@BeforeTestCase
	@Keyword
	def login() {
		Thread.sleep(3000)
		String url = WebUI.getUrl()
		if (!(url.contains('dashboard'))) {
			WebUI.navigateToUrl(GlobalVariable.Url)
			loginPage.login(GlobalVariable.Email,GlobalVariable.Password)
		}
	}

	//@BeforeTestCase
	def getAuthToken() {
		if(GlobalVariable.accessToken.toString() != "invalid" ) {
			return;
		}

		def responseLogin = WS.sendRequest(findTestObject('Object Repository/API/login'))
		def loginJSON = jsonSlurper.parseText(responseLogin.getResponseText())

		println "accessToken: " + loginJSON.token

		GlobalVariable.accessToken = loginJSON.token
	}

	//@AfterTestSuite
	//@AfterTestCase
	@Keyword
	def closewebSite() {
		WebUI.closeBrowser()
	}

}