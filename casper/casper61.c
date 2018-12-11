#include <stdio.h>
#include <unistd.h>

void greetUser(char *s) {
    printf("Hello %s!\n", s);
}

void clearEnvironment(char **envp) {
    char *var;
    while ((var = *envp++) != NULL) {
        while (*var != '\0') {
            *var++ = '\0';
        }
    }
}

struct data_t {
    char buf[666];
    void (*fp)(char *);
} somedata;

int main(int argc, char **argv, char **envp) {
    somedata.fp = &greetUser;

    if(argc < 2) {
	printf("Usage: %s YourName\n", argv[0]);
	exit(1);
    }

    clearEnvironment(envp);

    strcpy(somedata.buf, argv[1]);
    (somedata.fp)(somedata.buf);

    return 0;
}
