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
    (somedata.fp)(somedata.buf);

    return 0;
}
