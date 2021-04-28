# maven-service
program utility service

# Usage
Add the following to pom.xml:
<project ...>
...

<repositories>
  <repository>
    <id>github-maven-service</id>
    <name>The Maven Repository on Github</name>
    <url>https://dolores-wzq.github.io/maven-service/java-maven/maven-repo/</url>
  </repository>
</repositories>
 
<dependency>
  <groupId>org.example</groupId>
  <artifactId>java-maven</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

...
</project>

# Sample code
Millionaire millionaire = new Millionaire();
System.out.println(millionaire.howToBecomeRich());

Calculator c = new Calculator();
