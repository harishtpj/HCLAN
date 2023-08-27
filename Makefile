# Makefile for HCLAN Language
PACKAGE = com.harishlangs.
SRC = com\harishlangs\hclan
NAME = Hclan
JAR = HCLAN.jar

all: compile run

run: compile
	@echo "--> Running HCLAN"
	@java $(PACKAGE)hclan.$(NAME) .\\ $(ARGS)

compile:
	@echo "--> Building HCLAN Lang"
	@javac $(SRC)\\Hclan.java

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
	@jpackage --input export --name HCLAN --main-jar HCLAN.jar --main-class $(PACKAGE)hclan.$(NAME) \
				--type msi --win-dir-chooser --win-per-user-install --win-console --license-file LICENSE \
				--vendor "Harish Kumar" --description "The HCLAN Language" \
				--copyright "Copyright (c) 2023, Harish Kumar"