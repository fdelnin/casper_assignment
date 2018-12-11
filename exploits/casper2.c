#include <stdio.h>
#include <unistd.h>

int main(int argc, char **argv) {
    setresuid(geteuid(), geteuid(), geteuid());
    execl("/bin/xh", "/bin/xh", NULL);
    return 0;
}
