package fr.flash.sitesexternes.superdup;

import fr.flash.JUnitTestAvecScreenshot;
import fr.flash.util.annotations.Feature;
import java.util.concurrent.TimeUnit;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;

public class SuperdupTest extends JUnitTestAvecScreenshot {

  @Page
  SuperdupTableauDeBordPage superdupTableauDeBordPage;
  @Page
  SuperdupConnexionPage superdupConnexionPage;


  @Test
  @Feature("Superdup")
  public void verifierConnexionSuperdup() {
    INFO("Essai de connexion sur Superdup...");
    connexionSuperdup();
    takeScreenshot(superdupTableauDeBordPage, "connexion réussie");
  }


  public void connexionSuperdup() {
    superdupConnexionPage.connexion();
    await().explicitlyFor(500, TimeUnit.MILLISECONDS).atMost(25, TimeUnit.SECONDS).until(
        superdupTableauDeBordPage.bienvenueASuperdup).displayed();
  }


}
