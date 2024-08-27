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
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import org.openqa.selenium.WebElement as WebElement
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Date as Date
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Chatting/button_group'), 0)

WebUI.click(findTestObject('Object Repository/Page_Chatting/button_group'))

WebUI.click(findTestObject('Object Repository/Page_Chatting/default_channel'))

// Starting the test
if (messageType == 'text') {
    WebUI.click(findTestObject('Object Repository/Page_Chatting/input_message'))

    WebUI.setText(findTestObject('Object Repository/Page_Chatting/input_message'), messageContent)

    WebUI.sendKeys(findTestObject('Object Repository/Page_Chatting/input_message'), Keys.chord(Keys.ENTER))
} else if ((messageType == 'file') || (messageType == 'image')) {
    if (messageType == 'file') {
        WebUI.click(findTestObject('Object Repository/Page_Chatting/btn_fileUpload'))
    } else if (messageType == 'image') {
        WebUI.click(findTestObject('Object Repository/Page_Chatting/btn_imageUpload'))
    }
    
    WebUI.delay(1)

    Robot robot = new Robot()

    robot.keyPress(KeyEvent.VK_ESCAPE)

    robot.keyRelease(KeyEvent.VK_ESCAPE)

    WebUI.delay(1)

    WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Chatting/input_fileUpload'), 0)

    String projectPath = RunConfiguration.getProjectDir()

    println(projectPath)
	
	String filePath = (projectPath + '\\') + messageContent
	filePath = filePath.replace('/', '\\')
    WebUI.uploadFile(findTestObject('Object Repository/Page_Chatting/input_fileUpload'), filePath)
}

// Verify upload process
if ((messageType == 'file') || (messageType == 'image')) {
    //WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_Chatting/icon_loading'), 0, FailureHandling.CONTINUE_ON_FAILURE)
    String notiMessage = WebUI.getText(findTestObject('Object Repository/Page_Chatting/notiMessage'), FailureHandling.CONTINUE_ON_FAILURE)

    //String notiMessage = WebUI.getAlertText()
    WebUI.verifyMatch(notiMessage, ('.*' + messagePattern) + '.*', true, FailureHandling.CONTINUE_ON_FAILURE)
}

if (expected) {
    // Verify the message time
    Date currentTime = new Date()

    // Create a SimpleDateFormat object with the desired format
    SimpleDateFormat timeFormat = new SimpleDateFormat('HH:mm')

    // Format the current time
    String formattedTime = timeFormat.format(currentTime)

    String actualTime = WebUI.getText(findTestObject('Object Repository/Page_Chatting/message_time'), FailureHandling.CONTINUE_ON_FAILURE).split(
        ' ')[0]

    WebUI.verifyMatch(actualTime, formattedTime, true, FailureHandling.CONTINUE_ON_FAILURE)

    switch (messageType) {
        case 'text':
            WebUI.verifyElementText(findTestObject('Object Repository/Page_Chatting/message_text'), messageContent, FailureHandling.CONTINUE_ON_FAILURE)

            break
        case 'file':
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Chatting/message_fileName'), 0, FailureHandling.CONTINUE_ON_FAILURE)

            break
        case 'image':
            WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Chatting/message_image'), 0, FailureHandling.CONTINUE_ON_FAILURE)

            break
        default:
            break
    }
}

