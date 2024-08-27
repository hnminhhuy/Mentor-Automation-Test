import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.configuration.RunConfiguration

WebUI.refresh()

// Open the form
WebUI.click(findTestObject('Object Repository/Page_GroupManagement/btn_add'))

// Fill the form
WebUI.click(findTestObject('Object Repository/Page_GroupManagement/input_groupname'))

WebUI.setText(findTestObject('Object Repository/Page_GroupManagement/input_groupname'), groupName)

WebUI.click(findTestObject('Object Repository/Page_GroupManagement/input_description'))

WebUI.setText(findTestObject('Object Repository/Page_GroupManagement/input_description'), groupDescription)

if (groupType) {
    WebUI.click(findTestObject('Object Repository/Page_GroupManagement/input_grouptype'))

    WebUI.sendKeys(findTestObject('Object Repository/Page_GroupManagement/input_grouptype'), Keys.chord(Keys.ARROW_DOWN))

    WebUI.sendKeys(findTestObject('Object Repository/Page_GroupManagement/input_grouptype'), Keys.chord(Keys.ENTER))
}

if (mentorUseFile) {
    // Upload file
    WebUI.click(findTestObject('Object Repository/Page_GroupManagement/upload_mentor'))

    WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/input_file'), 0, FailureHandling.OPTIONAL)
	
	String projectPath = RunConfiguration.getProjectDir()
	
	String filePath = (projectPath + '\\') + mentorContent
	filePath = filePath.replace('/', '\\')
	
    WebUI.uploadFile(findTestObject('Object Repository/Page_GroupManagement/input_file'),  filePath)
	
	WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), 0, FailureHandling.OPTIONAL)
	
	String noti = WebUI.getText(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), FailureHandling.OPTIONAL)

    WebUI.click(findTestObject('Object Repository/Page_GroupManagement/btn_upload'))

	if (noti != '') {
		WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), 0, FailureHandling.OPTIONAL)
		noti = WebUI.getText(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), FailureHandling.CONTINUE_ON_FAILURE)
	}

    WebUI.verifyMatch(noti, ('.*' + mentorMessagePattern) + '.*', !(mentorUploadExpected), FailureHandling.CONTINUE_ON_FAILURE)

    // Recovery
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/div_uploader'), 0, FailureHandling.CONTINUE_ON_FAILURE)) {
        WebUI.click(findTestObject('Object Repository/Page_GroupManagement/btn_cancelUpload'), FailureHandling.CONTINUE_ON_FAILURE)
    }
} else {
    List<String> mentorEmailList = mentorContent.split(',').collect({ 
            it.trim()
        })

    for (def email : mentorEmailList) {
        WebUI.setText(findTestObject('Object Repository/Page_GroupManagement/input_mentor'), email)

        WebUI.sendKeys(findTestObject('Object Repository/Page_GroupManagement/input_mentor'), Keys.chord(Keys.ENTER))
    }
}

if (menteeUseFile) {
    // Upload file
    WebUI.click(findTestObject('Object Repository/Page_GroupManagement/upload_mentee'))

    WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/input_file'), 0, FailureHandling.OPTIONAL)
	
	String projectPath = RunConfiguration.getProjectDir()	
	String filePath = (projectPath + '\\') + menteeContent
	filePath = filePath.replace('/', '\\')
	
	WebUI.uploadFile(findTestObject('Object Repository/Page_GroupManagement/input_file'),  filePath)
	WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), 0, FailureHandling.OPTIONAL)
	
	String noti = WebUI.getText(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), FailureHandling.CONTINUE_ON_FAILURE)
	
    WebUI.click(findTestObject('Object Repository/Page_GroupManagement/btn_upload'))

	if (noti == '') {
		WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), 0, FailureHandling.OPTIONAL)
		noti = WebUI.getText(findTestObject('Object Repository/Page_GroupManagement/upload_notification'), FailureHandling.CONTINUE_ON_FAILURE)
	} 
   
    WebUI.verifyMatch(noti, ('.*' + mentorMessagePattern) + '.*', !(menteeUploadExpected), FailureHandling.CONTINUE_ON_FAILURE)

    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/div_uploader'), 0, FailureHandling.CONTINUE_ON_FAILURE)) {
        WebUI.click(findTestObject('Object Repository/Page_GroupManagement/btn_cancelUpload'),FailureHandling.CONTINUE_ON_FAILURE)
    }
} else {
    List<String> menteeEmailList = menteeContent.split(',').collect({ 
            it.trim()
        })

    for (def email : menteeEmailList) {
        WebUI.setText(findTestObject('Object Repository/Page_GroupManagement/input_mentee'), email)

        WebUI.sendKeys(findTestObject('Object Repository/Page_GroupManagement/input_mentee'), Keys.chord(Keys.ENTER))
    }
}

String[] start_pattern = start.split('/')

String[] end_pattern = end.split('/')

WebUI.click(findTestObject('Object Repository/Page_GroupManagement/input_start'))

for (def var : (0..2)) {
    WebUI.sendKeys(findTestObject('Object Repository/Page_GroupManagement/input_start'), start_pattern[var])
}

WebUI.click(findTestObject('Object Repository/Page_GroupManagement/input_end'))

for (def var : (0..2)) {
    WebUI.sendKeys(findTestObject('Object Repository/Page_GroupManagement/input_end'), end_pattern[var])
}

WebUI.click(findTestObject('Object Repository/Page_GroupManagement/btn_confirm'))

// Verify point
switch (expected) {
    case 'success':
		WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/unotification_message'), 0, FailureHandling.OPTIONAL)
        String noti = WebUI.getText(findTestObject('Object Repository/Page_GroupManagement/notification_message'), FailureHandling.CONTINUE_ON_FAILURE)
		
        WebUI.verifyMatch(noti, ('.*' + messagePattern) + '.*', true, FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyElementText(findTestObject('Page_GroupManagement/verify_groupname'), groupName, FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyElementText(findTestObject('Page_GroupManagement/verify_grouptype'), 'Software testing', FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyElementText(findTestObject('Page_GroupManagement/verify_start'), start, FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyElementText(findTestObject('Page_GroupManagement/verify_end'), end, FailureHandling.CONTINUE_ON_FAILURE)

        break
    case 'form-validation':
        if (groupName == '') {
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/validate_groupname'), 0, FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        if (!(groupType)) {
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/validate_grouptype'), 0, FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        if ((mentorUseFile && !(mentorUploadExpected)) || (mentorContent == '')) {
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/validate_mentor'), 0, FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        if ((menteeUseFile && !(menteeUploadExpected)) || (menteeContent == '')) {
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/validate_mentee'), 0, FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        break
    case 'failed':
	
		WebUI.verifyElementPresent(findTestObject('Object Repository/Page_GroupManagement/notification_message'), 0, FailureHandling.OPTIONAL)
        String noti = WebUI.getText(findTestObject('Object Repository/Page_GroupManagement/notification_message'), FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyMatch(noti, ('.*' + messagePattern) + '.*', true, FailureHandling.CONTINUE_ON_FAILURE)

        break
    default:
        break
}

WebUI.delay(2)


