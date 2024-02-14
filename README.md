# 1D Ripleys analysis in QuPath
One dimensional Ripleys analysis of points on a line. This analysis looks at the interpoint distances and normalizes them for a random distribution. The resulting graph shows deviation from zero for clustered points. Edge correction is done by "connecting" the endpoints of the line. This imposes a symmetry on the the resulting ripley curve, because each set of two points has two paths to connect them. 

 ## Simulations
Simulations have been performed in python. 

Ripley curve when simulating 100 random points on a line of 10 μm long:
![100 random points](imgs/random.png?raw=true "Random")
The lines are around zero

Ripley curve when simulating 50 random points and 50 clustered points in a cluster at 5 μm with a standard deviation of 0.05 μm on a line of 10 μm long:
![50 random and 50 clustered points](imgs/clustered.png?raw=true "Clustered")

Figures have been made with [this jupyternotebook](src/python/RipleySimulation.ipynb).

## Groovy Script
Groovy script can be found [here](src/groovy/RipleyQuPath.groovy).

## References
Original paper for 2D point patterns: https://doi.org/10.2307/3212829 
