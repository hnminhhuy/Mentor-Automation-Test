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

WebUI.click(findTestObject('Object Repository/Page_AccountManagement/button_addThm'))

WebUI.delay(1)

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_AccountManagement/form_NewAccount'), 1)

WebUI.click(findTestObject('Object Repository/Page_AccountManagement/input_fullname'))

WebUI.setText(findTestObject('Object Repository/Page_AccountManagement/input_fullname'), fullname)

WebUI.click(findTestObject('Object Repository/Page_AccountManagement/input_email'))

WebUI.setText(findTestObject('Object Repository/Page_AccountManagement/input_email'), email)

WebUI.click(findTestObject('Object Repository/Page_AccountManagement/input_role'))

String role_text = ''

WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.CONTROL, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.DELETE))

switch (role) {
    case 'admin':
        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ARROW_DOWN))

        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ARROW_DOWN))

        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ENTER))

        role_text = 'Quản trị viên'

        break
    case 'user':
        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ARROW_DOWN))

        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ARROW_DOWN))

        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ARROW_DOWN))

        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ENTER))

        role_text = 'Người dùng'

        break
    case 'super_admin':
        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ARROW_DOWN))

        WebUI.sendKeys(findTestObject('Object Repository/Page_AccountManagement/input_role'), Keys.chord(Keys.ENTER))

        role_text = 'Quản trị viên cấp cao'

        break
    default:
        break
}

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/Page_AccountManagement/btn_confirm'))

String regex = ('.*' + message_pattern) + '.*'

switch (expected) {
    case 'success':
        // Verify the notification
        String actualNotification = WebUI.getText(findTestObject('Object Repository/Page_AccountManagement/text_notification'), 
            FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyMatch(actualNotification, regex, true, FailureHandling.CONTINUE_ON_FAILURE)

        // Verify the data
        WebUI.verifyElementText(findTestObject('Object Repository/Page_AccountManagement/verify_fullname'), fullname, FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyElementText(findTestObject('Object Repository/Page_AccountManagement/verify_email'), email, FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyElementText(findTestObject('Object Repository/Page_AccountManagement/verify_role'), role_text, FailureHandling.CONTINUE_ON_FAILURE)

        break
    case 'form-validation':
        if (fullname == '') {
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_AccountManagement/validate_fullname'), 0, 
                FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        if (email == '') {
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_AccountManagement/validate_email'), 0, FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        if (role == '') {
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_AccountManagement/validate_role'), 0, FailureHandling.CONTINUE_ON_FAILURE)
        }
        
        break
    case 'failed':
        String actualNotification = WebUI.getText(findTestObject('Object Repository/Page_AccountManagement/text_notification'), 
            FailureHandling.CONTINUE_ON_FAILURE)

        WebUI.verifyMatch(actualNotification, regex, true, FailureHandling.CONTINUE_ON_FAILURE)

        break
    default:
        break
}

WebUI.delay(2)

WebUI.refresh()

