package fr.flash.ecm.exemple;

import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("#/")
public class FlashHomePage extends CommunFlashPage {

  @FindBy(css = "a[href='#/element1']")
  public FluentWebElement element1;
  @FindBy(className = "element 2")
  public FluentWebElement element2;

}
