
//Equipe: Caio Telles, Felipe Santos, Guilherme Céspedes
//Referência: https://www.youtube.com/watch?v=6PH-M7qyRLo
//Problema do Barbeiro Dorminhoco utilizando semáforos


#include <cstdio>
#include <cstdlib>
#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>

int n_clientes = 15;

sem_t sem_cadeira; //Semaforo cadeiras livres
sem_t sem_cadeira_atendimento; //Semaforo cadeira do barbeiro
sem_t sem_cabelo_cortado; //semaforo para cabelo cortado ou nao

pthread_t barbeiro; //thread para cabelo
pthread_t cliente[15];//thread para clientes
int id[15];

void * vida_cliente (void *var)
{
	int id = *(int *) var;
	usleep(id*2);

	if(sem_trywait(&sem_cadeira) == 0)//ocupa cadeira
	{
		printf("Há cadeiras para o cliente %d. \n", id);
		sem_wait(&sem_cadeira_atendimento); //aguarda barbeiro
		sem_post(&sem_cadeira);
		sem_wait(&sem_cabelo_cortado);
		printf("O cliente %d cortou o cabelo. \n \n", id);
		sem_post(&sem_cadeira);
		sem_post(&sem_cadeira_atendimento);
	}
	else
	{
		printf("Não havia cadeiras para o cliente %d.\n\n", id);
		pthread_exit(NULL);
	}
}

void *vida_barbeiro (void *var)
{
	usleep(10);
	while(1)
	{
		sem_wait(&sem_cadeira);
		printf("Barbeiro atendendo cliente...\n");
		usleep(500);
		sem_post(&sem_cabelo_cortado);
	}
	pthread_exit(NULL);

}

int main()
{
	sem_init(&sem_cadeira, 0, 3);
	sem_init(&sem_cadeira_atendimento, 0, 1);
	sem_init(&sem_cabelo_cortado, 0, 0);
	int j = 0;
	
	pthread_create(&barbeiro, NULL, &vida_barbeiro, (void*) &id[j]);//cria t
	for(j = 0; j < n_clientes; j++)
	{
		id[j] = j;
		pthread_create(&cliente[j], NULL, &vida_cliente, (void*) &id[j]); ///cria threads
		usleep(rand()%100); //tempo para ver outro cliente
	}

	for(int i = 0; i < n_clientes; i++)
	{
		pthread_join(cliente[i], NULL);
	}

	printf("Acabou o expediente e barbeiro foi para casa dormir\n");

	return 0;	
}
