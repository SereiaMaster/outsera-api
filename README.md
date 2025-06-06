# Getting Started

## Installation

To install Java 21, follow the instructions for your operating system:

### Windows
[Installation Guide for Windows](https://docs.oracle.com/en/java/javase/21/install/installation-jdk-microsoft-windows-platforms.html#GUID-61460339-5500-40CC-9006-D4FC3FBCFC0D)

### Linux
[Installation Guide for Linux](https://docs.oracle.com/en/java/javase/22/install/installation-jdk-linux-platforms.html)

### Mac
[Installation Guide for Mac](https://docs.oracle.com/en/java/javase/22/install/installation-jdk-macos.html)

After installing Java 21, run the following commands in your terminal:

```bash
./mvnw clean
./mvnw package
./mvnw install
```

## Usage
To start the application, run the following class:

src/main/java/com/outsera/movie/MovieApplication.java

## API
Get Producers with Min and Max consecutive Awards
```bash
GET http://localhost:8080/winners/min-max-interval
```