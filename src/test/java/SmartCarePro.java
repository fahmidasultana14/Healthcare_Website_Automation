import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class SmartCarePro {
WebDriver driver;

    String filePath = System.getProperty("user.dir") + "/src/test/resources/demo_file.txt";

    @BeforeAll
    public void setup(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }
    @Test
    public void automateVitals() throws InterruptedException, IOException {


        String nrc = "";
        String date = "";
        String time = "";
        String weight = "";
        String height = "";
        String temperature = "";
        String systolic = "";
        String diastolic = "";
        String pulseRate = "";
        String respiratoryRate = "";
        String oxygenSaturation = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split line by a delimiter
                String[] data = line.split("\t");
                nrc = data[0];
                date = data[1];
                time = data[2];
                weight = data[3];
                height = data[4];
                temperature = data[5];
                systolic = data[6];
                diastolic = data[7];
                pulseRate = data[8];
                respiratoryRate = data[9];
                oxygenSaturation = data[10];


            }
        } catch(IOException e){

        }


            driver.get("https://carepro-training.ihmafrica.com/");   /////Navigate to the webpage
            driver.findElement(By.name("username")).sendKeys("tester");    ///  Username input
            driver.findElement(By.name("password")).sendKeys("tester2023!");  ////password input
            driver.findElement(By.cssSelector("[type='submit']")).click();            //submit button
            Thread.sleep(9000);//7000
            WebElement province = driver.findElements(By.className("custom-input")).get(0);
            Select select = new Select(province);
            select.selectByValue("1");                               ///select province
            WebElement district = driver.findElements(By.className("custom-input")).get(1);
            Select selectDistrict = new Select(district);
            selectDistrict.selectByVisibleText("Lusaka");             ///select district
            WebElement facility = driver.findElement(By.tagName("input"));

            facility.sendKeys("Dr.");
            Thread.sleep(6000);

            driver.findElement(By.xpath("//div[normalize-space()='Dr. Watson Dental Clinic']")).click();
            ////  facility click

            driver.findElement(By.className("bg-primaryColor")).click();     /// Enter click
            Thread.sleep(2000);
            driver.findElement(By.name("nrc")).sendKeys(nrc);  ///NRC
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("[type='submit']")).click();    ///search button click
            driver.findElement(By.xpath(" //button[normalize-space()='Attend to Patient']")).click();   //attend to patient click

            Thread.sleep(5000);
            driver.findElement(By.xpath("//p[normalize-space()='Vital']")).click();   // Vital button clivk
            Thread.sleep(19000);
            driver.findElement(By.xpath("//button[normalize-space()='Add Vital']")).click();  //Add Vital Click

            ////////////////// form Start/////////////////
            WebElement Weight = driver.findElement(By.name("weight"));
            Weight.click();
            Weight.clear();
            Weight.sendKeys(weight);

            WebElement Height = driver.findElement(By.name("height"));
            Height.click();
            Height.clear();
            Height.sendKeys(height);


            WebElement Temperature = driver.findElement(By.name("temperature"));
            Temperature.sendKeys(temperature);

            WebElement Systolic = driver.findElement(By.name("systolic"));
            Systolic.sendKeys(systolic);

            WebElement Diastolic = driver.findElement(By.name("diastolic"));
            Diastolic.sendKeys(diastolic);

            WebElement PulseRate = driver.findElement(By.name("pulseRate"));
            PulseRate.sendKeys(pulseRate);

            WebElement RespiratoryRate  = driver.findElement(By.name("respiratoryRate"));
            RespiratoryRate.sendKeys(respiratoryRate);

            WebElement OxygenSaturation   = driver.findElement(By.name("oxygenSaturation"));
            OxygenSaturation.sendKeys(oxygenSaturation);
            scroll();

            driver.findElement(By.xpath("//span[contains(@class,'inline-block text-whiteColor')]")).click();

            Thread.sleep(2000);


            String message=driver.findElement(By.className("heading")).getText();
            Assertions.assertTrue(message.contains("Vitals & Anthropometry"));    ///If the execution returns to a page containing header
                                                                                    ///"Vitals & Anthropometry" this means vitals
                                                                                        //has been added successfully
    }

    public void scroll(){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,220)");

    }
    @AfterAll
    public void exit(){
        driver.quit();
    }
}
