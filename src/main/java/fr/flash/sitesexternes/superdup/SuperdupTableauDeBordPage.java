package fr.flash.sitesexternes.superdup;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class SuperdupTableauDeBordPage extends FluentPage {

  @FindBy(css = "h1[=\"dashboard.WELCOME\"]")
  public FluentWebElement bienvenueASuperdup;


}
