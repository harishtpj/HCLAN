package com.harishlangs.hcl;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;

public class ArgParser {
    ArgumentParser parser = ArgumentParsers.newFor("hcl").build()
                            .description("The HCL programming language interperter")
                            .version(Meta.verStr);
    
    ArgParser() {
        parser.addArgument("File")
              .dest("file")
              .nargs("?")
              .type(String.class)
              .help("The File to compile");
        
        parser.addArgument("-v", "--version")
              .action(Arguments.version());
    }

    public ArgumentParser getParser() {
        return parser;
    }
}
