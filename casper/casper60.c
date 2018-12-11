#include <stdio.h>
#include <unistd.h>

void greetUser(char *s) {
    printf("Hello %s!\n", s);
}

struct data_t {
    char buf[666];
    void (*fp)(char *);
} somedata;

int main(int argc, char **argv) {
    somedata.fp = &greetUser;

    if(argc < 2) {
	printf("Usage: %s YourName\n", argv[0]);
	exit(1);
    }

    char *ptr;
    for (ptr = argv[1]; *ptr != '\0'; ptr++) {
        if (*ptr == '\x90') {
            printf("Hack detected!\n");
            exit(1);
        }
    }
    strcpy(somedata.buf, argv[1]);
    (somedata.fp)(somedata.buf);

    return 0;
}
