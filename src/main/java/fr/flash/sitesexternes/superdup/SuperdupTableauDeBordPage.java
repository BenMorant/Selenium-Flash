package fr.flash.sitesexternes.superdup;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SuperdupTableauDeBordPage extends FluentPage {

  @FindBy(css = "h1[=\"dashboard.WELCOME\"]")
  public FluentWebElement bienvenueASuperdup;

  @FindBy(css = "tr[ng-repeat-start]")
  public FluentList<FluentWebElement> resultatsTableau;

  @FindBy(css = "form[ng-submit=\"search()\"] input[ng-model=\"barInfo.search\"")
  public FluentWebElement champDeRecherche;

  @FindBy(css = "form[ng-submit=\"search()\"] button[type=\"submit\"]")
  public FluentWebElement boutonRecherche;

  @FindBy(css = "div[class=\"panel-wrapper\"] div[class=\"panel-body\"]")
  public FluentWebElement panelBody;

  @FindBy(css = "tr[class=\"nobottom ng-scope\"]")
  public FluentList<FluentWebElement> premiereLigneTableau;

  @FindBy(css = "tr[class=\"notop ng-scope\"]")
  public FluentList<FluentWebElement> deuxiemeLigneTableau;

  @FindBy(css = "a[class=\"btn btn-primary btn-o btn-xs\"]")
  public FluentWebElement lienVersFichierTraite;

  @FindBy(css = "span[class=\"text-bold ng-binding\"]")
  public FluentWebElement nomDuFichierTraite;


  public void goToUrlFichierTraite() {
    goTo(lienVersFichierTraite.attribute("href"));
  }

  public FluentList<FluentWebElement> getAllTDLines(FluentWebElement tr) {
    return tr.$(By.tagName("td"));
  }

  public FluentList<FluentWebElement> getLignesResultats() {
    return this.$(By.xpath("//div[@ng-controller=\"processingJobListCtrl\"]//tbody//tr"));
  }


}
