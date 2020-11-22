package fr.flash.util;

import java.util.ResourceBundle;


public class LaunchUtil {

  public static ResourceBundle config;
  public static DefinitionSite superdupDefinition = new DefinitionSite("superdup");

  private static void LaunchConfig() {
    //...
    superdupDefinition.initSiteDefinition(config);
//...
  }


}
