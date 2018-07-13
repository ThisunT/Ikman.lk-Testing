package exercise_02;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "/Users/thisunpathirage/IdeaProjects/IkmanlkTesting/src/test/resources/adfeatures.feature"
        ,glue={"IkmanBDD"}
        ,format={"pretty","html:target/cucumber"}
)

public class BDDExecute {

}