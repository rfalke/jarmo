all:
	python3 jarmo-core/src/main/java/holdec/jarmo/arm64/generate-java.py
	mvn package

clean:
	rm -f jarmo-core/src/main/java/holdec/jarmo/arm64/ArmDisasmDecoder.java
	mvn clean

benchmark: all
	./run-benchmarks.sh
