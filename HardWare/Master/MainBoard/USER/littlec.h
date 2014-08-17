#ifndef _littlec_h_
#define _littlec_h_
int RunInterpretProgram() ;
int get_token(void);
void eval_exp0(int *value); 
void eval_exp(int *value); 
void eval_exp1(int *value); 
void eval_exp2(int *value); 
void eval_exp3(int *value); 
void eval_exp4(int *value); 
void eval_exp5(int *value); 
void atom(int *value); 
void sntx_err(int error), putback(void); 
void assign_var(char *var_name, int value); 
int isdelim(char c), look_up(char *s), iswhite(char c);
int find_var(char *s), get_token(void); 
int internal_func(char *s); 
int is_var(char *s); 
char *find_func(char *name); 
void call(void);
int call_getch() ;
int call_putch() ;
int call_puts(void) ;
int print(void) ;
int getnum(void) ;
#endif