package fr.flash.util;

import java.util.Collections;
import java.util.List;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;


public class ScenarioRunner extends Suite {


  private static List<String> TESTS_LIST = propertiesToList("tests");
  private static List<String> TESTS_LISTIGNORE = propertiesToList("ignoretest");
  private static List<String> FEATURE_LIST = propertiesToList("feature");

  public ScenarioRunner(Class<?> klass, RunnerBuilder builder) throws InitializationError {
    super(klass, builder);
  }

  private static List<String> propertiesToList(String tests) {
    List<String> fakeList = Collections.singletonList(tests);
    return fakeList;
  }
}