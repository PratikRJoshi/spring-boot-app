### Spring Boot

- The `spring-boot:run` command is provided by the **Spring Boot Maven Plugin**.
- It allows you to run your Spring Boot application directly from Maven without needing to build a separate JAR or WAR.
- This plugin compiles your code, starts an embedded server (like Tomcat), and launches your app.
- The plugin is declared in your `pom.xml` under the `<build><plugins>` section.
- Usage: Run `./mvnw spring-boot:run` in your project directory to start the application quickly.
- ✅ ./mvnw clean spring-boot:run → Runs your app from source, no .jar built
- ✅ ./mvnw clean package → Builds the .jar, but doesn’t run it
- ✅ java -jar target/your-app.jar → Runs the packaged app
- Can I just tweak the application.yml without bouncing the whole app ? No, you have to restart your Spring Boot app to apply changes in `application.yml` or `application.properties`. The `application.yml` is loaded at startup time, not dynamically. Spring Boot reads it once and wires up beans based on it — and it doesn’t keep watching it for changes.  

### Kubernetes Setup
* **Deployment selector:**
  `spec.selector.matchLabels` **must match** `spec.template.metadata.labels` to manage the correct pods.

* **Service selector:**
  `spec.selector` **must match** the pod labels (`spec.template.metadata.labels`) to route traffic to the right pods.
