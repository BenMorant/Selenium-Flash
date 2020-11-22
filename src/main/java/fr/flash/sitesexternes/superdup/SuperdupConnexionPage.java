package fr.flash.sitesexternes.superdup;

import fr.flash.util.LaunchUtil;
import java.util.concurrent.TimeUnit;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class SuperdupConnexionPage extends FluentPage {

  @FindBy(css = "input[ng-model=\"formLogin.username\"]")
  public FluentWebElement champLogin;
  @FindBy(css = "input[ng-model=\"formLogin.password\"]")
  public FluentWebElement champMdp;
  @FindBy(css = "button[ng-click=\"signIn(formLogin)\"]")
  public FluentWebElement boutonConnexion;

  public void connexion() {
    goTo(LaunchUtil.superdupDefinition.getUrl());
    await().explicitlyFor(1, TimeUnit.SECONDS).atMost(25, TimeUnit.SECONDS).until(champLogin)
        .displayed();
    champLogin.fill().withText(LaunchUtil.superdupDefinition.getLogin());
    champMdp.fill().withText(LaunchUtil.superdupDefinition.getPassword());
    boutonConnexion.click();
  }
}

