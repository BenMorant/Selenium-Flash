package fr.flash.ecm.exemple;

import static com.aventstack.extentreports.Status.PASS;
import static fr.flash.util.TestUtil.LI_SCENARIO;
import static org.junit.Assert.assertTrue;

import fr.flash.util.annotations.DescriptionTest;
import fr.flash.util.annotations.Feature;
import org.junit.Before;
import org.junit.Test;


public class FlashHomePageTest extends CommunFlashPageTest {

  @Before
  public void connectHomePage() {

  }

  @Test
  @DescriptionTest(description = "Vérifie la présence de l'élément 1'<ul>" +
      LI_SCENARIO + "Accès à la page d'accueil" +
      LI_SCENARIO + "Vérification de l'affichage des éléments de la page" +
      "</ul>")
  @Feature({"Accueil"})
  public void notificationZoneVerification() {
    INFO("Verification de la zone de notification");
    assertTrue("L'élément 1 doit être affiché ", flashHomePage.element1.displayed());
    INFO("L'indication affichée par l'élément 1 est  : " + flashHomePage.element1.textContent());
    takeScreenshot(flashHomePage, "capture d'écran de la page montrant l'élément 1", PASS);
  }
}
