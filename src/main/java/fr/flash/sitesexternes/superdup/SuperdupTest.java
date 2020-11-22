package fr.flash.sitesexternes.superdup;

import fr.flash.JUnitTestAvecScreenshot;
import fr.flash.util.LaunchUtil;
import fr.flash.util.TestUtil;
import fr.flash.util.annotations.Feature;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.SystemUtils;
import org.assertj.core.api.Assertions;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SuperdupTest extends JUnitTestAvecScreenshot {

  private final String FIN_FICHIER_TELECHARGE = ".crdownload";
  @Page
  SuperdupTableauDeBordPage superdupTableauDeBordPage;
  @Page
  SuperdupConnexionPage superdupConnexionPage;
  private Path dezipRepertoire = getUnzipPath();

  @Test
  @Feature("Superdup")
  public void verifierConnexionSuperdup() {
    INFO("Essai de connexion sur Superdup...");
    connexionSuperdup();
    takeScreenshot(superdupTableauDeBordPage, "connexion réussie");
  }

  public boolean telechargerEtDezipper() {
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
      dezipper(FichierTelecharge, dezipRepertoire);
      SupprimerSiExiste(FichierTelecharge);
      return Files.exists(dezipRepertoire);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public List<Path> listerTousFichiersMedia(Path path) {
    Path mediaPath = path.resolve("MEDIA");
    List<Path> fileList = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(mediaPath)) {
      stream.forEach(fileList::add);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileList;
  }

  public Path getDezipRepertoire() {
    return dezipRepertoire;
  }

  public void setDezipRepertoire(Path dezipRepertoire) {
    this.dezipRepertoire = dezipRepertoire;
  }

  public List<Path> listAllFiles() {
    List<Path> fileList = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dezipRepertoire)) {
      for (Path path : stream) {
        fileList.add(path);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileList;
  }

  public String findXmlFileTitle(Path path) {
    try (InputStream in = new FileInputStream(String.valueOf(path))) {
      String xmlFileTitle = "";
      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLEventFactory ef = XMLEventFactory.newInstance();
      XMLEventReader reader = factory.createXMLEventReader(in);
      boolean findTitle = false;
      while (reader.hasNext()) {
        XMLEvent event = (XMLEvent) reader.next();
        if (event.isCharacters() && findTitle) {
          xmlFileTitle = event.asCharacters().getData();
          return xmlFileTitle;
        } else if (event.isStartElement() && !findTitle) {
          StartElement s = event.asStartElement();
          String tagName = s.getName().getLocalPart();
          if (tagName.equals("titre")) {
            findTitle = true;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public List<MediaInXml> findMediaInXml(File file) {
    List<MediaInXml> mediaInXmls = new ArrayList<>();
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      docFactory.setNamespaceAware(true);
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse(file);
      doc.getDocumentElement().normalize();
      XPath xPath = XPathFactory.newInstance().newXPath();
      XPathExpression expression = xPath.compile("//*[@*[local-name() ='mediaFileName']]");
      NodeList nodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          MediaInXml mediaInXml = new MediaInXml(element);
          mediaInXmls.add(mediaInXml);
        }
      }
    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
      e.printStackTrace();
    }
    return mediaInXmls;
  }

  public void comparerFlashIds(List<MediaInXml> mediaInXmls, List<Path> filesInDirMedia) {
    for (MediaInXml mediaInXml : mediaInXmls) {
      if (isMediaInPaths(mediaInXml, filesInDirMedia)) {
        INFO("L'élément " + mediaInXml.getMediaIdFichier()
            + " est semblable dans le dossier Media et le document XML.");
      } else {
        INFO("L'élément " + mediaInXml.getMediaIdFichier()
            + " n'est pas semblable dans le dossier Media et le document XML.");
      }
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

  private Path getUnzipPath() {
    try {
      return Files
          .createDirectory(cheminContenuDuFichier().resolve(TestUtil.sdf.format(new Date())));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  private void printMediaInXml(MediaInXml mediaInXml) {
    INFO("répertoire du fichier dans Superdup :" + mediaInXml.getMediaUriDossierLocal()
        + "<br>fichier :" + mediaInXml.getMediaCheminFichier()
        + "<br>flash ID :" + mediaInXml.getMediaIdFichier()
        + "<br>nom du fichier :" + mediaInXml.getMediaNomFichier()
        + "<br>extension du fichier :" + mediaInXml.getMediaExtensionFichier()
        + "<br>répertoire du fichier :" + mediaInXml.getMediaUriFichier()
        + "<br>taille du fichier (en octets) :" + mediaInXml.getMediaTailleFichier());
  }

  private boolean isMediaInPaths(MediaInXml mediaInXml, List<Path> filesInMedia) {
    for (Path fileInMedia : filesInMedia) {
      if (isMediaInPath(mediaInXml, fileInMedia)) {
        return true;
      }
    }
    return false;
  }

  private boolean isMediaInPath(MediaInXml mediaInXml, Path fileInMedia) {
    return fileInMedia.getFileName().toString().startsWith(mediaInXml.getMediaIdFichier())
        && fileInMedia.getFileName().toString().endsWith(mediaInXml.getMediaExtensionFichier());
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

  private void SupprimerSiExiste(Path file) {
    try {
      Files.deleteIfExists(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
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

  private void dezipper(Path zipFile, Path decryptDir) throws IOException {
    try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
      ZipEntry entry;
      while ((entry = zipInputStream.getNextEntry()) != null) {
        final Path decryptPath = decryptDir.resolve(entry.getName());
        if (entry.isDirectory()) {
          Files.createDirectory(decryptPath);
        } else {
          Files.copy(zipInputStream, decryptPath);
        }
      }
    }
  }

  private Path cheminContenuDuFichier() {
    if (SystemUtils.IS_OS_WINDOWS) {
      return Paths.get("C:\\temp");
    } else {
      return Paths.get("/tmp/");
    }
  }
}
