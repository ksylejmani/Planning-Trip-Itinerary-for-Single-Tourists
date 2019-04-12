#include <iostream> 
#include <ctime> 
#include <cstdlib>
#include <ctime>
using namespace std;
int getRandomNumber(int highest);
int main() 
{ 
    //srand((unsigned)time(0)); 
    //int random_integer; 
    //int lowest=1, highest=50; 
    //int range=(highest-lowest)+1; 
    //for(int index=0; index<20; index++){ 
    //    random_integer = lowest+int(range*rand()/(RAND_MAX + 1.0)); 
    //    cout << random_integer << endl; 
    //} 

	cout<<"Numri i rastit:"<<getRandomNumber(50)<<endl;
}
int getRandomNumber(int highest)
{
    srand(time(NULL)); 
	rand();
    int random_integer; 
    int lowest=1;
    int range=(highest-lowest)+1; 
        random_integer = lowest+int(range*rand()/(RAND_MAX + 1.0)); 
        cout << random_integer << endl; 
	return random_integer;
}