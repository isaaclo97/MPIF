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
@article{LozanoOsorio24,
	author = {Lozano-Osorio, I. and Sánchez-Oro, J. and López-Sánchez, A.D. and Duarte, A.},
	title = {A variable neighborhood search for the median location problem with interconnected facilities},
	journal = {International Transactions in Operational Research},
	volume = {n/a},
	number = {n/a},
	pages = {},
	keywords = {facility location, p$p$-median problem, interconnected facilities, variable neighborhood search},
	doi = {https://doi.org/10.1111/itor.13468},
	url = {https://onlinelibrary.wiley.com/doi/abs/10.1111/itor.13468},
	eprint = {https://onlinelibrary.wiley.com/doi/pdf/10.1111/itor.13468},
	abstract = {Abstract The p\$p\$-median problem has been widely studied in the literature. However, there are several variants that include new constraints to the classical problem that make it more realistic. In this work, we study the variant that considers interconnected facilities, that is, the distances between each pair of facilities are less than or equal to a certain threshold r\$r\$. This optimization problem, usually known as the median location problem with interconnected facilities, consists of locating a set of interconnected facilities to minimize the distance between those interconnected facilities and the demand points. The large variety of real-world applications that fit into this model makes it attractive to design an algorithm able to solve the problem efficiently. To this end, a procedure based on the variable neighborhood search methodology is designed and implemented by using problem-dependent neighborhoods. Experimental results show that our proposal is able to reach most of the optimal solutions when they are known. Additionally, it outperforms previous state-of-the-art methods in those instances where the optima are unknown. These results are further confirmed by conducting nonparametrical statistical tests.}
}
```

RIS
```
TY  - JOUR
T1  - A variable neighborhood search for the median location problem with interconnected facilities
AU  - Lozano-Osorio, I.
AU  - Sánchez-Oro, J.
AU  - López-Sánchez, A.D.
AU  - Duarte, A.
Y1  - 2024/04/24
PY  - 2024
DA  - 2024/04/24
DO  - https://doi.org/10.1111/itor.13468
T2  - International Transactions in Operational Research
JF  - International Transactions in Operational Research
JO  - International Transactions in Operational Research
JA  - Intl. Trans. in Op. Res.
VL  - n/a
IS  - n/a
KW  - facility location
KW  - p$p$-median problem
KW  - interconnected facilities
KW  - variable neighborhood search
PB  - John Wiley & Sons, Ltd
SN  - 0969-6016
UR  - https://doi.org/10.1111/itor.13468
Y2  - 2024/04/25
N2  - Abstract The p$p$-median problem has been widely studied in the literature. However, there are several variants that include new constraints to the classical problem that make it more realistic. In this work, we study the variant that considers interconnected facilities, that is, the distances between each pair of facilities are less than or equal to a certain threshold r$r$. This optimization problem, usually known as the median location problem with interconnected facilities, consists of locating a set of interconnected facilities to minimize the distance between those interconnected facilities and the demand points. The large variety of real-world applications that fit into this model makes it attractive to design an algorithm able to solve the problem efficiently. To this end, a procedure based on the variable neighborhood search methodology is designed and implemented by using problem-dependent neighborhoods. Experimental results show that our proposal is able to reach most of the optimal solutions when they are known. Additionally, it outperforms previous state-of-the-art methods in those instances where the optima are unknown. These results are further confirmed by conducting nonparametrical statistical tests.
ER  - 
```

AMA Style
```
Lozano-Osorio I, Sánchez-Oro J, López-Sánchez AD, Duarte A. A variable neighborhood search for the median location problem with interconnected facilities. International Transactions in Operational Research. doi:10.1111/itor.13468
```

Chicago/Turabian Style
```
Lozano-Osorio, I., J. Sánchez-Oro, A. D. López-Sánchez, and A. Duarte. ‘A Variable Neighborhood Search for the Median Location Problem with Interconnected Facilities’. International Transactions in Operational Research. https://doi.org/10.1111/itor.13468.
```
