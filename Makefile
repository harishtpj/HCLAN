# Makefile for HCL Language
PACKAGE = com.harishlangs.
SRC = com\harishlangs\hcl
NAME = Hcl

all: compile run

run: compile
	@echo "--> Running HCL"
	@java $(PACKAGE)hcl.$(NAME) $(ARGS)

compile:
	@echo "--> Building HCL Lang"
	@javac $(SRC)\\*.java

genast:
	@echo "--> Generating AST Tree"
	@javac com\harishlangs\tool\\*.java
	@java $(PACKAGE)tool.GenerateAst $(SRC)