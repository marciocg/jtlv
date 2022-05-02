# jtlv

Simple library that parses BER-TLV (as String or byte array) into a TLV structure for easy handling

## Getting Started

### Dependencies

* Requires Java 17 LTS.

### Installing

* Clone the repo
```
git clone https://github.com/marciocg/jtlv.git
```
* Generate the jar package with maven
```
mvn package
```
* Import the jar to your project

## Version History

* 0.1.1
    * Included the format static method
* 0.1.0
    * Initial Release with parse

## Documentation

* Generate the maven site with javadoc under ./target/site directory
```
mvn site
```
* Maven javadoc generation is broken, run the javadoc command and go to ./target/javadocsrc:
```
[windows]
javadoc -d .\target\javadocsrc .\src\main\java\io\github\marciocg\jtlv\TLV.java
[linux]
javadoc -d ./target/javadocsrc ./src/main/java/io/github/marciocg/jtlv/TLV.java
```

## License

This project is licensed under the Apache License 2.0 - see the LICENSE.md file for details

## Acknowledgments

Inspiration, code snippets, etc.
* [BER Definition from Oracle](https://docs.oracle.com/cd/E19476-01/821-0510/def-basic-encoding-rules.html)
* [EMV v4.3 Book 3](https://www.emvco.com/wp-content/uploads/2017/04/EMV_v4.3_Book_3_Application_Specification_20120607062110791.pdf)
