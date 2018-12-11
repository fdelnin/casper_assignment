#include <stdio.h>
#include <unistd.h>

void greetUser() {
    char buf[666];
    printf("Enter your name: ");
    fflush(0);
    gets(buf);
    char *ptr;
    for (ptr = buf; *ptr != '\0'; ptr++) {
        if (*ptr == '\x90') {
            printf("Hack detected!\n");
            exit(1);
        }
    }
    printf("Hello %s!\n", buf);
}

int main() {
    greetUser();
    return 0;
}
