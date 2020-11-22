package fr.flash.sitesexternes.superdup;

import org.w3c.dom.Element;

public class MediaInXml {

  private String mediaUriDossierLocal;
  private String mediaCheminFichier;
  private String mediaIdFichier;
  private String mediaNomFichier;
  private String mediaExtensionFichier;
  private String mediaUriFichier;
  private String mediaTailleFichier;

  public MediaInXml(Element element) {
    this.setMediaUriDossierLocal(element.getAttribute("Media:mediaLocalDirUri"));
    this.setMediaCheminFichier(element.getAttribute("Media:mediaFilePath"));
    this.setMediaIdFichier(element.getAttribute("Media:mediaFileId"));
    this.setMediaNomFichier(element.getAttribute("Media:mediaFileName"));
    this.setMediaExtensionFichier(element.getAttribute("Media:mediaFileExt"));
    this.setMediaUriFichier(element.getAttribute("Media:mediaFileUri"));
    this.setMediaTailleFichier(element.getAttribute("Media:mediaFileSize"));
  }



  public String getMediaUriDossierLocal() {
    return mediaUriDossierLocal;
  }

  public void setMediaUriDossierLocal(String mediaUriDossierLocal) {
    this.mediaUriDossierLocal = mediaUriDossierLocal;
  }

  public String getMediaCheminFichier() {
    return mediaCheminFichier;
  }

  public void setMediaCheminFichier(String mediaCheminFichier) {
    this.mediaCheminFichier = mediaCheminFichier;
  }


  public String getMediaIdFichier() {
    return mediaIdFichier;
  }

  public void setMediaIdFichier(String mediaIdFichier) {
    this.mediaIdFichier = mediaIdFichier;
  }

  public String getMediaNomFichier() {
    return mediaNomFichier;
  }

  public void setMediaNomFichier(String mediaNomFichier) {
    this.mediaNomFichier = mediaNomFichier;
  }

  public String getMediaExtensionFichier() {
    return mediaExtensionFichier;
  }

  public void setMediaExtensionFichier(String mediaExtensionFichier) {
    this.mediaExtensionFichier = mediaExtensionFichier;
  }

  public String getMediaUriFichier() {
    return mediaUriFichier;
  }

  public void setMediaUriFichier(String mediaUriFichier) {
    this.mediaUriFichier = mediaUriFichier;
  }

  public String getMediaTailleFichier() {
    return mediaTailleFichier;
  }

  public void setMediaTailleFichier(String mediaTailleFichier) {
    this.mediaTailleFichier = mediaTailleFichier;
  }
}
