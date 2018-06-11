all:
	python3 src/main/java/holdec/jarmo/arm64/generate-java.py
	mvn package

clean:
	rm -f src/main/java/holdec/jarmo/arm64/ArmDisasmDecoder.java
	rm -rf target
