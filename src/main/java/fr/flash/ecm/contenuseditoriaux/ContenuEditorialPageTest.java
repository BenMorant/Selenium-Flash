package fr.flash.ecm.contenuseditoriaux;

import static fr.flash.util.TestUtil.LI_SCENARIO;

import fr.flash.ecm.exemple.CommunFlashPageTest;
import fr.flash.sitesexternes.superdup.SuperdupTest;
import fr.flash.util.TestUtil;
import fr.flash.util.annotations.DescriptionTest;
import fr.flash.util.annotations.Feature;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
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
          LI_SCENARIO + "Etape 3 : Vérification de la présence sur Superdup" +
          LI_SCENARIO + "Etape 4 : Vérification de la présence sur le site frontal" +
          "</ul>")
  public void publicationContenuEditorial() {
    String dateJourTime1 = TestUtil.sdf.format(new Date());
    titreSaisie = "SELENIUM Contenu Editorial" + dateJourTime1;

    INFO("création d'un contenu éditorial de titre : " + titreSaisie);
    creationContenuEditorial();

    INFO("Demande de publication du contenu éditorial");
    boolean publiOk = demandePublicationContenuEditorial();

    if (publiOk) {
      INFO("Vérification de la présence du contenu éditorial sur Superdup");
      verificationSuperdup();

      INFO("Vérification de la présence du contenu éditorial sur le site frontal");
      verificationPresenceSiteFrontal(titreSaisie);
    } else {
      INFO("Erreur sur publication ; on ne vérifie pas sur SuperDup ou le site frontal");
    }
  }


  private void verificationSuperdup() {
    SuperdupTest superdupTest = initSuperdupTest();
    String flashId = contenuEditorialPage.getFlashId(getCurrentUrl());

    String documentTitle = contenuEditorialPage.titleDocument.text();

    superdupTest.connexionSuperdup();

    superdupTest.goToModulePublicationEnded();
    superdupTest.searchFinishedPublicationsByFlashId(flashId, documentTitle);
    superdupTest.printModuleInfo();
    String statusTraitement = superdupTest.getProcessingStatus();
    Assertions.assertThat(statusTraitement)
        .as("Le traitement sur Superdup doit avoir un statut COMPLETED").isEqualTo("COMPLETED");
    boolean downloadAndUnzip = superdupTest.telechargerEtDezipper();
    Assertions.assertThat(downloadAndUnzip)
        .as("le dossier dézipppé doit être présent dans le repertoire").isTrue();
    List<Path> listeFichiers = superdupTest.listAllFiles();
    Assertions.assertThat(listeFichiers.size())
        .as("le dossier dézippé doit contenir un seul élément").isEqualTo(1);
    String titreXml = superdupTest.findXmlFileTitle(listeFichiers.get(0));
    INFO("Le titre dans le fichier XML est : " + titreXml);
    Assertions.assertThat(titreXml)
        .as("Le titre du contenu éditorial dans le document XML et le titre saisi doivent être identiques.")
        .isEqualTo(titreSaisie);
  }

  private void verificationPresenceSiteFrontal(String titreSaisie) {
  }

  private boolean demandePublicationContenuEditorial() {
    boolean publiOk = true;
    return publiOk;
  }


  private void creationContenuEditorial() {

  }


  private SuperdupTest initSuperdupTest() {
    SuperdupTest superdupTest = new SuperdupTest();
    superdupTest.initFluent(getLocaldriver());
    superdupTest.loggertest = this.loggertest;
    superdupTest.initialisationScreenShot();
    return superdupTest;
  }

}
