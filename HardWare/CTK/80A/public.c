
#include "public.h"


/*软件延迟函数，x约为50us.根据PIC的具体情况设定 */
void CTKSoftDelay(uint x){
    uint a,b;
    for(a=x;a>0;a--)
        for(b=10;b>0;b--);
}

