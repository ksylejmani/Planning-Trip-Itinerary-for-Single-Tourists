#include <iostream>
#include <string>
#include <stdio.h>
#include <stdlib.h>
using namespace std;
void KonvertoStringun(string str);
int main()
{
	string str;
	//float InputData[102][22];
	//float OutputData[102][26];
	//100*22=2200, 2220;
	float X[2220];
	

	str="1 48 994 4 10 3 6 4 2 4 6 4 6
		0 -10.442 19.999 0.0 0.0 0 1000
		2 -29.73 64.136 2.0 12.0 354 392.8 431.5 470.2 509 0 77 0 0 0 0 0 0 0 0 0 0
		3 -30.664 5.463 7.0 8.0 234 275.8 317.5 359.2 401 1 77 1 1 0 1 0 0 1 0 0 0
		4 51.642 5.469 21.0 16.0 411 451.5 492 532.5 573 1 42 0 0 1 0 0 1 1 1 0 1
		5 -13.171 69.336 24.0 5.0 474 511 548 585 622 1 70 0 0 0 0 0 1 0 0 0 0
		6 -67.413 68.323 1.0 12.0 155 190 225 260 295 1 75 1 0 0 0 0 0 0 0 0 1
		7 48.907 6.274 17.0 5.0 361 398 435 472 509 1 46 0 0 1 0 0 0 0 0 0 0
		8 5.243 22.26 6.0 13.0 451 495.5 540 584.5 629 0 50 0 1 1 1 0 0 0 0 1 0
		9 -65.002 77.234 5.0 20.0 425 465.8 506.5 547.2 588 1 27 0 0 0 0 0 0 0 1 0 0
		10 -4.175 -1.569 7.0 13.0 72 103.8 135.5 167.2 199 1 62 0 0 0 0 0 0 0 0 0 0
		11 23.029 11.639 1.0 18.0 157 197.2 237.5 277.8 318 0 79 0 1 1 1 0 0 0 1 0 0
		12 25.482 6.287 4.0 7.0 296 333 370 407 444 1 30 0 0 0 1 0 0 1 1 0 1
		13 -42.615 -26.392 10.0 6.0 111 145.5 180 214.5 249 0 4 0 0 0 1 1 0 0 0 0 0
		14 -76.672 99.341 2.0 9.0 368 408 448 488 528 1 9 0 0 0 0 0 1 0 0 1 1
		15 -20.673 57.892 16.0 9.0 98 136.2 174.5 212.8 251 1 25 0 0 0 1 0 0 0 0 0 0
		16 -52.039 6.567 23.0 4.0 96 124 152 180 208 1 25 0 0 1 0 1 1 1 1 1 0
		17 -41.376 50.824 18.0 25.0 382 420.2 458.5 496.8 535 0 83 0 1 0 0 1 0 0 1 0 1
		18 -91.943 27.588 3.0 5.0 436 472 508 544 580 1 10 0 0 1 0 0 0 0 0 0 1
		19 -65.118 30.212 15.0 17.0 405 435.8 466.5 497.2 528 1 67 0 1 0 0 0 0 0 0 0 0
		20 18.597 96.716 13.0 3.0 255 294.8 334.5 374.2 414 1 65 0 0 0 0 0 0 0 0 0 1
		21 -40.942 83.209 10.0 16.0 293 337.2 381.5 425.8 470 1 85 1 0 0 0 0 0 1 0 0 0
		22 -37.756 -33.325 4.0 25.0 298 325.5 353 380.5 408 1 41 0 0 0 0 0 0 1 0 0 0
		23 23.767 29.083 23.0 21.0 479 511 543 575 607 0 13 0 0 0 1 0 0 0 0 0 1
		24 -43.03 20.453 20.0 14.0 376 416 456 496 536 1 13 1 0 0 0 0 1 0 0 0 0
		25 -35.297 -24.896 10.0 19.0 91 127.8 164.5 201.2 238 1 44 0 0 0 0 0 0 0 0 1 0
		26 -54.755 14.368 4.0 14.0 360 396.2 432.5 468.8 505 1 7 0 1 0 0 1 1 0 0 0 0
		27 -49.329 33.374 2.0 6.0 379 416.2 453.5 490.8 528 1 35 1 0 0 1 0 1 0 0 0 0
		28 57.404 23.822 23.0 16.0 258 300.5 343 385.5 428 1 35 1 0 0 1 0 0 0 0 0 0
		29 -22.754 55.408 6.0 9.0 352 391.2 430.5 469.8 509 0 42 0 1 0 0 0 0 1 0 1 0
		30 -56.622 73.34 8.0 20.0 288 311 334 357 380 1 58 0 0 0 1 0 0 0 0 0 1
		31 -38.562 -3.705 10.0 13.0 159 200.2 241.5 282.8 324 0 39 0 0 0 0 0 1 0 0 0 1
		32 -16.779 19.537 7.0 10.0 423 458.2 493.5 528.8 564 1 35 0 1 0 0 0 0 0 1 0 1
		33 -11.56 11.615 1.0 16.0 238 275.2 312.5 349.8 387 1 14 0 1 0 0 0 1 0 0 0 0
		34 -46.545 97.974 21.0 19.0 339 361.5 384 406.5 429 1 69 0 1 1 0 0 0 0 1 0 1
		35 16.229 9.32 6.0 22.0 397 440.8 484.5 528.2 572 0 12 0 1 0 0 0 0 0 0 1 0
		36 1.294 7.349 4.0 14.0 479 509 539 569 599 1 59 0 0 0 0 1 0 0 0 0 1
		37 -26.404 29.529 13.0 10.0 315 359.2 403.5 447.8 492 0 17 1 0 0 0 0 0 0 1 0 0
		38 4.352 14.685 9.0 11.0 132 171.5 211 250.5 290 0 84 1 0 0 0 0 0 1 1 0 0
		39 -50.665 -23.126 22.0 15.0 161 202.2 243.5 284.8 326 1 88 0 1 0 0 0 0 0 0 0 0
		40 -22.833 -9.814 22.0 13.0 387 417.2 447.5 477.8 508 1 63 0 0 0 0 0 1 1 0 0 1
		41 -71.1 -18.616 18.0 15.0 284 324.5 365 405.5 446 1 76 0 1 0 0 0 1 0 1 0 0
		42 -7.849 32.074 10.0 8.0 296 339.8 383.5 427.2 471 1 34 1 0 1 1 0 0 0 0 0 0
		43 11.877 -24.933 25.0 22.0 381 406.2 431.5 456.8 482 1 87 0 0 0 1 0 0 0 0 1 0
		44 -18.927 -23.73 23.0 24.0 401 431.2 461.5 491.8 522 1 62 1 0 0 0 0 1 0 0 0 0
		45 -11.92 11.755 4.0 3.0 432 465 498 531 564 1 85 0 0 0 0 0 1 0 0 0 0
		46 29.84 11.633 9.0 25.0 289 329.2 369.5 409.8 450 1 85 0 0 0 0 0 0 0 0 0 0
		47 12.268 -55.811 17.0 19.0 451 487.5 524 560.5 597 1 0 0 0 1 0 0 0 1 1 0 0
		48 -37.933 -21.613 10.0 21.0 123 167.8 212.5 257.2 302 1 0 1 1 1 0 1 0 0 0 0 0
		49 42.883 -2.966 17.0 10.0 98 131.8 165.5 199.2 233 1 76 0 1 0 0 0 1 1 0 0 0
";
	//3+10+7+22
	/*int i=0;
	for(i=0;i<85;i+=2)*/
	//lexoStringun(str,X);
	KonvertoStringun(str);

	/*for(int i=0;i<2;i++)
	{
		for(int j=0;j<22;j++)
		{
			
			cout<<str.at(i+j)<<endl;


		}
	}*/


	system("Pause");
	return 0;
}

void KonvertoStringun(string str)
{
	int a,b,k=0;
	string temp;
	b=10;
	string s;
	for(int i=0;i<str.length();i++)
	{
		if(str.at(i)!=' ')
		{
			s=str.at(i);
			temp.append(s);
		}
		else
		{
			k++;
			a = atoi(temp.c_str());
			temp.clear();
			cout<<k<<". a*b="<<a*b<<endl;
	    }

}
}