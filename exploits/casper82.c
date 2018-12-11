#include <stdio.h>
#include <unistd.h>

#define BUFSIZE 666

void greetUser(char *s) {
    char buf[BUFSIZE];
    strcpy(buf, s);

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

int main(int argc, char **argv) {
    if(argc < 2) {
	printf("Usage: %s YourName\n", argv[0]);
	exit(1);
    }

    //setresuid(geteuid(), geteuid(), geteuid());
    greetUser(argv[1]);

    return 0;
}
