package login

import java.awt.Robot
import java.awt.event.KeyEvent

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

public class LoginForJourneyApplication {

	Robot robot = new Robot()

	@Keyword
	def loginToJourneyApplication() {

//		robot.keyPress(KeyEvent.VK_CONTROL)
//		robot.keyPress(KeyEvent.VK_T)
//		robot.keyRelease(KeyEvent.VK_CONTROL)
//		robot.keyRelease(KeyEvent.VK_T)
//		WebUI.switchToWindowIndex(WebUI.getWindowIndex()+1)
		WebUI.navigateToUrl(GlobalVariable.journeyUrl)
	}

	@Keyword
	def reloadThePage() {
		robot.keyPress(KeyEvent.VK_F5)
		robot.keyRelease(KeyEvent.VK_F5)
	}
}
