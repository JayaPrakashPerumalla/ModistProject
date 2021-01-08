package common

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import webAction.WebAction
public class uploading {

	WebAction actions= new WebAction()

	@Keyword
	def clickProfileIcon() {
		actions.click(findTestObject('Object Repository/OptionsUnderProfileIcon/ProfileIcon'))
	}

	@Keyword
	def clickOnProfile() {
		actions.click(findTestObject('Object Repository/OptionsUnderProfileIcon/Profile Options/Profile'))
	}

	@Keyword
	def uploadProfilePicture() {

		WebUI.delay(5)

		String userDir = System.getProperty('user.dir')

		WebUI.uploadFile(findTestObject('Object Repository/OptionsUnderProfileIcon/Profile Options/choosefile'),userDir + '\\prakashtest.jpg')
	}

	@Keyword
	def getHeaders(String elementName) {
		int value = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/CommonButtons/headers'), 30).size()

		for(int i=0;i<value;i++) {
			String att = WebUI.getText(findTestObject('Object Repository/CommonButtons/getHeaders',["value":i]))
			println att
		}
	}
}
