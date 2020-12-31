import com.Modist.Login.LoginForAdminDevEnvironment
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.Modist.Login.LoginForAdminDevEnvironment
import internal.GlobalVariable as GlobalVariable

class Login {
	
	LoginForAdminDevEnvironment loginPage = new LoginForAdminDevEnvironment()
	
	
	@BeforeTestCase
	@Keyword
	def OpenWebSite() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(GlobalVariable.Url)
		loginPage.login()
		
	}
}