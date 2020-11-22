package fr.flash.util;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;

public class FluentFlashElement extends FluentWebElement {


  /**
   * Creates a new fluent web element.
   *
   * @param element      underlying element
   * @param control      control interface
   * @param instantiator component instantiator
   */
  public FluentFlashElement(WebElement element, FluentControl control,
      ComponentInstantiator instantiator) {
    super(element, control, instantiator);
  }
}
