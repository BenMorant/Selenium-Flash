package fr.flash.ecm.exemple;

import fr.flash.FlashPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class CommunFlashPage extends FlashPage {

  @FindBy(css = "div.commun-element")
  public FluentWebElement communElement;


}
