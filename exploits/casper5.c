#include <stdio.h>
#include <unistd.h>

void greetUser() {
    char buf[666];
    printf("Enter your name: ");
    fflush(0);
    gets(buf);
    printf("Hello %s!\n", buf);
}

int main() {
    greetUser();
    return 0;
}
