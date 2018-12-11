#include <stdio.h>
#include <unistd.h>

int isAdmin = 0;

void greetUser(char *s) {
    char buf[666];
    sprintf(buf, "Hello %s!\n", s);
    printf(buf);
}

int main(int argc, char **argv) {
    if(argc < 2) {
	printf("Usage: %s YourName\n", argv[0]);
	exit(1);
    }

    greetUser(argv[1]);

    if(isAdmin) {
	setresuid(geteuid(), geteuid(), geteuid());
	execl("/bin/xh", "/bin/xh", NULL);
    }

    return 0;
}
