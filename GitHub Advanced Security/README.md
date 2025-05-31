# GitHub Advanced Security Configuration Guide

This document provides comprehensive guidance on configuring GitHub Advanced Security (GHAS) features including CodeQL, Dependabot, and Secret Scanning for your repositories.

## Table of Contents
- [Prerequisites](#prerequisites)
- [CodeQL Configuration](#codeql-configuration)
- [Dependabot Configuration](#dependabot-configuration)
- [Secret Scanning Configuration](#secret-scanning-configuration)
- [Best Practices](#best-practices)

## Prerequisites

Before configuring GitHub Advanced Security features:

1. Ensure you have a GitHub Enterprise plan or a public repository (GHAS is free for public repositories)
2. Verify you have admin permissions for the repository or organization
3. Enable GitHub Advanced Security for your organization or repository:
   - Navigate to repository settings
   - Select "Advanced Security" tab in the left panel
   - Click "Enable" for GitHub Advanced Security

## CodeQL Configuration

CodeQL is GitHub's semantic code analysis engine that automatically discovers vulnerabilities in your code.

### Basic Setup

1. Enable CodeQL:
   - Go to repository settings
   - Navigate to "Advanced Security" > Code Scanning section
   - Click "Set Up > Default" for CodeQL Analysis
   - You can also configure other tools for SAST scanning


## Dependabot Configuration

Dependabot helps you keep dependencies updated and secure by automatically creating pull requests when vulnerabilities are detected.

### Basic Setup

1. Enable Dependabot:
   - Go to repository settings
   - Navigate to "Advanced Security" > Dependabot section
   - Click "Enable" for Dependabot Alerts, Dependabot Security Updates,  Dependabot Version Updates


## Secret Scanning Configuration

Secret Scanning automatically detects secrets (like API keys and tokens) committed to your repository.

### Basic Setup

1. Enable Secret Scanning:
   - Go to repository settings
   - Navigate to "Advanced Security"
   - Click "Enable" for Secret Protection 

2. Configure push protection (optional but recommended):
   - In the same "Advanced Security" section
   - Enable "Push protection" to prevent secrets from being pushed

### Advanced Configuration

1. **Custom Patterns**: Create custom secret patterns for your organization:
   - Go to organization settings
   - Navigate to "Advanced Security" > "Secret scanning"
   - Click "New pattern" and define your regex pattern

2. **Secret Scanning Alerts Configuration**:
   - Create a `.github/secret_scanning.yml` file:

```yaml
paths-ignore:
  - "**/*.test.js"
  - "**/test/fixtures/**"
```

3. **Handling Alerts**:
   - Set up notification preferences in repository settings
   - Configure webhooks to integrate with your security tools
   - Create custom workflows to handle detected secrets

## Best Practices

1. **Integrate with CI/CD**:
   - Run CodeQL analysis on every pull request
   - Block merges if security issues are detected

2. **Establish Alert Handling Process**:
   - Define severity levels for different types of alerts
   - Assign team members responsible for addressing alerts
   - Set SLAs for remediation based on severity

3. **Regular Maintenance**:
   - Review and update CodeQL queries regularly
   - Adjust Dependabot settings based on team capacity
   - Audit custom secret scanning patterns periodically

4. **Developer Training**:
   - Train developers to understand security alerts
   - Document common remediation strategies
   - Share lessons learned from security findings

5. **Reporting**:
   - Generate regular reports on security posture
   - Track metrics like time-to-fix and alert volume
   - Use GitHub Security Overview for organization-wide insights

---

By implementing these GitHub Advanced Security features, you can significantly improve your repository's security posture and catch vulnerabilities before they reach production.
