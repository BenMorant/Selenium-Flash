package fr.flash;

import static com.aventstack.extentreports.Status.INFO;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import fr.flash.ecm.exemple.FlashHomePage;
import fr.flash.util.Screenshot;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;

public class JUnitTestBase extends FluentTest implements Screenshot {

  public ExtentTest loggertest;
  @Page
  protected FlashHomePage flashHomePage;
  private Screenshot screenShot;

  @Override
  public void takeScreenshot(FluentPage page, String legende, Status status) {

  }

  @Override
  public void takeScreenshot(FluentPage page, String legende) {

  }

  public void initialisationScreenShot() {
    screenShot = new Screenshot() {
      @Override
      public void takeScreenshot(FluentPage page, String legende, Status status) {

      }

      @Override
      public void takeScreenshot(FluentPage page, String legende) {

      }
    };
  }

  public WebDriver getLocaldriver() {
    return super.getDriver();
  }

  protected void INFO(String info) {
    log(INFO, info);
  }

  private void log(Status logStatus, String text) {
    if (loggertest != null) {
      loggertest.log(logStatus, text);
    }
  }
}
