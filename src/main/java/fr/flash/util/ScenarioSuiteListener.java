package fr.flash.util;


import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class ScenarioSuiteListener extends RunListener {

  private static final int NB_SUITE = 3;
  private static final int NB_SUITE_TO_EXECUTE = calculateExecutionNumber(NB_SUITE);


  private static int calculateExecutionNumber(int n) {
    return n * (n + 1) / 2;
  }

  @Override
  public void testRunStarted(Description description) throws Exception {

  }

  @Override
  public void testRunFinished(Result result) throws Exception {

  }
}
