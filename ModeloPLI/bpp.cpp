/*
******************************************************************
** Autores: Felipe de Carvalho Pereira e Jônatas Trauco Belotti **
** Instututo de Computação - Unicamp                            **
******************************************************************
*/

#include <iostream>
#include <cmath>
#include <cstdlib>
#include <cstring>
#include <string>
#include <queue>
#include <time.h>
#include <sys/resource.h>
#include "gurobi_c++.h"


// Macro for getting runtime execution
// this macro produces a time equal to the one produced by clock(),
// but does not suffer from the wraparound problem of clock()
extern int getrusage();
#define CPUTIME(ruse) (getrusage(RUSAGE_SELF,&ruse),ruse.ru_utime.tv_sec + ruse.ru_stime.tv_sec + 1e-6 * (ruse.ru_utime.tv_usec + ruse.ru_stime.tv_usec))
struct rusage grb_ruse; // Global variable used on time counter

using namespace std;

// Load number of items and capacity
void load_N_C(FILE* input_file, int *N, int *C)
{
	fscanf(input_file, "%d", N);
	fscanf(input_file, "%d", C);
}

// Load the edges and degrees
void load_weights(FILE* input_file, int N, int *weights)
{
	for(int i = 0; i < N; i++)
	{
		int weight;
		fscanf(input_file, "%d", &weight);
		weights[i] = weight;
	}
}

int main (int argc, char *argv[])
{
	int N, C;

	string input_path;
	FILE *input_file;
	input_path = argv[1];
	input_file = fopen(input_path.c_str(), "r");


	load_N_C(input_file, &N, &C);
	int weights[N];

	load_weights(input_file, N, weights);
	
	double beg_time_opt = CPUTIME(grb_ruse);
	double end_time_opt;

	cout << endl << endl << input_path << endl << endl;

	// Gurobi environment
	GRBEnv* env = 0;

	try
	{

		// Gurobi Model
		env = new GRBEnv();
		GRBModel model = GRBModel(*env);
		model.set(GRB_StringAttr_ModelName, "bpp");

		// Gurobi Parameters
		//model.set(GRB_IntParam_OutputFlag, 0); // Disable printing on console
		//model.set(GRB_IntParam_Presolve, 0); // Disable presolve algorithms
		//model.set(GRB_DoubleParam_Heuristics, 0.0); // Disable use of heuristics
		model.set(GRB_IntParam_Threads, 1); // Define use of only one core
		model.set(GRB_DoubleParam_TimeLimit, 600); // Define timelimit of 600s

		// VARIABLES

		// Variables for setting a item to a bin
		// Bins decision variables: bins[i] == 1 if bin i is usded, equals 0 if not
		GRBVar* bins;
		bins = model.addVars(N, GRB_BINARY);

		// Variables for chosing the bins to be opened
		// Decision variables of association between a item and a bin
		// If a item j is allocated to a bin i so bins_items[i][j] = 1, equals 0 if not
		GRBVar* bins_items[N];
		for(int i = 0; i < N; i++)
			bins_items[i] = model.addVars(N, GRB_BINARY);

		// OBJECTIVE 

		// Objective Funcion
		GRBLinExpr obj_expr = 0;
		for(int i = 0; i < N; i++)
			obj_expr += (bins[i]);
		model.setObjective(obj_expr, GRB_MINIMIZE);

		// CONSTRAINTS

		// Capacity constraints
		GRBLinExpr capac_expr[N]; // Expression that represents the cost function
		for(int i = 0; i < N; i++)
		{
			capac_expr[i] = 0;
			for(int j = 0; j < N; j++)
				capac_expr[i] += (weights[j] * bins_items[i][j]);
			model.addConstr(capac_expr[i], GRB_LESS_EQUAL, C * bins[i]);
		}

		// Allocations constraints
		GRBLinExpr alloc_expr[N];
		for(int j = 0; j < N; j++)
		{
			alloc_expr[j] = 0;
			for(int i = 0; i < N; i++)
				alloc_expr[j] += (bins_items[i][j]);
			model.addConstr(alloc_expr[j], GRB_EQUAL, 1);
		}

		
		// Bins index constraints
		for(int i = 0; i < N - 1; i++)
		{
			model.addConstr(bins[i + 1], GRB_LESS_EQUAL, bins[i]);
		}
		
		// Update model to set variables and constraints
		model.update();

		// Optimizing objective function
		printf("Optimizing Gurobi model...\n");
		model.optimize();
		end_time_opt = CPUTIME(grb_ruse); // Final runtime
		//model.write("model.sol");

		// Deleting variables
		delete[] bins;
		for(int i = 0; i < N; i++)
			delete[] bins_items[i];

	}
		catch (GRBException e)
	{
		cout << "Error code = " << e.getErrorCode() << endl;
		cout << e.getMessage() << endl;
	}
		catch (...)
	{
		cout << "Exception during optimization" << endl;
	}

	delete env;

	return 0;
}