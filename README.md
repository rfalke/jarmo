# jarmo

jarmo is a Java based disassembler for the ARM instruction sets. 

jarmo is a basic building block for a full disassembler. A full disassembler 
will in addition also know how to detect executable 
file formats, loading them, relocating the image and also how to detect what 
is code and what is data. There is no plan to make jarmo a full disassembler.

jarmo currently only supports the aarch64 aka arm64 aka armv8 as encoded in A64. jarmo supports roughly ARMv8.3. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need:
- python 3
- JDK 8
- mvn
- make

### Installing

You should be able to install the prerequisites through your normal package manager.

Once you cloned the repo you can just call `make`:

```
$ make
python3 src/main/java/holdec/jarmo/arm64/generate-java.py
mvn package
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building An ARM disassembler 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ jarmo ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:compile (default-compile) @ jarmo ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 5 source files to target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ jarmo ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:testCompile (default-testCompile) @ jarmo ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ jarmo ---
[INFO] Surefire report directory: target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running holdec.jarmo.arm64.A64DisasmTest
Tests run: 599, Failures: 0, Errors: 0, Skipped: 12, Time elapsed: 1.449 sec
Running holdec.jarmo.arm64.A64RandomDataTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 2, Time elapsed: 0 sec

Results :

Tests run: 601, Failures: 0, Errors: 0, Skipped: 14

[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ jarmo ---
[INFO] Building jar: target/jarmo-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 12.113 s
[INFO] Finished at: 2018-06-07T20:25:39+02:00
[INFO] Final Memory: 19M/397M
[INFO] ------------------------------------------------------------------------
$
```

You can then use the sample driver included:

```
$ java -jar target/jarmo-1.0-SNAPSHOT.jar 
java -jar jarmo.jar [options]
  -f <filename>                 disassemble a raw binary file
  -s <bytes in hex>             disassemble bytes

Examples
  java -jar jarmo.jar -s d5 03 20 1f 4dda105b 6a 69 4c 30
  echo -n jiL0 >testfile
  java -jar jarmo.jar -f testfile
$ java -jar target/jarmo-1.0-SNAPSHOT.jar -s d5 03 20 1f 4dda105b 6a 69 4c 30
   0 NOP
   4 LD1 {v27.B}[12],[X2],X26
   8 BICS W16,W1,W9, lsr #19
$
```

## Running the tests

A comprehensive test (`A64DisasmTest`) is run during normal building. 

There are however also tests which use random data. To run `holdec.jarmo.arm64.A64RandomDataTest.compareWithObjdump` you need an objdump which understands aarch64 and which also doesn't emit SVE (Scalable Vector Extension) instructions since they are not yet supported by jarmo. *TODO: add more information about setup and running them.*

## Future plans

- Support A32 and T32
- Make it a bit faster
- Compare with capstone

## Compatibility

There is documentation of the Instruction Set Architecture (ISA) provided by ARM. And there are disassembler implementations with objdump and capstone being the most mature among them.

Comparing the output of jarmo and objdump (2.30) using random input revealed the following differences.
- jarmo ignores the actual values of should be one (SBO) bits. objdump ignores them mostly but not always. See [bug](https://sourceware.org/bugzilla/show_bug.cgi?id=23242).
- objdump is too relaxed when replacing ORR with the MOV alias [Bug](https://sourceware.org/bugzilla/show_bug.cgi?id=23193)
- objdump allows fmov of different sizes. [Bug](https://sourceware.org/bugzilla/show_bug.cgi?id=20319)
- a few other bugs: [23109](https://sourceware.org/bugzilla/show_bug.cgi?id=23109), [23192](https://sourceware.org/bugzilla/show_bug.cgi?id=23192), [23204](https://sourceware.org/bugzilla/show_bug.cgi?id=23204),[23212](https://sourceware.org/bugzilla/show_bug.cgi?id=23212), [23230](https://sourceware.org/bugzilla/show_bug.cgi?id=23230) 

*TODO: Compare with capstone*

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

1. Fork the repo
1. Edit files
1. Create a pull request

## Authors

* **Raimar Falke** - *Initial work* - [rfalke](https://github.com/rfalke)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Hat tip to people who build the gnu binary toolchain (gcc, binutils, gdb, ...) over the last decades
