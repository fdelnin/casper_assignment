#include <stdio.h>
#include <unistd.h>

void greetUser(char *s) {
    printf("Hello %s!\n", s);
}

struct data_t {
    char buf[666];
    void (*fp)(char *);
} somedata;

int main() {
    somedata.fp = &greetUser;
    printf("Enter your name: ");
    fflush(0);
    gets(somedata.buf);
    char *ptr;
    for (ptr = somedata.buf; *ptr != '\0'; ptr++) {
        if (*ptr == '\x90') {
            printf("Hack detected!\n");
            exit(1);
        }
    }
    (somedata.fp)(somedata.buf);

    return 0;
}
