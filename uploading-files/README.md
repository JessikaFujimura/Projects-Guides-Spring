# UPLOADIN FILES

## About
This is a simple application that storage a set files in local directory.

## How to use

To use this application clone this repository.

Change the directory in class *src/main/java/com/example/uploading_files/storage/StorageProperties.java*.
The variable **location**.

Run the command `mvn spring-boot:run`

Access in your browser the url: http://localhost:8080/

Now you can upload the files, just click on Browser button and after click on Submit Query.

![Screen.png](Screen.png)

## Lessons Learned
* [Model](https://www.baeldung.com/spring-mvc-model-model-map-model-view)
* [Resource](https://docs.spring.io/spring-framework/reference/core/resources.html)
* [Path](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html)
* [Files](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#copy-java.io.InputStream-java.nio.file.Path-java.nio.file.CopyOption...-)
* [FileSystemUtils](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/FileSystemUtils.html)
