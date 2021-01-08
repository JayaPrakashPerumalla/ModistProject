
import org.testng.annotations.AfterTest

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import login.LoginForAdminDevEnvironment

class Login {
	
	LoginForAdminDevEnvironment loginPage = new LoginForAdminDevEnvironment()
	
	
	//@BeforeTestCase
	@BeforeTestSuite
	@Keyword
	def OpenWebSite() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(GlobalVariable.Url)
		loginPage.login(GlobalVariable.Email,GlobalVariable.Password)
		
	}
	
	
}