#include <stdio.h>
#include <unistd.h>

void greetUser() {
    char buf[666];
    printf("Enter your name: ");
    fflush(0);
    gets(buf);
    printf("Hello %s!\n", buf);
}

int main(int argc, char **argv) {
    //setresuid(geteuid(), geteuid(), geteuid());
    greetUser();

    return 0;
}
