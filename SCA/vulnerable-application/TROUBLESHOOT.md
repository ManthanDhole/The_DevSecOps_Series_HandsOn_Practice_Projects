### Gradle Version Required above 7.0.0

Open the official [installation guide for gradle](https://gradle.org/install/)

Download the [Binary-only](https://gradle.org/next-steps/?version=8.14.1&format=bin) zip file.

### Linux & Mac Users
1. Unzip the distribution zip file in the directory of your choosing.
````
mkdir /opt/gradle
unzip -d /opt/gradle gradle-8.14.1-bin.zip
ls /opt/gradle/gradle-8.14.1
````

2. Uninstall if gradle is fetched using apt package manager to avoid conflict.
```
sudo apt remove gradle
```

3. Open the `~/.bashrc` file in a text editor and add an alias for `gradle`

Add the alias like follows, save the `~/.bashrc` file
```
alias gradle="sh /opt/gradle/gradle-8.14.1/bin/gradle"
```

4. Check the gradle version using following command now:

```
gradle --version
```
It should show the following information
```
------------------------------------------------------------
Gradle 8.14.1
------------------------------------------------------------

Build time:    2025-05-22 13:44:09 UTC
Revision:      c174b82566a79e3575bac8c7648c7b36cd815e94

Kotlin:        2.0.21
Groovy:        3.0.24
Ant:           Apache Ant(TM) version 1.10.15 compiled on August 25 2024
Launcher JVM:  17.0.15 (Ubuntu 17.0.15+6-Ubuntu-0ubuntu124.04)
Daemon JVM:    /usr/lib/jvm/java-17-openjdk-amd64 (no JDK specified, using current Java home)
OS:            Linux 5.15.167.4-microsoft-standard-WSL2 amd64
```