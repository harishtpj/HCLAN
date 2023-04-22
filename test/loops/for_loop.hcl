# This program test the code in `for_as_loop.hcl`

for (let i := 0; i < 100; i := i + 1) {
    println i;
}