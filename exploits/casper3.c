#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>

int main()
{
        char pin[4];
	char input[5]; // space for null byte
        int i;
        int correct;

        srand(time(0));

	for (i = 0; i < 4; i++) {
		pin[i] = rand() % 10 + '0';
	}

	printf("Enter pincode: ");
	scanf("%s", input);	

        correct = 1;
	for (i = 0; i < 4 && correct; i++) {
		if (pin[i] != input[i]) {
			printf("Pin incorrect\n");
                        correct = 0;
		}
	}
	
        if (correct) {
            setresuid(geteuid(), geteuid(), geteuid());
            execl("/bin/xh", "/bin/xh", NULL);
        }
	
	return 0;
}
