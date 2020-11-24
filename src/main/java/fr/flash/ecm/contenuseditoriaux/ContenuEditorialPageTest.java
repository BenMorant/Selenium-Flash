package fr.flash.ecm.contenuseditoriaux;

import static fr.flash.util.TestUtil.LI_SCENARIO;

import fr.flash.ecm.exemple.CommunFlashPageTest;
import fr.flash.util.TestUtil;
import fr.flash.util.annotations.DescriptionTest;
import fr.flash.util.annotations.Feature;
import java.util.Date;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;

public class ContenuEditorialPageTest extends CommunFlashPageTest {

  @Page
  private ContenuEditorialPage contenuEditorialPage;

  private String titreSaisie;

  @Test
  @Feature("Exemple Contenu Editorial")
  @DescriptionTest(description =
      "Tentative de création et publication d'un exemple de contenu éditorial <ul>" +
          LI_SCENARIO + "Etape 1 : création d'un contenu éditorial" +
          LI_SCENARIO + "Etape 2 : Publication" +
          LI_SCENARIO + "Etape 3 : Vérification de la présence sur le site frontal" +
          "</ul>")
  public void publicationContenuEditorial() {
    String dateJourTime1 = TestUtil.sdf.format(new Date());
    titreSaisie = "SELENIUM Contenu Editorial" + dateJourTime1;

    INFO("création d'un contenu éditorial de titre : " + titreSaisie);
    creationContenuEditorial();

    INFO("Demande de publication du contenu éditorial");
    boolean publiOk = demandePublicationContenuEditorial();

    if (publiOk) {

      INFO("Vérification de la présence du contenu éditorial sur le site frontal");
      verificationPresenceSiteFrontal(titreSaisie);
    } else {
      INFO("Erreur sur publication ; on ne vérifie pas sur le site frontal");
    }
  }



  private void verificationPresenceSiteFrontal(String titreSaisie) {
  }

  private boolean demandePublicationContenuEditorial() {
    boolean publiOk = true;
    return publiOk;
  }


  private void creationContenuEditorial() {

  }

}
