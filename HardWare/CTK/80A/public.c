
#include "public.h"


/*����ӳٺ�����xԼΪ50us.����PIC�ľ�������趨 */
void CTKSoftDelay(uint x){
    uint a,b;
    for(a=x;a>0;a--)
        for(b=10;b>0;b--);
}

