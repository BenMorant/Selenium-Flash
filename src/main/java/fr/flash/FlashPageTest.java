package fr.flash;

import org.fluentlenium.core.annotation.Page;

public class FlashPageTest extends JUnitTestAvecScreenshot {

  @Page
  protected FlashPage flashPage;

  protected String getCurrentUrl() {
    return getLocaldriver().getCurrentUrl();
  }
}
