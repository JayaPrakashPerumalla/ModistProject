package common

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class uploading {

	@Keyword
	def uploadProfilePicture() {

		WebUI.click(findTestObject('Object Repository/OptionsUnderProfileIcon/Profile Options/Profile'))

		WebUI.click(findTestObject('Object Repository/OptionsUnderProfileIcon/Profile Options/ChooseFile for profile picture'))

		WebUI.delay(5)

		String userDir = System.getProperty('user.dir')

		WebUI.uploadFile(findTestObject('Object Repository/OptionsUnderProfileIcon/Profile Options/choosefile'),userDir + '\\prakashtest.jpg')
	}

	@Keyword
	def getHeaders() {
		int value = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/CommonButtons/headers'), 30).size()

		for(int i=0;i<value;i++) {
			String att = WebUI.getText(findTestObject('Object Repository/CommonButtons/getHeaders',["value":i]))
			println att
		}
	}
}
