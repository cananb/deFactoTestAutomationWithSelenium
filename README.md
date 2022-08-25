# DeFacto WebSite Test Automation Project
## About this template

This project tests product purchasing process of the [De Facto](https://www.defacto.com.tr/) via web application.

## Requirements
    IDE (IntelliJ IDE)
    gauge --install java_maven_selenium
    Selenium
    Java
    Log4J

### Defining Specifications

* This template includes a sample specification which opens up a browser and navigates to `Get Started` page of Gauge.
* Add more specifications on top of sample specification.

### Utils
- helper File
- model File
- driver File
- methods File
- steps File
- utils File
- resources
### Writing the implementations

This is where the java implementation of the steps would be implemented. Since this is a Selenium based project, the java implementation would invoke Selenium APIs as required.

_We recommend considering modelling your tests using the [Page Object](https://github.com/SeleniumHQ/selenium/wiki/PageObjects) pattern, and the [Webdriver support](https://github.com/SeleniumHQ/selenium/wiki/PageFactory) for creating them._

- Note that every Gauge step implementation is annotated with a `Step` attribute that takes the Step text pattern as a parameter.
Read more about [Step implementations in Java](http://getgauge.io/documentation/user/current/test_code/java/java.html)

### Execution

* You can execute the specification as:

```
mvn test
```
