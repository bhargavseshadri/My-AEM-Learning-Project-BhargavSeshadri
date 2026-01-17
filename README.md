# **Bhargav Notes**

## **For all the code details**
com/aem/geeks/core/Bhargav-notes.txt

### **To Setup the AEM Instance**

#### Manditory Packages

##### Step: 1 - Download AEM instance and these pkgs
* Java - https://drive.google.com/file/d/1iHlFc8p2RMhBcjT0Pc-5PHDyk65P1fAO/view?usp=drive_link
* Maven - https://drive.google.com/file/d/1tEkH3D-5x7qXLGkQli19wR0_H2xr9CFa/view?usp=drive_link
* NodeJs - https://drive.google.com/file/d/1nBOG65PiYltc7va_ESLZU5HgxU0NsCb_/view?usp=drive_link

###### Sample locations to place the files
* Node : C:\Program Files\nodejs
* Java : C:\Program Files\Java\jdk1.8.0_202\jre
* Maven : C:\Program Files\Maven\apache-maven-3.8.9


##### Step: 2 - after installing the above packages Setup Environment Variables

* Java, Maven
1. ![img.png](img.png)
2. Go to path and edit
   ![img_1.png](img_1.png)

3. And add that java and maven inside path like this
   ![img_2.png](img_2.png)

4. Add Nodejs also similarly
   ![img_3.png](img_3.png)

Note :  add for intellij also
1. ![img_4.png](img_4.png)
2. Add it in the path as well
   ![img_5.png](img_5.png)


## **GIT Related**
### To Push The Code in to Git
git status                            
git add .                              
git commit -m "random commit"  
git push origin main

##### To check the status:
git status

##### to add the untracked file:
git add core/src/main/java/com/aem/geeks/core/Bhargav-notes.txt

## AEM Important Commands

#### STARTING AEM SERVER IN DEBUG MODE
`java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8080 -jar aem-author-p4502.jar -gui
`

### Common Maven Commands for AEM
##### Build everything (all modules)
`mvn clean install`
- Run this from the root project folder (where the parent pom.xml is).
- Cleans (target/ folders) and rebuilds all modules (core, ui.apps, ui.content, all).
- Doesn’t deploy to AEM, just builds JARs/ZIPs in target/.

##### Build and Deploy to AEM
`mvn clean install -PautoInstallSinglePackage`
- Use this when you want to build the whole project and install the full package (all module) into your local AEM instance.
- Useful for fresh deployments.

##### Build and Deploy Only One Module
1. Core module (OSGi bundle):
`--> cd core
mvn clean install -PautoInstallBundle`
- Deploys only the core bundle (Java code) to AEM.
- Faster than full build when you just changed backend Java code.
- UI modules (frontend / components):
  cd ui.apps
  mvn clean install -PautoInstallPackage
  or
- cd ui.content
  mvn clean install -PautoInstallPackage

- Deploys only that module’s package (JS, HTL, dialogs, content, etc.).
- Faster if you only changed frontend code, dialogs, or content.

##### Skip Tests for Faster Builds
`mvn clean install -DskipTests`
-Useful if you know tests are passing and want quicker builds.

Combine with deploy:
`mvn clean install -PautoInstallSinglePackage -DskipTests`

##### Deploy Manually Built Package
If you don’t want Maven auto-deploy:
Build: mvn clean install
Find the all package in:
all/target/*.zip
Upload it via AEM Package Manager (http://localhost:4502/crx/packmgr).


# **Very Important Query Builder - Queries and Groovys**

#### 1. QUERY: if we want to know the references of a component or a template using sling:resourceType
`path=/content
property=sling:resourceType
property.value=intel/dm/components/content/contentwithvideo
p.limit=10`


#### 2. if we want to know the available pages in the with the particular jcr property in the page

path=/content
type=cq:Page
property=jcr:content/cq:lastReplicationAction
property.value=Deactivate
p.limit=-1


#### 3. To check the usages in content/data

path=/content/data
1_property=sling:resourceType
1_property.value=intel/commons/components/content/contactsupportgrid
1_property.operation=like
p.limit=-1


#### 5. Ashwin query to To get all pages that use a particular component under /us/en pages

path=/content/www/us/en
type=nt:unstructured
property=sling:resourceType
property.value=intel/commons/components/content/toogleBar
p.limit=-1


#### 6. Query To get the images where the property is not equal to the given value like dam:scene7FileStatus value is not PublishComplete

path=/content/dam
type=dam:Asset
property=jcr:content/metadata/dam:scene7FileStatus
property.operation=unequals
property.value=PublishComplete


#### 7. query used to fetch the images which have dam:scene7FileStatus and and gives its value and dam:scene7FileStatus

path=/content/dam
type=dam:Asset
1_property=jcr:content/metadata/dam:scene7FileStatus
1_property.operation=exists

p.limit=-1
p.hits=selective
p.properties=jcr:path jcr:content/metadata/dam:scene7FileStatus

=================================================================================================

=> Ashwin Query  DM:

path=/content/dam
type=dam:Asset
2_property=jcr:content/metadata/dam:scene7Type
2_property.value=Image

p.limit=-1
p.hits=selective
p.properties=jcr:path jcr:content/metadata/dam:scene7FileStatus jcr:content/metadata/dam:scene7ID jcr:content/metadata/dam:scene7LastModified jcr:content/metadata/dam:scene7File jcr:content/cq:lastReplicationAction jcr:content/metadata/dam:scene7UploadTimeStamp jcr:content/metadata/dam:scene7PublishTimeStamp jcr:content/metadata/dam:scene7Domain jcr:content/metadata/dam:scene7Folder jcr:content/metadata/dam:scene7Name

=================================================================================================

=> Query to fetch based on one json prop(here status), when that prop is not the value(here it gives all assets when status is not equals to Finished)

path=/content/dam
type=dam:Asset
1_property=jcr:content/status
1_property.value=Finished
1_property.operation=unequals

p.limit=-1
p.hits=selective
p.properties=jcr:path jcr:content/status jcr:content/metadata/dam:scene7PublishedBy jcr:content/metadata/dam:scene7FileStatus jcr:content/metadata/dam:scene7ID jcr:content/metadata/dam:scene7Domain

=================================================================================================

===> Fetching the assets, when one particular prop is absent in jcr props

path=/content/dam
type=dam:Asset
property=jcr:content/metadata/dam:scene7File
property.operation=not

p.limit=50
p.hits=selective
p.properties=jcr:path jcr:content/metadata/dam:scene7ID jcr:content/metadata/dam:MIMEtype jcr:content/metadata/dam:scene7FileStatus jcr:content/metadata/dam:scene7Type

give 3 lakhs results for currect dm issue scenario

==================================================================================================

===> Fetching the assets, when a particular prop is starting with some word

path=/content/dam
type=dam:Asset
1_property=jcr:content/metadata/dam:MIMEtype
1_property.value=image/%
1_property.operation=like

p.limit=50
p.hits=selective
p.properties=jcr:path jcr:content/metadata/dam:scene7ID jcr:content/metadata/dam:scene7File

===================================================================================================

===> Fetching the assets based on different values for a prop

path=/content/dam
type=dam:Asset
1_property=jcr:content/metadata/dam:MIMEtype
1_property.1_value=image/png
1_property.2_value=image/jpeg
1_property.3_value=image/jpg

p.limit=-1
p.hits=selective
p.properties=jcr:path jcr:content/metadata/dam:scene7ID jcr:content/metadata/dam:MIMEtype jcr:content/metadata/dam:scene7FileStatus jcr:content/metadata/dam:scene7File

==================================================================================================

===> Fetching the props based on two properties

path=/content/dam
type=dam:Asset
1_property=jcr:content/metadata/dam:MIMEtype
1_property.1_value=image/png
1_property.2_value=image/jpeg
1_property.3_value=image/jpg
1_group.p.or=true

2_property=jcr:content/metadata/dam:scene7File
2_property.operation=not

p.limit=-1
p.hits=selective
p.properties=jcr:path jcr:content/metadata/dam:MIMEtype jcr:content/metadata/dam:scene7ID jcr:content/metadata/scene7Type


===================================================================================================

GROOVY

Fetching images based on the presence of some props

import javax.jcr.Node
import javax.jcr.Session
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets


def csvFilePath = "/content/dam/groovy-csv/imgsdatainlocal.csv"

def csvAsset = resourceResolver.getResource(csvFilePath)?.adaptTo(com.day.cq.dam.api.Asset)
if (!csvAsset) {
println "CSV file not found at: $csvFilePath"
return
}

def csvInputStream = csvAsset.getOriginal().getStream()
def csvContent = IOUtils.toString(csvInputStream, StandardCharsets.UTF_8)

def lines = csvContent.readLines()
def header = lines[0]
def paths = lines.drop(1)

def session = resourceResolver.adaptTo(Session)
def validImagePaths = []

paths.each { path ->
try {
def metadataPath = "$path/jcr:content/metadata"
if (session.nodeExists(metadataPath)) {
Node metadataNode = session.getNode(metadataPath)
if (metadataNode.hasProperty("dam:scene7ID") && metadataNode.hasProperty("dam:scene7Domain")) {
validImagePaths << path
}
}
} catch (Exception e) {
println "Error processing path: $path -> ${e.message}"
}
}

println "=== Images with dam:scene7ID and dam:scene7Domain ==="
validImagePaths.each { println it + ","}


////THE ABOVE IS WORKING


==========================================================================================================================
__________________________________________________________________________________________________________________________
==========================================================================================================================
AEM SERVER START


java -jar AEM_6.5_Quickstart-author-p4502.jar

2==>(not using) java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8080 -jar AEM_6.5_Quickstart-author-p4502.jar

aem-author-p4502

(in use) java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8080 -jar aem-author-p4502.jar


**___________________BHARGAV Notes Ends_________________**

# **AEM GEEKS NOTES - start**

# Sample AEM project template

This is a project template for AEM-based applications. It is intended as a best-practice set of examples as well as a potential starting point to develop your own functionality.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode specific configs as well as Hobbes-tests
* ui.content: contains sample content using the components from the ui.apps
* ui.tests: Java bundle containing JUnit tests that are executed server-side. This bundle is not to be deployed onto production.
* ui.launcher: contains glue code that deploys the ui.tests bundle (and dependent bundles) to the server and triggers the remote JUnit execution
* ui.frontend: an optional dedicated front-end build mechanism (Angular, React or general Webpack project)

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance - To build and package the whole project and deploy into AEM with

    mvn clean install -PautoInstallPackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish

Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the purticular bundle to the author, run

    mvn clean install -PautoInstallBundle

## Testing

There are three levels of testing contained in the project:

* unit test in core: this show-cases classic unit testing of the code contained in the bundle. To test, execute:

    mvn clean test

* server-side integration tests: this allows to run unit-like tests in the AEM-environment, ie on the AEM server. To test, execute:

    mvn clean verify -PintegrationTests

* client-side Hobbes.js tests: JavaScript-based browser-side tests that verify browser-side behavior. To test:

    in the browser, open the page in 'Developer mode', open the left panel and switch to the 'Tests' tab and find the generated 'MyName Tests' and run them.

## ClientLibs

The frontend module is made available using an [AEM ClientLib](https://helpx.adobe.com/experience-manager/6-5/sites/developing/using/clientlibs.html). When executing the NPM build script, the app is built and the [`aem-clientlib-generator`](https://github.com/wcm-io-frontend/aem-clientlib-generator) package takes the resulting build output and transforms it into such a ClientLib.

A ClientLib will consist of the following files and directories:

- `css/`: CSS files which can be requested in the HTML
- `css.txt` (tells AEM the order and names of files in `css/` so they can be merged)
- `js/`: JavaScript files which can be requested in the HTML
- `js.txt` (tells AEM the order and names of files in `js/` so they can be merged
- `resources/`: Source maps, non-entrypoint code chunks (resulting from code splitting), static assets (e.g. icons), etc.

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
