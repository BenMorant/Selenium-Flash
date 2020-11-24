package fr.flash.sitesexternes.superdup;

import fr.flash.JUnitTestAvecScreenshot;
import fr.flash.util.LaunchUtil;
import fr.flash.util.annotations.Feature;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SystemUtils;
import org.assertj.core.api.Assertions;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;

public class SuperdupTest extends JUnitTestAvecScreenshot {

  private final String FIN_FICHIER_TELECHARGE = ".crdownload";
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

  public boolean telecharger() {
    try {
      goToDetailsTraitement();
      String debutNomFichier = remplacerSlashParUnderscore(
          superdupTableauDeBordPage.nomDuFichierTraite);
      superdupTableauDeBordPage.goToUrlFichierTraite();
      await().explicitlyFor(1, TimeUnit.SECONDS).atMost(600, TimeUnit.SECONDS)
          .until(() -> isTelechargementFini(debutNomFichier, cheminContenuDuFichier()));
      Path FichierTelecharge = null;
      FichierTelecharge = cheminContenuDuFichier().resolve(
          Objects
              .requireNonNull(fichierTelechargeComplet(debutNomFichier, cheminContenuDuFichier())));
      return Files.exists(FichierTelecharge);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }


  public String getProcessingStatus() {
    FluentList<FluentWebElement> secondLine = superdupTableauDeBordPage
        .getAllTDLines(superdupTableauDeBordPage.deuxiemeLigneTableau.first());
    return secondLine.get(8).textContent();
  }

  public void verifierSiComplete() {
    FluentList<FluentWebElement> secondLine = superdupTableauDeBordPage
        .getAllTDLines(superdupTableauDeBordPage.deuxiemeLigneTableau.first());
    Assertions.assertThat(secondLine.get(8).textContent())
        .as("Le module doit avoir un statut COMPLETED").isEqualTo("COMPLETED");
  }

  public void connexionSuperdup() {
    superdupConnexionPage.connexion();
    await().explicitlyFor(500, TimeUnit.MILLISECONDS).atMost(25, TimeUnit.SECONDS).until(
        superdupTableauDeBordPage.bienvenueASuperdup).displayed();
  }

  public void goToModulePublicationEnded() {
    goTo(LaunchUtil.superdupDefinition.getUrl()
        + "index.html#/app/publications/endedProcessing/PUBLICATION");
    tableResultsLoading();
    takeScreenshot(superdupTableauDeBordPage,
        "liste de tous les modules de publication terminés (= COMPLETED ou FAILED)");
  }

  public void searchFinishedPublicationsByFlashId(String flashId, String documentTitle) {
    superdupTableauDeBordPage.champDeRecherche.fill().withText(flashId);
    int nbLoop = 0;
    do {
      superdupTableauDeBordPage.boutonRecherche.click();
      await().explicitlyFor(5, TimeUnit.SECONDS);
      nbLoop++;
    } while (superdupTableauDeBordPage.getLignesResultats().size() == 0 && nbLoop < 60);

    FluentList<FluentWebElement> firstLine = superdupTableauDeBordPage
        .getAllTDLines(superdupTableauDeBordPage.premiereLigneTableau.first());
    Assertions.assertThat(documentTitle)
        .as("le tître du document doit correspondre aux infos reçues")
        .isEqualTo(firstLine.get(1).textContent());
    takeScreenshot(superdupTableauDeBordPage, "résultat de la recherche");
  }

  public void printModuleInfo() {
    FluentList<FluentWebElement> firstLine = superdupTableauDeBordPage
        .getAllTDLines(superdupTableauDeBordPage.premiereLigneTableau.first());
    FluentList<FluentWebElement> secondLine = superdupTableauDeBordPage
        .getAllTDLines(superdupTableauDeBordPage.deuxiemeLigneTableau.first());
    INFO("Nom du job : " + firstLine.get(0).textContent() +
        "<br> Titre : " + firstLine.get(1).textContent() +
        "<br> Container : " + firstLine.get(2).textContent() +
        "<br> Canal : " + secondLine.get(0).textContent() +
        "<br> TEE : " + secondLine.get(1).textContent() +
        "<br> Action : " + secondLine.get(2).textContent() +
        "<br> Environnement : " + secondLine.get(3).textContent() +
        "<br> Email : " + secondLine.get(4).textContent() +
        "<br> Debut traitement : " + secondLine.get(5).textContent() +
        "<br> Date de fin d'exécution : " + secondLine.get(6).textContent() +
        "<br> Durée : " + secondLine.get(7).textContent() + " millisecondes." +
        "<br> Statut : " + secondLine.get(8).textContent());
  }

  private void goToDetailsTraitement() {
    superdupTableauDeBordPage.premiereLigneTableau.first().click();
    await().explicitlyFor(500, TimeUnit.MILLISECONDS).atMost(25, TimeUnit.SECONDS).until(
        superdupTableauDeBordPage.panelBody).displayed();
    takeScreenshot(superdupTableauDeBordPage, "détails du traitement");
  }

  private String remplacerSlashParUnderscore(FluentWebElement fluentWebElement) {
    return fluentWebElement.textContent().replace('/', '_');
  }


  private void tableResultsLoading() {
    await().explicitlyFor(500, TimeUnit.MILLISECONDS).atMost(100, TimeUnit.SECONDS).until(
        superdupTableauDeBordPage.resultatsTableau).size().greaterThan(0);
  }

  private boolean isTelechargementFini(String prefix, Path path) {
    final DirectoryStream.Filter<Path> filter = getFilter(prefix);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
      for (Path file : stream) {
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  private Path fichierTelechargeComplet(String prefix, Path path) throws IOException {
    final DirectoryStream.Filter<Path> filter = getFilter(prefix);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
      for (Path file : stream) {
        return file.getFileName();
      }
    }
    return null;
  }

  private DirectoryStream.Filter<Path> getFilter(String prefix) {
    return entry -> Files.isRegularFile(entry)
        && entry.getFileName().toString().startsWith(prefix)
        && !entry.getFileName().toString().endsWith(FIN_FICHIER_TELECHARGE);
  }


  private Path cheminContenuDuFichier() {
    if (SystemUtils.IS_OS_WINDOWS) {
      return Paths.get("C:\\temp");
    } else {
      return Paths.get("/tmp/");
    }
  }
}
