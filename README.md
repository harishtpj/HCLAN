# HCL
[![made-with-java](https://img.shields.io/badge/Made%20with-Java-orange?&logo=java&logoColor=white)](https://www.oracle.com/in/java/)
[![java-version](https://img.shields.io/badge/Java-v18.0.1.1-orange)](https://www.oracle.com/in/java/)
![hcl-version](https://img.shields.io/badge/HCL-v1.0.0-blue)
<a href="https://codeclimate.com/github/harishtpj/HCL/maintainability"><img src="https://api.codeclimate.com/v1/badges/3a263ecd67ac61522f2f/maintainability" /></a>

A simple, yet advanced **H**igh level **C**omputing **L**anguage

# About
HCL is an **Interpreted, Object Oriented** language written in Java. It is also **dynamically typed** and has a 
very **modern syntax** inspired from Java and Python. This interpreter is inspired from [jLox of Crafting 
Interpreters](https://craftinginterpreters.com/).

# Requirements
- Java JDK (latest version is more preferable)

# Installation
To Install the interpreter, head over to the [releases](https://github.com/harishtpj/HCL/releases) page and 
download the latest release of HCL interpreter. The zip/tarball file will be named as `hcl-<latest_version>`.
Unzip the release and store it in your desired directory.

Then Add the `/bin` directory to your PATH environment variable.


#### For windows users

1. Copy the `\bin` location of HCL installed folder to clipboard
2. The first step depends which version of Windows you're using:
  * If you're using Windows 8 or 10, press the Windows key, then search for and
    select "System (Control Panel)".
  * If you're using Windows 7, right click the "Computer" icon on the desktop
    and click "Properties".
3. Click "Advanced system settings".
4. Click "Environment Variables".
5. Under "System Variables", find the `PATH` variable, select it, and click
   "Edit". If there is no `PATH` variable, click "New".
6. Add the copied directory location to the beginning of the variable value followed by `;` (a
   semicolon). For example, if the value was `C:\Windows\System32`, change it to
   `C:\Users\Me\HCL\bin;C:\Windows\System32`.
7. Click "OK".
8. Restart your terminal.
9. Verify that the path is set by opening a new command window and run `hcl -v`.

#### For Mac users:

1. Copy the `\bin` location of HCL installed folder to clipboard
2. Open the `.bash_profile` file in your home directory (for example,
   `/Users/your-user-name/.bash_profile`) in a text editor.
3. Add `export PATH="<copied-hcl-bin-path>:$PATH"` to the last line of the file, where
   *<copied-hcl-bin-path>* is the copied directory location of HCL.
4. Save the `.bash_profile` file.
5. Restart your terminal.
6. Verify that the path is set by opening a new terminal and run `hcl -v`.

#### For Linux users:

1. Copy the `\bin` location of HCL installed folder to clipboard
1. Open the `.bashrc` file in your home directory (for example,
   `/home/your-user-name/.bashrc`) in a text editor.
2. Add `export PATH="<copied-hcl-bin-path>:$PATH"` to the last line of the file, where
   *<copied-hcl-bin-path>* is the copied directory location of HCL.
3. Save the `.bashrc` file.
4. Restart your terminal.
5. Verify that the path is set by opening a new terminal and run `hcl -v`.

# Usage
To run your first program, fire up you favourite text editor and copy the following lines to `hello.hcl` file:
```hcl
# A Simple Hello, World! program

println "Hello, world!";
println "HCL is interesting!";
```

Then open a new terminal in the same directory as the file and run:
```bash
$ hcl hello.hcl
```

For windows users: just run the same command.

You'll see the following output on your terminal:

```
Hello, world!
HCL is interesting!
```

# Support
If you've any questions regarding this project, please check our [documentation](https://harish-kumar.gitbook.io/hcl-docs/) or file an [issue](https://github.com/harishtpj/HCL/issues/new).

# Contributing
Contributions are welcome, To contribute please refer to details [here](https://github.com/harishtpj/HCL/blob/master/CONTRIBUTING.md).

<!--# Authors and acknowledgment
-- TODO -->

# License
#### Copyright © 2023 [M.V.Harish Kumar](https://github.com/harishtpj). <br>
#### This project is [BSD-2](https://github.com/harishtpj/HCL/blob/master/LICENSE) licensed.

# Project status
This project is still **In its baby steps**. Many features need to be added. Thus the stability of the syntax is not guaranteed. However, most features is planned to be retained. Contributers can give their contribution to this project to make this project stable.
