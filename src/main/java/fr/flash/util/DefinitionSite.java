package fr.flash.util;

import java.util.ResourceBundle;

public class DefinitionSite {

  private String url;
  private String login;
  private String password;
  private String keyValue = "";

  public DefinitionSite(String keyValue) {
    this.keyValue = keyValue;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public void initSiteDefinition(ResourceBundle configBundle) {
    if (configBundle.containsKey(keyValue)) {
      ResourceBundle site = ResourceBundle.getBundle(configBundle.getString(keyValue));
      setUrl(site.getString("url"));
      setLogin(site.getString("login"));
      setPassword(site.getString("password"));
    }

  }
}
