#include <stdio.h>
#include <unistd.h>

void greetUser(char *s) {
    char buf[666];
    strcpy(buf, s);
    printf("Hello %s!\n", buf);
}

int main(int argc, char **argv) {
    if(argc < 2) {
	printf("Usage: %s YourName\n", argv[0]);
	exit(1);
    }

    greetUser(argv[1]);

    return 0;
}
