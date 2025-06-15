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
- `helm create springboot-app` 
  - This makes a directory springboot-app/ with a bunch of files:
    - `Chart.yaml` — metadata about your chart
    - `values.yaml` — config values you’ll customize
    - `templates/` — Kubernetes YAML templates with placeholders
- To undo the `helm create` command itself → delete the generated folder
- To undo an actual deployed app → `helm uninstall <release-name>`
- Got it! I’ll add some basic commands related to the points in the summary. Here’s the updated plain raw text content including those commands:

---

springboot\_k8s\_deploy\_summary

1. When does helm upgrade create new deployments/pods

* Helm upgrade creates new deployments/pods when there are changes to the deployment manifests/templates, such as changes in container image tags, environment variables, resource limits, or other pod spec details.
* If no changes affect the pod spec, helm upgrade may not trigger a new pod rollout.
* A common pattern is to update the image tag or trigger a rollout restart to force new pods.

Basic commands:

* To upgrade helm release:
  helm upgrade <release-name> <chart-path> --values <values-file>

* To restart deployment (force rollout):
  kubectl rollout restart deployment/<deployment-name>

* To check rollout status:
  kubectl rollout status deployment/<deployment-name>

2. Simple steps to take a locally built app jar into a Kubernetes cluster

* Build the app jar locally (e.g., using Maven or Gradle).
  ./mvnw clean package

* Build a Docker image containing the app jar.
  docker build -t springboot-app\:local .

* Load the Docker image into the Kubernetes cluster’s container runtime. For kind clusters:
  kind load docker-image springboot-app\:local

* Deploy or upgrade your Kubernetes deployment manifest or Helm chart referencing the new image.

* Optionally, restart the deployment or wait for the rollout to complete (commands above).

* Verify pods are running with the new image and the app is working:
  kubectl get pods
  kubectl logs <pod-name>

---

If you want, I can package this into a file for you to download as well. Just say the word!
