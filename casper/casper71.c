#include <stdio.h>
#include <unistd.h>

void greetUser(char *s) {
    printf("Hello %s!\n", s);
}

struct data_t {
    char buf[666];
    void (*fp)(char *);
} somedata;

void clearEnvironment(char **envp) {
    char *var;
    while ((var = *envp++) != NULL) {
        while (*var != '\0') {
            *var++ = '\0';
        }
    }
}

int main(int argc, char **argv, char **envp) {
    clearEnvironment(envp);
    somedata.fp = &greetUser;
    printf("Enter your name: ");
    fflush(0);
    gets(somedata.buf);
    (somedata.fp)(somedata.buf);

    return 0;
}
