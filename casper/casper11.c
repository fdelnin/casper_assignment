#include <stdio.h>
#include <unistd.h>

int enableGlobalAdmin = 0;
int enableFullAdmin = 0;

struct role_t {
    char rolename[32];
    int isAdmin;
} defaultRole = { .rolename = "regular user", .isAdmin = 0 };

struct user_t {
    char name[666];
    struct role_t *role;
};

void inputUser(struct user_t *u) {
    printf("Enter your name: ");
    fflush(0);
    gets(u->name);
}

void greetUser(struct user_t *u) {
    printf("Hello %s, you have role '%s'\n", u->name, u->role->rolename);
}

int main(int argc, char **argv) {
    struct user_t thisUser;
    thisUser.role = &defaultRole;

    inputUser(&thisUser);
    greetUser(&thisUser);

    if((thisUser.role)->isAdmin == 1) {
	printf("Congratulations! You are an admin\n");
	setresuid(geteuid(), geteuid(), geteuid());
	execl("/bin/xh", "/bin/xh", NULL);
    }

    return 0;
}
