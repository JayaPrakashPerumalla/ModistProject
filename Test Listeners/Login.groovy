
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import login.LoginForAdminDevEnvironment

class Login {

	LoginForAdminDevEnvironment loginPage = new LoginForAdminDevEnvironment()

	@BeforeTestSuite
	@Keyword
	def OpenWebSite() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(GlobalVariable.Url)
		loginPage.login(GlobalVariable.Email,GlobalVariable.Password)
		if(!(WebUI.getUrl().endsWith('dashboard'))) {
			WebUI.takeScreenshot()
			KeywordUtil.markFailed('url not navigated to dashboard')
		}
	}

	@BeforeTestCase
	@Keyword
	def login() {
		WebUI.navigateToUrl(GlobalVariable.Url)
		loginPage.login(GlobalVariable.Email,GlobalVariable.Password)
	}

	@AfterTestSuite
	//@AfterTestCase
	@Keyword
	def closewebSite() {
		WebUI.closeBrowser()
	}

}