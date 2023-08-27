package com.harishlangs.hclan;

public class Meta {
    public static final int MAJOR = 1;
    public static final int MINOR = 1;
    public static final int PATCH = 0;

    public static final String version = String.format("v%d.%d.%d", MAJOR, MINOR, PATCH);
    public static final String copyrights = "Copyright (c) 2023, Harish Kumar";

    public static final String verStr = String.format("This is HCLAN programming language %s\n%s", version, copyrights);
    public static final String replStr = String.format("The HCLAN programming language REPL %s\n%s\n", version, copyrights);

}
