#include <stdio.h>
#include <unistd.h>

void greetUser(char *s) {
    char buf[666];
    strcpy(buf, s);
    printf("Hello %s!\n", buf);
}

void clearEnvironment(char **envp) {
    char *var;
    while ((var = *envp++) != NULL) {
        while (*var != '\0') {
            *var++ = '\0';
        }
    }
}

int main(int argc, char **argv, char **envp) {
    if(argc < 2) {
	printf("Usage: %s YourName\n", argv[0]);
	exit(1);
    }

    clearEnvironment(envp);

    //setresuid(geteuid(), geteuid(), geteuid());
    greetUser(argv[1]);

    return 0;
}
