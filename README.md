# jtlv

Simple library that parses BER-TLV (as String or byte[] array) into a TLV structure for easy handling

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

### Usage
> reading the test cases are enough to learn how to handle TLVs with this library, the usage is pretty straigthforward

Use the **parse** static method to break (decode) the TLV unstructured data into a List of immutable TLV objects. 
* Take a look at the TLVparseTest.java class in the test directory 

Use the **TLV** constructor passing the tag name and data value as hexadecimals Strings to generate a TLV object.
* Take a look at the TLVConstructorTest.java class in the test directory 

Use the **format** static method to encode a List<TLV> into byte[] array, ready to be sent over the network.
* Take a look at the TLVformatTest.java class in the test directory 


## Documentation

* Generate the maven site with javadoc under ./target/site directory
```
mvn javadoc:javadoc
mvn site
```
* If Maven javadoc generation is broken, run the javadoc command and go to ./target/javadocsrc:
```
[windows]
javadoc -d .\target\javadocsrc .\src\main\java\io\github\marciocg\jtlv\TLV.java
[linux]
javadoc -d ./target/javadocsrc ./src/main/java/io/github/marciocg/jtlv/TLV.java
```

## License

This project is licensed under the Apache License 2.0 - see the LICENSE.md file for details

## Acknowledgments

Inspiration and references:
* [BER Definition from Oracle](https://docs.oracle.com/cd/E19476-01/821-0510/def-basic-encoding-rules.html)
* [EMV v4.3 Book 3](https://www.emvco.com/wp-content/uploads/2017/04/EMV_v4.3_Book_3_Application_Specification_20120607062110791.pdf)
