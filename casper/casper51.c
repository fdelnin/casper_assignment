#include <stdio.h>
#include <unistd.h>

void greetUser() {
    char buf[666];
    printf("Enter your name: ");
    fflush(0);
    gets(buf);
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

    clearEnvironment(envp);

    greetUser();

    return 0;
}
