package fr.flash.util;

import com.aventstack.extentreports.Status;
import org.fluentlenium.core.FluentPage;

public interface Screenshot {

  void takeScreenshot(FluentPage page, String legende, Status status);

  void takeScreenshot(FluentPage page, String legende);
}
