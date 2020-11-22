package fr.flash.ecm.contenuseditoriaux;

import fr.flash.ecm.exemple.FlashHomePage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;


public class ContenuEditorialPage extends FlashHomePage {

  @FindBy(css = "div.infos-doc > div.name-doc")
  public FluentWebElement titleDocument;

  public String getFlashId(String url) {
    String flashId = "randomFlashId";
    return flashId;
  }

}
