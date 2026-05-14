package com.iamguru.AI.testCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.iamguru.AI.utilities.AudioCaptureUtil;
import com.iamguru.AI.utilities.ReadConfig;

public class BaseClass {

    ReadConfig readconfig = new ReadConfig();
    public String baseUrlLogin = readconfig.getApplicationURL();
    public String baseUrlRegistration = readconfig.getApplicationURLRegistration();
    public String baseURL = readconfig.getApplicationURL();

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Logger logger = LoggerFactory.getLogger(BaseClass.class);

    public static WebDriver getWebDriver() {
        return driver;
    }

    @Parameters("browser")
    @BeforeClass
    public void setup(String br) {

        if (br.equals("chrome")) {

            ChromeOptions options = new ChromeOptions();

            // Stability flags
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-features=VizDisplayCompositor");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-plugins");
            options.addArguments("--disable-background-timer-throttling");
            options.addArguments("--disable-renderer-backgrounding");
            options.addArguments("--disable-backgrounding-occluded-windows");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-crash-reporter");
            options.addArguments("--disable-in-process-stack-traces");
            options.addArguments("--disable-logging");
            options.addArguments("--disable-dev-tools");
            options.addArguments("--log-level=3");
            options.addArguments("--silent");

            // Twin voice capture flags
            options.addArguments("--use-fake-ui-for-media-stream");
            options.addArguments("--use-fake-device-for-media-stream");
            options.addArguments("--autoplay-policy=no-user-gesture-required");
            options.addArguments("--allow-file-access-from-files");

            // Feed a pre-recorded .wav as fake microphone input
            String fakeAudioInput = System.getProperty("user.dir")
                    + "/src/test/resources/audio/input_voice.wav";
            options.addArguments("--use-file-for-fake-audio-capture=" + fakeAudioInput);

            // Auto-grant mic + camera permissions
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
            prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
            prefs.put("profile.default_content_setting_values.notifications", 2);
            options.setExperimentalOption("prefs", prefs);

            driver = new ChromeDriver(options);

        } else if (br.equals("firefox")) {
            driver = new FirefoxDriver();

        } else if (br.equals("ie")) {
            driver = new InternetExplorerDriver();
        }

        // FIX 1: Reduced implicit wait from 40s to 10s — set ONCE here only
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // FIX 2: Initialize WebDriverWait — was declared but never initialized before
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // FIX 3: Removed driver.get(baseURL) here — each test navigates to its own URL
        // This avoids a redundant page load before every test class
    }

    @AfterClass
    public void tearDown() {
        // Stop any active audio recording before quitting
        if (AudioCaptureUtil.isRecording()) {
            AudioCaptureUtil.stopRecording();
            logger.info("[BaseClass] Audio recording stopped in tearDown.");
        }
        // FIX 4: Uncommented driver.quit() — was commented out causing memory leaks
        if (driver != null) {
            //driver.quit();
        }
    }

    // Screenshot utility
    public void captureScreen(WebDriver driver, String tname) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir")
                + "/Screenshots/" + tname + ".png");
        FileUtils.copyFile(source, target);
        System.out.println("Screenshot taken: " + tname);
    }

    /**
     * FIX 5: startVoiceCapture() now actually starts recording.
     * Previously it only generated a path but never called AudioCaptureUtil.startRecording().
     * Call this just before clicking the Twin call button.
     */
    public String startVoiceCapture() {
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss_SSS")
                .format(new java.util.Date());
        String path = System.getProperty("user.dir")
                + "/AudioRecordings/twin_" + timestamp + ".wav";
        new File(path).getParentFile().mkdirs();
        AudioCaptureUtil.startRecording(path); // FIX: was missing — recording never started
        logger.info("[BaseClass] Voice capture started → " + path);
        return path;
    }

    /**
     * Start recording to a custom path.
     */
    public void startVoiceCapture(String customPath) {
        new File(customPath).getParentFile().mkdirs();
        AudioCaptureUtil.startRecording(customPath);
        logger.info("[BaseClass] Voice capture started → " + customPath);
    }

    /**
     * Stop recording — call this after the Twin session completes.
     */
    public void stopVoiceCapture() {
        AudioCaptureUtil.stopRecording();
        logger.info("[BaseClass] Voice capture stopped.");
    }
}