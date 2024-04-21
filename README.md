![Manintained](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![GitHub last commit (master)](https://img.shields.io/github/last-commit/isaaclo97/MPIF)
![Starts](https://img.shields.io/github/stars/isaaclo97/MPIF.svg)

# A Variable Neighborhood Search for the Median Location Problem with Interconnected Facilities

The p-Median problem has been widely studied in the literature. However, there are several variants that include new constraints to the classical problem that make it more realistic. In this work, we study the variant that considers interconnected facilities, i.e., the distances between each pair of facilities is less than or equal to a certain threshold r. This optimization problem, usually known as Median Location Problem with Interconnected Facilities (MPIF), consists in locating a set of interconnected facilities to minimize the distance between those interconnected facilities and the demand points. The large variety of real-world applications that fit into this model makes it attractive to design an algorithm able to solve the problem efficiently. To this end, a procedure based on the Variable Neighborhood Search methodology is designed and implemented by using problem-dependent neighborhoods. Experimental results show that our proposal is able to reach most of the optimal solutions when they are known. Additionally, it outperforms previous state-of-the-art methods in those instances where the optima are unknown. These results are further confirmed by conducting non-parametrical statistical tests.

- Journal: International Transactions in Operational Research
- Impact Factor: 3.1 
- Paper link: https://doi.org/10.1111/itor.13468
- Area: Operations Research & Management Science
- Quartil: Q2 - 32/86

## Datasets

* [pmed](./instances/pmed/)
* [kmedian](./instances/kmedian/)


All txt format instances can be found in instances folder.

## Repository folders

- code:source code of the proposal.
- instances: Within it, there are 2 folders, one with the pmed instances (120) and the other with the kmedian instances (108).
- results.xlsx: A file containing 3 sheets, the first for the exact values, the second for the pmed instances, and the third for the kmedian instances. Each sheet has per instance the objective value, computational time, and comparison with the previous algorithm Cherkesly et al., showing the deviation and number of best solutions.
- selected.xlsx: Shows for the pmed and kmedian instances the nodes chosen by our proposal and Cherkesly et al.
- MPIF.jar: It is the final executable of the application; further information is provided below.

## Executable

You can just run the MPIF.jar as follows (the algorithm will select manuscript parameters).

```
java -jar MPIF.jar ./instances/pmed/
```

If you want to customice the execution the algorithm can recieve 3 parameters.
1. Instances directory (expected in txt and default it is ../instances/pmed)
2. Iterations (default it is 100) [1,...,N]
3. Kmax as double number (default it is 0.2) 

```
java -jar MPIF.jar pathInstances iterations kmax
```

Once you run the algorithm, the screen will show you the metrics and status. You can also found a folder named as experiments where an excel will generated when the experiment end.


## Cite

Please cite our paper if you use it in your own work:

Bibtext
```
```

MDPI and ACS Style
```
```

AMA Style
```
```

Chicago/Turabian Style
```
```
