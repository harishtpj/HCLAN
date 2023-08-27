# Makefile for HCL Language
PACKAGE = com.harishlangs.
SRC = com\harishlangs\hcl
NAME = Hcl
JAR = HCL.jar

all: compile run

run: compile
	@echo "--> Running HCL"
	@java $(PACKAGE)hcl.$(NAME) .\\ $(ARGS)

compile:
	@echo "--> Building HCL Lang"
	@javac $(SRC)\\Hcl.java

genast:
	@echo "--> Generating AST Tree"
	@javac com\harishlangs\tools\\*.java
	@java $(PACKAGE)tools.GenAst $(SRC)

clean:
	@echo "--> Cleaning Directory"
	@del /s /q *.class *.msi
	@echo "--> Cleaned Directory"

export: clean compile
	@echo "--> Exporting Jar"
	@if not exist export mkdir export
	@jar cfm $(JAR) $(SRC)\\manifest.txt $(SRC)\\*.class $(SRC)\\std\\*.class
	@move $(JAR) export

installer: export
	@echo "--> Creating MSI installer"
	@jpackage --input export --name HCL --main-jar HCL.jar --main-class com.harishlangs.hcl.Hcl \
				--type msi --win-dir-chooser --win-per-user-install --win-console --license-file LICENSE \
				--vendor "Harish Kumar" --description "The HCL Language" \
				--copyright "Copyright (c) 2023, Harish Kumar"