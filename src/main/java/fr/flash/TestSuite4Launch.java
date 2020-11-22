package fr.flash;


import fr.flash.ecm.contenuseditoriaux.ContenuEditorialPageTest;
import fr.flash.ecm.exemple.CommunFlashPageTest;
import fr.flash.ecm.exemple.FlashHomePageTest;
import fr.flash.sitesexternes.superdup.SuperdupTest;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({
    ListeTests.class,
    CommunFlashPageTest.class,
    FlashHomePageTest.class,
    ContenuEditorialPageTest.class,
    SuperdupTest.class

})

public class TestSuite4Launch extends JUnitTestSuite {

}
