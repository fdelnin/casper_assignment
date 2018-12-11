#include <stdio.h>
#include <unistd.h>

#define BUFSIZE 666

void greetUser() {
    char buf[BUFSIZE];
    printf("Enter your name: ");
    fflush(0);
    gets(buf);

    int i;
    for (i = 0; i < BUFSIZE; i++) {
        if (buf[i] == '\0') {
            break;
        }
        if (!isascii(buf[i])) {
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
