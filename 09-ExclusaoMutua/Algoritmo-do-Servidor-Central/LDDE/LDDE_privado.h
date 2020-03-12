
#include "LDDE_publico.h"

typedef struct LDDE{
    struct NoLDDE *inicio;		// ponteiro para o primeiro n� da lista
	struct NoLDDE *fim;			// ponteiro para o �ltimo n� da lista
    int tamanho_info;           // tamanho da informa��o contida nos n�s
}LDDE;

typedef struct NoLDDE{
    void *dados;        		// ponteiro para os dados do n�
    struct NoLDDE *prox;		// ponteiro para o pr�ximo elemento
	struct NoLDDE *ant;			// ponteiro para o elemento anterior
}NoLDDE;
