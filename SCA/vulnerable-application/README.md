# Using OWASP Dependency Check for Java Gradle Projects

This guide demonstrates how to use OWASP Dependency Check to scan Java Gradle projects for vulnerabilities in dependencies and how to interpret the resulting reports.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Setting Up Dependency Check with Gradle](#setting-up-dependency-check-with-gradle)
- [Running a Scan](#running-a-scan)
- [Understanding the Vulnerability Report](#understanding-the-vulnerability-report)
- [Integrating with CI/CD](#integrating-with-cicd)
- [Best Practices](#best-practices)

## Prerequisites

- Java JDK 8 or higher
- Gradle 6.0 or higher
- Internet connection (for NVD database updates)
- Your Java Gradle project with a built JAR file

## Method 1: OWASP Dependency-Check CLI Tool
1. Navigate to the official [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/) page and download the [CLI Tool](https://github.com/dependency-check/DependencyCheck/releases/download/v12.1.0/dependency-check-12.1.0-release.zip)

Unzip the downloaded file and store in location of your choice

```
unzip -d /opt/dependency-check/ dependency-check-12.1.0-release.zip
```
[Dependency Check GitHub Repo](https://github.com/dependency-check/DependencyCheck?tab=readme-ov-file) <br>
Request an NVD API Key: [Click here](https://nvd.nist.gov/developers/request-an-api-key) <br>
Enter your Email Address and Organization Info (NA if not applicable) <br>
In response you'll be getting an email with the API key which is used to connect with NVD Database and fetch the updates. <br>

2. Add the path to the dependency-check.sh file to `.~/bashrc` as alias

```
alias dependency-check='sh /opt/dependency-check/bin/dependency-check.sh'
```

3. Navigate to the folder where the jar/executable file is available in the project folder 

```
cd ./SCA/vulnerable-application/build/libs
ls

vulnerable-application-0.0.1-SNAPSHOT-plain.jar  vulnerable-application-0.0.1-SNAPSHOT.jar
```
4. Execute the following command to scan the JAR file for SCA Findings

```
dependency-check --scan vulnerable-application-0.0.1-SNAPSHOT.jar --format HTML --nvdApiKey <your-api-key>
```
This would create the scan results in an HTML format and you can review the findings by opening it in a web browser.

## Method 2: Setting Up Dependency Check with Gradle

1. Add the Dependency Check plugin to your `build.gradle` file:

```gradle
plugins {
    id 'java'
    id 'org.owasp.dependencycheck' version '8.4.0'  // Use the latest version
}

apply plugin: 'org.owasp.dependencycheck'

dependencyCheck {
    // Configuration options
    failBuildOnCVSS = 7  // Fail the build for CVSS scores >= 7
    formats = ['HTML', 'JSON', 'CSV', 'XML']  // Report formats
    outputDirectory = "${buildDir}/reports/dependency-check"
    scanConfigurations = ['runtimeClasspath', 'compileClasspath']
    
    // Analyzer configuration
    analyzers {
        assemblyEnabled = false
        nodeEnabled = false  // Disable Node.js analyzer if not needed
    }
}
```

2. Configure additional settings if needed:

```gradle
dependencyCheck {
    // Additional configuration options
    suppressionFile = file("$projectDir/dependency-check-suppressions.xml")
    nvd {
        apiKey = System.getenv('NVD_API_KEY')  // Optional: Use NVD API key for better rate limits
    }
}
```

## Running a Scan

### Scanning the Project Dependencies

Run the Dependency Check analysis on your project:

```bash
./gradlew dependencyCheckAnalyze
```

## Understanding the Vulnerability Report

After running the scan, reports will be generated in the configured output directory (default: `build/reports/dependency-check`).

### HTML Report

The HTML report is the most user-friendly format and includes:

1. **Summary Section**:
   - Overall project risk score
   - Number of dependencies scanned
   - Number of vulnerabilities found by severity

2. **Dependency Details**:
   - List of all dependencies with vulnerabilities
   - Each dependency shows:
     - Filename and path
     - Evidence collected (what identified this as a vulnerable component)
     - CPE (Common Platform Enumeration) identifiers
     - CWE (Common Weakness Enumeration) identifiers

3. **Vulnerability Details**:
   - CVE ID (Common Vulnerabilities and Exposures)
   - CVSS score and severity (Low, Medium, High, Critical)
   - Description of the vulnerability
   - References to more information
   - Recommended fixes or mitigations

### Interpreting CVSS Scores

CVSS (Common Vulnerability Scoring System) scores range from 0 to 10:
- **0.0-3.9**: Low severity
- **4.0-6.9**: Medium severity
- **7.0-8.9**: High severity
- **9.0-10.0**: Critical severity

### False Positives

Dependency Check may occasionally report false positives. You can suppress these using a suppression XML file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<suppressions xmlns="https://jeremylong.github.io/DependencyCheck/dependency-suppression.1.3.xsd">
   <suppress>
      <notes>Reason for suppression</notes>
      <gav regex="true">com\.example:library:.*</gav>
      <cve>CVE-2022-12345</cve>
   </suppress>
</suppressions>
```

## Integrating with CI/CD

### Jenkins Integration

Add this to your Jenkinsfile:

```groovy
stage('Security Scan') {
    steps {
        sh './gradlew dependencyCheckAnalyze'
    }
    post {
        always {
            dependencyCheckPublisher pattern: 'build/reports/dependency-check/dependency-check-report.xml'
        }
    }
}
```

### GitHub Actions Integration

Create a file `.github/workflows/dependency-check.yml`:

```yaml
name: Dependency Check

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  scan:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Run Dependency Check
        run: ./gradlew dependencyCheckAnalyze
      - name: Upload Report
        uses: actions/upload-artifact@v3
        with:
          name: dependency-check-report
          path: build/reports/dependency-check/
```

## Best Practices

1. **Regular Updates**: Run Dependency Check regularly to catch new vulnerabilities.

2. **Update Dependencies**: When vulnerabilities are found, update to non-vulnerable versions.

3. **Fail Builds on High Severity**: Configure the build to fail on high-severity issues.

4. **Suppression Management**: Document all suppressions and review them regularly.

5. **Automate Remediation**: Consider tools like Dependabot or Renovate to automate dependency updates.

6. **Establish Policies**: Define clear policies for handling different severity levels.

7. **Monitor NVD Database Updates**: Ensure your Dependency Check database is updated regularly.

## Troubleshooting

### Common Issues

1. **Slow Initial Run**: The first run downloads the NVD database and can take time.
   - Solution: Be patient on first run; subsequent runs will be faster.

2. **Memory Issues**: For large projects, you might encounter out-of-memory errors.
   - Solution: Increase Java heap size with `-Xmx` flag:
     ```bash
     ./gradlew -Dorg.gradle.jvmargs=-Xmx4g dependencyCheckAnalyze
     ```

3. **Network Issues**: Database updates require internet access.
   - Solution: Configure proxy settings if needed:
     ```gradle
     dependencyCheck {
         proxy {
             server = "proxy.example.org"
             port = 8080
             username = "proxyUser"  // if required
             password = "proxyPass"  // if required
         }
     }
     ```

## Conclusion

OWASP Dependency Check is a powerful tool for identifying vulnerable components in your Java Gradle projects. By regularly scanning your dependencies and addressing identified vulnerabilities, you can significantly improve your application's security posture.

Remember that no automated tool can catch all security issues. Dependency Check should be part of a comprehensive security program that includes other testing methods and security best practices.
