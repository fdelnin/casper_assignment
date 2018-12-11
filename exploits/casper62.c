#include <stdio.h>
#include <unistd.h>

#define BUFSIZE 666

void greetUser(char *s) {
    printf("Hello %s!\n", s);
}

struct data_t {
    char buf[BUFSIZE];
    void (*fp)(char *);
} somedata;

int main(int argc, char **argv) {
    somedata.fp = &greetUser;

    if(argc < 2) {
	printf("Usage: %s YourName\n", argv[0]);
	exit(1);
    }

    int i;
    for (i = 0; i < BUFSIZE; i++) {
        if (somedata.buf[i] == '\0') {
            break;
        }
        if (!isascii(somedata.buf[i])) {
            printf("Hack detected!\n");
            exit(1);
        }
    }
    strcpy(somedata.buf, argv[1]);
    (somedata.fp)(somedata.buf);

    return 0;
}
