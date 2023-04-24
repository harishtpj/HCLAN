# HCL Language preprocessor
import re
from java.lang import System

def error(line, msg):
    print "[Line %d]: Preprocessor error: %s" % (line, msg)
    System.exit(70)

def preprocess(path):
    srcFile = open(path, 'r')
    lines = srcFile.readlines()
    srcFile.close()
    lines = [line.rstrip() for line in lines]

    proc_lines = []
    def_stack = []
    imp_stack = []
    imported_files = []
    

    for i in range(len(lines)):
        line = lines[i]
        
        if not(len(line) <= 0) and line[0] == "!":
            if re.search(r"^\s*!\s*define\s+(\w+)\s+(.*)$", line):
                match = re.search(r"^\s*!\s*define\s+(\w+)\s+(.*)$", line)
                def_stack.append(match.groups())
                proc_lines.append("")

            elif re.search(r"^\s*!\s*import\s+([\.\w\\/]+)$", line):
                match = re.search(r"^\s*!\s*import\s+([\.\w\\/]+)$", line)
                imp_file = match.group(1)
                if imp_file in imported_files:
                    error(i+1, "Reimporting %s again" % imp_file)
            
                prgm = preprocess(imp_file + ".hcl")[1]

                imp_stack.append(prgm)
                imported_files.append(imp_file)
                proc_lines.append("")
        else:
            proc_lines.append(line)
        
    src = "\n".join(proc_lines)
    
    for const, val in def_stack:
        pattern = r"(?<=\W)" + const + r"(?=\W)"
        src = re.sub(pattern, val, src)

    return imp_stack, src

